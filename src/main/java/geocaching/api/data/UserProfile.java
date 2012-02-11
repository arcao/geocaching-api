package geocaching.api.data;

import java.lang.reflect.Method;


public class UserProfile {
	//private final List<FavoritePoint> favoritePoints;
	//private final GeocacheFindStats findStats;
	//private final PublicProfile publicProfile;
	//private final List<Souvenir> souvenirs;
	//private final GlobalStats globalStats;
	//private final TrackableStats trackableStats;
	private final User user;
	
	public UserProfile(/*List<FavoritePoint> favoritePoints, GeocacheFindStats findStats, PublicProfile publicProfile, List<Souvenir> souvenirs,
			GlobalStats globalStats, TrackableStats trackableStats,*/ User user) {
/*		this.favoritePoints = favoritePoints;
		this.findStats = findStats;
		this.publicProfile = publicProfile;
		this.souvenirs = souvenirs;
		this.globalStats = globalStats;
		this.trackableStats = trackableStats;*/
		this.user = user;
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
