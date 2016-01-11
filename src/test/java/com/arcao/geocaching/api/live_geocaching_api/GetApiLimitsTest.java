package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.apilimits.ApiLimitsResponse;
import com.arcao.geocaching.api.data.apilimits.MaxPerPage;
import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.exception.GeocachingApiException;

public class GetApiLimitsTest extends AbstractGeocachingTest {

  @Test
  public void GetApiLimitsCompleteTest() throws GeocachingApiException {
    ApiLimitsResponse limitsResponse = api.getApiLimits();

    Assert.assertNotNull(limitsResponse);

    ApiLimits limits = limitsResponse.getApiLimits();
    Assert.assertNotNull(limits);
    Assert.assertNotNull(limits.getCacheLimits());
    Assert.assertNotNull(limits.getLiteCacheLimits());
    Assert.assertNotNull(limits.getMethodLimits());
    Assert.assertEquals(MemberType.Premium, limits.getForMembershipType());

    MaxPerPage maxPerPage = limitsResponse.getMaxPerPage();
    Assert.assertNotNull(maxPerPage);
    Assert.assertNotSame(0, maxPerPage.getGeocaches());
    Assert.assertNotSame(0, maxPerPage.getGeocacheLogs());
    Assert.assertNotSame(0, maxPerPage.getTrackables());
    Assert.assertNotSame(0, maxPerPage.getTrackableLogs());
    Assert.assertNotSame(0, maxPerPage.getCacheNotes());
    Assert.assertNotSame(0, maxPerPage.getGalleryImages());
  }

}
