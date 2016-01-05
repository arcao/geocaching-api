package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.GeocacheStatus;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.impl.live_geocaching_api.exception.LiveGeocachingApiException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetGeocacheStatusTest extends AbstractGeocachingTest {
  private static final List<String> ZERO_CACHE_CODES = Collections.emptyList();
  private static final List<String> ONE_CACHE_CODE = Arrays.asList("GCXZE8");
  private static final List<String> TWO_CACHE_CODES = Arrays.asList("GCXZE8", "GCY81P");

  @Test(expected = LiveGeocachingApiException.class)
  public void zeroGetGeocacheStatusTest() throws Exception {
    api.getGeocacheStatus(ZERO_CACHE_CODES);

    Assert.fail();
  }

  @Test
  public void oneGetGeocacheStatusTest() throws Exception {
    List<GeocacheStatus> geocacheStatusList = api.getGeocacheStatus(ONE_CACHE_CODE);

    Assert.assertNotNull(geocacheStatusList);
    Assert.assertEquals(1, geocacheStatusList.size());

    GeocacheStatus status = geocacheStatusList.get(0);
    Assert.assertEquals(ONE_CACHE_CODE.get(0), status.getCacheCode());
    Assert.assertEquals("TB Hotel Skalni mlyn", status.getCacheName());
    Assert.assertEquals(GeocacheType.Mystery, status.getCacheType());

    Assert.assertFalse(status.isArchived());
    Assert.assertTrue(status.isAvailable());
    Assert.assertFalse(status.isPremium());

    Assert.assertNotSame(0, status.getTrackableCount());
  }

  @Test
  public void twoGetGeocacheStatusTest() throws Exception {
    List<GeocacheStatus> geocacheStatusList = api.getGeocacheStatus(TWO_CACHE_CODES);

    Assert.assertNotNull(geocacheStatusList);
    Assert.assertEquals(2, geocacheStatusList.size());
  }

}
