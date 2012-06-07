package com.arcao.geocaching.api.impl.live_geocaching_api.exception;

import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.StatusJsonParser.Status;

public class LiveGeocachingApiException extends GeocachingApiException {
  private static final long serialVersionUID = 4459503722935685091L;
  
  protected StatusCode statusCode;
  protected int originalStatusCode;
  protected String exceptionDetails;
  
  public LiveGeocachingApiException(Status status) {
    super(status.getStatusMessage());

    statusCode = status.getStatusCode();
    originalStatusCode = status.getOriginalStatusCode();
    exceptionDetails = status.getExceptionDetails();
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

}
