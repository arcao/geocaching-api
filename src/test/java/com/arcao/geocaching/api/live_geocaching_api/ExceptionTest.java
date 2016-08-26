package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.exception.InvalidSessionException;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GCY81P";

    @Test(expected = InvalidSessionException.class)
    public void invalidSessionExceptionTest() throws GeocachingApiException {
        api.openSession("invalid");

        api.getGeocache(GeocachingApi.ResultQuality.LITE, CACHE_CODE, 0, 0);
        Assert.fail();
    }
}
