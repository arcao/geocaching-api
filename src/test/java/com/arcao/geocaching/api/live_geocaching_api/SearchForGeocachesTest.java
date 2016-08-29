package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.GeocacheLimits;
import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.filter.BookmarksExcludeFilter;
import com.arcao.geocaching.api.filter.Filter;
import com.arcao.geocaching.api.filter.PointRadiusFilter;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class SearchForGeocachesTest extends AbstractGeocachingTest {
    protected final static String CACHE_CODE = "GCY81P";

    @Test
    public void getLiteGeocacheByCacheCodeTest() throws Exception {
        Geocache cache = api.getGeocache(GeocachingApi.ResultQuality.LITE, CACHE_CODE, 0, 0);

        // ResultQuality.LITE
        assertNotNull(cache);
        assertNotNull(cache.owner());
        assertNotSame(MemberType.Guest, cache.owner().memberType());
        assertEquals(CACHE_CODE, cache.code());
        assertEquals(GeocacheType.Multi, cache.geocacheType());
        assertNotNull(cache.placedBy());
        assertEquals(ContainerType.Micro, cache.containerType());
        assertNotNull(cache.createDate());
        assertNotNull(cache.placeDate());
        assertNotNull(cache.lastUpdateDate());
        assertEquals(1.5F, cache.difficulty(), 0);
        assertEquals(1.5F, cache.terrain(), 0);
        assertFalse(Double.isNaN(cache.coordinates().latitude()));
        assertFalse(Double.isNaN(cache.coordinates().longitude()));
        assertNotNull(cache.name());
        assertFalse(cache.archived());
        assertTrue(cache.available());
        assertFalse(cache.foundByUser());
        assertFalse(cache.premium());
        assertNotSame(0, cache.imageCount());
        assertNotNull(cache.url());
        assertNotNull(cache.guid());

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertEquals(1, api.getLastSearchResultsFound());
    }

    @Test
    public void getSummaryGeocacheByCacheCodeTest() throws Exception {
        Geocache cache = api.getGeocache(GeocachingApi.ResultQuality.SUMMARY, CACHE_CODE, 0, 0);

        // ResultQuality.LITE
        assertNotNull(cache);
        assertNotNull(cache.owner());
        assertNotSame(MemberType.Guest, cache.owner().memberType());
        assertEquals(CACHE_CODE, cache.code());
        assertEquals(GeocacheType.Multi, cache.geocacheType());
        assertNotNull(cache.placedBy());
        assertEquals(ContainerType.Micro, cache.containerType());
        assertNotNull(cache.createDate());
        assertNotNull(cache.placeDate());
        assertNotNull(cache.lastUpdateDate());
        assertEquals(1.5F, cache.difficulty(), 0);
        assertEquals(1.5F, cache.terrain(), 0);
        assertFalse(Double.isNaN(cache.coordinates().latitude()));
        assertFalse(Double.isNaN(cache.coordinates().longitude()));
        assertNotNull(cache.name());
        assertFalse(cache.archived());
        assertTrue(cache.available());
        assertFalse(cache.foundByUser());
        assertFalse(cache.premium());
        assertNotSame(0, cache.imageCount());
        assertNotNull(cache.url());
        assertNotNull(cache.guid());

        // ResultQuality.SUMMARY
        assertNotNull(cache.waypoints());
        assertEquals(8, cache.waypoints().size());
        assertNotNull(cache.hint());
        assertNotNull(cache.shortDescription());
        assertTrue(cache.shortDescriptionHtml());
        assertNotNull(cache.longDescription());
        assertTrue(cache.longDescriptionHtml());
        assertNotSame(0, cache.userWaypoints().size());
        assertNotNull(cache.images());
        assertFalse(cache.images().isEmpty());

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertEquals(1, api.getLastSearchResultsFound());
    }

    @Test
    public void getFullGeocacheByCacheCodeTest() throws Exception {
        Geocache cache = api.getGeocache(GeocachingApi.ResultQuality.FULL, CACHE_CODE, 5, 0);

        // ResultQuality.LITE
        assertNotNull(cache);
        assertNotNull(cache.owner());
        assertNotSame(MemberType.Guest, cache.owner().memberType());
        assertEquals(CACHE_CODE, cache.code());
        assertEquals(GeocacheType.Multi, cache.geocacheType());
        assertNotNull(cache.placedBy());
        assertEquals(ContainerType.Micro, cache.containerType());
        assertNotNull(cache.createDate());
        assertNotNull(cache.placeDate());
        assertNotNull(cache.lastUpdateDate());
        assertEquals(1.5F, cache.difficulty(), 0);
        assertEquals(1.5F, cache.terrain(), 0);
        assertFalse(Double.isNaN(cache.coordinates().latitude()));
        assertFalse(Double.isNaN(cache.coordinates().longitude()));
        assertNotNull(cache.name());
        assertFalse(cache.archived());
        assertTrue(cache.available());
        assertFalse(cache.foundByUser());
        assertFalse(cache.premium());
        assertNotSame(0, cache.imageCount());
        assertNotNull(cache.url());
        assertNotNull(cache.guid());


        // ResultQuality.SUMMARY
        assertNotNull(cache.waypoints());
        assertEquals(8, cache.waypoints().size());
        assertNotNull(cache.hint());
        assertNotNull(cache.shortDescription());
        assertTrue(cache.shortDescriptionHtml());
        assertNotNull(cache.longDescription());
        assertTrue(cache.longDescriptionHtml());
        assertNotNull(cache.images());
        assertFalse(cache.images().isEmpty());

        // ResultQuality.FULL
        assertNotNull(cache.countryName());
        assertNotNull(cache.stateName());

        assertNotNull(cache.geocacheLogs());
        assertEquals(5, cache.geocacheLogs().size());
        for (GeocacheLog geocacheLog : cache.geocacheLogs()) {
            assertNotNull(geocacheLog.author());
            assertNotNull(geocacheLog.created());
            assertNotNull(geocacheLog.visited());
            assertNotSame(GeocacheLogType.Unknown, geocacheLog.logType());
            assertNotNull(geocacheLog.text());
        }

        assertNotNull(cache.attributes());
        assertFalse(cache.attributes().isEmpty());
        assertNotNull(cache.countryName());
        assertNotNull(cache.stateName());

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertEquals(1, api.getLastSearchResultsFound());
    }

    @Test
    public void searchForGeocachesPointRadiusFilterSimpleGeocacheTest() throws Exception {
        assertEquals(3,
                api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0,
                        Collections.singletonList((Filter) new PointRadiusFilter(50, 14, 60000)), null).size()
        );

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertNotSame(0, api.getLastSearchResultsFound());
    }

    @Test
    public void searchForGeocachesBookmarksExcludeFilterSimpleGeocacheTest() throws Exception {
        assertEquals(3,
                api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0, Arrays.asList(
                        new PointRadiusFilter(50, 14, 60000),
                        new BookmarksExcludeFilter(true, null)
                ), null).size()
        );

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertNotSame(0, api.getLastSearchResultsFound());
    }

    @Test
    public void getMoreGeocachesTest() throws Exception {
        assertEquals(3,
                api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0,
                        Collections.singletonList((Filter) new PointRadiusFilter(50, 14, 60000)), null).size()
        );

        assertEquals(3,
                api.getMoreGeocaches(GeocachingApi.ResultQuality.LITE, 3, 3, 0, 0).size()
        );
    }
}
