package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.Date;

public class UserWaypoint {
	private final String cacheCode;
	private final String description;
	private final long id;
	private final double latitude;
	private final double longitude;
	private final Date date;
	private final int userId;
	
	public UserWaypoint(String cacheCode, String description, long id, double latitude, double longitude, Date date, int userId) {
		super();
		this.cacheCode = cacheCode;
		this.description = description;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.userId = userId;
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
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getUserId() {
		return userId;
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
