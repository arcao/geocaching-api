package com.arcao.geocaching.api.configuration.impl;

import org.jetbrains.annotations.NotNull;

public class DefaultStagingGeocachingApiConfiguration extends AbstractGeocachingApiConfiguration {
    private static final String STAGING_ENTRY_POINT_URL = "https://staging.api.groundspeak.com/Live/v6beta/geocaching.svc";

    @NotNull
    @Override
    public String getApiServiceEntryPointUrl() {
        return STAGING_ENTRY_POINT_URL;
    }
}
