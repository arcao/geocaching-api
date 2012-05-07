package com.arcao.geocaching.api.data.type;

public enum TrackableLogType {
  WriteNote("", 4, States.IN_CACHE | States.WITH_PERSON),
  PickUp("", 13, States.IN_CACHE),
  DropOff("", 14, States.IN_CACHE | States.WITH_PERSON),
  MarkAsMissing("", 16, States.IN_CACHE | States.WITH_PERSON),
  Grab("", 19, States.WITH_PERSON),
  Discovered("", 48, States.IN_CACHE | States.WITH_PERSON),
  MoveToCollection("", 69, States.WITH_PERSON),
  MoveToInventory("", 70, States.WITH_PERSON),
  Visited("", 75, States.WITH_PERSON),
  Unknown("", -1, States.DEFAULT);
      
  private final String friendlyName;
  private final int groundSpeakId;
  private final int allowedState;

  private TrackableLogType(String friendlyName, int groundSpeakId, int allowedState) {
    this.friendlyName = friendlyName;
    this.groundSpeakId = groundSpeakId;
    this.allowedState = allowedState;
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
  
  public boolean allowedInCache() {
    return (allowedState & States.IN_CACHE) == States.IN_CACHE;
  }
  
  public boolean allowedWithPerson() {
    return (allowedState & States.WITH_PERSON) == States.WITH_PERSON;
  }

  public static TrackableLogType parseTrackableLogType(String friendlyName) {
    for (TrackableLogType type : values()) {
      if (type.getFriendlyName().equals(friendlyName))
        return type;
    }

    return Unknown;
  }
  
  public static TrackableLogType parseTrackableLogTypeByGroundSpeakId(int groundSpeakId) {
    for (TrackableLogType type : values()) {
      if (type.getGroundSpeakId() == groundSpeakId)
        return type;
    }

    return Unknown;
  }

  private interface States {
    static final int DEFAULT = 0;
    static final int IN_CACHE = 1;
    static final int WITH_PERSON = 2;  
  }
}
