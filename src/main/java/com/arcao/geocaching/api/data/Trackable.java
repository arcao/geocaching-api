package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.util.DebugUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Trackable implements Serializable {
	private static final long serialVersionUID = 5984147222015866863L;

	private final long id;
	private final String name;
	private final String goal;
	private final String description;
	private final String trackableTypeName;
	private final String trackableTypeImage;
	private final User owner;
	private final String currentCacheCode;
	private final User currentOwner;
	private final String trackingNumber;
	private String lookupCode;
	private final Date created;
	private final boolean allowedToBeCollected;
	private final boolean inCollection;
	private final boolean archived;

	private final List<TrackableLog> trackableLogs;
	private final List<ImageData> images;

	private static final String TRACKABLE_URL = "http://www.geocaching.com/track/details.aspx?tracker=%s";

	public Trackable(long id, String name, String goal, String description,
			String trackableTypeName, String trackableTypeImage,
			User owner, String currentCacheCode,
			User currentOwner, String trackingNumber, Date created,
			boolean allowedToBeCollected, boolean inCollection, boolean archived, List<TrackableLog> trackableLogs, List<ImageData> images) {
		this.id = id;
		this.name = name;
		this.goal = goal;
		this.description = description;
		this.trackableTypeName = trackableTypeName;
		this.trackableTypeImage = trackableTypeImage;
		this.owner = owner;
		this.currentCacheCode = currentCacheCode;
		this.currentOwner = currentOwner;
		this.trackingNumber = trackingNumber;
		this.created = created;
		this.allowedToBeCollected = allowedToBeCollected;
		this.inCollection = inCollection;
		this.archived = archived;

		this.trackableLogs = trackableLogs != null ? trackableLogs : Collections.<TrackableLog>emptyList();
		this.images = images != null ? images : Collections.<ImageData>emptyList();

		lookupCode = "";
	}

	public long getId() {
	  return id;
  }

	public String getName() {
		return name;
	}

	public String getGoal() {
		return goal;
	}

	public String getDescription() {
		return description;
	}

	public String getTrackableTypeName() {
		return trackableTypeName;
	}

	public String getTrackableTypeImage() {
		return trackableTypeImage;
	}

	public User getOwner() {
		return owner;
	}

	public String getCurrentCacheCode() {
		return currentCacheCode;
	}

	public User getCurrentOwner() {
		return currentOwner;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public String getLookupCode() {
		return lookupCode;
	}

	public void setLookupCode(String lookupCode) {
    this.lookupCode = lookupCode;
  }

	public String getTrackablePage() {
	  return String.format(TRACKABLE_URL, trackingNumber);
	}

	public Date getCreated() {
	  return created;
  }

	public boolean isAllowedToBeCollected() {
	  return allowedToBeCollected;
  }

	public boolean isInCollection() {
	  return inCollection;
  }

	public boolean isArchived() {
	  return archived;
  }

	public List<TrackableLog> getTrackableLogs() {
		return trackableLogs;
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
		private String name;
		private String goal;
		private String description;
		private String trackableTypeName;
		private String trackableTypeImage;
		private User owner;
		private String currentCacheCode;
		private User currentOwner;
		private String trackingNumber;
		private String lookupCode;
		private Date created;
		private boolean allowedToBeCollected;
		private boolean inCollection;
		private boolean archived;
		private List<TrackableLog> trackableLogs;
		private List<ImageData> images;

		private Builder() {
		}

		public static Builder trackable() {
			return new Builder();
		}

		public Builder withId(long id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withGoal(String goal) {
			this.goal = goal;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withTrackableTypeName(String trackableTypeName) {
			this.trackableTypeName = trackableTypeName;
			return this;
		}

		public Builder withTrackableTypeImage(String trackableTypeImage) {
			this.trackableTypeImage = trackableTypeImage;
			return this;
		}

		public Builder withOwner(User owner) {
			this.owner = owner;
			return this;
		}

		public Builder withCurrentCacheCode(String currentCacheCode) {
			this.currentCacheCode = currentCacheCode;
			return this;
		}

		public Builder withCurrentOwner(User currentOwner) {
			this.currentOwner = currentOwner;
			return this;
		}

		public Builder withTrackingNumber(String trackingNumber) {
			this.trackingNumber = trackingNumber;
			return this;
		}

		public Builder withLookupCode(String lookupCode) {
			this.lookupCode = lookupCode;
			return this;
		}

		public Builder withCreated(Date created) {
			this.created = created;
			return this;
		}

		public Builder withAllowedToBeCollected(boolean allowedToBeCollected) {
			this.allowedToBeCollected = allowedToBeCollected;
			return this;
		}

		public Builder withInCollection(boolean inCollection) {
			this.inCollection = inCollection;
			return this;
		}

		public Builder withArchived(boolean archived) {
			this.archived = archived;
			return this;
		}

		public Builder withTrackableLogs(List<TrackableLog> trackableLogs) {
			this.trackableLogs = trackableLogs;
			return this;
		}

		public Builder withImages(List<ImageData> images) {
			this.images = images;
			return this;
		}

		public Trackable build() {
			Trackable t = new Trackable (
							id,
							name,
							goal,
							description,
							trackableTypeName,
							trackableTypeImage,
							owner,
							currentCacheCode,
							currentOwner,
							trackingNumber,
							created,
							allowedToBeCollected,
							inCollection,
							archived,
							trackableLogs,
							images);

			if (!StringUtils.isEmpty(lookupCode)) {
				t.setLookupCode(lookupCode);
			}

			return t;
		}
	}
}
