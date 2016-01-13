package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import com.arcao.geocaching.api.impl.live_geocaching_api.Status;

import java.io.IOException;

public class StatusJsonParser {

	public static Status parse(JsonReader r) throws IOException {
		boolean statusCodeFound = false;

		Status.Builder status = Status.Builder.status();
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("StatusCode".equals(name)) {
        status.withCode(r.nextInt());
				statusCodeFound = true;
			} else if ("StatusMessage".equals(name)) {
        status.withMessage(r.nextString());
			} else if ("ExceptionDetails".equals(name)) {
				status.withExceptionDetails(r.nextString());
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
