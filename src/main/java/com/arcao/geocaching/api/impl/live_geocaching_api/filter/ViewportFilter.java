package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.google.gson.stream.JsonWriter;

/**
 * 
 * @author arcao
 * @since 1.4
 */
public class ViewportFilter implements Filter {
  private static final String NAME = "Viewport";
  
  protected final double topLeftLatitude;
  protected final double topLeftLongitude;
  protected final double bottomRightLatitude;
  protected final double bottomRightLongitude;
  
    
  public ViewportFilter(double topLeftLatitude, double topLeftLongitude, double bottomRightLatitude, double bottomRightLongitude) {
    this.topLeftLatitude = topLeftLatitude;
    this.topLeftLongitude = topLeftLongitude;
    this.bottomRightLatitude = bottomRightLatitude;
    this.bottomRightLongitude = bottomRightLongitude;
  }
  
  public double getTopLeftLatitude() {
    return topLeftLatitude;
  }
  
  public double getTopLeftLongitude() {
    return topLeftLongitude;
  }
  
  public double getBottomRightLatitude() {
    return bottomRightLatitude;
  }
  
  public double getBottomRightLongitude() {
    return bottomRightLongitude;
  }

  public String getName() {
    return NAME;
  }
  
  public boolean isValid() {
    return true;
  }
    
  public void writeJson(JsonWriter w) throws IOException {
    w.name(NAME);
    w.beginObject();
    
    w.name("TopLeft");
    w.beginObject();
    w.name("Latitude").value(topLeftLatitude);
    w.name("Longitude").value(topLeftLatitude);
    w.endObject();
    
    w.name("BottomRight");
    w.beginObject();
    w.name("Latitude").value(bottomRightLatitude);
    w.name("Longitude").value(bottomRightLongitude);
    w.endObject();
    
    w.endObject();
  }
}
