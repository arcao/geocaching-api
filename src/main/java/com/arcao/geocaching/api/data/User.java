package com.arcao.geocaching.api.data;


import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1808891631464643511L;

	private final long id;
	private final String publicGuid;
	private final String userName;
	private final String avatarUrl;
	private final Coordinates homeCoordinates;
	private final boolean admin;
	private final MemberType memberType;
	private final int findCount;
	private final int hideCount;
  private final int galleryImageCount;

  private User(Builder builder) {
    this.id = builder.id;
    this.publicGuid = builder.publicGuid;
    this.userName = builder.userName;
    this.avatarUrl = builder.avatarUrl;
    this.homeCoordinates = builder.homeCoordinates;
    this.admin = builder.admin;
    this.memberType = builder.memberType;
    this.findCount = builder.findCount;
    this.hideCount = builder.hideCount;
    this.galleryImageCount = builder.galleryImageCount;
  }

  public long getId() {
    return id;
  }

  public String getPublicGuid() {
    return publicGuid;
  }

  public String getUserName() {
    return userName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public Coordinates getHomeCoordinates() {
    return homeCoordinates;
  }

  public boolean isAdmin() {
    return admin;
  }

  public MemberType getMemberType() {
    return memberType;
  }

  public int getFindCount() {
    return findCount;
  }

  public int getHideCount() {
    return hideCount;
  }

  public int getGalleryImageCount() {
    return galleryImageCount;
  }

  @Override
	public String toString() {
    return DebugUtils.toString(this);
	}


  public static class Builder {
    private long id;
    private String publicGuid;
    private String userName;
    private String avatarUrl;
    private Coordinates homeCoordinates;
    private boolean admin;
    private MemberType memberType;
    private int findCount;
    private int hideCount;
    private int galleryImageCount;

    private Builder() {
    }

    public static Builder user() {
      return new Builder();
    }

    public Builder withId(long id) {
      this.id = id;
      return this;
    }

    public Builder withPublicGuid(String publicGuid) {
      this.publicGuid = publicGuid;
      return this;
    }

    public Builder withUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder withAvatarUrl(String avatarUrl) {
      this.avatarUrl = avatarUrl;
      return this;
    }

    public Builder withHomeCoordinates(Coordinates homeCoordinates) {
      this.homeCoordinates = homeCoordinates;
      return this;
    }

    public Builder withAdmin(boolean admin) {
      this.admin = admin;
      return this;
    }

    public Builder withMemberType(MemberType memberType) {
      this.memberType = memberType;
      return this;
    }

    public Builder withFindCount(int findCount) {
      this.findCount = findCount;
      return this;
    }

    public Builder withHideCount(int hideCount) {
      this.hideCount = hideCount;
      return this;
    }

    public Builder withGalleryImageCount(int galleryImageCount) {
      this.galleryImageCount = galleryImageCount;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
