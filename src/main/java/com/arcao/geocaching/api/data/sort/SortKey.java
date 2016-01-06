package com.arcao.geocaching.api.data.sort;

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

  /** Key id */
  public final String id;

  SortKey(String id) {
    this.id = id;
  }
}
