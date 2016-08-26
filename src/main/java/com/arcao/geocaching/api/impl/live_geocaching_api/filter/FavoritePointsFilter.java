package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FavoritePointsFilter implements Filter {
    private static final String NAME = "FavoritePoints";

    private final Integer min;
    private final Integer max;

    public FavoritePointsFilter(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return NAME;
    }

    public boolean isValid() {
        return !(min != null && max != null && min > max) && (min != null || max != null);
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        if (min != null)
            w.name("MinFavoritePoints").value(min.intValue());
        if (max != null)
            w.name("MaxFavoritePoints").value(max.intValue());
        w.endObject();
    }
}
