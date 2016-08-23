package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonSerializable;

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
    public abstract String getName();

    /**
     * Is filter valid, mean can be used?
     *
     * @return true if the filter is valid otherwise false
     */
    public abstract boolean isValid();
}
