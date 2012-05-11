package com.arcao.geocaching.api.data;

import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.TrackableLogType;

public class TrackableLog {
  private final int cacheID;
  private final String code;
  private final int id;
  private final List<ImageData> images;
  private final boolean archived;
  private final String guid;
  private final String text;
  private final TrackableLogType type;
  private final User loggedBy;
  private final Date created;
  private final Coordinates updatedCoordinates;
  private final String url;
  private final Date visited;

  public TrackableLog(int cacheID, String code, int id, List<ImageData> images, boolean archived, String guid, String text, TrackableLogType type,
      User loggedBy,
      Date created, Coordinates updatedCoordinates, String url, Date visited) {
    this.cacheID = cacheID;
    this.code = code;
    this.id = id;
    this.images = images;
    this.archived = archived;
    this.guid = guid;
    this.text = text;
    this.type = type;
    this.loggedBy = loggedBy;
    this.created = created;
    this.updatedCoordinates = updatedCoordinates;
    this.url = url;
    this.visited = visited;
  }

  public int getCacheID() {
    return cacheID;
  }

  public String getCode() {
    return code;
  }

  public int getId() {
    return id;
  }

  public List<ImageData> getImages() {
    return images;
  }

  public boolean isArchived() {
    return archived;
  }

  public String getGuid() {
    return guid;
  }

  public String getText() {
    return text;
  }

  public TrackableLogType getType() {
    return type;
  }

  public User getLoggedBy() {
    return loggedBy;
  }

  public Date getCreated() {
    return created;
  }

  public Coordinates getUpdatedCoordinates() {
    return updatedCoordinates;
  }

  public String getUrl() {
    return url;
  }

  public Date getVisited() {
    return visited;
  }
}
