package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.BeforeClass;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.configuration.impl.DefaultStaggingGeocachingApiConfiguration;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;

  // Please do not use this in production. It's only for testing!!!
  protected static final String TEST_USER = "APIUser_508689241";
  // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples
  protected static final String TEST_AUTH_TOKEN = "wzWIiStBEkH2Ql8pyMlZw5yEqms=";

  @BeforeClass
  public static void setUp() throws Exception {
    api = new LiveGeocachingApi(new DefaultStaggingGeocachingApiConfiguration());

    api.openSession(TEST_AUTH_TOKEN);
  }
}
