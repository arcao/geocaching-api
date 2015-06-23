package com.arcao.geocaching.api.data.bookmarks;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class BookmarkList implements Serializable {
  private static final long serialVersionUID = 2322622811124797813L;

  private final int id;
  private final String guid;
  private final String name;
  private final String description;
  private final int itemCount;
  private final boolean shared;
  private final boolean isPublic;
  private final boolean archived;
  private final boolean special;
  private final int type;

  public BookmarkList(int id, String guid, String name, String description, int itemCount, boolean shared, boolean isPublic, boolean archived, boolean special, int type) {
    this.id = id;
    this.guid = guid;
    this.name = name;
    this.description = description;
    this.itemCount = itemCount;
    this.shared = shared;
    this.isPublic = isPublic;
    this.archived = archived;
    this.special = special;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public String getGuid() {
    return guid;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getItemCount() {
    return itemCount;
  }

  public boolean isShared() {
    return shared;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public boolean isArchived() {
    return archived;
  }

  public boolean isSpecial() {
    return special;
  }

  public int getType() {
    return type;
  }

  @Override
  public String toString() {
    return DebugUtils.toString(this);
  }
}
