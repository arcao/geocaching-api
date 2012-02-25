package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;

public class Geocache extends SimpleGeocache {
  private final String countryName;
  private final String stateName;
  private final String shortDescription;
  private final String longDescription;
  private final String hint;
  private final List<CacheLog> cacheLogs;
  private final List<Trackable> trackables;
  private final List<Waypoint> waypoints;
  private final List<AttributeType> attributes;
  private final List<UserWaypoint> userWaypoints;

  public Geocache(String cacheCode, String name, double longitude,
      double latitude, CacheType cacheType, float difficultyRating,
      float terrainRating, String authorGuid, String authorName,
      boolean available, boolean archived, boolean premiumListing,
      Date created, String contactName, ContainerType containerType,
      int trackableCount, boolean found, String countryName, String stateName, String shortDescription,
      String longDescrition, String hint, List<CacheLog> cacheLogs,
      List<Trackable> trackables, List<Waypoint> waypoints, List<AttributeType> attributes, List<UserWaypoint> userWaypoints) {
    super(cacheCode, name, longitude, latitude, cacheType, difficultyRating,
        terrainRating, authorGuid, authorName, available, archived,
        premiumListing, created, contactName, containerType, trackableCount, found);
    this.countryName = countryName;
    this.stateName = stateName;
    this.shortDescription = shortDescription;
    this.longDescription = longDescrition;
    this.hint = hint;
    this.cacheLogs = cacheLogs;
    this.trackables = trackables;
    this.waypoints = waypoints;
    this.attributes = attributes;
    this.userWaypoints = userWaypoints;
  }
  
  public String getCountryName() {
    return countryName;
  }
  
  public String getStateName() {
    return stateName;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public String getHint() {
    return hint;
  }

  public List<CacheLog> getCacheLogs() {
    return cacheLogs;
  }

  public List<Trackable> getTravelBugs() {
    return trackables;
  }

  public List<Waypoint> getWaypoints() {
    return waypoints;
  }

  public List<AttributeType> getAttributes() {
    return attributes;
  }

  public List<UserWaypoint> getUserWaypoints() {
    return userWaypoints;
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
      } catch (Exception e) {}
      sb.append(", ");
    }
    return sb.toString();
  }
}
