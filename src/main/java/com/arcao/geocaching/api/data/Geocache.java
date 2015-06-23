package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

public class Geocache implements Serializable {
	private static final long serialVersionUID = 7938069911500506011L;

  // ResultQuality.LITE
  private final boolean archived;
  private final boolean available;
  private final GeocacheType geocacheType;
  private final boolean favoritable;
  private final String code;
  private final ContainerType containerType;
  private final Date lastUpdateDate;
  private final Date lastVisitDate;
  private final float difficulty;
  private final int favoritePoints;
  private final Date foundDate;
  private final String personalNote;
  private final boolean favoritedByUser;
  private final boolean foundByUser;
  private final long id;
  private final int imageCount; // only LITE and FULL
  private final boolean premium;
  private final boolean recommended;
  private final Coordinates coordinates;
  private final String name;
  private final User owner;
  private final String placedBy;
  private final Date publishDate;
  private final float terrain;
  private final int trackableCount; // only LITE and FULL
  private final Date placeDate;
  private final String url;
  private final String guid;

  // ResultQuality.SUMMARY
  private final List<Waypoint> waypoints;
  private final String hint;
  private final String longDescription;
  private final boolean longDescriptionHtml;
  private final String shortDescription;
  private final boolean shortDescriptionHtml;
  private final List<Trackable> trackables;
  private final List<UserWaypoint> userWaypoints;

  // ResultQuality.FULL
  private final EnumSet<AttributeType> attributes;
  private final String countryName;
  private final Date createDate;
  private final List<GeocacheLog> geocacheLogs;
  private final List<ImageData> images;
  private final String stateName;

  public Geocache(
          // ResultQuality.LITE
          boolean archived,
          boolean available,
          GeocacheType geocacheType,
          boolean favoritable,
          String code,
          ContainerType containerType,
          Date lastUpdateDate,
          Date lastVisitDate,
          float difficulty,
          int favoritePoints,
          Date foundDate,
          String personalNote,
          boolean favoritedByUser,
          boolean foundByUser,
          long id,
          int imageCount,
          boolean premium,
          boolean recommended,
          Coordinates coordinates,
          String name,
          User owner,
          String placedBy,
          Date publishDate,
          float terrain,
          int trackableCount,
          Date placeDate,
          String url,
          String guid,

          // ResultQuality.SUMMARY
          List<Waypoint> waypoints,
          String hint,
          String longDescription,
          boolean longDescriptionHtml,
          String shortDescription,
          boolean shortDescriptionHtml,
          List<Trackable> trackables,
          List<UserWaypoint> userWaypoints,

          // ResultQuality.FULL
          EnumSet<AttributeType> attributes,
          String countryName,
          Date createDate,
          List<GeocacheLog> geocacheLogs,
          List<ImageData> images,
          String stateName) {
    this.archived = archived;
    this.available = available;
    this.geocacheType = geocacheType;
    this.favoritable = favoritable;
    this.code = code;
    this.containerType = containerType;
    this.lastUpdateDate = lastUpdateDate;
    this.lastVisitDate = lastVisitDate;
    this.difficulty = difficulty;
    this.favoritePoints = favoritePoints;
    this.foundDate = foundDate;
    this.personalNote = personalNote;
    this.favoritedByUser = favoritedByUser;
    this.foundByUser = foundByUser;
    this.id = id;
    this.imageCount = imageCount;
    this.premium = premium;
    this.recommended = recommended;
    this.coordinates = coordinates;
    this.name = name;
    this.owner = owner;
    this.placedBy = placedBy;
    this.publishDate = publishDate;
    this.terrain = terrain;
    this.trackableCount = trackableCount;
    this.placeDate = placeDate;
    this.url = url;
    this.guid = guid;
    this.waypoints = waypoints != null ? waypoints : Collections.<Waypoint>emptyList();
    this.hint = hint;
    this.longDescription = longDescription;
    this.longDescriptionHtml = longDescriptionHtml;
    this.shortDescription = shortDescription;
    this.shortDescriptionHtml = shortDescriptionHtml;
    this.trackables = trackables != null ? trackables : Collections.<Trackable>emptyList();
    this.userWaypoints = userWaypoints != null ? userWaypoints : Collections.<UserWaypoint>emptyList();
    this.attributes = attributes != null ? attributes : EnumSet.noneOf(AttributeType.class);
    this.countryName = countryName;
    this.createDate = createDate;
    this.geocacheLogs = geocacheLogs != null ? geocacheLogs : Collections.<GeocacheLog>emptyList();
    this.images = images != null ? images : Collections.<ImageData>emptyList();
    this.stateName = stateName;
  }

  public boolean isArchived() {
    return archived;
  }

  public boolean isAvailable() {
    return available;
  }

  public GeocacheType getGeocacheType() {
    return geocacheType;
  }

  public boolean isFavoritable() {
    return favoritable;
  }

  public String getCode() {
    return code;
  }

  public ContainerType getContainerType() {
    return containerType;
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

  public int getFavoritePoints() {
    return favoritePoints;
  }

  public Date getFoundDate() {
    return foundDate;
  }

  public String getPersonalNote() {
    return personalNote;
  }

  public boolean isFavoritedByUser() {
    return favoritedByUser;
  }

  public boolean isFoundByUser() {
    return foundByUser;
  }

  public long getId() {
    return id;
  }

  public int getImageCount() {
    return imageCount;
  }

  public boolean isPremium() {
    return premium;
  }

  public boolean isRecommended() {
    return recommended;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public String getName() {
    return name;
  }

  public User getOwner() {
    return owner;
  }

  public String getPlacedBy() {
    return placedBy;
  }

  public Date getPublishDate() {
    return publishDate;
  }

  public float getTerrain() {
    return terrain;
  }

  public int getTrackableCount() {
    return trackableCount;
  }

  public Date getPlaceDate() {
    return placeDate;
  }

  public String getUrl() {
    return url;
  }

  public String getGuid() {
    return guid;
  }

  public List<Waypoint> getWaypoints() {
    return waypoints;
  }

  public String getHint() {
    return hint;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public boolean isLongDescriptionHtml() {
    return longDescriptionHtml;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public boolean isShortDescriptionHtml() {
    return shortDescriptionHtml;
  }

  public List<Trackable> getTrackables() {
    return trackables;
  }

  public List<UserWaypoint> getUserWaypoints() {
    return userWaypoints;
  }

  public EnumSet<AttributeType> getAttributes() {
    return attributes;
  }

  public String getCountryName() {
    return countryName;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public List<GeocacheLog> getGeocacheLogs() {
    return geocacheLogs;
  }

  public List<ImageData> getImages() {
    return images;
  }

  public String getStateName() {
    return stateName;
  }

  @Override
  public String toString() {
    return DebugUtils.toString(this);
  }


  public static class Builder {
    private boolean archived;
    private boolean available;
    private GeocacheType geocacheType;
    private boolean favoritable;
    private String code;
    private ContainerType containerType;
    private Date lastUpdateDate;
    private Date lastVisitDate;
    private float difficulty;
    private int favoritePoints;
    private Date foundDate;
    private String personalNote;
    private boolean favoritedByUser;
    private boolean foundByUser;
    private long id;
    private int imageCount;
    private boolean premium;
    private boolean recommended;
    private Coordinates coordinates;
    private String name;
    private User owner;
    private String placedBy;
    private Date publishDate;
    private float terrain;
    private int trackableCount;
    private Date placeDate;
    private String url;
    private String guid;
    private List<Waypoint> waypoints;
    private String hint;
    private String longDescription;
    private boolean longDescriptionHtml;
    private String shortDescription;
    private boolean shortDescriptionHtml;
    private List<Trackable> trackables;
    private List<UserWaypoint> userWaypoints;
    private EnumSet<AttributeType> attributes;
    private String countryName;
    private Date createDate;
    private List<GeocacheLog> geocacheLogs;
    private List<ImageData> images;
    private String stateName;

    private Builder() {
    }

    public static Builder geocache() {
      return new Builder();
    }

    public Builder withArchived(boolean archived) {
      this.archived = archived;
      return this;
    }

    public Builder withAvailable(boolean available) {
      this.available = available;
      return this;
    }

    public Builder withCacheType(GeocacheType geocacheType) {
      this.geocacheType = geocacheType;
      return this;
    }

    public Builder withFavoritable(boolean favoritable) {
      this.favoritable = favoritable;
      return this;
    }

    public Builder withCode(String code) {
      this.code = code;
      return this;
    }

    public Builder withContainerType(ContainerType containerType) {
      this.containerType = containerType;
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

    public Builder withFavoritePoints(int favoritePoints) {
      this.favoritePoints = favoritePoints;
      return this;
    }

    public Builder withFoundDate(Date foundDate) {
      this.foundDate = foundDate;
      return this;
    }

    public Builder withPersonalNote(String personalNote) {
      this.personalNote = personalNote;
      return this;
    }

    public Builder withFavoritedByUser(boolean favoritedByUser) {
      this.favoritedByUser = favoritedByUser;
      return this;
    }

    public Builder withFoundByUser(boolean foundByUser) {
      this.foundByUser = foundByUser;
      return this;
    }

    public Builder withId(long id) {
      this.id = id;
      return this;
    }

    public Builder withImageCount(int imageCount) {
      this.imageCount = imageCount;
      return this;
    }

    public Builder withPremium(boolean premium) {
      this.premium = premium;
      return this;
    }

    public Builder withRecommended(boolean recommended) {
      this.recommended = recommended;
      return this;
    }

    public Builder withCoordinates(Coordinates coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
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

    public Builder withPublishDate(Date publishDate) {
      this.publishDate = publishDate;
      return this;
    }

    public Builder withTerrain(float terrain) {
      this.terrain = terrain;
      return this;
    }

    public Builder withTrackableCount(int trackableCount) {
      this.trackableCount = trackableCount;
      return this;
    }

    public Builder withPlaceDate(Date placeDate) {
      this.placeDate = placeDate;
      return this;
    }

    public Builder withUrl(String url) {
      this.url = url;
      return this;
    }

    public Builder withGuid(String guid) {
      this.guid = guid;
      return this;
    }

    public Builder withWaypoints(List<Waypoint> waypoints) {
      this.waypoints = waypoints;
      return this;
    }

    public Builder withHint(String hint) {
      this.hint = hint;
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

    public Builder withShortDescription(String shortDescription) {
      this.shortDescription = shortDescription;
      return this;
    }

    public Builder withShortDescriptionHtml(boolean shortDescriptionHtml) {
      this.shortDescriptionHtml = shortDescriptionHtml;
      return this;
    }

    public Builder withTrackables(List<Trackable> trackables) {
      this.trackables = trackables;
      return this;
    }

    public Builder withUserWaypoints(List<UserWaypoint> userWaypoints) {
      this.userWaypoints = userWaypoints;
      return this;
    }

    public Builder withAttributes(EnumSet<AttributeType> attributes) {
      this.attributes = attributes;
      return this;
    }

    public Builder withCountryName(String countryName) {
      this.countryName = countryName;
      return this;
    }

    public Builder withCreateDate(Date createDate) {
      this.createDate = createDate;
      return this;
    }

    public Builder withCacheLogs(List<GeocacheLog> geocacheLogs) {
      this.geocacheLogs = geocacheLogs;
      return this;
    }

    public Builder withImages(List<ImageData> images) {
      this.images = images;
      return this;
    }

    public Builder withStateName(String stateName) {
      this.stateName = stateName;
      return this;
    }

    public Geocache build() {
      return new Geocache(
              // ResultQuality.LITE
              archived,
              available,
              geocacheType,
              favoritable,
              code,
              containerType,
              lastUpdateDate,
              lastVisitDate,
              difficulty,
              favoritePoints,
              foundDate,
              personalNote,
              favoritedByUser,
              foundByUser,
              id,
              imageCount,
              premium,
              recommended,
              coordinates,
              name,
              owner,
              placedBy,
              publishDate,
              terrain,
              trackableCount,
              placeDate,
              url,
              guid,

              // ResultQuality.SUMMARY
              waypoints,
              hint,
              longDescription,
              longDescriptionHtml,
              shortDescription,
              shortDescriptionHtml,
              trackables,
              userWaypoints,

              // ResultQuality.FULL
              attributes,
              countryName,
              createDate,
              geocacheLogs,
              images,
              stateName
      );
    }
  }
}

