package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BookmarksExcludeFilter implements Filter {
    private static final String NAME = "BookmarksExclude";

    private final int[] bookmarkListIds;
    private final Boolean excludeIgnoreList;

    public BookmarksExcludeFilter(Boolean excludeIgnoreList, int... bookmarkListIds) {
        this.excludeIgnoreList = excludeIgnoreList;
        this.bookmarkListIds = bookmarkListIds;
    }

    public boolean isValid() {
        return !((bookmarkListIds == null || bookmarkListIds.length == 0) && excludeIgnoreList == null);

    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);

        w.beginObject();
        if (bookmarkListIds != null && bookmarkListIds.length > 0) {
            w.name("BookmarkListIDs");
            w.beginArray();
            for (int bookmarkListId : bookmarkListIds) {
                w.value(bookmarkListId);
            }
            w.endArray();
        }

        if (excludeIgnoreList != null)
            w.name("ExcludeIgnoreList").value(excludeIgnoreList);

        w.endObject();
    }

    public String getName() {
        return NAME;
    }
}
