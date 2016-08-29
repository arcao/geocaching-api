package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.data.Waypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.data.type.WaypointType.fromName;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonUTCDate;

public final class WaypointJsonParser {
    private WaypointJsonParser() {
    }

    public static List<Waypoint> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<Waypoint> list = new ArrayList<Waypoint>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    private static Waypoint parse(JsonReader r) throws IOException {
        Waypoint.Builder builder = Waypoint.builder();
        Coordinates.Builder coordinatesBuilder = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Longitude".equals(name)) {
                coordinatesBuilder.longitude(r.nextDouble());
            } else if ("Latitude".equals(name)) {
                coordinatesBuilder.latitude(r.nextDouble());
            } else if ("UTCEnteredDate".equals(name)) {
                builder.time(parseJsonUTCDate(r.nextString()));
            } else if ("Code".equals(name)) {
                builder.waypointCode(r.nextString());
            } else if ("Name".equals(name)) {
                builder.waypointType(fromName(r.nextString()));
            } else if ("Description".equals(name)) {
                builder.name(r.nextString());
            } else if ("Comment".equals(name)) {
                builder.note(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        builder.coordinates(coordinatesBuilder.build());

        return builder.build();
    }
}
