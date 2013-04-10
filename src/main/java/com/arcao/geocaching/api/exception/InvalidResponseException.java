package com.arcao.geocaching.api.exception;

public class InvalidResponseException extends GeocachingApiException {
  private static final long serialVersionUID = 3015569084055361721L;

  public InvalidResponseException(String message) {
    super(message);
  }

  public InvalidResponseException(String message, Throwable cause) {
    super(message, cause);
  }

}
