package com.arcao.geocaching.api.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.util.DebugUtils;

public class SimpleGeocache implements Serializable {
	private static final long serialVersionUID = 8329679481723682422L;

	private final long id;
	private final String cacheCode;
	private final String name;
	private final Coordinates coordinates;
	private final CacheType cacheType;
	private final float difficultyRating;
	private final float terrainRating;
	private final User author;
	private final boolean available;
	private final boolean archived;
	private final boolean premiumListing;
	private final Date created;
	private final Date placed;
	private final Date lastUpdated;
	private final String contactName;
	private final ContainerType containerType;
	private final int trackableCount;
	private final boolean found;

	private static final DateFormat GPX_TIME_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	static {
		GPX_TIME_FMT.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
	}

	public SimpleGeocache(long id, String cacheCode, String name, Coordinates coordinates,
			CacheType cacheType, float difficultyRating,
			float terrainRating, User author,
			boolean available, boolean archived, boolean premiumListing,
			Date created, Date placed, Date lastUpdated, String contactName, ContainerType containerType,
			int trackableCount, boolean found) {
		this.id = id;
		this.cacheCode = cacheCode;
		this.name = name;
		this.coordinates = coordinates;
		this.cacheType = cacheType;
		this.difficultyRating = difficultyRating;
		this.terrainRating = terrainRating;
		this.author = author;
		this.available = available;
		this.archived = archived;
		this.premiumListing = premiumListing;
		this.created = created;
		this.placed = placed;
		this.lastUpdated = lastUpdated;
		this.contactName = contactName;
		this.containerType = containerType;
		this.trackableCount = trackableCount;
		this.found = found;
	}
	
	public long getId() {
	  return id;
  }

	public String getCacheCode() {
		return cacheCode;
	}

	public String getName() {
		return name;
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

	public CacheType getCacheType() {
		return cacheType;
	}

	public float getDifficultyRating() {
		return difficultyRating;
	}

	public float getTerrainRating() {
		return terrainRating;
	}

	public User getAuthor() {
		return author;
	}

	public boolean isAvailable() {
		return available;
	}

	public boolean isArchived() {
		return archived;
	}

	public boolean isPremiumListing() {
		return premiumListing;
	}

	public Date getCreated() {
		return created;
	}

	public Date getPlaced() {
		return placed;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public String getContactName() {
		return contactName;
	}

	public ContainerType getContainerType() {
		return containerType;
	}

	public int getTrackableCount() {
		return trackableCount;
	}

	public boolean isFound() {
		return found;
	}

	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}
}
