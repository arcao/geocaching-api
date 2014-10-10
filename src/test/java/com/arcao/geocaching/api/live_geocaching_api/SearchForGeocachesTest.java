package com.arcao.geocaching.api.live_geocaching_api;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.data.CacheLimits;
import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.SimpleGeocache;
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
    SimpleGeocache cache = api.getCacheSimple(CACHE_CODE);

    Assert.assertNotNull(cache);
    Assert.assertNotNull(cache.getAuthor());
    Assert.assertNotSame(MemberType.Guest, cache.getAuthor().getMemberType());
    Assert.assertEquals(CACHE_CODE, cache.getCacheCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotSame("", cache.getContactName());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotSame(new Date(0), cache.getCreated());
    Assert.assertNotSame(new Date(0), cache.getPlaced());
    Assert.assertNotSame(new Date(0), cache.getLastUpdated());
    Assert.assertEquals(1.5F, cache.getDifficultyRating(), 0);
    Assert.assertNotSame(Double.NaN, cache.getLatitude());
    Assert.assertNotSame(Double.NaN, cache.getLongitude());
    Assert.assertNotSame("", cache.getName());
    Assert.assertEquals(1.5F, cache.getTerrainRating(), 0);
    Assert.assertEquals(false, cache.isArchived());
    Assert.assertEquals(true, cache.isAvailable());
    Assert.assertEquals(false, cache.isFound());
    Assert.assertEquals(false, cache.isPremiumListing());
    
    CacheLimits limits = api.getLastCacheLimits();
    Assert.assertNotNull(limits);
  }

  @Test
  public void getGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCache(CACHE_CODE, 5, -1);

    Assert.assertNotNull(cache);
    Assert.assertNotNull(cache.getAuthor());
    Assert.assertNotSame(MemberType.Guest, cache.getAuthor().getMemberType());
    Assert.assertEquals(CACHE_CODE, cache.getCacheCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotSame("", cache.getContactName());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotSame(new Date(0), cache.getCreated());
    Assert.assertNotSame(new Date(0), cache.getPlaced());
    Assert.assertNotSame(new Date(0), cache.getLastUpdated());
    Assert.assertNotSame(new Date(0), cache.getLastVisited());
    Assert.assertEquals(1.5F, cache.getDifficultyRating(), 0);
    Assert.assertNotSame(Double.NaN, cache.getLatitude());
    Assert.assertNotSame(Double.NaN, cache.getLongitude());
    Assert.assertNotSame("", cache.getName());
    Assert.assertEquals(1.5F, cache.getTerrainRating(), 0);
    Assert.assertEquals(false, cache.isArchived());
    Assert.assertEquals(true, cache.isAvailable());
    Assert.assertEquals(false, cache.isFound());
    Assert.assertEquals(false, cache.isPremiumListing());

    Assert.assertEquals(true, cache.isShortDescriptionHtml());
    Assert.assertEquals(true, cache.isLongDescriptionHtml());
    
    Assert.assertNotSame("", cache.getCountryName());
    Assert.assertNotSame("", cache.getStateName());

    Assert.assertEquals(8, cache.getWaypoints().size());

    Assert.assertEquals(5, cache.getCacheLogs().size());
    for (CacheLog cacheLog : cache.getCacheLogs()) {
      Assert.assertNotSame("", cacheLog.getAuthor());
      Assert.assertNotSame(new Date(0), cacheLog.getCreated());
      Assert.assertNotSame(new Date(0), cacheLog.getVisited());
      Assert.assertNotSame(CacheLogType.Unknown, cacheLog.getLogType());
      Assert.assertNotSame("", cacheLog.getText());
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
