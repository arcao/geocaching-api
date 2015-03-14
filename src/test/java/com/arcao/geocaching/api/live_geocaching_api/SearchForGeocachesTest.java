package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.CacheLimits;
import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.type.CacheLogType;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.BookmarksExcludeFilter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.PointRadiusFilter;

import java.util.Arrays;

public class SearchForGeocachesTest extends AbstractGeocachingTest {
  protected final static String CACHE_CODE = "GCY81P";

  @Test
  public void getLiteGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCache(GeocachingApi.ResultQuality.LITE, CACHE_CODE, 0, 0);

    // ResultQuality.LITE
    Assert.assertNotNull(cache);
    Assert.assertNotNull(cache.getOwner());
    Assert.assertNotSame(MemberType.Guest, cache.getOwner().getMemberType());
    Assert.assertEquals(CACHE_CODE, cache.getCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotNull(cache.getPlacedBy());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotNull(cache.getCreateDate());
    Assert.assertNotNull(cache.getPlaceDate());
    Assert.assertNotNull(cache.getLastUpdateDate());
    Assert.assertEquals(1.5F, cache.getDifficulty(), 0);
    Assert.assertEquals(1F, cache.getTerrain(), 0);
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    Assert.assertNotNull(cache.getName());
    Assert.assertFalse(cache.isArchived());
    Assert.assertFalse(cache.isAvailable());
    Assert.assertFalse(cache.isFoundByUser());
    Assert.assertFalse(cache.isPremium());
    Assert.assertNotSame(0, cache.getImageCount());
    
    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
    Assert.assertEquals(1, api.getLastSearchResultsFound());
  }

  @Test
  public void getSummaryGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCache(GeocachingApi.ResultQuality.SUMMARY, CACHE_CODE, 0, 0);

    // ResultQuality.LITE
    Assert.assertNotNull(cache);
    Assert.assertNotNull(cache.getOwner());
    Assert.assertNotSame(MemberType.Guest, cache.getOwner().getMemberType());
    Assert.assertEquals(CACHE_CODE, cache.getCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotNull(cache.getPlacedBy());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotNull(cache.getCreateDate());
    Assert.assertNotNull(cache.getPlaceDate());
    Assert.assertNotNull(cache.getLastUpdateDate());
    Assert.assertEquals(1.5F, cache.getDifficulty(), 0);
    Assert.assertEquals(1F, cache.getTerrain(), 0);
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    Assert.assertNotNull(cache.getName());
    Assert.assertFalse(cache.isArchived());
    Assert.assertFalse(cache.isAvailable());
    Assert.assertFalse(cache.isFoundByUser());
    Assert.assertFalse(cache.isPremium());
    Assert.assertSame(0, cache.getImageCount()); // NOTE: It's not returned for SUMMARY

    // ResultQuality.SUMMARY
    Assert.assertEquals(10, cache.getWaypoints().size());
    Assert.assertNotNull(cache.getHint());
    Assert.assertNotNull(cache.getShortDescription());
    Assert.assertTrue(cache.isShortDescriptionHtml());
    Assert.assertNotNull(cache.getLongDescription());
    Assert.assertTrue(cache.isLongDescriptionHtml());

    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
    Assert.assertEquals(1, api.getLastSearchResultsFound());
  }

  @Test
  public void getFullGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCache(GeocachingApi.ResultQuality.FULL, CACHE_CODE, 5, 0);

    // ResultQuality.LITE
    Assert.assertNotNull(cache);
    Assert.assertNotNull(cache.getOwner());
    Assert.assertNotSame(MemberType.Guest, cache.getOwner().getMemberType());
    Assert.assertEquals(CACHE_CODE, cache.getCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotNull(cache.getPlacedBy());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotNull(cache.getCreateDate());
    Assert.assertNotNull(cache.getPlaceDate());
    Assert.assertNotNull(cache.getLastUpdateDate());
    Assert.assertEquals(1.5F, cache.getDifficulty(), 0);
    Assert.assertEquals(1F, cache.getTerrain(), 0);
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    Assert.assertNotNull(cache.getName());
    Assert.assertFalse(cache.isArchived());
    Assert.assertFalse(cache.isAvailable());
    Assert.assertFalse(cache.isFoundByUser());
    Assert.assertFalse(cache.isPremium());
    Assert.assertNotSame(0, cache.getImageCount());

    // ResultQuality.SUMMARY
    Assert.assertEquals(10, cache.getWaypoints().size());
    Assert.assertNotNull(cache.getHint());
    Assert.assertNotNull(cache.getShortDescription());
    Assert.assertTrue(cache.isShortDescriptionHtml());
    Assert.assertNotNull(cache.getLongDescription());
    Assert.assertTrue(cache.isLongDescriptionHtml());

    // ResultQuality.FULL
    Assert.assertNotNull(cache.getCountryName());
    Assert.assertNotNull(cache.getStateName());

    Assert.assertEquals(5, cache.getCacheLogs().size());
    for (CacheLog cacheLog : cache.getCacheLogs()) {
      Assert.assertNotNull(cacheLog.getAuthor());
      Assert.assertNotNull(cacheLog.getCreated());
      Assert.assertNotNull(cacheLog.getVisited());
      Assert.assertNotSame(CacheLogType.Unknown, cacheLog.getLogType());
      Assert.assertNotNull(cacheLog.getText());
    }

    Assert.assertNotNull(cache.getAttributes());
    Assert.assertFalse(cache.getAttributes().isEmpty());
    Assert.assertNotNull(cache.getCountryName());
    Assert.assertNotNull(cache.getStateName());
    Assert.assertNotNull(cache.getImages());
    Assert.assertFalse(cache.getImages().isEmpty());

    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
    Assert.assertEquals(1, api.getLastSearchResultsFound());
  }

  @Test
  public void searchForGeocachesPointRadiusFilterSimpleGeocacheTest() throws Exception {
    Assert.assertEquals(
        3,
        api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0,
                Arrays.asList((Filter) new PointRadiusFilter(50, 14, 60000)), null).size()
        );

    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
    Assert.assertNotSame(0, api.getLastSearchResultsFound());
  }
  
  @Test
  public void searchForGeocachesBookmarksExcludeFilterSimpleGeocacheTest() throws Exception {
    Assert.assertEquals(
        3,
        api.searchForGeocaches(GeocachingApi.ResultQuality.LITE, 3, 0, 0, Arrays.asList(
                new PointRadiusFilter(50, 14, 60000),
                new BookmarksExcludeFilter(true, null)
          ), null).size()
        );

    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
    Assert.assertNotSame(0, api.getLastSearchResultsFound());
  }
}
