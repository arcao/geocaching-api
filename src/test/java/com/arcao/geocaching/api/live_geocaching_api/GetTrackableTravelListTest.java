package com.arcao.geocaching.api.live_geocaching_api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.TrackableTravel;
import com.arcao.geocaching.api.exception.GeocachingApiException;


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
				history += previousPoint.getCoordinates().distanceTo(point.getCoordinates());
			}
			
			previousPoint = point;
		}
		
		Assert.assertEquals(TRACKING_HISTORY, history / 1000D, 0.2);
	}
}
