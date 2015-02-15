package com.arcao.geocaching.api.data.sort;

import java.io.Serializable;

public class SortBy implements Serializable {
  private static final long serialVersionUID = -8572570659481989738L;

  private final SortKey key;
  private final SortOrder order;

  public SortBy(SortKey key, SortOrder order) {
    this.key = key;
    this.order = order;
  }

  public SortKey getKey() {
    return key;
  }

  public SortOrder getOrder() {
    return order;
  }

}
