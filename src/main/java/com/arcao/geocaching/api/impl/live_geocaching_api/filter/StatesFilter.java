package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StatesFilter implements Filter {
    private static final String NAME = "States";

    private final int[] states;

    public StatesFilter(int... states) {
        this.states = states;
    }

    public String getName() {
        return NAME;
    }

    public boolean isValid() {
        return states != null && states.length > 0;
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("StateIds");
        w.beginArray();
        for (int state : states) {
            w.value(state);
        }
        w.endArray();
        w.endObject();
    }
}
