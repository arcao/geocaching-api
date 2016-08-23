package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FieldNoteFindsFilter implements Filter {
    private static final String NAME = "FieldNoteFinds";

    private final String userName;

    public FieldNoteFindsFilter(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return NAME;
    }

    public boolean isValid() {
        return userName != null;
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("UserName").value(userName);
        w.endObject();
    }
}
