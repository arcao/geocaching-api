package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.builder.JsonSerializable;

/**
 * Interface for all filters used in
 * {@link GeocachingApi#searchForGeocaches(boolean, int, int, int, Filter[])}
 * method.
 *
 * @author arcao
 */
public interface Filter extends JsonSerializable {
    /**
     * Get a name of filter
     *
     * @return name of filter
     */
    String getName();

    /**
     * Is filter valid, mean can be used?
     *
     * @return true if the filter is valid otherwise false
     */
    boolean isValid();
}
