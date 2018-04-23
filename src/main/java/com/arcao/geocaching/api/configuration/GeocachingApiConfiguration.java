package com.arcao.geocaching.api.configuration;

import com.arcao.geocaching.api.configuration.impl.DefaultProductionGeocachingApiConfiguration;
import com.arcao.geocaching.api.configuration.impl.DefaultStagingGeocachingApiConfiguration;

import org.jetbrains.annotations.NotNull;

/**
 * Basic interface for configuration class.
 *
 * @author arcao
 */
public interface GeocachingApiConfiguration {
    /**
     * Production configuration.
     */
    GeocachingApiConfiguration PRODUCTION = new DefaultProductionGeocachingApiConfiguration();
    /**
     * Staging configuration.
     */
    GeocachingApiConfiguration STAGING = new DefaultStagingGeocachingApiConfiguration();

    /**
     * Returns a common part of Gecaching API service URL.
     *
     * @return url
     */
    @NotNull
    String getApiServiceEntryPointUrl();

    /**
     * Sets a specified timeout value, in milliseconds, to be used when opening
     * a communications link. A timeout of zero is interpreted as an infinite timeout.
     *
     * @return an <code>int</code> that specifies the connect timeout value in milliseconds
     * @since 1.5.16
     */
    int getConnectTimeout();

    /**
     * Sets the read timeout to a specified timeout, in milliseconds. A non-zero
     * value specifies the timeout when reading from Input stream when a connection is
     * established to a resource. A timeout of zero is interpreted as an infinite timeout.
     *
     * @return an <code>int</code> that specifies the timeout value to be used in milliseconds
     * @since 1.5.16
     */
    int getReadTimeout();
}
