package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;

import com.arcao.geocaching.api.impl.live_geocaching_api.Status;
import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;

public class StatusJsonParser {

	public static Status parse(JsonReader r) throws IOException {
		Status.Builder status = Status.Builder.status();
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("StatusCode".equals(name)) {
        status.withCode(r.nextInt());
			} else if ("StatusMessage".equals(name)) {
        status.withMessage(r.nextString());
			} else if ("ExceptionDetails".equals(name)) {
				status.withExceptionDetails(r.nextString());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return status.build();
	}

}
