package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.BeforeClass;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.configuration.impl.DefaultStagingGeocachingApiConfiguration;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;

  // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples
  protected static final String TEST_AUTH_TOKEN = "hFHmU3c7wnljwzCg4QodthxIuMo=";

  @BeforeClass
  public static void setUp() throws Exception {
    api = new LiveGeocachingApi.Builder().setConfiguration(new DefaultStagingGeocachingApiConfiguration()).build();

    api.openSession(TEST_AUTH_TOKEN);
  }
}
