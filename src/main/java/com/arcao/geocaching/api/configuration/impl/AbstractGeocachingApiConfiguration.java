package com.arcao.geocaching.api.configuration.impl;

import com.arcao.geocaching.api.configuration.GeocachingApiConfiguration;

public abstract class AbstractGeocachingApiConfiguration implements GeocachingApiConfiguration {
	private static final int DEFAULT_TIMEOUT = 60000;
	
	public int getConnectTimeout() {
		return DEFAULT_TIMEOUT;
	}
	
	public int getReadTimeout() {
		return DEFAULT_TIMEOUT;
	}
}
