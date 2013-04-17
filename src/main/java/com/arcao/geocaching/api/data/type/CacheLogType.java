package com.arcao.geocaching.api.data.type;

public enum CacheLogType {
	Unknown("Unknown", 0),
	FoundIt("Found it", 2),
	DidntFindIt("Didn't find it", 3),
	WriteNote("Write note", 4),
	NeedsMaintenance("Needs Maintenance", 45),
	OwnerMaintenance("Owner Maintenance", 46),
	PublishListing("Publish Listing", 24),
	EnableListing("Enable Listing", 23),
	TemporarilyDisableListing("Temporarily Disable Listing", 22),
	UpdateCoordinates("Update Coordinates", 47),
	Announcement("Announcement", 74),
	WillAttend("Will Attend", 9),
	Attended("Attended", 10),
	PostReviewerNote("Post Reviewer Note", 68),
	NeedsArchived("Needs Archived", 7),
	WebcamPhotoTaken("Webcam Photo Taken", 11),
	RetractListing("Retract Listing", 25);

	private final String friendlyName;
	private final int groundspeakId;

	private CacheLogType(String friendlyName, int groundspeakId) {
		this.friendlyName = friendlyName;
		this.groundspeakId = groundspeakId;
	}

	@Override
	public String toString() {
		return friendlyName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}
	
	public int getGroundspeakId() {
		return groundspeakId;
	}

	public static CacheLogType parseLogType(String log) {
		for (CacheLogType type : values()) {
			if (type.getFriendlyName().equals(log))
				return type;
		}

		return WriteNote;
	}
}
