package com.arcao.geocaching.api.filter;


import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GeocacheTypeFilter implements Filter {
    private static final String NAME = "GeocacheType";

    @NotNull private final GeocacheType[] geocacheTypes;

    public GeocacheTypeFilter(@NotNull GeocacheType... geocacheTypes) {
        this.geocacheTypes = geocacheTypes.clone();
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        if (geocacheTypes.length == 0) {
            return false;
        }

        boolean valid = false;
        for (GeocacheType geocacheType : geocacheTypes) {
            if (geocacheType != null) {
                valid = true;
            }
        }

        return valid;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("GeocacheTypeIds");
        w.beginArray();
        for (GeocacheType geocacheType : geocacheTypes) {
            if (geocacheType != null) {
                w.value(geocacheType.id);
            }
        }
        w.endArray();
        w.endObject();
    }
}
