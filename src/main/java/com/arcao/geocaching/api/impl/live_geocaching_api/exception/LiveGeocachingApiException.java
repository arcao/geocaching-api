package com.arcao.geocaching.api.impl.live_geocaching_api.exception;

import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;
import com.arcao.geocaching.api.impl.live_geocaching_api.Status;

public class LiveGeocachingApiException extends GeocachingApiException {
	private static final long serialVersionUID = 4459503722935685091L;

	protected final String originalMessage;
	protected final StatusCode statusCode;
	protected final int code;
	protected final String exceptionDetails;

	public LiveGeocachingApiException(Status status) {
		super(status.getMessage());
		initCause(null);  // Disallow subsequent initCause

		originalMessage = status.getMessage();
		statusCode = status.getStatusCode();
		code = status.getCode();
		exceptionDetails = status.getExceptionDetails();
	}

	@Override
	public String getMessage() {
		if (exceptionDetails == null || exceptionDetails.length() == 0) {
			return super.getMessage() + " (" + code + ")";
		} else {
			return super.getMessage() + " (" + code + "); nested exception is: \n\t" +
							exceptionDetails;
		}
	}

	/**
	 * Returns unmodified error message.
	 * @return unmodified error message
   */
	public String getOriginalMessage() {
		return originalMessage;
	}

	/**
	 * Returns error code represented by StatusCode enum.
	 * @return error code
   */
	public StatusCode getStatusCode() {
		return statusCode;
	}

	/**
	 * Returns error code as an integer.
	 * @return error code
   */
	public int getCode() {
		return code;
	}

	/**
	 * Returns exception details received from Live Geocaching service.
	 * @return exception details
   */
	public String getExceptionDetails() {
		return exceptionDetails;
	}
}
