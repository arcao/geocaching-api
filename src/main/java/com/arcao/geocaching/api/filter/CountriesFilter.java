package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CountriesFilter implements Filter {
    private static final String NAME = "Countries";

    @NotNull private final int[] countries;

    public CountriesFilter(@NotNull int... countries) {
        this.countries = countries.clone();
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return countries.length > 0;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("CountryIds");
        w.beginArray();
        for (int state : countries) {
            w.value(state);
        }
        w.endArray();
        w.endObject();
    }
}
