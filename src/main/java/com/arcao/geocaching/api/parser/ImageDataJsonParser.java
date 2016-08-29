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
        String description = null;
        String mobileUrl = null;
        String imageName = null;
        String thumbUrl = null;
        String url = null;

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Description".equals(name)) {
                description = r.nextString();
            } else if ("MobileUrl".equals(name)) {
                mobileUrl = r.nextString();
            } else if ("Name".equals(name)) {
                imageName = r.nextString();
            } else if ("ThumbUrl".equals(name)) {
                thumbUrl = r.nextString();
            } else if ("Url".equals(name)) {
                url = r.nextString();
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return new ImageData(description, mobileUrl, imageName, thumbUrl, url);
    }

}
