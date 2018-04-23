package com.arcao.geocaching.api.filter;

import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class GeocacheExclusionsFilter implements Filter {
    private static final String NAME = "GeocacheExclusions";

    @Nullable private final Boolean archived;
    @Nullable private final Boolean available;
    @Nullable private final Boolean hasCorrectedCoordinates;
    @Nullable private final Boolean hasPersonalCacheNote;
    @Nullable private final Boolean premium;
    @Nullable private final Boolean published;

    public GeocacheExclusionsFilter(@Nullable Boolean archived, @Nullable Boolean available,
                                    @Nullable Boolean hasCorrectedCoordinates, @Nullable Boolean hasPersonalCacheNote,
                                    @Nullable Boolean premium, @Nullable Boolean published) {
        this.archived = archived;
        this.available = available;
        this.hasCorrectedCoordinates = hasCorrectedCoordinates;
        this.hasPersonalCacheNote = hasPersonalCacheNote;
        this.premium = premium;
        this.published = published;
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return archived != null
                || available != null
                || hasCorrectedCoordinates != null
                || hasPersonalCacheNote != null
                || premium != null
                || published != null;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        if (archived != null) {
            w.name("Archived").value(archived);
        }
        if (available != null) {
            w.name("Available").value(available);
        }
        if (hasCorrectedCoordinates != null) {
            w.name("HasCorrectedCoordinates").value(hasCorrectedCoordinates);
        }
        if (hasPersonalCacheNote != null) {
            w.name("HasPersonalCacheNote").value(hasPersonalCacheNote);
        }
        if (premium != null) {
            w.name("Premium").value(premium);
        }
        if (published != null) {
            w.name("Published").value(published);
        }
        w.endObject();
    }
}
