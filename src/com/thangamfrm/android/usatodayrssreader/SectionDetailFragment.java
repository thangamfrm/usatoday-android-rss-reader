package com.thangamfrm.android.usatodayrssreader;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Item;
import com.thangamfrm.android.usatodayrssreader.content.USATodayNewsContent;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class SectionDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private USATodayNewsContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SectionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = USATodayNewsContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			@SuppressWarnings("unchecked")
			List<Item> items = mItem.channel.getItems();
			String[] data = new String[items.size()];
			int index = 0;
			for (Item item : items) {
				data[index] = item.getTitle();
				index++;
			}
			
			ListView lv = ((ListView) getActivity().findViewById(R.id.listView1));
			ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(getActivity(), 
					android.R.layout.simple_list_item_1, data);
			lv.setAdapter(arrAdapter);
			
			getActivity().setTitle(mItem.content);
		}

		return rootView;
	}
}
