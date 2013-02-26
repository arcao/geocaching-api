package com.arcao.geocaching.api.configuration;

/**
 * Basic interface for configuration class
 * @author arcao
 *
 */
public interface GeocachingApiConfiguration {
  /**
   * Returns a common part of Gecaching API service URL
   * @return url
   */
	String getApiServiceEntryPointUrl();
}
