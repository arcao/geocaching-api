package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.List;

import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheFindStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;


public class UserProfile {
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
		this.souvenirs = souvenirs;
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
			} catch (Exception e) {
			}
			sb.append(", ");
		}
		return sb.toString();
	}
}
