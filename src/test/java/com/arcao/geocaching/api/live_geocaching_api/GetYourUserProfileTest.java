package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.DeviceInfo;
import com.arcao.geocaching.api.data.UserProfile;

public class GetYourUserProfileTest extends AbstractGeocachingTest {
	private static final DeviceInfo DEVICE_INFO = new DeviceInfo(
			0, 
			0, 
			"1.0", 
			"Unknown", 
			"Unknown", 
			"Unknown", 
			0, 
			"123456", 
			null, 
			null);
	
	@Test
	public void getYourUserProfileSimpleTest() throws Exception {	
		UserProfile userProfile = api.getYourUserProfile(false, false, false, false, false, false, DEVICE_INFO);
		
		Assert.assertNull(userProfile.getFavoritePointsStats());
		Assert.assertNull(userProfile.getGeocacheFindStats());
		Assert.assertNull(userProfile.getPublicProfile());
		Assert.assertNotNull(userProfile.getSouvenirs());
		Assert.assertEquals(0, userProfile.getSouvenirs().size());
		
		Assert.assertNotNull(userProfile);
		Assert.assertNotNull(userProfile.getGlobalStats());
		Assert.assertNotNull(userProfile.getUser());
	}

	@Test
  public void getYourUserProfileCompleteTest() throws Exception {
		UserProfile userProfile = api.getYourUserProfile(true, true, true, true, false, true, DEVICE_INFO);
		
		//Assert.assertNotNull(userProfile.getFavoritePointsStats());
		Assert.assertNotNull(userProfile.getGeocacheFindStats());
		Assert.assertNotNull(userProfile.getGlobalStats());
		Assert.assertNotNull(userProfile.getSouvenirs());
		Assert.assertEquals(0, userProfile.getSouvenirs().size());
		
		Assert.assertNotNull(userProfile.getPublicProfile());
		Assert.assertNotNull(userProfile);
		Assert.assertNotNull(userProfile.getUser());
	}
}
