package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SuppressWarnings("Duplicates")
public class GetTrackableTest extends AbstractGeocachingTest {
    private static final String TRACKABLE_CODE = "TB3GW4D";
    private static final String LOOKUP_CODE = "PCFYTA";
    private static final int TRACKABLE_LOGS_COUNT = 5;

    @Test
    public void simpleGetTrackableTest() throws GeocachingApiException {
        Trackable trackable = api.getTrackable(TRACKABLE_CODE, TRACKABLE_LOGS_COUNT);
        assertNotNull(trackable);
        assertNotNull(trackable.created());
        assertNotNull(trackable.owner());
        assertNotNull(trackable.images());
        List<TrackableLog> trackableLogs = trackable.trackableLogs();
        assertNotNull(trackableLogs);
        assertEquals(TRACKABLE_LOGS_COUNT, trackableLogs.size());
    }

    @Test
    public void lookupCodeGetTrackableTest() throws GeocachingApiException {
        Trackable trackable = api.getTrackable(LOOKUP_CODE, TRACKABLE_LOGS_COUNT);
        assertNotNull(trackable);
        assertNotNull(trackable.created());
        assertNotNull(trackable.owner());
        assertNotNull(trackable.images());
        List<TrackableLog> trackableLogs = trackable.trackableLogs();
        assertNotNull(trackableLogs);
        assertEquals(TRACKABLE_LOGS_COUNT, trackableLogs.size());
    }

}
