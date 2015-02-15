package com.arcao.geocaching.api.data.sort;

public enum SortKey {
  Distance("Distance"),
  FavoritePoint("FavoritePoint"),
  Difficulty("Difficulty"),
  Terrain("Terrain"),
  PlaceDate("PlaceDate"),
  FoundDate("FoundDate"),
  ContainerSize("ContainerSize"),
  LastVisitDate("DateLastVisited");

  private final String id;

  private SortKey(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
