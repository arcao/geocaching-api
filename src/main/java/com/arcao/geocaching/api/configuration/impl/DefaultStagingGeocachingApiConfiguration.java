package com.arcao.geocaching.api.configuration.impl;

public class DefaultStagingGeocachingApiConfiguration extends AbstractGeocachingApiConfiguration {
	protected static final String STAGGING_ENTRY_POINT_URL = "https://staging.api.groundspeak.com/Live/v6beta/geocaching.svc";

	public String getApiServiceEntryPointUrl() {
		return STAGGING_ENTRY_POINT_URL;
	}
}
