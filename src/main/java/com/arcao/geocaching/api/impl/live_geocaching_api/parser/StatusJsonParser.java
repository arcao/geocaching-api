package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;

import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;

public class StatusJsonParser {

	public static Status parse(JsonReader r) throws IOException {
		Status status = new Status();
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("StatusCode".equals(name)) {
			  status.originalStatusCode = r.nextInt();
				status.statusCode = StatusCode.parseStatusCode(status.originalStatusCode);
			} else if ("StatusMessage".equals(name)) {
				status.statusMessage = r.nextString();
			} else if ("ExceptionDetails".equals(name)) {
				status.exceptionDetails = r.nextString();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return status;
	}
		
	public static class Status {
	  protected int originalStatusCode;
		protected StatusCode statusCode;
		protected String statusMessage;
		protected String exceptionDetails;
				
		public StatusCode getStatusCode() {
			return statusCode;
		}
		
		public int getOriginalStatusCode() {
      return originalStatusCode;
    }
		
		public String getStatusMessage() {
			return statusMessage;
		}
		
		public String getExceptionDetails() {
			return exceptionDetails;
		}
	}
}
