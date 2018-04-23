package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonReader;
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

        List<TrackableLog> list = new ArrayList<>();
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
            switch (name) {
                case "CacheID":
                    builder.cacheId(r.nextInt());
                    break;
                case "Code":
                    builder.code(r.nextString());
                    break;
                case "ID":
                    builder.id(r.nextInt());
                    break;
                case "Images":
                    builder.images(ImageDataJsonParser.parseList(r));
                    break;
                case "IsArchived":
                    builder.archived(r.nextBoolean());
                    break;
                case "LogGuid":
                    builder.guid(r.nextString());
                    break;
                case "LogText":
                    builder.text(r.nextString());
                    break;
                case "LogType":
                    builder.type(parseTrackableLogType(r));
                    break;
                case "LoggedBy":
                    builder.loggedBy(UserJsonParser.parse(r));
                    break;
                case "UTCCreateDate":
                    builder.created(parseJsonUTCDate(r.nextString()));
                    break;
                case "UpdatedLatitude":
                    updatedCoordinatesBuilder.latitude(r.nextDouble());
                    break;
                case "UpdatedLongitude":
                    updatedCoordinatesBuilder.longitude(r.nextDouble());
                    break;
                case "Url":
                    builder.url(r.nextString());
                    break;
                case "VisitDate":
                    builder.visited(parseJsonDate(r.nextString()));
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
}
