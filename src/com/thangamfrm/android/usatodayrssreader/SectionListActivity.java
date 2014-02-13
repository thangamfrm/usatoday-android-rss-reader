package com.thangamfrm.android.usatodayrssreader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Channel;
import com.thangamfrm.android.usatodayrssreader.content.USATodayNewsContent;


/**
 * An activity representing a list of Sections. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link SectionDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SectionListFragment} and the item details (if present) is a
 * {@link SectionDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link SectionListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class SectionListActivity extends FragmentActivity implements SectionListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	private ProgressDialog progressDialog;

	private boolean destroyed = false;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_list);

		if (findViewById(R.id.section_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((SectionListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.section_list))
					.setActivateOnItemClick(true);
		}
		setTitle("USAToday RSS Reader");
		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link SectionListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		new FetchSecuredResourceTask().execute(id);
	}

	public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(CharSequence message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}

		progressDialog.setMessage(message);
		progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (progressDialog != null && !destroyed) {
			progressDialog.dismiss();
		}
	}
	
	private void displayResponse(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(SectionDetailFragment.ARG_ITEM_ID, id);
			SectionDetailFragment fragment = new SectionDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.section_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, SectionDetailActivity.class);
			detailIntent.putExtra(SectionDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	private class FetchSecuredResourceTask extends AsyncTask<String, Void, Channel> {

		private String currentSelectionId = "";

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected Channel doInBackground(String... params) {

			currentSelectionId = params[0];
			String section = USATodayNewsContent.getSection(params[0]);
			RestTemplate restTemplate = new RestTemplate();

			String url = "http://api.usatoday.com/open/articles?section={section}&api_key={key}";
			Map<String, String> vars = new HashMap<String, String>();
			vars.put("section", section);
			vars.put("key", "<<YOUR_USAToday_API_Key>>");

			RssChannelHttpMessageConverter rssChannelConverter = new RssChannelHttpMessageConverter();
	        rssChannelConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_XML));
			restTemplate.getMessageConverters().add(rssChannelConverter);
			
			Channel channel = restTemplate.getForObject(url, Channel.class, vars);

			return channel;
		}

		@Override
		protected void onPostExecute(Channel channel) {
			dismissProgressDialog();
			USATodayNewsContent.setChannel(currentSelectionId, channel);
			displayResponse(currentSelectionId);
		}
	
	}
}
