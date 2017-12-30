package com.arcao.geocaching.api.data;


import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.WaypointType;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;

@AutoValue
public abstract class Waypoint implements Serializable {
    private static final long serialVersionUID = -7183357014183017947L;

    public abstract Coordinates coordinates();

    public abstract Date time();

    public abstract String waypointCode();

    public abstract String name();

    @Nullable
    public abstract String note();

    public abstract WaypointType waypointType();

    public String iconName() {
        return waypointType().iconName;
    }

    public static Builder builder() {
        return new AutoValue_Waypoint.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder coordinates(Coordinates coordinates);

        public abstract Builder time(Date time);

        public abstract Builder waypointCode(String waypointCode);

        public abstract Builder name(String name);

        public abstract Builder note(String note);

        public abstract Builder waypointType(WaypointType waypointType);

        public abstract Waypoint build();
    }
}
