package com.arcao.geocaching.api.exception;

import org.jetbrains.annotations.Nullable;

public class InvalidResponseException extends GeocachingApiException {
    private static final long serialVersionUID = 3015569084055361721L;

    private final int statusCode;
    @Nullable private final String statusMessage;
    @Nullable private final String data;

    public InvalidResponseException(int statusCode, @Nullable String statusMessage, @Nullable String data) {
        super(String.valueOf(statusCode) + " " + statusMessage + "\n\n" + data);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public InvalidResponseException(String message, Throwable cause) {
        super(message, cause);
        statusCode = 0;
        statusMessage = null;
        data = null;
    }

    public InvalidResponseException(String message) {
        super(message);
        statusCode = 0;
        statusMessage = null;
        data = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Nullable
    public String getStatusMessage() {
        return statusMessage;
    }

    @Nullable
    public String getData() {
        return data;
    }
}
