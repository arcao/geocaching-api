package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;

import com.arcao.geocaching.api.configuration.impl.DefaultStagingGeocachingApiConfiguration;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;

  // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples
  protected static final String STAGING_AUTH_TOKEN = "JvJcmhBJ88iDoWR107RIKpQgLLU=";

  @BeforeClass
  public static void setUp() throws Exception {
    LiveGeocachingApi.Builder builder = LiveGeocachingApi.Builder.liveGeocachingApi();

    if (Boolean.parseBoolean(StringUtils.defaultIfEmpty(System.getenv("GEOCACHING_STAGING"), "true"))) {
      builder.withConfiguration(new DefaultStagingGeocachingApiConfiguration());
    }


    api = builder.build();

    api.openSession(StringUtils.defaultIfEmpty(System.getenv("GEOCACHING_TOKEN"), STAGING_AUTH_TOKEN));
  }
}
