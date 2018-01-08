package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GetUsersTrackablesTest extends AbstractGeocachingTest {
    @Test
    public void simpleGetUsersTrackablesTest() throws GeocachingApiException {
        List<Trackable> trackables = api.getUsersTrackables(0, 30, 0, false);
        Assert.assertEquals(8, trackables.size()); // from staging geocaching.com website
    }
}
