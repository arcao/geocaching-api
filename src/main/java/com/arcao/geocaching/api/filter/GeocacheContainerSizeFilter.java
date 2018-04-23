package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.data.type.ContainerType;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GeocacheContainerSizeFilter implements Filter {
    private static final String NAME = "GeocacheContainerSize";

    @NotNull private final ContainerType[] containerTypes;

    public GeocacheContainerSizeFilter(@NotNull ContainerType... containerTypes) {
        this.containerTypes = containerTypes.clone();
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        if (containerTypes.length == 0) {
            return false;
        }

        for (ContainerType containerType : containerTypes) {
            if (containerType != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        w.name("GeocacheContainerSizeIds");
        w.beginArray();
        for (ContainerType containerType : containerTypes) {
            if (containerType != null) {
                w.value(containerType.id);
            }
        }
        w.endArray();
        w.endObject();
    }
}
