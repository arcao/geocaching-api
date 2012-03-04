package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;

public class Trackable {
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
	private final String lookupCode;

	private static final String TRACKABLE_URL = "http://www.geocaching.com/track/details.aspx?tracker=%s";

	public Trackable(String guid, String name, String goal, String description,
			String trackableTypeName, String trackableTypeImage,
			User owner, String currentCacheCode,
			User currentOwner, String trackingNumber) {
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

		lookupCode = "";
	}

	public Trackable(String lookupCode, String name, String trackingNumber,
			User owner) {
		this.lookupCode = lookupCode;
		this.name = name;
		this.trackingNumber = trackingNumber;
		this.owner = owner;

		guid = "";
		goal = "";
		description = "";
		trackableTypeName = "";
		trackableTypeImage = "";
		currentCacheCode = null;
		currentOwner = null;
	}

	public Trackable(String trackingNumber, String name, String currentCacheCode) {
		this.trackingNumber = trackingNumber;
		this.name = name;
		this.currentCacheCode = currentCacheCode;

		guid = "";
		goal = "";
		description = "";
		trackableTypeName = "";
		trackableTypeImage = "";
		owner = null;
		currentOwner = null;
		lookupCode = "";
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
	
	public String getTrackablePage() {
	  return String.format(TRACKABLE_URL, trackingNumber);
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
