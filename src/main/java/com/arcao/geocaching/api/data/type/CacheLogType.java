package com.arcao.geocaching.api.data.type;

public enum CacheLogType {
	Unknown("Unknown"),
	FoundIt("Found it"),
	DidntFindIt("Didn't find it"),
	WriteNote("Write note"),
	NeedsMaintenance("Needs Maintenance"),
	OwnerMaintenance("Owner Maintenance"),
	PublishListing("Publish Listing"),
	EnableListing("Enable Listing"),
	TemporarilyDisableListing("Temporarily Disable Listing"),
	UpdateCoordinates("Update Coordinates"),
	Announcement("Announcement"),
	WillAttend("Will Attend"),
	Attended("Attended"),
	PostReviewerNote("Post Reviewer Note"),
	NeedsArchived("Needs Archived"),
	WebcamPhotoTaken("Webcam Photo Taken"),
	RetractListing("Retract Listing");

	private final String friendlyName;

	private CacheLogType(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	@Override
	public String toString() {
		return friendlyName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public static CacheLogType parseLogType(String log) {
		for (CacheLogType type : values()) {
			if (type.getFriendlyName().equals(log))
				return type;
		}

		return WriteNote;
	}
}
