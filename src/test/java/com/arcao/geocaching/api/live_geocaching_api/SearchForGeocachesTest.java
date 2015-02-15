package com.arcao.geocaching.api.live_geocaching_api;

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

public class SearchForGeocachesTest extends AbstractGeocachingTest {
  protected final static String CACHE_CODE = "GCY81P";

  @Test
  public void getSimpleGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCacheSimple(CACHE_CODE);

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
    Assert.assertEquals(1.5F, cache.getTerrain(), 0);
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    Assert.assertNotNull(cache.getName());
    Assert.assertFalse(cache.isArchived());
    Assert.assertTrue(cache.isAvailable());
    Assert.assertFalse(cache.isFoundByUser());
    Assert.assertFalse(cache.isPremium());
    
    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
  }

  @Test
  public void getGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCache(CACHE_CODE, 5, 0);

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
    Assert.assertEquals(1.5F, cache.getTerrain(), 0);
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLatitude()));
    Assert.assertFalse(Double.isNaN(cache.getCoordinates().getLongitude()));
    Assert.assertNotNull(cache.getName());
    Assert.assertFalse(cache.isArchived());
    Assert.assertTrue(cache.isAvailable());
    Assert.assertFalse(cache.isFoundByUser());
    Assert.assertFalse(cache.isPremium());

    Assert.assertTrue(cache.isShortDescriptionHtml());
    Assert.assertTrue(cache.isLongDescriptionHtml());
    
    Assert.assertNotNull(cache.getCountryName());
    Assert.assertNotNull(cache.getStateName());

    Assert.assertEquals(8, cache.getWaypoints().size());

    Assert.assertEquals(5, cache.getCacheLogs().size());
    for (CacheLog cacheLog : cache.getCacheLogs()) {
      Assert.assertNotNull(cacheLog.getAuthor());
      Assert.assertNotNull(cacheLog.getCreated());
      Assert.assertNotNull(cacheLog.getVisited());
      Assert.assertNotSame(CacheLogType.Unknown, cacheLog.getLogType());
      Assert.assertNotNull(cacheLog.getText());
    }

    // TODO more tests
    
    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
  }

  @Test
  public void searchForGeocachesPointRadiusFilterSimpleGeocacheTest() throws Exception {
    Assert.assertEquals(
        3,
        api.searchForGeocaches(true, 3, 0, 0, new Filter[] {
            new PointRadiusFilter(50, 14, 60000)
        }).size()
        );

    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
  }
  
  @Test
  public void searchForGeocachesBookmarksExcludeFilterSimpleGeocacheTest() throws Exception {
    Assert.assertEquals(
        3,
        api.searchForGeocaches(true, 3, 0, 0, new Filter[] {
            new PointRadiusFilter(50, 14, 60000),
            new BookmarksExcludeFilter(true, null)
        }).size()
        );

    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
  }
}
