package com.arcao.geocaching.api.data.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    /**
     * Friendly name.
     */
    @NotNull public final String name;
    /**
     * Groundspeak Id.
     */
    public final int id;
    @NotNull private final EnumSet<States> allowedState;

    TrackableLogType(@NotNull String name, int id, @NotNull EnumSet<States> allowedState) {
        this.name = name;
        this.id = id;
        this.allowedState = allowedState;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Is this TrackableLogType allowed in Geocache.
     * @return true if allowed, otherwise false
     */
    public boolean isAllowedInCache() {
        return allowedState.contains(States.IN_CACHE);
    }

    /**
     * Is this TrackableLogType allowed with Person.
     * @return true if allowed, otherwise false
     */
    public boolean isAllowedWithPerson() {
        return allowedState.contains(States.WITH_PERSON);
    }

    /**
     * Get a TrackableLogType from Friendly name. If Friendly name is not valid, the null is returned.
     *
     * @param name Friendly name
     * @return Trackable Log Type or null
     */
    @Nullable
    public static TrackableLogType fromName(@Nullable String name) {
        for (TrackableLogType type : values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        return null;
    }

    /**
     * Get a TrackableLogType from Groundspeak Id. If Groundspeak Id is not valid, the null is returned.
     *
     * @param id Groundspeak Id
     * @return Trackable Log Type or null
     */
    @Nullable
    public static TrackableLogType fromId(int id) {
        for (TrackableLogType type : values()) {
            if (type.id == id) {
                return type;
            }
        }

        return null;
    }

    private enum States {
        IN_CACHE,
        WITH_PERSON
    }
}
