package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.exception.GeocachingApiException;


public class GetTrackableTest extends AbstractGeocachingTest {
	private static final String TRACKABLE_CODE = "TB3GW4D";
	private static final int TRACKABLE_LOGS_COUNT = 5;
	
	@Test
	public void simpleGetTrackableTest() throws GeocachingApiException {
		Trackable trackable = api.getTrackable(TRACKABLE_CODE, TRACKABLE_LOGS_COUNT);
		Assert.assertNotNull(trackable);
		Assert.assertNotNull(trackable.getCreated());
		Assert.assertNotNull(trackable.getOwner());
		Assert.assertNotNull(trackable.getImages());
		Assert.assertEquals(TRACKABLE_LOGS_COUNT, trackable.getTrackableLogs().size());		
	}
}
