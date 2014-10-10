package com.arcao.geocaching.api.impl.live_geocaching_api.exception;

import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.StatusJsonParser.Status;

public class LiveGeocachingApiException extends GeocachingApiException {
	private static final long serialVersionUID = 4459503722935685091L;

	protected final StatusCode statusCode;
	protected final int originalStatusCode;
	protected final String exceptionDetails;

	public LiveGeocachingApiException(Status status) {
		super(status.getStatusMessage());

		statusCode = status.getStatusCode();
		originalStatusCode = status.getOriginalStatusCode();
		exceptionDetails = status.getExceptionDetails();

		if (exceptionDetails != null && exceptionDetails.length() > 0) {
			initCause(new LiveGeocachingApiRemoteException(exceptionDetails));
		}
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public int getOriginalStatusCode() {
		return originalStatusCode;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	@Override
	public String toString() {
        return super.toString() + " (" + originalStatusCode + ")";
	}

	private static class LiveGeocachingApiRemoteException extends Exception {
		private static final long serialVersionUID = 6441674083027722835L;

		private final String exceptionDetails;

		public LiveGeocachingApiRemoteException(String exceptionDetails) {
			this.exceptionDetails = exceptionDetails;
		}

		@Override
		public String toString() {
			return exceptionDetails;
		}
	}
}
