package com.arcao.geocaching.api.data.type;

public enum WaypointType {
    FinalLocation("Final Location", "flag.jpg"),
    ParkingArea("Parking Area", "pkg.jpg"),
    VirtualStage("Virtual Stage", "puzzle.jpg"),
    PhysicalStage("Physical Stage", "stage.jpg"),
    Trailhead("Trailhead", "trailhead.jpg"),
    ReferencePoint("Reference Point", "waypoint.jpg");

    /**
     * Friendly name
     */
    public final String name;
    /**
     * Icon image file name
     */
    public final String iconName;

    WaypointType(String name, String iconName) {
        this.name = name;
        this.iconName = iconName;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get a WaypointType from Friendly name. If Friendly name is not valid, the ReferencePoint value is returned.
     *
     * @param name Friendly name
     * @return Waypoint Type
     */
    public static WaypointType fromName(String name) {
        for (WaypointType type : values()) {
            if (type.name.equals(name))
                return type;
        }

        return ReferencePoint;
    }
}
