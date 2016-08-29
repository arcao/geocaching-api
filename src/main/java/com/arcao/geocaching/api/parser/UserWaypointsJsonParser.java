package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.data.UserWaypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
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

        List<UserWaypoint> list = new ArrayList<UserWaypoint>();
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
            if ("CacheCode".equals(name)) {
                builder.cacheCode(r.nextString());
            } else if ("Description".equals(name)) {
                builder.description(r.nextString());
            } else if ("ID".equals(name)) {
                builder.id(r.nextLong());
            } else if ("Latitude".equals(name)) {
                coordinatesBuilder.latitude(r.nextDouble());
            } else if ("Longitude".equals(name)) {
                coordinatesBuilder.longitude(r.nextDouble());
            } else if ("UTCDate".equals(name)) {
                builder.date(parseJsonUTCDate(r.nextString()));
            } else if ("UserID".equals(name)) {
                builder.userId(r.nextInt());
            } else if ("IsCorrectedCoordinate".equals(name)) {
                builder.correctedCoordinate(r.nextBoolean());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        builder.coordinates(coordinatesBuilder.build());

        return builder.build();
    }
}
