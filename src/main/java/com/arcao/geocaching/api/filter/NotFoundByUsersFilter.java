package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class NotFoundByUsersFilter implements Filter {
    private static final String NAME = "NotFoundByUsers";

    @NotNull private final String[] userNames;

    public NotFoundByUsersFilter(@NotNull String... userNames) {
        this.userNames = userNames;
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        if (userNames.length == 0)
            return false;

        for (String userName : userNames) {
            if (userName != null && !userName.isEmpty())
                return true;
        }

        return false;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("UserNames");
        w.beginArray();
        for (String userName : userNames) {
            if (userName != null && !userName.isEmpty())
                w.value(userName);
        }
        w.endArray();
        w.endObject();
    }
}
