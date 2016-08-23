package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.apilimits.ApiLimitsResponse;
import com.arcao.geocaching.api.data.apilimits.MaxPerPage;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Test;

public class GetApiLimitsTest extends AbstractGeocachingTest {

    @Test
    public void GetApiLimitsCompleteTest() throws GeocachingApiException {
        ApiLimitsResponse limitsResponse = api.getApiLimits();

        Assert.assertNotNull(limitsResponse);

        ApiLimits limits = limitsResponse.apiLimits();
        Assert.assertNotNull(limits);
        Assert.assertNotNull(limits.cacheLimits());
        Assert.assertNotNull(limits.liteCacheLimits());
        Assert.assertNotNull(limits.methodLimits());
        Assert.assertEquals(MemberType.Premium, limits.forMembershipType());

        MaxPerPage maxPerPage = limitsResponse.maxPerPage();
        Assert.assertNotNull(maxPerPage);
        Assert.assertNotSame(0, maxPerPage.geocaches());
        Assert.assertNotSame(0, maxPerPage.geocacheLogs());
        Assert.assertNotSame(0, maxPerPage.trackables());
        Assert.assertNotSame(0, maxPerPage.trackableLogs());
        Assert.assertNotSame(0, maxPerPage.cacheNotes());
        Assert.assertNotSame(0, maxPerPage.galleryImages());
    }

}
