package com.arcao.geocaching.api.configuration;

/**
 * OAuth 1.0 extension for {@link GeocachingApiConfiguration} interface
 * @author arcao
 *
 */
public interface OAuthGeocachingApiConfiguration extends GeocachingApiConfiguration {
  /**
   * Returns an OAuth consumer key
   * @return consumer key
   */
  String getConsumerKey();
  /**
   * Returns an OAuth consumer secret
   * @return consumer secret
   */
  String getConsumerSecret();
  
  /**
   * Returns the URL used to obtain an unauthorized Request Token
   * @return URL for unauthorized Request Token
   */
  String getOAuthRequestUrl();
  
  /**
   * Returns the URL used to obtain User authorization for Consumer access
   * @return URL for User authorization
   */
  String getOAuthAuthorizeUrl();
  
  /**
   * Returns the URL used to exchange the User-authorized Request Token for an Access Token
   * @return URL for exchanging User-authorized Request Token for Access Token
   */
  String getOAuthAccessUrl();
}