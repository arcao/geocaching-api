package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import com.arcao.geocaching.api.impl.live_geocaching_api.Status;
import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;

import java.io.IOException;

public class StatusJsonParser {
    public static Status parse(JsonReader r) throws IOException {
        Status.Builder status = Status.builder();

        boolean statusCodeFound = false;

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("StatusCode".equals(name)) {
                status.code(r.nextInt());
                statusCodeFound = true;
            } else if ("StatusMessage".equals(name)) {
                status.message(r.nextString());
            } else if ("ExceptionDetails".equals(name)) {
                status.exceptionDetails(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        if (!statusCodeFound)
            return null;

        return status.build();
    }

}
