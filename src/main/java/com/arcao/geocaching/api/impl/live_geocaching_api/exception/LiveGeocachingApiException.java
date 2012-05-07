package com.arcao.geocaching.api.impl.live_geocaching_api.exception;

import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.StatusCode;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.StatusJsonParser.Status;

public class LiveGeocachingApiException extends GeocachingApiException {
  private static final long serialVersionUID = 4459503722935685091L;
  
  protected Status status;
  
  public LiveGeocachingApiException(Status status) {
    super(status.getStatusMessage());
  }
  
  public StatusCode getStatusCode() {
    return status.getStatusCode();
  }
  
  public int getOriginalStatusCode() {
    return status.getOriginalStatusCode();
  }
  
  public String getExceptionDetails() {
    return status.getExceptionDetails();
  }
  
  @Override
  public String toString() {
    return super.toString() + " (" + status.getOriginalStatusCode() + ")";
  }

}
