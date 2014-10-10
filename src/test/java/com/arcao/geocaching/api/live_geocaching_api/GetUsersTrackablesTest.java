package com.arcao.geocaching.api.live_geocaching_api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.exception.GeocachingApiException;

public class GetUsersTrackablesTest extends AbstractGeocachingTest {
	@Test
	public void simpleGetUsersTrackablesTest() throws GeocachingApiException {
		List<Trackable> trackables = api.getUsersTrackables(0, 30, 0, false);
		Assert.assertEquals(2, trackables.size()); // from staging geocaching.com website
	}
}
