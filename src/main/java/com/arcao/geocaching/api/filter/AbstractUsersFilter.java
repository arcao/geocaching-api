package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

abstract class AbstractUsersFilter implements Filter {
    @NotNull private final String[] userNames;

    public AbstractUsersFilter(@NotNull String... userNames) {
        this.userNames = userNames.clone();
    }

    @Override
    public boolean valid() {
        if (userNames.length == 0) {
            return false;
        }

        for (String userName : userNames) {
            if (userName != null && !userName.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(name());
        w.beginObject();
        w.name("UserNames");
        w.beginArray();
        for (String userName : userNames) {
            if (userName != null && !userName.isEmpty()) {
                w.value(userName);
            }
        }
        w.endArray();
        w.endObject();
    }
}
