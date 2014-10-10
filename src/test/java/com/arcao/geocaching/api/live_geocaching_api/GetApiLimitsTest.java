package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.exception.GeocachingApiException;

public class GetApiLimitsTest extends AbstractGeocachingTest {

  @Test
  public void GetApiLimitsCompleteTest() throws GeocachingApiException {
    ApiLimits limits = api.getApiLimits();
    
    Assert.assertNotNull(limits);
    Assert.assertNotNull(limits.getCacheLimits());
    Assert.assertNotNull(limits.getLiteCacheLimits());
    Assert.assertNotNull(limits.getMethodLimits());
    Assert.assertEquals(MemberType.Premium, limits.getForMembershipType());
  }

}
