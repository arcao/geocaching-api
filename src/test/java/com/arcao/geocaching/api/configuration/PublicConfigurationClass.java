package com.arcao.geocaching.api.configuration;


public class PublicConfigurationClass implements OAuthGeocachingApiConfiguration {

  public String getApiServiceEntryPointUrl() {
    return null;
  }

  public String getConsumerKey() {
    return null;
  }

  public String getConsumerSecret() {
    return null;
  }

  public String getOAuthAuthorizeUrl() {
    return null;
  }

  public String getOAuthRequestUrl() {
    return null;
  }

  public String getOAuthAccessUrl() {
    return null;
  }

	public int getConnectTimeout() {
		return 0;
	}

	public int getReadTimeout() {
		return 0;
	}

}
