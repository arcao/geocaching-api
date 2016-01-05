package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.GeocacheLimits;
import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.BookmarksExcludeFilter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.PointRadiusFilter;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SearchForGeocachesTest extends AbstractGeocachingTest {
  protected final static String CACHE_CODE = "GCY81P";

  @Test
  public void getLiteGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getGeocache(GeocachingApi.ResultQuality.LITE, CACHE_CODE, 0, 0);

    // ResultQuality.LITE
    assertNotNull(cache);
    assertNotNull(cache.getOwner());
    assertNotSame(MemberType.Guest, cache.getOwner().getMemberType());
    assertEquals(CACHE_CODE, cache.getCode());
    assertEquals(GeocacheType.Multi, cache.getGeocacheType());
    assertNotNull(cache.getPlacedBy());
    assertEquals(ContainerType.Micro, cache.getContainerType());
    assertNotNull(cache.getCreateDate());
    assertNotNull(cache.getPlaceDate());
    assertNotNull(cache.getLastUpdateDate());
    assertEquals(1.5F, cache.getDifficulty(), 0);
    assertEquals(1.5F, cache.getTerrain(), 0);
    assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    assertNotNull(cache.getName());
    assertFalse(cache.isArchived());
    assertTrue(cache.isAvailable());
    assertFalse(cache.isFoundByUser());
    assertFalse(cache.isPremium());
    assertNotSame(0, cache.getImageCount());
    assertNotNull(cache.getUrl());
    assertNotNull(cache.getGuid());

    GeocacheLimits limits = api.getLastGeocacheLimits();
    assertNotNull(limits);
    assertEquals(1, api.getLastSearchResultsFound());
  }

  @Test
  public void getSummaryGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getGeocache(GeocachingApi.ResultQuality.SUMMARY, CACHE_CODE, 0, 0);

    // ResultQuality.LITE
    assertNotNull(cache);
    assertNotNull(cache.getOwner());
    assertNotSame(MemberType.Guest, cache.getOwner().getMemberType());
    assertEquals(CACHE_CODE, cache.getCode());
    assertEquals(GeocacheType.Multi, cache.getGeocacheType());
    assertNotNull(cache.getPlacedBy());
    assertEquals(ContainerType.Micro, cache.getContainerType());
    assertNotNull(cache.getCreateDate());
    assertNotNull(cache.getPlaceDate());
    assertNotNull(cache.getLastUpdateDate());
    assertEquals(1.5F, cache.getDifficulty(), 0);
    assertEquals(1.5F, cache.getTerrain(), 0);
    assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    assertNotNull(cache.getName());
    assertFalse(cache.isArchived());
    assertTrue(cache.isAvailable());
    assertFalse(cache.isFoundByUser());
    assertFalse(cache.isPremium());
    assertSame(0, cache.getImageCount()); // NOTE: It's not returned for SUMMARY
    assertNotNull(cache.getUrl());
    assertNotNull(cache.getGuid());

    // ResultQuality.SUMMARY
    assertEquals(8, cache.getWaypoints().size());
    assertNotNull(cache.getHint());
    assertNotNull(cache.getShortDescription());
    assertTrue(cache.isShortDescriptionHtml());
    assertNotNull(cache.getLongDescription());
    assertTrue(cache.isLongDescriptionHtml());
    assertNotSame(0, cache.getUserWaypoints().size());

    GeocacheLimits limits = api.getLastGeocacheLimits();
    assertNotNull(limits);
    assertEquals(1, api.getLastSearchResultsFound());
  }

  @Test
  public void getFullGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getGeocache(GeocachingApi.ResultQuality.FULL, CACHE_CODE, 5, 0);

    // ResultQuality.LITE
    assertNotNull(cache);
    assertNotNull(cache.getOwner());
    assertNotSame(MemberType.Guest, cache.getOwner().getMemberType());
    assertEquals(CACHE_CODE, cache.getCode());
    assertEquals(GeocacheType.Multi, cache.getGeocacheType());
    assertNotNull(cache.getPlacedBy());
    assertEquals(ContainerType.Micro, cache.getContainerType());
    assertNotNull(cache.getCreateDate());
    assertNotNull(cache.getPlaceDate());
    assertNotNull(cache.getLastUpdateDate());
    assertEquals(1.5F, cache.getDifficulty(), 0);
    assertEquals(1.5F, cache.getTerrain(), 0);
    assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    assertNotNull(cache.getName());
    assertFalse(cache.isArchived());
    assertTrue(cache.isAvailable());
    assertFalse(cache.isFoundByUser());
    assertFalse(cache.isPremium());
    assertNotSame(0, cache.getImageCount());
    assertNotNull(cache.getUrl());
    assertNotNull(cache.getGuid());


    // ResultQuality.SUMMARY
    assertEquals(8, cache.getWaypoints().size());
    assertNotNull(cache.getHint());
    assertNotNull(cache.getShortDescription());
    assertTrue(cache.isShortDescriptionHtml());
    assertNotNull(cache.getLongDescription());
    assertTrue(cache.isLongDescriptionHtml());

    // ResultQuality.FULL
    assertNotNull(cache.getCountryName());
    assertNotNull(cache.getStateName());

    assertEquals(5, cache.getGeocacheLogs().size());
    for (GeocacheLog geocacheLog : cache.getGeocacheLogs()) {
      assertNotNull(geocacheLog.getAuthor());
      assertNotNull(geocacheLog.getCreated());
      assertNotNull(geocacheLog.getVisited());
      assertNotSame(GeocacheLogType.Unknown, geocacheLog.getLogType());
      assertNotNull(geocacheLog.getText());
    }

    assertNotNull(cache.getAttributes());
    assertFalse(cache.getAttributes().isEmpty());
    assertNotNull(cache.getCountryName());
    assertNotNull(cache.getStateName());
    assertNotNull(cache.getImages());
    assertFalse(cache.getImages().isEmpty());

    GeocacheLimits limits = api.getLastGeocacheLimits();
    assertNotNull(limits);
    assertEquals(1, api.getLastSearchResultsFound());
  }

  @Test
  public void searchForGeocachesPointRadiusFilterSimpleGeocacheTest() throws Exception {
    assertEquals(
                  3,
                  api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0,
                                          Arrays.asList((Filter) new PointRadiusFilter(50, 14, 60000)), null).size()
    );

    GeocacheLimits limits = api.getLastGeocacheLimits();
    assertNotNull(limits);
    assertNotSame(0, api.getLastSearchResultsFound());
  }
  
  @Test
  public void searchForGeocachesBookmarksExcludeFilterSimpleGeocacheTest() throws Exception {
    assertEquals(
                  3,
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
    assertEquals(
            3,
            api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0,
                    Arrays.asList((Filter) new PointRadiusFilter(50, 14, 60000)), null).size()
    );

    assertEquals(
            3,
            api.getMoreGeocaches(GeocachingApi.ResultQuality.LITE, 3, 3, 0, 0).size()
    );
  }
}
