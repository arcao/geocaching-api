package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.BeforeClass;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;
  
  // Please do not use this in production. It's only for testing!!!
  protected static final String TEST_USER = "geocaching-api";
  protected static final String TEST_AUTH_TOKEN = "u5HIMAyRXz1MsZ5ylHHzOjkkuFs="; // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples

  @BeforeClass
  public static void setUp() throws Exception {
    api = new LiveGeocachingApi();
    
    api.openSession(TEST_AUTH_TOKEN);
  }

}
