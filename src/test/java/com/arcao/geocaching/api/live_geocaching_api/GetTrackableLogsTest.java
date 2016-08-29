package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GetTrackableLogsTest extends AbstractGeocachingTest {
    private static final String TRACKABLE_CODE = "TB3GW4D";
    private static final int TRACKABLE_LOG_COUNT = 10;

    @Test
    public void simpleGetTrackableLogsTest() throws GeocachingApiException {
        List<TrackableLog> trackableLogs = api.getTrackableLogs(TRACKABLE_CODE, 0, TRACKABLE_LOG_COUNT);

        Assert.assertNotNull(trackableLogs);
        Assert.assertEquals(TRACKABLE_LOG_COUNT, trackableLogs.size());
    }
}

