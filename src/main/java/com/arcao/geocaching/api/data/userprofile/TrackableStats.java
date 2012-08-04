package com.arcao.geocaching.api.data.userprofile;

import java.lang.reflect.Method;
import java.util.List;

import com.arcao.geocaching.api.data.Trackable;

public class TrackableStats {
	private final int trackableFindCount;
	private final List<Trackable> trackableFindTypes;
	private final int trackableOwnedCount;
	private final List<Trackable> trackableOwnedTypes;

	public TrackableStats(int trackableFindCount, List<Trackable> trackableFindTypes, int trackableOwnedCount, List<Trackable> trackableOwnedTypes) {
	  this.trackableFindCount = trackableFindCount;
	  this.trackableFindTypes = trackableFindTypes;
	  this.trackableOwnedCount = trackableOwnedCount;
	  this.trackableOwnedTypes = trackableOwnedTypes;
  }

	public int getTrackableFindCount() {
  	return trackableFindCount;
  }

	public List<Trackable> getTrackableFindTypes() {
  	return trackableFindTypes;
  }

	public int getTrackableOwnedCount() {
  	return trackableOwnedCount;
  }

	public List<Trackable> getTrackableOwnedTypes() {
  	return trackableOwnedTypes;
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
