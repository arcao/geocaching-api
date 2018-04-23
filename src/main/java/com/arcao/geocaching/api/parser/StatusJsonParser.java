package com.arcao.geocaching.api.parser;


import com.arcao.geocaching.api.Status;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

public final class StatusJsonParser {
    private StatusJsonParser() {
    }

    public static Status parse(JsonReader r) throws IOException {
        Status.Builder status = Status.builder();

        boolean statusCodeFound = false;

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "StatusCode":
                    status.code(r.nextInt());
                    statusCodeFound = true;
                    break;
                case "StatusMessage":
                    status.message(r.nextString());
                    break;
                case "ExceptionDetails":
                    status.exceptionDetails(r.nextString());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        if (!statusCodeFound) {
            return null;
        }

        return status.build();
    }

}
