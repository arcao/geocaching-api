package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.google.gson.stream.JsonReader;
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

        List<GeocacheLog> list = new ArrayList<>();
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
            switch (name) {
                case "ID":
                    builder.id(r.nextLong());
                    break;
                case "CacheCode":
                    builder.cacheCode(r.nextString());
                    break;
                case "UTCCreateDate":
                    builder.created(parseJsonUTCDate(r.nextString()));
                    break;
                case "VisitDate":
                    builder.visited(parseJsonDate(r.nextString()));
                    break;
                case "VisitDateIso":
                    builder.visited(parseIsoDate(r.nextString()));
                    break;
                case "LogType":
                    builder.logType(parseLogType(r));
                    break;
                case "Finder":
                    builder.author(UserJsonParser.parse(r));
                    break;
                case "LogText":
                    builder.text(r.nextString());
                    break;
                case "Images":
                    builder.images(ImageDataJsonParser.parseList(r));
                    break;
                case "UpdatedLatitude":
                    updatedCoordinatesBuilder.latitude(r.nextDouble());
                    break;
                case "UpdatedLongitude":
                    updatedCoordinatesBuilder.longitude(r.nextDouble());
                    break;
                case "IsApproved":
                    builder.approved(r.nextBoolean());
                    break;
                case "IsArchived":
                    builder.archived(r.nextBoolean());
                    break;
                case "CannotDelete":
                    builder.undeletable(r.nextBoolean());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        builder.updatedCoordinates(updatedCoordinatesBuilder.build());

        return builder.build();
    }

    private static GeocacheLogType parseLogType(JsonReader r) throws IOException {
        GeocacheLogType geocacheLogType = null;

        if (isNextNull(r)) {
            return null;
        }

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
