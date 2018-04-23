package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.data.UserWaypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonUTCDate;

public final class UserWaypointsJsonParser {
    private UserWaypointsJsonParser() {
    }

    public static List<UserWaypoint> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<UserWaypoint> list = new ArrayList<>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    private static UserWaypoint parse(JsonReader r) throws IOException {
        UserWaypoint.Builder builder = UserWaypoint.builder();
        Coordinates.Builder coordinatesBuilder = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "CacheCode":
                    builder.cacheCode(r.nextString());
                    break;
                case "Description":
                    builder.description(r.nextString());
                    break;
                case "ID":
                    builder.id(r.nextLong());
                    break;
                case "Latitude":
                    coordinatesBuilder.latitude(r.nextDouble());
                    break;
                case "Longitude":
                    coordinatesBuilder.longitude(r.nextDouble());
                    break;
                case "UTCDate":
                    builder.date(parseJsonUTCDate(r.nextString()));
                    break;
                case "UserID":
                    builder.userId(r.nextInt());
                    break;
                case "IsCorrectedCoordinate":
                    builder.correctedCoordinate(r.nextBoolean());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        builder.coordinates(coordinatesBuilder.build());

        return builder.build();
    }
}
