package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DifficultyFilter implements Filter {
    private static final String NAME = "Difficulty";

    private final float min;
    private final float max;

    public DifficultyFilter(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return NAME;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public boolean isValid() {
        return min != 1 || max != 5;
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();

        w.name("MinDifficulty").value(min);
        w.name("MaxDifficulty").value(max);

        w.endObject();
    }
}
