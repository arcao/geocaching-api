package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class FavoritePointsFilter implements Filter {
    private static final String NAME = "FavoritePoints";

    @Nullable private final Integer min;
    @Nullable private final Integer max;

    public FavoritePointsFilter(@Nullable Integer min, @Nullable Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    @NotNull
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return (min != null && max != null && min <= max) ||
                (min == null && max != null) ||
                (min != null && max == null);
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        if (min != null) {
            w.name("MinFavoritePoints").value(min.intValue());
        }
        if (max != null) {
            w.name("MaxFavoritePoints").value(max.intValue());
        }
        w.endObject();
    }
}
