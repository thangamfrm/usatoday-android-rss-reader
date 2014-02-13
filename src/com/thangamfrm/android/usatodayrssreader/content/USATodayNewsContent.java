package com.thangamfrm.android.usatodayrssreader.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.rss.Channel;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class USATodayNewsContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		addItem(new DummyItem("1", "Home", null));
		addItem(new DummyItem("2", "News", null));
		addItem(new DummyItem("3", "Travel", null));
		addItem(new DummyItem("4", "Money", null));
		addItem(new DummyItem("5", "Sports", null));
		addItem(new DummyItem("6", "Life", null));
		addItem(new DummyItem("7", "Tech", null));
		addItem(new DummyItem("8", "Weather", null));
		addItem(new DummyItem("9", "Nation", null));
		addItem(new DummyItem("10", "World", null));
		addItem(new DummyItem("11", "Opinion", null));
		addItem(new DummyItem("12", "Health", null));
		addItem(new DummyItem("13", "HighSchool", null));
		addItem(new DummyItem("14", "People", null));
		addItem(new DummyItem("15", "Books", null));
		addItem(new DummyItem("16", "Movies", null));
		addItem(new DummyItem("17", "Music", null));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	public static String getSection(String id) {
		return ITEM_MAP.get(id).content.toLowerCase();
	}

	public static Channel getChannel(String id) {
		return ITEM_MAP.get(id).channel;
	}

	public static void setChannel(String id, Channel channel) {
		ITEM_MAP.get(id).channel = channel;
	}

	
	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		
		public String id;
		public String content;
		public Channel channel;

		public DummyItem(String id, String content, Channel channel) {
			this.id = id;
			this.content = content;
			this.channel = channel;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
