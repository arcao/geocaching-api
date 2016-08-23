package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GeocacheExclusionsFilter implements Filter {
    private static final String NAME = "GeocacheExclusions";

    private final Boolean archived;
    private final Boolean available;
    private final Boolean hasCorrectedCoordinates;
    private final Boolean hasPersonalCacheNote;
    private final Boolean premium;
    private final Boolean published;

    public GeocacheExclusionsFilter(Boolean archived, Boolean available, Boolean premium) {
        this(archived, available, null, null, premium, null);
    }

    public GeocacheExclusionsFilter(Boolean archived, Boolean available, Boolean hasCorrectedCoordinates, Boolean hasPersonalCacheNote, Boolean premium, Boolean published) {
        this.archived = archived;
        this.available = available;
        this.hasCorrectedCoordinates = hasCorrectedCoordinates;
        this.hasPersonalCacheNote = hasPersonalCacheNote;
        this.premium = premium;
        this.published = published;
    }


    public boolean isValid() {
        return archived != null || available != null || hasCorrectedCoordinates != null || hasPersonalCacheNote != null || premium != null || published != null;
    }

    public void writeJson(JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();
        if (archived != null)
            w.name("Archived").value(archived);
        if (available != null)
            w.name("Available").value(available);
        if (hasCorrectedCoordinates != null)
            w.name("HasCorrectedCoordinates").value(hasCorrectedCoordinates);
        if (hasPersonalCacheNote != null)
            w.name("HasPersonalCacheNote").value(hasPersonalCacheNote);
        if (premium != null)
            w.name("Premium").value(premium);
        if (published != null)
            w.name("Published").value(published);
        w.endObject();
    }

    public String getName() {
        return NAME;
    }

}
