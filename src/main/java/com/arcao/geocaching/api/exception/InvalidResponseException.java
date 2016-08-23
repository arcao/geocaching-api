package com.arcao.geocaching.api.exception;

public class InvalidResponseException extends GeocachingApiException {
    private static final long serialVersionUID = 3015569084055361721L;

    private final int statusCode;
    private final String statusMessage;
    private final String data;

    public InvalidResponseException(String message) {
        this(message, null);
    }

    public InvalidResponseException(int statusCode, String statusMessage, String data) {
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

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getData() {
        return data;
    }
}
