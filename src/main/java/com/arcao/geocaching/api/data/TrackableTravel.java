package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.Date;

import com.arcao.geocaching.api.data.coordinates.Coordinates;

public class TrackableTravel {
	private final long cacheID;
	private final Date dateLogged;
	private final Coordinates coordinates;
	
	public TrackableTravel(long cacheID, Date dateLogged, Coordinates coordinates) {
		this.cacheID = cacheID;
		this.dateLogged = dateLogged;
		this.coordinates = coordinates;
	}
	
	public long getCacheID() {
		return cacheID;
	}
	
	public Date getDateLogged() {
		return dateLogged;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (final Method m : getClass().getMethods()) {
			if ((!m.getName().startsWith("get") && !m.getName().startsWith("is")) ||
					m.getParameterTypes().length != 0 ||
					void.class.equals(m.getReturnType()))
				continue;

			sb.append(m.getName());
			sb.append(':');
			try {
				sb.append(m.invoke(this, new Object[0]));
			} catch (final Exception e) {
			}
			sb.append(", ");
		}
		return sb.toString();
	}
}
