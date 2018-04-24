package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.GeocacheLimits;
import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SearchForGeocachesRequest;
import com.arcao.geocaching.api.data.UserWaypoint;
import com.arcao.geocaching.api.data.Waypoint;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.filter.BookmarksExcludeFilter;
import com.arcao.geocaching.api.filter.PointRadiusFilter;

import org.junit.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.*;

public class SearchForGeocachesTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GCY81P";

    @Test
    public void getLiteGeocacheByCacheCodeTest() throws GeocachingApiException {
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
    public void getSummaryGeocacheByCacheCodeTest() throws GeocachingApiException {
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
        List<Waypoint> waypoints = cache.waypoints();
        assertNotNull(waypoints);
        assertEquals(8, waypoints.size());
        assertNotNull(cache.hint());
        assertNotNull(cache.shortDescription());
        assertTrue(cache.shortDescriptionHtml());
        assertNotNull(cache.longDescription());
        assertTrue(cache.longDescriptionHtml());
        List<UserWaypoint> userWaypoints = cache.userWaypoints();
        assertNotNull(userWaypoints);
        assertNotSame(0, userWaypoints.size());
        List<ImageData> images = cache.images();
        assertNotNull(images);
        assertFalse(images.isEmpty());

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertEquals(1, api.getLastSearchResultsFound());
    }

    @Test
    public void getFullGeocacheByCacheCodeTest() throws GeocachingApiException {
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
        List<Waypoint> waypoints = cache.waypoints();
        assertNotNull(waypoints);
        assertEquals(8, waypoints.size());
        assertNotNull(cache.hint());
        assertNotNull(cache.shortDescription());
        assertTrue(cache.shortDescriptionHtml());
        assertNotNull(cache.longDescription());
        assertTrue(cache.longDescriptionHtml());
        List<ImageData> images = cache.images();
        assertNotNull(images);
        assertFalse(images.isEmpty());

        // ResultQuality.FULL
        assertNotNull(cache.countryName());
        assertNotNull(cache.stateName());

        List<GeocacheLog> geocacheLogs = cache.geocacheLogs();
        assertNotNull(geocacheLogs);
        assertEquals(5, geocacheLogs.size());
        for (GeocacheLog geocacheLog : geocacheLogs) {
            assertNotNull(geocacheLog.author());
            assertNotNull(geocacheLog.created());
            assertNotNull(geocacheLog.visited());
            assertNotSame(GeocacheLogType.Unknown, geocacheLog.logType());
            assertNotNull(geocacheLog.text());
        }

        EnumSet<AttributeType> attributes = cache.attributes();
        assertNotNull(attributes);
        assertFalse(attributes.isEmpty());
        assertNotNull(cache.countryName());
        assertNotNull(cache.stateName());

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertEquals(1, api.getLastSearchResultsFound());
    }

    @Test
    public void searchForGeocachesPointRadiusFilterSimpleGeocacheTest() throws GeocachingApiException {
        assertEquals(3,
                api.searchForGeocaches(SearchForGeocachesRequest.builder()
                        .resultQuality(GeocachingApi.ResultQuality.LITE)
                        .maxPerPage(3)
                        .geocacheLogCount(0)
                        .trackableLogCount(0)
                        .addFilter(new PointRadiusFilter(50, 14, 60000))
                        .build()
                ).size()
        );

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertNotSame(0, api.getLastSearchResultsFound());
    }

    @Test
    public void searchForGeocachesBookmarksExcludeFilterSimpleGeocacheTest() throws GeocachingApiException {
        assertEquals(3,
                api.searchForGeocaches(SearchForGeocachesRequest.builder()
                        .resultQuality(GeocachingApi.ResultQuality.LITE)
                        .maxPerPage(3)
                        .geocacheLogCount(0)
                        .trackableLogCount(0)
                        .addFilter(new PointRadiusFilter(50, 14, 60000))
                        .addFilter(new BookmarksExcludeFilter(true))
                        .build()
                ).size()
        );

        GeocacheLimits limits = api.getLastGeocacheLimits();
        assertNotNull(limits);
        assertNotSame(0, api.getLastSearchResultsFound());
    }

    @Test
    public void getMoreGeocachesTest() throws GeocachingApiException {
        assertEquals(3,
                api.searchForGeocaches(SearchForGeocachesRequest.builder()
                        .resultQuality(GeocachingApi.ResultQuality.LITE)
                        .maxPerPage(3)
                        .geocacheLogCount(0)
                        .trackableLogCount(0)
                        .addFilter(new PointRadiusFilter(50, 14, 60000))
                        .build()
                ).size()
        );

        assertEquals(3,
                api.getMoreGeocaches(GeocachingApi.ResultQuality.LITE, 3, 3, 0, 0).size()
        );
    }
}
