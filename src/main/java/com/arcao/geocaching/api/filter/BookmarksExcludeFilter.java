package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class BookmarksExcludeFilter implements Filter {
    private static final String NAME = "BookmarksExclude";

    @NotNull private final int[] bookmarkListIds;
    @Nullable private final Boolean excludeIgnoreList;

    public BookmarksExcludeFilter(@Nullable Boolean excludeIgnoreList, @NotNull int... bookmarkListIds) {
        this.excludeIgnoreList = excludeIgnoreList;
        this.bookmarkListIds = bookmarkListIds.clone();
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return bookmarkListIds.length > 0 || excludeIgnoreList != null;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);

        w.beginObject();
        if (bookmarkListIds.length > 0) {
            w.name("BookmarkListIDs");
            w.beginArray();
            for (int bookmarkListId : bookmarkListIds) {
                w.value(bookmarkListId);
            }
            w.endArray();
        }

        if (excludeIgnoreList != null) {
            w.name("ExcludeIgnoreList").value(excludeIgnoreList);
        }

        w.endObject();
    }
}
