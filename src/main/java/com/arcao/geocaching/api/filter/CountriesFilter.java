package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CountriesFilter implements Filter {
    private static final String NAME = "Countries";

    private final int[] countries;

    public CountriesFilter(int... countries) {
        this.countries = countries;
    }

    public String getName() {
        return NAME;
    }

    public boolean isValid() {
        return countries != null && countries.length > 0;
    }

    public void writeJson(JsonWriter w) throws IOException {
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
