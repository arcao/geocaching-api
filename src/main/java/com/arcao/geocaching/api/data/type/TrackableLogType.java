package com.arcao.geocaching.api.data.type;

import java.util.EnumSet;

public enum TrackableLogType {
  WriteNote("Write note", 4, EnumSet.of(States.IN_CACHE, States.WITH_PERSON)),
  PickUp("Retrieve It from a Cache", 13, EnumSet.of(States.IN_CACHE)),
  DropOff("Dropped Off", 14, EnumSet.of(States.IN_CACHE, States.WITH_PERSON)),
  MarkAsMissing("Mark Missing", 16, EnumSet.of(States.IN_CACHE, States.WITH_PERSON)),
  Grab("Grab It (Not from a Cache)", 19, EnumSet.of(States.WITH_PERSON)),
  Discovered("Discovered It", 48, EnumSet.of(States.IN_CACHE, States.WITH_PERSON)),
  MoveToCollection("Move To Collection", 69, EnumSet.of(States.WITH_PERSON)),
  MoveToInventory("Move To Inventory", 70, EnumSet.of(States.WITH_PERSON)),
  Visited("Visited", 75, EnumSet.of(States.WITH_PERSON));
      
  private final String name;
  private final int id;
  private final EnumSet<States> allowedState;

  private TrackableLogType(String name, int id, EnumSet<States> allowedState) {
    this.name = name;
    this.id = id;
    this.allowedState = allowedState;
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
  
  public boolean isAllowedInCache() {
    return allowedState.contains(States.IN_CACHE);
  }
  
  public boolean isAllowedWithPerson() {
    return allowedState.contains(States.WITH_PERSON);
  }

  public static TrackableLogType getByName(String name) {
    for (TrackableLogType type : values()) {
      if (type.getName().equals(name))
        return type;
    }

    return null;
  }
  
  public static TrackableLogType getById(int id) {
    for (TrackableLogType type : values()) {
      if (type.getId() == id)
        return type;
    }

    return null;
  }

  private enum States {
    IN_CACHE,
    WITH_PERSON
  }
}
