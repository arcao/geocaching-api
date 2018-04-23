package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author arcao
 * @since 1.4
 */
public class ViewportFilter implements Filter {
    private static final String NAME = "Viewport";

    private final double topLeftLatitude;
    private final double topLeftLongitude;
    private final double bottomRightLatitude;
    private final double bottomRightLongitude;

    public ViewportFilter(double topLeftLatitude, double topLeftLongitude, double bottomRightLatitude,
                          double bottomRightLongitude) {
        this.topLeftLatitude = topLeftLatitude;
        this.topLeftLongitude = topLeftLongitude;
        this.bottomRightLatitude = bottomRightLatitude;
        this.bottomRightLongitude = bottomRightLongitude;
    }

    /**
     * @param topLeftCoordinates
     * @param bottomRightCoordinates
     * @since 1.5
     */
    public ViewportFilter(@NotNull Coordinates topLeftCoordinates, @NotNull Coordinates bottomRightCoordinates) {
        this.topLeftLatitude = topLeftCoordinates.latitude();
        this.topLeftLongitude = topLeftCoordinates.longitude();
        this.bottomRightLatitude = bottomRightCoordinates.latitude();
        this.bottomRightLongitude = bottomRightCoordinates.longitude();
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

        w.name("TopLeft");
        w.beginObject();
        w.name("Latitude").value(topLeftLatitude);
        w.name("Longitude").value(topLeftLongitude);
        w.endObject();

        w.name("BottomRight");
        w.beginObject();
        w.name("Latitude").value(bottomRightLatitude);
        w.name("Longitude").value(bottomRightLongitude);
        w.endObject();

        w.endObject();
    }
}
