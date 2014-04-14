package com.arcao.geocaching.api.data;

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
}
