package com.arcao.geocaching.api.configuration.impl;

import org.jetbrains.annotations.NotNull;

public class DefaultProductionGeocachingApiConfiguration extends AbstractGeocachingApiConfiguration {
    private static final String PRODUCTION_ENTRY_POINT_URL = "https://api.groundspeak.com/LiveV6/geocaching.svc";

    @NotNull
    @Override
    public String getApiServiceEntryPointUrl() {
        return PRODUCTION_ENTRY_POINT_URL;
    }
}
