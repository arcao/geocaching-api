package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CacheCodeFilter implements Filter {
    private static final String NAME = "CacheCode";

    @NotNull private final String[] caches;

    public CacheCodeFilter(@NotNull String... caches) {
        this.caches = caches.clone();
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        if (caches.length == 0) {
            return false;
        }

        for (String cache : caches) {
            if (cache != null && !cache.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("CacheCodes");
        w.beginArray();
        for (String cache : caches) {
            if (cache != null && !cache.isEmpty()) {
                w.value(cache);
            }
        }
        w.endArray();
        w.endObject();
    }
}
