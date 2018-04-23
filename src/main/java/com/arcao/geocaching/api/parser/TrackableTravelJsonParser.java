package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.TrackableTravel;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonDate;

public final class TrackableTravelJsonParser {
    private TrackableTravelJsonParser() {
    }

    public static List<TrackableTravel> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<TrackableTravel> list = new ArrayList<>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();

        return list;
    }

    public static TrackableTravel parse(JsonReader r) throws IOException {
        TrackableTravel.Builder builder = TrackableTravel.builder();
        Coordinates.Builder coordinatesBuilder = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "CacheID":
                    builder.cacheId(r.nextInt());
                    break;
                case "DateLogged":
                    builder.dateLogged(parseJsonDate(r.nextString()));
                    break;
                case "Latitude":
                    coordinatesBuilder.latitude(r.nextDouble());
                    break;
                case "Longitude":
                    coordinatesBuilder.longitude(r.nextDouble());
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
