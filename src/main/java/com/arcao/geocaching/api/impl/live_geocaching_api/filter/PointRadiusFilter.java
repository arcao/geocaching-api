package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonWriter;

public class PointRadiusFilter implements Filter {
	private static final String NAME = "PointRadius"; 
	
	protected final long distanceInMeters;
	protected final double latitude;
	protected final double longitude;
	
	public PointRadiusFilter(double latitude, double longitude, long distanceInMeters) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.distanceInMeters = distanceInMeters;
	}
	
  /**
   * 
   * @since 1.5
   */
public PointRadiusFilter(Coordinates coordinates, long distanceInMeters) {
    latitude = coordinates.getLatitude();
    longitude = coordinates.getLongitude();
    this.distanceInMeters = distanceInMeters;
  }
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
  /**
   * 
   * @since 1.5
   */
  public Coordinates getCoordinates() {
    return new Coordinates(latitude, longitude); 
  }
	
	public long getDistanceInMeters() {
		return distanceInMeters;
	}

	public boolean isValid() {
		return true;
	}
	
	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		
		w.name("DistanceInMeters").value(distanceInMeters);
		
		w.name("Point");
		w.beginObject();
		w.name("Latitude").value(latitude);
		w.name("Longitude").value(longitude);
		w.endObject();
		
		w.endObject();
	}

	public String getName() {
		return NAME;
	}
}
