package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.builder.JsonBuilder;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Date;

abstract class AbstractDateRangeFilter implements Filter {
    @Nullable private final Date startDate;
    @Nullable private final Date endDate;

    public AbstractDateRangeFilter(@Nullable Date startDate, @Nullable Date endDate) {
        this.startDate = (Date) (startDate != null ? startDate.clone() : null);
        this.endDate = (Date) (endDate != null ? endDate.clone() : null);
    }

    @Override
    public boolean valid() {
        return startDate != null || endDate != null;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(name());
        w.beginObject();
        w.name("Range");
        w.beginObject();
        if (startDate != null) {
            w.name("StartDate").value(JsonBuilder.dateToJsonString(startDate));
        }
        if (endDate != null) {
            w.name("EndDate").value(JsonBuilder.dateToJsonString(endDate));
        }
        w.endObject();
        w.endObject();
    }

}
