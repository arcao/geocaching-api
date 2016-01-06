package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import com.arcao.geocaching.api.data.GeocacheStatus;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof.Nowacki on 2015.11.13.
 */
public class GeocacheStatusJsonParser extends JsonParser {

    public static List<GeocacheStatus> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<GeocacheStatus> list = new ArrayList<GeocacheStatus>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    public static GeocacheStatus parse(JsonReader r) throws IOException {

        GeocacheStatus.Builder geocacheStatus = GeocacheStatus.Builder.geocacheStatus();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Archived".equals(name)) {
                geocacheStatus.withArchived(r.nextBoolean());
            } else if ("Available".equals(name)) {
                geocacheStatus.withAvailable(r.nextBoolean());
            } else if ("CacheCode".equals(name)) {
                geocacheStatus.withCacheCode(r.nextString());
            } else if ("CacheName".equals(name)) {
                geocacheStatus.withCacheName(r.nextString());
            } else if ("CacheType".equals(name)) {
                geocacheStatus.withCacheType(GeocacheType.fromId(r.nextInt()));
            } else if ("Premium".equals(name)) {
                geocacheStatus.withPremium(r.nextBoolean());
            } else if ("TrackableCount".equals(name)) {
                geocacheStatus.withTrackableCount(r.nextInt());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return geocacheStatus.build();
    }

}
