package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.DeviceInfo;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Test;

public class GetYourUserProfileTest extends AbstractGeocachingTest {
    private static final DeviceInfo DEVICE_INFO = DeviceInfo.builder()
            .applicationCurrentMemoryUsage(0)
            .applicationPeakMemoryUsage(0)
            .applicationSoftwareVersion("1.0")
            .deviceManufacturer("Unknown")
            .deviceName("JUnit test")
            .deviceOperatingSystem("Unknown")
            .deviceTotalMemoryInMb(0)
            .deviceUniqueId("12345")
            .build();

    @Test
    public void getYourUserProfileSimpleTest() throws GeocachingApiException {
        UserProfile userProfile = api.getYourUserProfile(false, false, false, false, false, false, DEVICE_INFO);

        Assert.assertNotNull(userProfile);

        Assert.assertNull(userProfile.favoritePointsStats());
        Assert.assertNull(userProfile.geocacheStats());
        Assert.assertNull(userProfile.trackableStats());
        Assert.assertNull(userProfile.publicProfile());
        Assert.assertNull(userProfile.souvenirs());

        Assert.assertNotNull(userProfile.globalStats());
        Assert.assertNotNull(userProfile.user());
    }

    @Test
    public void getYourUserProfileCompleteTest() throws GeocachingApiException {
        UserProfile userProfile = api.getYourUserProfile(true, true, true, true, false, true, DEVICE_INFO);

        Assert.assertNotNull(userProfile);

        //Assert.assertNotNull(userProfile.getFavoritePointsStats());
        Assert.assertNotNull(userProfile.geocacheStats());
        Assert.assertNotNull(userProfile.trackableStats());
        Assert.assertNotNull(userProfile.globalStats());
        //Assert.assertNotNull(userProfile.souvenirs());
        //Assert.assertEquals(0, userProfile.souvenirs().size());

        Assert.assertNotNull(userProfile.publicProfile());
        Assert.assertNotNull(userProfile.user());
    }
}
