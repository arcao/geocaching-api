package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class Trackable {
	private final long id;
	private final String guid;
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

	public Trackable(long id, String guid, String name, String goal, String description,
			String trackableTypeName, String trackableTypeImage,
			User owner, String currentCacheCode,
			User currentOwner, String trackingNumber, Date created,
			boolean allowedToBeCollected, boolean inCollection, boolean archived, List<TrackableLog> trackableLogs, List<ImageData> images) {
		this.id = id;
		this.guid = guid;
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
		
		this.trackableLogs = trackableLogs;
		this.images = images;

		lookupCode = "";
	}
	
	public long getId() {
	  return id;
  }

	public String getGuid() {
		return guid;
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
	
	public List<ImageData> getImages() {
	  return images;
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
