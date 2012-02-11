package geocaching.api.data.type;

public enum CacheType {
	TraditionalCache("Traditional Cache", 2),
	MultiCache("Multi-cache", 3),
	UnknownCache("Unknown Cache", 8),
	VirtualCache("Virtual Cache", 4),
	EarthCache("Earthcache", 137),
	ProjectApeCache("Project APE Cache", 9),
	LetterboxHybrid("Letterbox Hybrid", 5),
	WherigoCache("Wherigo Cache", 1858),
	EventCache("Event Cache", 6),
	MegaEventCache("Mega-Event Cache", 453),
	CacheInTrashOutEvent("Cache In Trash Out Event", 13),
	GpsAdventuresExhibit("GPS Adventures Exhibit", 1304),
	WebcamCache("Webcam Cache", 11),
	LocationlessCache("Locationless (Reverse) Cache", 12);

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

		return UnknownCache;
	}
	
	public static CacheType parseCacheTypeByGroundSpeakId(int groundSpeakId) {
		for (CacheType type : values()) {
			if (type.getGroundSpeakId() == groundSpeakId)
				return type;
		}

		return UnknownCache;
	}
	
}
