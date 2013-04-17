package com.arcao.geocaching.api;

import java.util.List;

import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.FieldNote;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SimpleGeocache;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.CacheCodeFilter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;

/**
 * Abstract class of Geocaching API
 * 
 * @author arcao
 * @since 1.4.3
 */
public abstract class AbstractGeocachingApi implements GeocachingApi {
  protected String session;

  public String getSession() {
    return session;
  }

  public void openSession(String session) throws GeocachingApiException {
    this.session = session;
  }
  
  public SimpleGeocache getCacheSimple(String cacheCode) throws GeocachingApiException {
    List<SimpleGeocache> caches = searchForGeocaches(true, 1, 0, 0, new Filter[] {
        new CacheCodeFilter(cacheCode)
    });
    if (caches.size() == 0)
      return null;
    return caches.get(0);
  }

  public Geocache getCache(String cacheCode, int cacheLogCount, int trackableCount) throws GeocachingApiException {
    List<SimpleGeocache> caches = searchForGeocaches(false, 1, cacheLogCount, trackableCount, new Filter[] {
        new CacheCodeFilter(cacheCode)
    });
    if (caches.size() == 0)
      return null;
    return (Geocache) caches.get(0);
  }

  public CacheLog createFieldNoteAndPublish(FieldNote fieldNote, boolean publish, ImageData imageData, boolean favoriteThisCache) throws GeocachingApiException {
    return createFieldNoteAndPublish(fieldNote.getCacheCode(), fieldNote.getLogType(), fieldNote.getDateLogged(), fieldNote.getNote(), publish, imageData,
        favoriteThisCache);
  }
  
  @Deprecated
  public UserProfile getYourUserProfile(boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData, boolean trackableData) throws GeocachingApiException {  	
  	return getYourUserProfile(false, favoritePointData, geocacheData, publicProfileData, souvenirData, trackableData, null);
  }
}
