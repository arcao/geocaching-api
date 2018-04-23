package com.arcao.geocaching.api.data.sort;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@AutoValue
public abstract class SortBy implements Serializable {
    private static final long serialVersionUID = -8572570659481989738L;

    /**
     * The key to be used for sorting.
     *
     * @return sorting key
     */
    public abstract SortKey key();

    /**
     * The order to be used for sorting.
     *
     * @return sorting order
     */
    public abstract SortOrder order();

    public static SortBy create(@NotNull SortKey key, @NotNull SortOrder order) {
        return new AutoValue_SortBy(key, order);
    }
}
