package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.BeforeClass;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;
  
  // Please do not use this in production. It's only for testing!!!
  protected static final String TEST_USER = "gc-java-api-premium";
  // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples
  protected static final String TEST_AUTH_TOKEN = "s0oLcAnxgA/KV78b2/R7PamqBWc=";
  
  // Geocaching API service URL for test environment mode
  protected static final String STAGGING_SERVICE_URL = "https://staging.api.groundspeak.com/Live/v6beta/geocaching.svc"; 

  @BeforeClass
  public static void setUp() throws Exception {
    api = new LiveGeocachingApi(STAGGING_SERVICE_URL);
    
    api.openSession(TEST_AUTH_TOKEN);
  }

}
