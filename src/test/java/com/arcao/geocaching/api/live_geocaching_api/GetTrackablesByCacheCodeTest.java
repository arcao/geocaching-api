package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GetTrackablesByCacheCodeTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GCXZE8";

    @Test
    public void simpleGetTrackableByCacheCode() throws GeocachingApiException {
        List<Trackable> trackables = api.getTrackablesByCacheCode(CACHE_CODE, 0, 1, 0);

        Assert.assertNotNull(trackables);
        Assert.assertNotSame(0, trackables.size());
    }
}
