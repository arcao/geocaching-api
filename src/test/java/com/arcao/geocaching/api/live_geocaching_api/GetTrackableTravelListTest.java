package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.TrackableTravel;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class GetTrackableTravelListTest extends AbstractGeocachingTest {
	private static final String TRACKABLE_CODE = "TB3GW4D";
	private static final double TRACKING_HISTORY = 144.03; // source: http://staging.geocaching.com/track/details.aspx?id=2862183
	
	@Test
	public void simpleGetTrackableTravelListTest() throws GeocachingApiException {
		List<TrackableTravel> travel = api.getTrackableTravelList(TRACKABLE_CODE);
		
		TrackableTravel previousPoint = null;
		double history = 0D;
		
		for(TrackableTravel point : travel) {
			if (previousPoint != null) {
				history += previousPoint.coordinates().distanceTo(point.coordinates());
			}
			
			previousPoint = point;
		}
		
		Assert.assertEquals(TRACKING_HISTORY, history / 1000D, 0.2);
	}
}
