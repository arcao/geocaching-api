package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.data.Trackable;
import com.google.gson.stream.JsonReader;
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

        List<Trackable> list = new ArrayList<>();
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
            switch (name) {
                case "Id":
                    builder.id(r.nextLong());
                    break;
                case "Name":
                    builder.name(r.nextString());
                    break;
                case "CurrentGoal":
                    builder.goal(r.nextString());
                    break;
                case "Description":
                    builder.description(r.nextString());
                    break;
                case "TBTypeName":
                    builder.trackableTypeName(r.nextString());
                    break;
                case "IconUrl":
                    builder.trackableTypeImage(r.nextString());
                    break;
                case "OriginalOwner":
                    builder.owner(UserJsonParser.parse(r));
                    break;
                case "CurrentGeocacheCode":
                    builder.currentCacheCode(r.nextString());
                    break;
                case "CurrentOwner":
                    builder.currentOwner(UserJsonParser.parse(r));
                    break;
                case "Code":
                    builder.trackingNumber(r.nextString());
                    break;
                case "DateCreated":
                    builder.created(parseJsonDate(r.nextString()));
                    break;
                case "AllowedToBeCollected":
                    builder.allowedToBeCollected(r.nextBoolean());
                    break;
                case "InCollection":
                    builder.inCollection(r.nextBoolean());
                    break;
                case "Archived":
                    builder.archived(r.nextBoolean());
                    break;
                case "Images":
                    builder.images(ImageDataJsonParser.parseList(r));
                    break;
                case "TrackableLogs":
                    builder.trackableLogs(TrackableLogJsonParser.parseList(r));
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }
}
