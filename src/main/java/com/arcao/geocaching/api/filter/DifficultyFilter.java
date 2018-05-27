package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DifficultyFilter implements Filter {
    private static final String NAME = "Difficulty";

    private final float min;
    private final float max;

    public DifficultyFilter(float min, float max) {
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
        return min >= 1 && max <= 5 && min <= max && (min != 1 || max != 5);
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();

        w.name("MinDifficulty").value(min);
        w.name("MaxDifficulty").value(max);

        w.endObject();
    }
}
