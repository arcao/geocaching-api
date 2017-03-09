package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.data.Trackable;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonDate;

public final class TrackableJsonParser {
    private TrackableJsonParser() {
    }

    public static List<Trackable> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<Trackable> list = new ArrayList<Trackable>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    public static Trackable parse(JsonReader r) throws IOException {
        Trackable.Builder builder = Trackable.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Id".equals(name)) {
                builder.id(r.nextLong());
            } else if ("Name".equals(name)) {
                builder.name(r.nextString());
            } else if ("CurrentGoal".equals(name)) {
                builder.goal(r.nextString());
            } else if ("Description".equals(name)) {
                builder.description(r.nextString());
            } else if ("TBTypeName".equals(name)) {
                builder.trackableTypeName(r.nextString());
            } else if ("IconUrl".equals(name)) {
                builder.trackableTypeImage(r.nextString());
            } else if ("OriginalOwner".equals(name)) {
                builder.owner(UserJsonParser.parse(r));
            } else if ("CurrentGeocacheCode".equals(name)) {
                builder.currentCacheCode(r.nextString());
            } else if ("CurrentOwner".equals(name)) {
                builder.currentOwner(UserJsonParser.parse(r));
            } else if ("Code".equals(name)) {
                builder.trackingNumber(r.nextString());
            } else if ("DateCreated".equals(name)) {
                builder.created(parseJsonDate(r.nextString()));
            } else if ("AllowedToBeCollected".equals(name)) {
                builder.allowedToBeCollected(r.nextBoolean());
            } else if ("InCollection".equals(name)) {
                builder.inCollection(r.nextBoolean());
            } else if ("Archived".equals(name)) {
                builder.archived(r.nextBoolean());
            } else if ("Images".equals(name)) {
                builder.images(ImageDataJsonParser.parseList(r));
            } else if ("TrackableLogs".equals(name)) {
                builder.trackableLogs(TrackableLogJsonParser.parseList(r));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}
