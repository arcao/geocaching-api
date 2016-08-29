package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GeocacheNameFilter implements Filter {
    private static final String NAME = "GeocacheName";

    @NotNull private final String name;

    public GeocacheNameFilter(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return !name.isEmpty();
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("GeocacheName").value(name);
        w.endObject();
    }
}
