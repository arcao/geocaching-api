package com.arcao.geocaching.api.data;

import java.io.Serializable;
import java.util.Date;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.util.DebugUtils;

public class TrackableTravel implements Serializable {
	private static final long serialVersionUID = 61007459728740881L;

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
    return DebugUtils.toString(this);
	}
}
