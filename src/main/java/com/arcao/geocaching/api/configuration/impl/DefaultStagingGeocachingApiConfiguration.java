package com.arcao.geocaching.api.configuration.impl;

public class DefaultStagingGeocachingApiConfiguration extends AbstractGeocachingApiConfiguration {
    private static final String STAGING_ENTRY_POINT_URL = "https://staging.api.groundspeak.com/Live/v6beta/geocaching.svc";

    @Override
    public String getApiServiceEntryPointUrl() {
        return STAGING_ENTRY_POINT_URL;
    }
}
