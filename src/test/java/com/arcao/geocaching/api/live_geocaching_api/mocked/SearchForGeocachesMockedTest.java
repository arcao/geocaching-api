package com.arcao.geocaching.api.live_geocaching_api.mocked;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.GeocacheLimits;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class SearchForGeocachesMockedTest extends AbstractMockedGeocachingTest {
    private static final String CACHE_CODE = "GCY81P";

    @Test
    public void getLiteGeocacheByCacheCodeNotFoundTest() throws GeocachingApiException {
        setDownloaderResponseBody("result-lite-geocache-by-cache-code-not-found.json");

        Geocache geocache = api.getGeocache(GeocachingApi.ResultQuality.LITE, CACHE_CODE, 0, 0);
        assertNull(geocache);
    }

    @Test
    public void getLiteGeocacheByCacheCodeRequestTest() throws GeocachingApiException, IOException {
        setDownloaderResponseBody("result-lite-geocache-by-cache-code.json");

        byte[] expectedRequestBody = createByteArrayFromResource("request-lite-geocache-by-cache-code.json");

        Geocache geocache = api.getGeocache(GeocachingApi.ResultQuality.LITE, CACHE_CODE, 0, 0);

        verify(downloader).post(any(URL.class), eq(expectedRequestBody));

        // ResultQuality.LITE
        assertNotNull(geocache);
        assertNotNull(geocache.owner());
        assertNotSame(MemberType.Guest, geocache.owner().memberType());
        assertEquals(CACHE_CODE, geocache.code());
        assertEquals(GeocacheType.Multi, geocache.geocacheType());
        assertNotNull(geocache.placedBy());
        assertEquals(ContainerType.Micro, geocache.containerType());
        assertNotNull(geocache.createDate());
        assertNotNull(geocache.placeDate());
        assertNotNull(geocache.lastUpdateDate());
        assertEquals(1.5F, geocache.difficulty(), 0);
        assertEquals(1.5F, geocache.terrain(), 0);
        assertFalse(Double.isNaN(geocache.coordinates().latitude()));
        assertFalse(Double.isNaN(geocache.coordinates().longitude()));
        assertNotNull(geocache.name());
        assertFalse(geocache.archived());
        assertTrue(geocache.available());
        assertFalse(geocache.foundByUser());
        assertFalse(geocache.premium());
        assertNotSame(0, geocache.imageCount());
        assertNotNull(geocache.url());
        assertNotNull(geocache.guid());

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertEquals(1, api.getLastSearchResultsFound());

        GeocacheLimits geocacheLimits = api.getLastGeocacheLimits();
        assertNotNull(geocacheLimits);
        assertEquals(131, geocacheLimits.currentGeocacheCount());
        assertEquals(9869, geocacheLimits.geocacheLeft());
        assertEquals(10000, geocacheLimits.maxGeocacheCount());
    }
}
