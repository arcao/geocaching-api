package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FoundByUserFilter implements Filter {
    private static final String NAME = "FoundByUser";

    @NotNull private final String userName;

    public FoundByUserFilter(@NotNull String userName) {
        this.userName = userName;
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return !userName.isEmpty();
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("UserName").value(userName);
        w.endObject();
    }
}
