package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class HiddenByUsersFilter implements Filter {
    private static final String NAME = "HiddenByUsers";

    protected final String[] userNames;

    public HiddenByUsersFilter(String... userNames) {
        this.userNames = userNames;
    }

    public boolean isValid() {
        if (userNames == null || userNames.length == 0)
            return false;

        boolean valid = false;
        for (String userName : userNames) {
            if (userName != null && userName.length() > 0)
                valid = true;
        }

        return valid;
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("UserNames");
        w.beginArray();
        for (String userName : userNames) {
            if (userName != null && userName.length() > 0)
                w.value(userName);
        }
        w.endArray();
        w.endObject();
    }

    public String getName() {
        return NAME;
    }
}
