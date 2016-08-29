package com.arcao.geocaching.api.exception;

import com.arcao.geocaching.api.Status;
import com.arcao.geocaching.api.StatusCode;

public class LiveGeocachingApiException extends GeocachingApiException {
    private static final long serialVersionUID = 4459503722935685091L;

    protected final String originalMessage;
    protected final StatusCode statusCode;
    protected final int code;
    protected final String exceptionDetails;

    public LiveGeocachingApiException(Status status) {
        super(status.message());
        initCause(null);  // Disallow subsequent initCause

        originalMessage = status.message();
        statusCode = status.statusCode();
        code = status.code();
        exceptionDetails = status.exceptionDetails();
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
     *
     * @return unmodified error message
     */
    public String getOriginalMessage() {
        return originalMessage;
    }

    /**
     * Returns error code represented by StatusCode enum.
     *
     * @return error code
     */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * Returns error code as an integer.
     *
     * @return error code
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns exception details received from Live Geocaching service.
     *
     * @return exception details
     */
    public String getExceptionDetails() {
        return exceptionDetails;
    }
}
