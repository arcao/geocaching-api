package com.arcao.geocaching.api.data.type;

public enum WaypointType {
	QuestionToAnswer("Question to Answer", "puzzle.jpg"),
	FinalLocation("Final Location", "flag.jpg"),
	ParkingArea("Parking Area", "pkg.jpg"),
	Trailhead("Trailhead", "trailhead.jpg"),
	StagesOfAMulticache("Stages of a Multicache", "stage.jpg"),
	ReferencePoint("Reference Point", "waypoint.jpg");

	private final String friendlyName;
	private final String iconName;

	private WaypointType(String friendlyName, String iconName) {
		this.friendlyName = friendlyName;
		this.iconName = iconName;
	}

	@Override
	public String toString() {
		return friendlyName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public String getId() {
		return friendlyName;
	}

	public String getIconName() {
		return iconName;
	}

	public static WaypointType parseWayPointType(String waypointName) {
		for (WaypointType type : values()) {
			if (type.toString().equals(waypointName))
				return type;
		}

		return ReferencePoint;
	}
}
