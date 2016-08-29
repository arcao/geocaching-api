package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.StatusCode;
import com.arcao.geocaching.api.exception.LiveGeocachingApiException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

public class GeocachePersonalNoteTest extends AbstractGeocachingTest {
  private final static String CACHE_CODE = "GCY81P";

  @Before
  @After
  public void cleanUp() throws Exception {
    try {
      api.deleteCachePersonalNote(CACHE_CODE);
    } catch (LiveGeocachingApiException e) {
      if (e.getStatusCode() != StatusCode.NoNoteOnThisCacheToDelete)
        throw e;
    }
  }

  @Test
  public void simpleSetGeocachePersonalNoteTest() throws GeocachingApiException {
    final String expected = "Test1234";

    Geocache geocache =  api.getGeocache(GeocachingApi.ResultQuality.FULL, CACHE_CODE, 0, 0);
    Assert.assertNotNull(geocache);
    Assert.assertThat(geocache.personalNote(), isEmptyOrNullString());

    api.setGeocachePersonalNote(CACHE_CODE, expected);

    geocache = api.getGeocache(GeocachingApi.ResultQuality.FULL, CACHE_CODE, 0, 0);
    Assert.assertNotNull(geocache);
    Assert.assertEquals(expected, geocache.personalNote());
  }
}
