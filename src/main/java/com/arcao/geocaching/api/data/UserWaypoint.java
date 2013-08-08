package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.Date;

import com.arcao.geocaching.api.data.coordinates.Coordinates;

public class UserWaypoint {
	private final String cacheCode;
	private final String description;
	private final long id;
	private final Coordinates coordinates;
	private final Date date;
	private final int userId;
	private final boolean correctedCoordinate;
	
	public UserWaypoint(String cacheCode, String description, long id, Coordinates coordinates, Date date, int userId, boolean correctedCoordinate) {
		super();
		this.cacheCode = cacheCode;
		this.description = description;
		this.id = id;
		this.coordinates = coordinates;
		this.date = date;
		this.userId = userId;
		this.correctedCoordinate = correctedCoordinate;
	}
	
	public String getCacheCode() {
		return cacheCode;
	}
	
	public String getDescription() {
		return description;
	}
	
	public long getId() {
		return id;
	}
	
	public Coordinates getCoordinates() {
    return coordinates;
  }
	
	public double getLatitude() {
		return coordinates.getLatitude();
	}
	
	public double getLongitude() {
		return coordinates.getLongitude();
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public boolean isCorrectedCoordinate() {
		return correctedCoordinate;
	}
	
	public String getUserWaypointCode(int index) {
    int base = Integer.parseInt("U1", 36);
    String value = Integer.toString(base + index, 36);
    
    return value.substring(value.length() - 2, value.length()) + cacheCode.substring(2);
  }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Method m : getClass().getMethods()) {
			if ((!m.getName().startsWith("get") && !m.getName().startsWith("is")) ||
					m.getParameterTypes().length != 0 ||
					void.class.equals(m.getReturnType()))
				continue;

			sb.append(m.getName());
			sb.append(':');
			try {
				sb.append(m.invoke(this, new Object[0]));
			} catch (Exception e) {
			}
			sb.append(", ");
		}
		return sb.toString();
	}
}
