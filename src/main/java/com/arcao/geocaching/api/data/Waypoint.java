package com.arcao.geocaching.api.data;


import java.lang.reflect.Method;
import java.util.Date;

import com.arcao.geocaching.api.data.type.WaypointType;

public class Waypoint {
	private final double latitude;
	private final double longitude;
	private final Date time;
	private final String waypointCode;
	private final String name;
	private final String note;
	private final WaypointType waypointType;
	private final String iconName;

	public Waypoint(double longitude, double latitude, Date time, String waypointCode, String name, String note, WaypointType waypointType) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		this.waypointCode = waypointCode;
		this.name = name;
		this.note = note;
		this.waypointType = waypointType;
		this.iconName = waypointType.getIconName();
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public Date getTime() {
		return time;
	}

	public String getWaypointCode() {
		return waypointCode;
	}

	public String getName() {
		return name;
	}

	public String getNote() {
		return note;
	}

	public WaypointType getWaypointType() {
		return waypointType;
	}

	public String getIconName() {
		return iconName;
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
