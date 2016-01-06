package com.arcao.geocaching.api.data.sort;

import java.io.Serializable;

public class SortBy implements Serializable {
  private static final long serialVersionUID = -8572570659481989738L;

  /** The key to be used for sorting */
  public final SortKey key;
  /** The order to be used for sorting */
  public final SortOrder order;

  public SortBy(SortKey key, SortOrder order) {
    this.key = key;
    this.order = order;
  }
}
