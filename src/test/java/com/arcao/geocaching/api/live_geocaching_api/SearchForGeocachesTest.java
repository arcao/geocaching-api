package com.arcao.geocaching.api.live_geocaching_api;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.SimpleGeocache;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.PointRadiusFilter;

public class SearchForGeocachesTest extends AbstractGeocachingTest {
  protected final static String CACHE_CODE = "GCY81P";
  
  @Test
  public void getSimpleGeocacheByCacheCodeTest() throws Exception {
    SimpleGeocache cache = api.getCacheSimple(CACHE_CODE);
    
    Assert.assertNotNull(cache);
    Assert.assertNotSame("", cache.getAuthorGuid());
    Assert.assertNotSame("", cache.getAuthorName());
    Assert.assertEquals(CACHE_CODE, cache.getCacheCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotSame("", cache.getContactName());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotSame(new Date(0), cache.getCreated());
    Assert.assertEquals(1.5F, cache.getDifficultyRating(), 0);
    Assert.assertNotSame(Double.NaN, cache.getLatitude());
    Assert.assertNotSame(Double.NaN, cache.getLongitude());
    Assert.assertNotSame("", cache.getName());
    Assert.assertEquals(1.5F, cache.getTerrainRating(), 0);
    Assert.assertEquals(false, cache.isArchived());
    Assert.assertEquals(true, cache.isAvailable());
    Assert.assertEquals(false, cache.isFound());
    Assert.assertEquals(false, cache.isPremiumListing());
  }
  
  @Test
  public void getGeocacheByCacheCodeTest() throws Exception {
    Geocache cache = api.getCache(CACHE_CODE, 5, -1);
    
    Assert.assertNotNull(cache);
    Assert.assertNotSame("", cache.getAuthorGuid());
    Assert.assertNotSame("", cache.getAuthorName());
    Assert.assertEquals(CACHE_CODE, cache.getCacheCode());
    Assert.assertEquals(CacheType.Multi, cache.getCacheType());
    Assert.assertNotSame("", cache.getContactName());
    Assert.assertEquals(ContainerType.Micro, cache.getContainerType());
    Assert.assertNotSame(new Date(0), cache.getCreated());
    Assert.assertEquals(1.5F, cache.getDifficultyRating(), 0);
    Assert.assertNotSame(Double.NaN, cache.getLatitude());
    Assert.assertNotSame(Double.NaN, cache.getLongitude());
    Assert.assertNotSame("", cache.getName());
    Assert.assertEquals(1.5F, cache.getTerrainRating(), 0);
    Assert.assertEquals(false, cache.isArchived());
    Assert.assertEquals(true, cache.isAvailable());
    Assert.assertEquals(false, cache.isFound());
    Assert.assertEquals(false, cache.isPremiumListing());
    
    Assert.assertNotSame("", cache.getCountryName());
    Assert.assertNotSame("", cache.getStateName());
    
    Assert.assertEquals(8, cache.getWaypoints().size());
    Assert.assertEquals(5, cache.getCacheLogs().size());
    
    // TODO more tests
  }
  
  @Test
  public void searchForGeocachesPointRadiusFilterSimpleGeocacheTest() throws Exception {
    Assert.assertEquals(
        3, 
        api.searchForGeocaches(true, 3, 0, 0, new Filter[] {
            new PointRadiusFilter(50, 14, 60000)
        }).size()
     );
  }
}
