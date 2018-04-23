package com.arcao.geocaching.api.data.apilimits;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Container class for an ApiLimits method response.
 *
 * @author arcao
 */
@AutoValue
public abstract class ApiLimitsResponse implements Serializable {
    private static final long serialVersionUID = 2753514511831397947L;

    /**
     * Returns an api limits.
     *
     * @return api limits
     */
    public abstract ApiLimits apiLimits();

    /**
     * Returns an information about max per page values.
     *
     * @return max per page values
     */
    public abstract MaxPerPage maxPerPage();

    public static ApiLimitsResponse create(ApiLimits apiLimits, MaxPerPage maxPerPage) {
        return new AutoValue_ApiLimitsResponse(apiLimits, maxPerPage);
    }
}
