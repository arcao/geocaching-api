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
	RetractListing("Retract Listing", 25),
	Archive("Archive", 5),
	Unarchive("Unarchive", 1);

	private final String name;
	private final int id;

	private CacheLogType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static CacheLogType getByName(String name) {
		for (final CacheLogType type : values()) {
			if (type.getName().equals(name))
				return type;
		}

		return null;
	}
}
