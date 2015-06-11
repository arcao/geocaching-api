package com.arcao.geocaching.api.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheFindStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;
import com.arcao.geocaching.api.util.DebugUtils;


public class UserProfile implements Serializable {
	private static final long serialVersionUID = 872593420072537813L;

	private final FavoritePointStats favoritePointsStats;
	private final GeocacheFindStats geocacheFindStats;
	private final PublicProfile publicProfile;
	private final List<Souvenir> souvenirs;
	private final GlobalStats globalStats;
	private final TrackableStats trackableStats;
	private final User user;
	
	public UserProfile(FavoritePointStats favoritePointsStats, GeocacheFindStats geocacheFindStats, PublicProfile publicProfile, List<Souvenir> souvenirs,
			GlobalStats globalStats, TrackableStats trackableStats, User user) {
		this.favoritePointsStats = favoritePointsStats;
		this.geocacheFindStats = geocacheFindStats;
		this.publicProfile = publicProfile;
		this.souvenirs = souvenirs != null ? souvenirs : Collections.<Souvenir>emptyList();
		this.globalStats = globalStats;
		this.trackableStats = trackableStats;
		this.user = user;
	}
	
	public FavoritePointStats getFavoritePointsStats() {
	  return favoritePointsStats;
  }
	
	public GeocacheFindStats getGeocacheFindStats() {
	  return geocacheFindStats;
  }
	
	public PublicProfile getPublicProfile() {
	  return publicProfile;
  }
	
	public List<Souvenir> getSouvenirs() {
	  return souvenirs;
  }
	
	public GlobalStats getGlobalStats() {
	  return globalStats;
  }
	
	public TrackableStats getTrackableStats() {
    return trackableStats;
  }
	
	public User getUser() {
    return user;
  }
	
	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}
}
