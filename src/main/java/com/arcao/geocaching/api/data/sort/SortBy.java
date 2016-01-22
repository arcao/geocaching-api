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

  /**
   * The key to be used for sorting
   * @return sorting key
   */
  public SortKey getKey() {
    return key;
  }

  /**
   * The order to be used for sorting
   * @return sorting order
   */
  public SortOrder getOrder() {
    return order;
  }
}
