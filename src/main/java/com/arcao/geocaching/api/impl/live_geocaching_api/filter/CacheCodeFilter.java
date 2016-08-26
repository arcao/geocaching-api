package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CacheCodeFilter implements Filter {
    private static final String NAME = "CacheCode";

    private final String[] caches;

    public CacheCodeFilter(String... caches) {
        this.caches = caches;
    }

    public String[] getCaches() {
        return caches;
    }

    public boolean isValid() {
        if (caches == null || caches.length == 0)
            return false;

        boolean valid = false;
        for (String cache : caches) {
            if (cache != null && cache.length() > 0)
                valid = true;
        }

        return valid;
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("CacheCodes");
        w.beginArray();
        for (String cache : caches) {
            if (cache != null && cache.length() > 0)
                w.value(cache);
        }
        w.endArray();
        w.endObject();
    }

    public String getName() {
        return NAME;
    }

}
