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

  @Override
  public Geocache getGeocache(ResultQuality resultQuality, String cacheCode, int cacheLogCount, int trackableLogCount) throws GeocachingApiException {
    List<Geocache> list = searchForGeocaches(resultQuality, 1, cacheLogCount, trackableLogCount,
            Arrays.asList((Filter) new CacheCodeFilter(cacheCode)), null);

    if (list.size() == 0)
      return null;

    return list.get(0);
  }

  public GeocacheLog createFieldNoteAndPublish(FieldNote fieldNote, boolean publish, ImageData imageData, boolean favoriteThisCache) throws GeocachingApiException {
    return createFieldNoteAndPublish(fieldNote.getCacheCode(), fieldNote.getLogType(), fieldNote.getDateLogged(), fieldNote.getNote(), publish, imageData,
        favoriteThisCache);
  }
}
