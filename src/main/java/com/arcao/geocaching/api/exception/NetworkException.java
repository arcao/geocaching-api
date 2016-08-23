package com.arcao.geocaching.api.exception;

public class NetworkException extends GeocachingApiException {
    private static final long serialVersionUID = 8683436618389727579L;

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }

}
