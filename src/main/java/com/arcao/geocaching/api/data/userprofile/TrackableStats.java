package com.arcao.geocaching.api.data.userprofile;

import java.io.Serializable;
import java.util.List;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.util.DebugUtils;

public class TrackableStats implements Serializable {
	private static final long serialVersionUID = 8539963736459413400L;

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
    return DebugUtils.toString(this);
	}
}
