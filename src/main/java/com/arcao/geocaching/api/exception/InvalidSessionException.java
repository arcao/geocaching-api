package com.arcao.geocaching.api.exception;

public class InvalidSessionException extends GeocachingApiException {
    private static final long serialVersionUID = 979749036657920639L;

    public InvalidSessionException(String message) {
        super(message);
    }

    public InvalidSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
