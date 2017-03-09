package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonDate;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonUTCDate;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseTrackableLogType;

public final class TrackableLogJsonParser {
    private TrackableLogJsonParser() {
    }

    public static List<TrackableLog> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<TrackableLog> list = new ArrayList<TrackableLog>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();

        return list;
    }

    public static TrackableLog parse(JsonReader r) throws IOException {
        TrackableLog.Builder builder = TrackableLog.builder();
        Coordinates.Builder updatedCoordinatesBuilder = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("CacheID".equals(name)) {
                builder.cacheId(r.nextInt());
            } else if ("Code".equals(name)) {
                builder.code(r.nextString());
            } else if ("ID".equals(name)) {
                builder.id(r.nextInt());
            } else if ("Images".equals(name)) {
                builder.images(ImageDataJsonParser.parseList(r));
            } else if ("IsArchived".equals(name)) {
                builder.archived(r.nextBoolean());
            } else if ("LogGuid".equals(name)) {
                builder.guid(r.nextString());
            } else if ("LogText".equals(name)) {
                builder.text(r.nextString());
            } else if ("LogType".equals(name)) {
                builder.type(parseTrackableLogType(r));
            } else if ("LoggedBy".equals(name)) {
                builder.loggedBy(UserJsonParser.parse(r));
            } else if ("UTCCreateDate".equals(name)) {
                builder.created(parseJsonUTCDate(r.nextString()));
            } else if ("UpdatedLatitude".equals(name)) {
                updatedCoordinatesBuilder.latitude(r.nextDouble());
            } else if ("UpdatedLongitude".equals(name)) {
                updatedCoordinatesBuilder.longitude(r.nextDouble());
            } else if ("Url".equals(name)) {
                builder.url(r.nextString());
            } else if ("VisitDate".equals(name)) {
                builder.visited(parseJsonDate(r.nextString()));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        builder.updatedCoordinates(updatedCoordinatesBuilder.build());

        return builder.build();
    }
}
