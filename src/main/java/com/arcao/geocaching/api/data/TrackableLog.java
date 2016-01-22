package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.TrackableLogType;
import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TrackableLog implements Serializable {
	private static final long serialVersionUID = -8616502691991228922L;

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

  public TrackableLog(Builder builder) {
    this.cacheID = builder.cacheID;
    this.code = builder.code;
    this.id = builder.id;
    this.images = builder.images != null ? builder.images : Collections.<ImageData>emptyList();
    this.archived = builder.archived;
    this.guid = builder.guid;
    this.text = builder.text;
    this.type = builder.type;
    this.loggedBy = builder.loggedBy;
    this.created = builder.created;
    this.updatedCoordinates = builder.updatedCoordinates;
    this.url = builder.url;
    this.visited = builder.visited;
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
  
	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}

  public static class Builder {
    private int cacheID;
    private String code;
    private int id;
    private List<ImageData> images;
    private boolean archived;
    private String guid;
    private String text;
    private TrackableLogType type;
    private User loggedBy;
    private Date created;
    private Coordinates updatedCoordinates;
    private String url;
    private Date visited;

    private Builder() {
    }

    public static Builder trackableLog() {
      return new Builder();
    }

    public Builder withCacheID(int cacheID) {
      this.cacheID = cacheID;
      return this;
    }

    public Builder withCode(String code) {
      this.code = code;
      return this;
    }

    public Builder withId(int id) {
      this.id = id;
      return this;
    }

    public Builder withImages(List<ImageData> images) {
      this.images = images;
      return this;
    }

    public Builder withArchived(boolean archived) {
      this.archived = archived;
      return this;
    }

    public Builder withGuid(String guid) {
      this.guid = guid;
      return this;
    }

    public Builder withText(String text) {
      this.text = text;
      return this;
    }

    public Builder withType(TrackableLogType type) {
      this.type = type;
      return this;
    }

    public Builder withLoggedBy(User loggedBy) {
      this.loggedBy = loggedBy;
      return this;
    }

    public Builder withCreated(Date created) {
      this.created = created;
      return this;
    }

    public Builder withUpdatedCoordinates(Coordinates updatedCoordinates) {
      this.updatedCoordinates = updatedCoordinates;
      return this;
    }

    public Builder withUrl(String url) {
      this.url = url;
      return this;
    }

    public Builder withVisited(Date visited) {
      this.visited = visited;
      return this;
    }

    public TrackableLog build() {
      return new TrackableLog(this);
    }
  }
}
