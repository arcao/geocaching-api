package geocaching.api.impl.live_geocaching_api.exception;

import geocaching.api.exception.GeocachingApiException;
import geocaching.api.impl.live_geocaching_api.StatusCode;

public class LiveGeocachingApiException extends GeocachingApiException {
  private static final long serialVersionUID = 4459503722935685091L;
  
  protected StatusCode statusCode;

  public LiveGeocachingApiException(StatusCode statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }
  
  @Override
  public String toString() {
    return super.toString() + " (" + statusCode + ")";
  }

}
