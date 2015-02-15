package com.arcao.geocaching.api.impl.live_geocaching_api;

import java.io.Serializable;

public class Status implements Serializable {
  private static final long serialVersionUID = -6603595166996374235L;

  private final int code;
  private final StatusCode statusCode;
  private final String message;
  private final String exceptionDetails;

  public Status(int code, String message, String exceptionDetails) {
    this.code = code;
    this.statusCode = StatusCode.getByCode(code);
    this.message = message;
    this.exceptionDetails = exceptionDetails;
  }

  public StatusCode getStatusCode() {
    return statusCode;
  }

  public int getCode() {
return code;
}

  public String getMessage() {
    return message;
  }

  public String getExceptionDetails() {
    return exceptionDetails;
  }


  public static class Builder {
    private int code;
    private String message;
    private String exceptionDetails;

    private Builder() {
    }

    public static Builder status() {
      return new Builder();
    }

    public Builder withCode(int code) {
      this.code = code;
      return this;
    }

    public Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder withExceptionDetails(String exceptionDetails) {
      this.exceptionDetails = exceptionDetails;
      return this;
    }

    public Status build() {
      return new Status(code, message, exceptionDetails);
    }
  }
}
