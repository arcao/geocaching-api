package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.builder.JsonSerializable;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for all filters used in
 * {@link GeocachingApi#searchForGeocaches(GeocachingApi.ResultQuality, int, int, int, java.util.Collection, java.util.Collection)}
 * method.
 *
 * @author arcao
 */
public interface Filter extends JsonSerializable {
    /**
     * Get a name of filter.
     *
     * @return name of filter
     */
    @NotNull
    String name();

    /**
     * Is filter valid, mean can be used?
     *
     * @return true if the filter is valid otherwise false
     */
    boolean valid();
}
