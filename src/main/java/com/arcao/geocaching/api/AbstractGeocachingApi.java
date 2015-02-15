package com.arcao.geocaching.api;

import com.arcao.geocaching.api.data.*;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.CacheCodeFilter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;

import java.util.Arrays;
import java.util.List;

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

  @Deprecated
  public Geocache getCacheSimple(String cacheCode) throws GeocachingApiException {
    return getCache(ResultQuality.LITE, cacheCode, 0, 0);
  }

  @Deprecated
  public Geocache getCache(String cacheCode, int cacheLogCount, int trackableLogCount) throws GeocachingApiException {
    return getCache(ResultQuality.FULL, cacheCode, cacheLogCount, trackableLogCount);
  }

  @Override
  public Geocache getCache(ResultQuality resultQuality, String cacheCode, int cacheLogCount, int trackableLogCount) throws GeocachingApiException {
    List<Geocache> list = searchForGeocaches(resultQuality, 1, cacheLogCount, trackableLogCount,
            Arrays.asList((Filter) new CacheCodeFilter(cacheCode)), null);

    if (list.size() == 0)
      return null;

    return list.get(0);
  }

  public CacheLog createFieldNoteAndPublish(FieldNote fieldNote, boolean publish, ImageData imageData, boolean favoriteThisCache) throws GeocachingApiException {
    return createFieldNoteAndPublish(fieldNote.getCacheCode(), fieldNote.getLogType(), fieldNote.getDateLogged(), fieldNote.getNote(), publish, imageData,
        favoriteThisCache);
  }
  
  @Deprecated
  public UserProfile getYourUserProfile(boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData, boolean trackableData) throws GeocachingApiException {  	
  	return getYourUserProfile(false, favoritePointData, geocacheData, publicProfileData, souvenirData, trackableData, null);
  }

  @Override
  @Deprecated
  public List<Geocache> searchForGeocaches(boolean isLite, int maxPerPage, int geocacheLogCount, int trackableLogCount, Filter[] filters) throws GeocachingApiException {
    return searchForGeocaches(isLite ? ResultQuality.LITE : ResultQuality.FULL, maxPerPage, geocacheLogCount, trackableLogCount, Arrays.asList(filters), null);
  }

  @Override
  @Deprecated
  public List<Geocache> getMoreGeocaches(boolean isLite, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount) throws GeocachingApiException {
    return getMoreGeocaches(isLite ? ResultQuality.LITE : ResultQuality.FULL, startIndex, maxPerPage, geocacheLogCount, trackableLogCount);
  }
}
