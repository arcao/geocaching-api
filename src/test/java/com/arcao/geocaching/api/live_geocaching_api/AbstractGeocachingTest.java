package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;
import org.junit.BeforeClass;

import com.arcao.geocaching.api.configuration.impl.DefaultStagingGeocachingApiConfiguration;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;

  // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples
  protected static final String TEST_AUTH_TOKEN = "XYy+i7Q/4GbIhEPfoj+sTNGmvrg=";

  @BeforeClass
  public static void setUp() throws Exception {
    api = LiveGeocachingApi.Builder.liveGeocachingApi().withConfiguration(new DefaultStagingGeocachingApiConfiguration()).build();

    api.openSession(TEST_AUTH_TOKEN);
  }
}
