package com.arcao.geocaching.api.data.type;

public enum CacheType {
	Traditional("Traditional Cache", 2),
	Multi("Multi-cache", 3),
	Unknown("Unknown Cache", 8),
	Virtual("Virtual Cache", 4),
	Earth("Earthcache", 137),
	ProjectApe("Project APE Cache", 9),
	LetterboxHybrid("Letterbox Hybrid", 5),
	Wherigo("Wherigo Cache", 1858),
	Event("Event Cache", 6),
	MegaEvent("Mega-Event Cache", 453),
	CacheInTrashOutEvent("Cache In Trash Out Event", 13),
	GpsAdventuresExhibit("GPS Adventures Exhibit", 1304),
	Webcam("Webcam Cache", 11),
	Locationless("Locationless (Reverse) Cache", 12);

	private final String friendlyName;
	private final int groundSpeakId;

	private CacheType(String friendlyName, int groundSpeakId) {
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

	public static CacheType parseCacheType(String cache) {
		for (CacheType type : values()) {
			if (type.toString().equals(cache))
				return type;
		}

		return Unknown;
	}
	
	public static CacheType parseCacheTypeByGroundSpeakId(int groundSpeakId) {
		for (CacheType type : values()) {
			if (type.getGroundSpeakId() == groundSpeakId)
				return type;
		}

		return Unknown;
	}
	
}
