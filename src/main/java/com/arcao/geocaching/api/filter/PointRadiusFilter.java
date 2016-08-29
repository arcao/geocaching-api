package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PointRadiusFilter implements Filter {
    private static final String NAME = "PointRadius";

    private final long distanceInMeters;
    private final double latitude;
    private final double longitude;

    public PointRadiusFilter(double latitude, double longitude, long distanceInMeters) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceInMeters = distanceInMeters;
    }

    /**
     * @since 1.5
     */
    public PointRadiusFilter(@NotNull Coordinates coordinates, long distanceInMeters) {
        this(coordinates.latitude(), coordinates.longitude(), distanceInMeters);
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean valid() {
        return true;
    }

    @Override
    public void writeJson(@NotNull JsonWriter w) throws IOException {
        w.name(NAME);
        w.beginObject();

        w.name("DistanceInMeters").value(distanceInMeters);

        w.name("Point");
        w.beginObject();
        w.name("Latitude").value(latitude);
        w.name("Longitude").value(longitude);
        w.endObject();

        w.endObject();
    }
}
