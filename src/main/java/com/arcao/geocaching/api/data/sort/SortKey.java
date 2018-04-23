package com.arcao.geocaching.api.data.sort;

import org.jetbrains.annotations.NotNull;

public enum SortKey {
    Distance("Distance"),
    FavoritePoint("FavoritePoint"),
    Difficulty("Difficulty"),
    Terrain("Terrain"),
    PlaceDate("PlaceDate"),
    FoundDate("FoundDate"),
    ContainerSize("ContainerSize"),
    LastVisitDate("DateLastVisited"),
    GeocacheName("GeocacheName"),
    FoundDateOfFoundByUser("FoundDateOfFoundByUser");

    /**
     * Key id.
     */
    @NotNull public final String id;

    SortKey(@NotNull String id) {
        this.id = id;
    }
}
