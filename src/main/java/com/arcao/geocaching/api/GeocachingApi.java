package com.arcao.geocaching.api;

import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.FieldNote;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SimpleGeocache;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.type.LogType;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.CacheCodeFilter;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;

/**
 * Abstract class of Geocaching API
 * 
 * @author arcao
 * @since 1.0
 */
public abstract class GeocachingApi {
  protected String session;

  /**
   * Gets a session id for current logged user
   * 
   * @return session id
   * @since 1.0
   */
  public String getSession() {
    return session;
  }

  /**
   * Open a session with giving session id of already logged-in user
   * 
   * @param session
   *          session id
   * @throws GeocachingApiException
   *           If Geocaching API error occurs
   * @since 1.0
   */
  public void openSession(String session) throws GeocachingApiException {
    this.session = session;
  }

  /**
   * Open a new session and log-in user
   * 
   * @param userName
   *          user name
   * @param password
   *          password
   * @throws GeocachingApiException
   *           If Geocaching API error occurs
   * @since 1.0
   */
  public abstract void openSession(String userName, String password) throws GeocachingApiException;

  /**
   * Close current used session
   * 
   * @since 1.0
   */
  public abstract void closeSession();

  /**
   * Check if current used session is still valid
   * 
   * @return true if is valid otherwise false
   * @since 1.0
   */
  public abstract boolean isSessionValid();

  /**
   * Get a trackable object for given trackable code. This method works for
   * public trackable code and also for private (secret) trackable code.
   * 
   * @param trackableCode
   *          trackable code
   * @param trackableLogCount
   *          count of trackable logs to get
   * @return information about trackable
   * @throws GeocachingApiException
   *           If error occurs during getting information
   * @since 1.1
   */
  public abstract Trackable getTrackable(String trackableCode, int trackableLogCount) throws GeocachingApiException;

  /**
   * Get a list of trackables which is currently placed in a cache.
   * 
   * @param cacheCode
   *          cache code
   * @param startIndex
   *          count of trackables to skip
   * @param maxPerPage
   *          count of trackables to get
   * @param trackableLogCount
   *          count of trackable logs to get
   * @return list of trackables
   * @throws GeocachingApiException
   *           If error occurs during getting information
   * @since 1.1
   */
  public abstract List<Trackable> getTrackablesByCacheCode(String cacheCode, int startIndex, int maxPerPage, int trackableLogCount)
      throws GeocachingApiException;

  /**
   * Get a list of cache logs in given cache.
   * 
   * @param cacheCode
   *          cache code
   * @param startIndex
   *          count of logs to skip
   * @param maxPerPage
   *          count of logs to get
   * @return list of cache logs
   * @throws GeocachingApiException
   *           If error occurs during getting information
   * @since 1.1
   */
  public abstract List<CacheLog> getCacheLogsByCacheCode(String cacheCode, int startIndex, int maxPerPage) throws GeocachingApiException;

  /**
   * Get a basic information about cache.
   * 
   * @param cacheCode
   *          cache code
   * @return basic cache information
   * @throws GeocachingApiException
   *           If error occurs during getting information
   * @since 1.0
   */
  public SimpleGeocache getCacheSimple(String cacheCode) throws GeocachingApiException {
    List<SimpleGeocache> caches = searchForGeocaches(true, 1, 0, 0, new Filter[] {
        new CacheCodeFilter(cacheCode)
    });
    if (caches.size() == 0)
      return null;
    return caches.get(0);
  }

  /**
   * Get a full information about cache.
   * 
   * @param cacheCode
   *          cache code
   * @param cacheLogCount
   *          count of logs to get
   * @param trackableCount
   *          count of trackables to get
   * @return full cache information
   * @throws GeocachingApiException
   *           If error occurs during getting information
   * @since 1.0
   */
  public Geocache getCache(String cacheCode, int cacheLogCount, int trackableCount) throws GeocachingApiException {
    List<SimpleGeocache> caches = searchForGeocaches(false, 1, cacheLogCount, trackableCount, new Filter[] {
        new CacheCodeFilter(cacheCode)
    });
    if (caches.size() == 0)
      return null;
    return (Geocache) caches.get(0);
  }

  /**
   * Search for geocaches and return list of found.
   * 
   * @param isLite
   *          true if return a basic information about caches or fales for full
   *          information about caches
   * @param maxPerPage
   *          count of caches to get
   * @param geocacheLogCount
   *          count of logs to get
   * @param trackableLogCount
   *          count of trackables to get
   * @param filters
   *          used filters while searching
   * @return list of found caches
   * @throws GeocachingApiException
   *           If error occurs during searching caches
   * @since 1.1
   */
  public abstract List<SimpleGeocache> searchForGeocaches(boolean isLite, int maxPerPage, int geocacheLogCount, int trackableLogCount,
      Filter[] filters) throws GeocachingApiException;

  /**
   * 
   * @param isLite
   *          true if return a basic information about caches or fales for full
   *          information about caches
   * @param startIndex
   *          count of caches to skip
   * @param maxPerPage
   *          count of caches to get
   * @param geocacheLogCount
   *          count of caches to get
   * @param trackableLogCount
   *          count of trackables to get
   * @return list of found caches
   * @throws GeocachingApiException
   *           If error occurs during searching caches
   * @since 1.1
   */
  public abstract List<SimpleGeocache> getMoreGeocaches(boolean isLite, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount)
      throws GeocachingApiException;

  /**
   * Get a information about user
   * 
   * @param favoritePointData
   *          include favorites points
   * @param geocacheData
   *          include information about caches
   * @param publicProfileData
   *          include public profile information
   * @param souvenirData
   *          include souvenirs
   * @param trackableData
   *          include trackables
   * @return UserProfile object with selected information
   * @throws GeocachingApiException
   *           If error occurs during getting information
   * @since 1.2
   */
  public abstract UserProfile getYourUserProfile(boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData,
      boolean trackableData) throws GeocachingApiException;

  /**
   * Create field note and publish them or store them to list of Field notes on
   * Geocaching site.
   * 
   * @param cacheCode
   *          geocache which can own this field note
   * @param logType
   *          type of log
   * @param dateLogged
   *          when cache was found
   * @param note
   *          text of field note
   * @param publish
   *          true if publish log to cache or false to add to Field note list on
   *          Geocaching site
   * @param imageData
   *          data about image to publish together or null to not publish any
   *          image
   * @param favoriteThisCache
   *          true if add cache to favorite list otherwise false
   * @return cache log
   * @throws GeocachingApiException
   *           If error occurs during sending field note
   * @since 1.1
   */
  public abstract CacheLog createFieldNoteAndPublish(String cacheCode, LogType logType, Date dateLogged, String note, boolean publish, ImageData imageData,
      boolean favoriteThisCache) throws GeocachingApiException;

  /**
   * Create field note and publish them or store them to list of Field notes on
   * Geocaching site.
   * 
   * @param fieldNote
   *          Field note object
   * @param publish
   *          true if publish log to cache or false to add to Field note list on
   *          Geocaching site
   * @param imageData
   *          data about image to publish together or null to not publish any
   *          image
   * @param favoriteThisCache
   *          true if add cache to favorite list otherwise false
   * @return cache log
   * @throws GeocachingApiException
   *           If error occurs during sending field note
   * @since 1.1
   */
  public CacheLog createFieldNoteAndPublish(FieldNote fieldNote, boolean publish, ImageData imageData, boolean favoriteThisCache) throws GeocachingApiException {
    return createFieldNoteAndPublish(fieldNote.getCacheCode(), fieldNote.getLogType(), fieldNote.getDateLogged(), fieldNote.getNote(), publish, imageData,
        favoriteThisCache);
  }
}
