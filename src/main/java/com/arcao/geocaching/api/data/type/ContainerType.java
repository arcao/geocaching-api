package com.arcao.geocaching.api.data.type;

public enum ContainerType {
	NotChosen("Not chosen", 1),
	Micro("Micro", 2),
	Small("Small", 8),
	Regular("Regular", 3),
	Large("Large", 4),
	Huge("Huge", 5),
	Other("Other", 6);

	private final String friendlyName;
	private final int groundSpeakId;

	private ContainerType(String friendlyName, int groundSpeakId) {
		this.friendlyName = friendlyName;
		this.groundSpeakId = groundSpeakId;
	}

	@Override
	public String toString() {
		return friendlyName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}
	
	public int getGroundSpeakId() {
		return groundSpeakId;
	}

	public static ContainerType parseContainerType(String container) {
		for (ContainerType type : values()) {
			if (type.toString().equals(container))
				return type;
		}

		return Other;
	}
	
	public static ContainerType parseContainerTypeByGroundSpeakId(int groundSpeakId) {
		for (ContainerType type : values()) {
			if (type.getGroundSpeakId() == groundSpeakId)
				return type;
		}

		return Other;
	}
}
