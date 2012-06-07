package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.google.gson.stream.JsonWriter;

public class BookmarksExcludeFilter implements Filter {
	private static final String NAME = "BookmarksExclude";
	
	protected final int[] bookmarkListIds;
	protected final boolean excludeIgnoreList;
	
	public BookmarksExcludeFilter(boolean excludeIgnoreList, int... bookmarkListIds) {
		this.excludeIgnoreList = excludeIgnoreList;
		this.bookmarkListIds = bookmarkListIds;
	}
	
	public boolean isValid() {
		if (bookmarkListIds == null || bookmarkListIds.length == 0)
			return false;
				
		return true;
	}
	
	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		w.name("BookmarkListIDs");
		w.beginArray();
		for (int bookmarkListId : bookmarkListIds) {
			w.value(bookmarkListId);
		}
		w.endArray();
		w.name("ExcludeIgnoreList").value(excludeIgnoreList);
		w.endObject(); 
	}
	
	public String getName() {
		return NAME;
	}
}
