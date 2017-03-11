package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.ImageData;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ImageDataJsonParser {
    private ImageDataJsonParser() {
    }

    public static List<ImageData> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<ImageData> list = new ArrayList<ImageData>();
        r.beginArray();

        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    private static ImageData parse(JsonReader r) throws IOException {
        ImageData.Builder builder = ImageData.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("DateCreated".equals(name)) {
                builder.created(JsonParserUtil.parseJsonDate(r.nextString()));
            } else if ("Description".equals(name)) {
                builder.description(r.nextString());
            } else if ("MobileUrl".equals(name)) {
                builder.mobileUrl(r.nextString());
            } else if ("Name".equals(name)) {
                builder.name(r.nextString());
            } else if ("ThumbUrl".equals(name)) {
                builder.thumbUrl(r.nextString());
            } else if ("Url".equals(name)) {
                builder.url(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}
