package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.isNextNull;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseIsoDate;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonDate;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonUTCDate;

public final class GeocacheLogJsonParser {
    private GeocacheLogJsonParser() {
    }

    public static List<GeocacheLog> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<GeocacheLog> list = new ArrayList<GeocacheLog>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    public static GeocacheLog parse(JsonReader r) throws IOException {
        GeocacheLog.Builder builder = GeocacheLog.builder();
        Coordinates.Builder updatedCoordinatesBuilder = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("ID".equals(name)) {
                builder.id(r.nextLong());
            } else if ("CacheCode".equals(name)) {
                builder.cacheCode(r.nextString());
            } else if ("UTCCreateDate".equals(name)) {
                builder.created(parseJsonUTCDate(r.nextString()));
            } else if ("VisitDate".equals(name)) {
                builder.visited(parseJsonDate(r.nextString()));
            } else if ("VisitDateIso".equals(name)) {
                builder.visited(parseIsoDate(r.nextString()));
            } else if ("LogType".equals(name)) {
                builder.logType(parseLogType(r));
            } else if ("Finder".equals(name)) {
                builder.author(UserJsonParser.parse(r));
            } else if ("LogText".equals(name)) {
                builder.text(r.nextString());
            } else if ("Images".equals(name)) {
                builder.images(ImageDataJsonParser.parseList(r));
            } else if ("UpdatedLatitude".equals(name)) {
                updatedCoordinatesBuilder.latitude(r.nextDouble());
            } else if ("UpdatedLongitude".equals(name)) {
                updatedCoordinatesBuilder.longitude(r.nextDouble());
            } else if ("IsApproved".equals(name)) {
                builder.approved(r.nextBoolean());
            } else if ("IsArchived".equals(name)) {
                builder.archived(r.nextBoolean());
            } else if ("CannotDelete".equals(name)) {
                builder.undeletable(r.nextBoolean());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        builder.updatedCoordinates(updatedCoordinatesBuilder.build());

        return builder.build();
    }

    private static GeocacheLogType parseLogType(JsonReader r) throws IOException {
        GeocacheLogType geocacheLogType = null;

        if (isNextNull(r))
            return null;

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("WptLogTypeName".equals(name)) {
                geocacheLogType = GeocacheLogType.fromName(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();
        return geocacheLogType;
    }
}
