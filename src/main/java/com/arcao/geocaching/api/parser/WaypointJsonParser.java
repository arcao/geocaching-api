package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.data.Waypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonReader;
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

        List<Waypoint> list = new ArrayList<>();
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
            switch (name) {
                case "Longitude":
                    coordinatesBuilder.longitude(r.nextDouble());
                    break;
                case "Latitude":
                    coordinatesBuilder.latitude(r.nextDouble());
                    break;
                case "UTCEnteredDate":
                    builder.time(parseJsonUTCDate(r.nextString()));
                    break;
                case "Code":
                    builder.waypointCode(r.nextString());
                    break;
                case "Name":
                    builder.waypointType(fromName(r.nextString()));
                    break;
                case "Description":
                    builder.name(r.nextString());
                    break;
                case "Comment":
                    builder.note(r.nextString());
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
