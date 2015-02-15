package com.arcao.geocaching.api.data;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.util.DebugUtils;

public class Geocache implements Serializable {
	private static final long serialVersionUID = 7938069911500506011L;

  private final long id;
  private final String code;
  private final String name;
  private final Coordinates coordinates;
  private final CacheType cacheType;
  private final ContainerType containerType;
  private final boolean available;
  private final boolean archived;
  private final boolean premium;
  private final Date createDate;
  private final Date publishDate;
  private final Date placeDate;
  private final Date foundDate;
  private final Date lastUpdateDate;
  private final Date lastVisitDate;
  private final float difficulty;
  private final float terrain;
  private final User owner;
  private final String placedBy;
  private final boolean foundByUser;
  private final String countryName;
  private final String stateName;
  private final String shortDescription;
  private final boolean shortDescriptionHtml;
  private final String longDescription;
  private final boolean longDescriptionHtml;
  private final String hint;
  private final String personalNote;
  private final int favoritePoints;
  private final List<Waypoint> waypoints;
  private final List<UserWaypoint> userWaypoints;

  private final Boolean favoritable;
  private final Boolean recommended;
  private final Integer imageCount;
  private final Boolean favoritedByUser;
  private final Integer trackableCount;

  private final List<CacheLog> cacheLogs;
  private final List<Trackable> trackables;
  private final EnumSet<AttributeType> attributes;
  private final List<ImageData> images;

  protected Geocache(
          long id,
          String code,
          String name,
          Coordinates coordinates,
          CacheType cacheType,
          ContainerType containerType,
          boolean available,
          boolean archived,
          boolean premium,
          Date createDate,
          Date publishDate,
          Date placeDate,
          Date foundDate,
          Date lastUpdateDate,
          Date lastVisitDate,
          float difficulty,
          float terrain,
          User owner,
          String placedBy,
          boolean foundByUser,
          String countryName,
          String stateName,
          String shortDescription,
          boolean shortDescriptionHtml,
          String longDescription,
          boolean longDescriptionHtml,
          String hint,
          String personalNote,
          int favoritePoints,
          List<Waypoint> waypoints,
          List<UserWaypoint> userWaypoints,
          Boolean favoritable,
          Boolean recommended,
          Integer imageCount,
          Boolean favoritedByUser,
          Integer trackableCount,
          List<CacheLog> cacheLogs,
          List<Trackable> trackables,
          EnumSet<AttributeType> attributes,
          List<ImageData> images) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.coordinates = coordinates;
    this.cacheType = cacheType;
    this.containerType = containerType;
    this.available = available;
    this.archived = archived;
    this.premium = premium;
    this.createDate = createDate;
    this.publishDate = publishDate;
    this.placeDate = placeDate;
    this.foundDate = foundDate;
    this.lastUpdateDate = lastUpdateDate;
    this.lastVisitDate = lastVisitDate;
    this.difficulty = difficulty;
    this.terrain = terrain;
    this.owner = owner;
    this.placedBy = placedBy;
    this.foundByUser = foundByUser;
    this.countryName = countryName;
    this.stateName = stateName;
    this.shortDescription = shortDescription;
    this.shortDescriptionHtml = shortDescriptionHtml;
    this.longDescription = longDescription;
    this.longDescriptionHtml = longDescriptionHtml;
    this.hint = hint;
    this.personalNote = personalNote;
    this.favoritePoints = favoritePoints;
    this.waypoints = waypoints;
    this.userWaypoints = userWaypoints;
    this.favoritable = favoritable;
    this.recommended = recommended;
    this.imageCount = imageCount;
    this.favoritedByUser = favoritedByUser;
    this.trackableCount = trackableCount;
    this.cacheLogs = cacheLogs;
    this.trackables = trackables;
    this.attributes = attributes;
    this.images = images;
  }

  public long getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public CacheType getCacheType() {
    return cacheType;
  }

  public ContainerType getContainerType() {
    return containerType;
  }

  public boolean isAvailable() {
    return available;
  }

  public boolean isArchived() {
    return archived;
  }

  public boolean isPremium() {
    return premium;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public Date getPublishDate() {
    return publishDate;
  }

  public Date getPlaceDate() {
    return placeDate;
  }

  public Date getLastUpdateDate() {
    return lastUpdateDate;
  }

  public Date getLastVisitDate() {
    return lastVisitDate;
  }

  public float getDifficulty() {
    return difficulty;
  }

  public float getTerrain() {
    return terrain;
  }

  public User getOwner() {
    return owner;
  }

  public String getPlacedBy() {
    return placedBy;
  }

  public boolean isFoundByUser() {
    return foundByUser;
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

  public boolean isShortDescriptionHtml() {
    return shortDescriptionHtml;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public boolean isLongDescriptionHtml() {
    return longDescriptionHtml;
  }

  public String getHint() {
    return hint;
  }

  public String getPersonalNote() {
    return personalNote;
  }

  public int getFavoritePoints() {
    return favoritePoints;
  }

  public List<Waypoint> getWaypoints() {
    return waypoints;
  }

  public List<UserWaypoint> getUserWaypoints() {
    return userWaypoints;
  }

  public Boolean getFavoritable() {
    return favoritable;
  }

  public Boolean getRecommended() {
    return recommended;
  }

  public Integer getImageCount() {
    return imageCount;
  }

  public Boolean getFavoritedByUser() {
    return favoritedByUser;
  }

  public Integer getTrackableCount() {
    return trackableCount;
  }

  public List<CacheLog> getCacheLogs() {
    return cacheLogs;
  }

  public List<Trackable> getTrackables() {
    return trackables;
  }

  public EnumSet<AttributeType> getAttributes() {
    return attributes;
  }

  public List<ImageData> getImages() {
    return images;
  }

  @Override
  public String toString() {
    return DebugUtils.toString(this);
  }

  public static class Builder {
    private long id;
    private String code;
    private String name;
    private Coordinates coordinates;
    private CacheType cacheType;
    private ContainerType containerType;
    private boolean available;
    private boolean archived;
    private boolean premium;
    private Date createDate;
    private Date publishDate;
    private Date placeDate;
    private Date foundDate;
    private Date lastUpdateDate;
    private Date lastVisitDate;
    private float difficulty;
    private float terrain;
    private User owner;
    private String placedBy;
    private boolean foundByUser;
    private String countryName;
    private String stateName;
    private String shortDescription;
    private boolean shortDescriptionHtml;
    private String longDescription;
    private boolean longDescriptionHtml;
    private String hint;
    private String personalNote;
    private int favoritePoints;
    private List<Waypoint> waypoints;
    private List<UserWaypoint> userWaypoints;
    private Boolean favoritable;
    private Boolean recommended;
    private Integer imageCount;
    private Boolean favoritedByUser;
    private Integer trackableCount;
    private List<CacheLog> cacheLogs;
    private List<Trackable> trackables;
    private EnumSet<AttributeType> attributes;
    private List<ImageData> images;

    private Builder() {
    }

    public static Builder geocache() {
      return new Builder();
    }

    public Builder withId(long id) {
      this.id = id;
      return this;
    }

    public Builder withCode(String cacheCode) {
      this.code = cacheCode;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withCoordinates(Coordinates coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    public Builder withCacheType(CacheType cacheType) {
      this.cacheType = cacheType;
      return this;
    }

    public Builder withContainerType(ContainerType containerType) {
      this.containerType = containerType;
      return this;
    }

    public Builder withAvailable(boolean available) {
      this.available = available;
      return this;
    }

    public Builder withArchived(boolean archived) {
      this.archived = archived;
      return this;
    }

    public Builder withPremium(boolean premium) {
      this.premium = premium;
      return this;
    }

    public Builder withCreateDate(Date createDate) {
      this.createDate = createDate;
      return this;
    }

    public Builder withPublishDate(Date publishDate) {
      this.publishDate = publishDate;
      return this;
    }

    public Builder withPlaceDate(Date placeDate) {
      this.placeDate = placeDate;
      return this;
    }

    public Builder withFoundDate(Date foundDate) {
      this.foundDate = foundDate;
      return this;
    }

    public Builder withLastUpdateDate(Date lastUpdateDate) {
      this.lastUpdateDate = lastUpdateDate;
      return this;
    }

    public Builder withLastVisitDate(Date lastVisitDate) {
      this.lastVisitDate = lastVisitDate;
      return this;
    }

    public Builder withDifficulty(float difficulty) {
      this.difficulty = difficulty;
      return this;
    }

    public Builder withTerrain(float terrain) {
      this.terrain = terrain;
      return this;
    }

    public Builder withOwner(User owner) {
      this.owner = owner;
      return this;
    }

    public Builder withPlacedBy(String placedBy) {
      this.placedBy = placedBy;
      return this;
    }

    public Builder withFoundByUser(boolean foundByUser) {
      this.foundByUser = foundByUser;
      return this;
    }

    public Builder withCountryName(String countryName) {
      this.countryName = countryName;
      return this;
    }

    public Builder withStateName(String stateName) {
      this.stateName = stateName;
      return this;
    }

    public Builder withShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
      return this;
    }

    public Builder withShortDescriptionHtml(boolean shortDescriptionHtml) {
      this.shortDescriptionHtml = shortDescriptionHtml;
      return this;
    }

    public Builder withLongDescription(String longDescription) {
      this.longDescription = longDescription;
      return this;
    }

    public Builder withLongDescriptionHtml(boolean longDescriptionHtml) {
      this.longDescriptionHtml = longDescriptionHtml;
      return this;
    }

    public Builder withHint(String hint) {
      this.hint = hint;
      return this;
    }

    public Builder withPersonalNote(String personalNote) {
      this.personalNote = personalNote;
      return this;
    }

    public Builder withFavoritePoints(int favoritePoints) {
      this.favoritePoints = favoritePoints;
      return this;
    }

    public Builder withWaypoints(List<Waypoint> waypoints) {
      this.waypoints = waypoints;
      return this;
    }

    public Builder withUserWaypoints(List<UserWaypoint> userWaypoints) {
      this.userWaypoints = userWaypoints;
      return this;
    }

    public Builder withFavoritable(Boolean favoritable) {
      this.favoritable = favoritable;
      return this;
    }

    public Builder withRecommended(Boolean recommended) {
      this.recommended = recommended;
      return this;
    }

    public Builder withImageCount(Integer imageCount) {
      this.imageCount = imageCount;
      return this;
    }

    public Builder withFavoritedByUser(Boolean favoritedByUser) {
      this.favoritedByUser = favoritedByUser;
      return this;
    }

    public Builder withTrackableCount(Integer trackableCount) {
      this.trackableCount = trackableCount;
      return this;
    }

    public Builder withCacheLogs(List<CacheLog> cacheLogs) {
      this.cacheLogs = cacheLogs;
      return this;
    }

    public Builder withTrackables(List<Trackable> trackables) {
      this.trackables = trackables;
      return this;
    }

    public Builder withAttributes(EnumSet<AttributeType> attributes) {
      this.attributes = attributes;
      return this;
    }

    public Builder withImages(List<ImageData> images) {
      this.images = images;
      return this;
    }

    public Geocache build() {
      return new Geocache(
              id,
              code,
              name,
              coordinates,
              cacheType,
              containerType,
              available,
              archived,
              premium,
              createDate,
              publishDate,
              placeDate,
              foundDate,
              lastUpdateDate,
              lastVisitDate,
              difficulty,
              terrain,
              owner,
              placedBy,
              foundByUser,
              countryName,
              stateName,
              shortDescription,
              shortDescriptionHtml,
              longDescription,
              longDescriptionHtml,
              hint,
              personalNote,
              favoritePoints,
              waypoints,
              userWaypoints,
              favoritable,
              recommended,
              imageCount,
              favoritedByUser,
              trackableCount,
              cacheLogs,
              trackables,
              attributes,
              images);
    }
  }
}

