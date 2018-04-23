package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TrackableCountFilter implements Filter {
    private static final String NAME = "TrackableCount";

    private final Integer min;
    private final Integer max;

    public TrackableCountFilter(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return !(min != null && max != null && min > max) && (min != null || max != null);

    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        if (min != null) {
            w.name("MinTrackables").value(min.intValue());
        }
        if (max != null) {
            w.name("MaxTrackables").value(max.intValue());
        }
        w.endObject();
    }
}
