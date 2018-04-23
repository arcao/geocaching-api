package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.ImageData;
import com.google.gson.stream.JsonReader;
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

        List<ImageData> list = new ArrayList<>();
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
            switch (name) {
                case "DateCreated":
                    builder.created(JsonParserUtil.parseJsonDate(r.nextString()));
                    break;
                case "Description":
                    builder.description(r.nextString());
                    break;
                case "MobileUrl":
                    builder.mobileUrl(r.nextString());
                    break;
                case "Name":
                    builder.name(r.nextString());
                    break;
                case "ThumbUrl":
                    builder.thumbUrl(r.nextString());
                    break;
                case "Url":
                    builder.url(r.nextString());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }
}
