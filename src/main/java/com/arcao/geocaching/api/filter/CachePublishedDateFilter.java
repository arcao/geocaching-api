package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.builder.JsonBuilder;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Date;

public class CachePublishedDateFilter implements Filter {
    private static final String NAME = "CachePublishedDate";

    @Nullable private final Date startDate;
    @Nullable private final Date endDate;

    public CachePublishedDateFilter(@Nullable Date startDate, @Nullable Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return startDate != null || endDate != null;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("Range");
        w.beginObject();
        if (startDate != null)
            w.name("StartDate").value(JsonBuilder.dateToJsonString(startDate));
        if (endDate != null)
            w.name("EndDate").value(JsonBuilder.dateToJsonString(endDate));
        w.endObject();
        w.endObject();
    }
}
