package com.arcao.geocaching.api;

import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.*;
import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.sort.SortBy;
import com.arcao.geocaching.api.data.type.CacheLogType;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;

/**
 * Interface of Geocaching API
 * 
 * @author arcao
 * @since 1.0
 */
public interface GeocachingApi {
  enum ResultQuality {
    SUMMARY,
    LITE,
    FULL
  }


	/**
	 * Gets a session id for current logged user
	 * 
	 * @return session id
	 * @since 1.0
	 */
	String getSession();

	/**
	 * Open a session with giving session id of already logged-in user
	 * 
	 * @param session
	 *            session id
	 * @throws GeocachingApiException
	 *             If Geocaching API error occurs
	 * @since 1.0
	 */
	void openSession(String session) throws GeocachingApiException;

	/**
	 * Close current used session
	 * 
	 * @since 1.0
	 */
	void closeSession();

	/**
	 * Check if current used session is still valid
	 * 
	 * @return true if is valid otherwise false
	 * @since 1.0
	 */
	boolean isSessionValid();

	/**
	 * Get a trackable object for given trackable code. This method works for
	 * public trackable code and also for private (secret) trackable code.
	 * 
	 * @param trackableCode
	 *            trackable code
	 * @param trackableLogCount
	 *            count of trackable logs to get
	 * @return information about trackable
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.1
	 */
	Trackable getTrackable(String trackableCode, int trackableLogCount) throws GeocachingApiException;

	/**
	 * Get a list of trackables which is currently placed in a cache.
	 * 
	 * @param cacheCode
	 *            cache code
	 * @param startIndex
	 *            count of trackables to skip
	 * @param maxPerPage
	 *            count of trackables to get
	 * @param trackableLogCount
	 *            count of trackable logs to get
	 * @return list of trackables
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.1
	 */
	List<Trackable> getTrackablesByCacheCode(String cacheCode, int startIndex, int maxPerPage, int trackableLogCount) throws GeocachingApiException;

	/**
	 * Get a list of complete travel track of specified trackable.
	 * @param trackableCode
	 *            trackable code
	 * @return information about trackable travel track
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.20
	 */
	List<TrackableTravel> getTrackableTravelList(String trackableCode) throws GeocachingApiException;
	
	/**
	 * Get a list of cache logs in given cache.
	 * 
	 * @param cacheCode
	 *            cache code
	 * @param startIndex
	 *            count of logs to skip
	 * @param maxPerPage
	 *            count of logs to get
	 * @return list of cache logs
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.1
	 */
	List<CacheLog> getCacheLogsByCacheCode(String cacheCode, int startIndex, int maxPerPage) throws GeocachingApiException;

	/**
	 * Get a basic information about cache.
	 * 
	 * @param cacheCode
	 *            cache code
	 * @return basic cache information
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
   * @deprecated Will be removed in future. Please use {@link #getCache(ResultQuality, String, int, int)}
	 * @since 1.0
	 */
  @Deprecated
  Geocache getCacheSimple(String cacheCode) throws GeocachingApiException;

	/**
	 * Get a full information about cache.
	 * 
	 * @param cacheCode
	 *            cache code
	 * @param cacheLogCount
	 *            count of logs to get
	 * @param trackableLogCount
	 *            count of trackable logs to get
	 * @return full cache information
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
   * @deprecated Will be removed in future. Please use {@link #getCache(ResultQuality, String, int, int)}
	 * @since 1.0
	 */
  @Deprecated
	Geocache getCache(String cacheCode, int cacheLogCount, int trackableLogCount) throws GeocachingApiException;

  /**
   * Get an information about cache.
   *
   * @param resultQuality
   *            How much data will be returned
   * @param cacheCode
   *            cache code
   * @param cacheLogCount
   *            count of logs to get
   * @param trackableLogCount
   *            count of trackable logs to get
   * @return full cache information
   * @throws GeocachingApiException
   *             If error occurs during getting information
   * @since 1.0
   */
  Geocache getCache(ResultQuality resultQuality, String cacheCode, int cacheLogCount, int trackableLogCount) throws GeocachingApiException;

  /**
	 * Search for geocaches and return list of found.
	 * 
	 * @param isLite
	 *            true if return a basic information about caches or false for
	 *            full information about caches
	 * @param maxPerPage
	 *            count of caches to get
	 * @param geocacheLogCount
	 *            count of logs to get
	 * @param trackableLogCount
	 *            count of trackables to get
	 * @param filters
	 *            used filters while searching
	 * @return list of found caches
	 * @throws GeocachingApiException
	 *             If error occurs during searching caches
	 * @since 1.1
   * @deprecated Will be removed in future. Please use {@link #searchForGeocaches(ResultQuality, int, int, int, java.util.List, java.util.List)}
	 */
  @Deprecated
	List<Geocache> searchForGeocaches(boolean isLite, int maxPerPage, int geocacheLogCount, int trackableLogCount, Filter[] filters) throws GeocachingApiException;

  /**
   * Search for geocaches and return list of found.
   *
   * @param resultQuality
   *            How much data will be returned
   * @param maxPerPage
   *            count of caches to get
   * @param geocacheLogCount
   *            count of logs to get
   * @param trackableLogCount
   *            count of trackables to get
   * @param filters
   *            used filters while searching
   * @return list of found caches
   * @throws GeocachingApiException
   *             If error occurs during searching caches
   * @since 1.6
   */
  List<Geocache> searchForGeocaches(ResultQuality resultQuality, int maxPerPage, int geocacheLogCount, int trackableLogCount, List<Filter> filters, List<SortBy> sortByList) throws GeocachingApiException;

  /**
	 * Retrieve next geocaches searched by searchForGeocaches method.
   *
	 * @param isLite
	 *            true if return a basic information about caches or fales for
	 *            full information about caches
	 * @param startIndex
	 *            count of caches to skip
	 * @param maxPerPage
	 *            count of caches to get
	 * @param geocacheLogCount
	 *            count of caches to get
	 * @param trackableLogCount
	 *            count of trackables to get
	 * @return list of found caches
	 * @throws GeocachingApiException
	 *             If error occurs during searching caches
	 * @since 1.1
   * @deprecated Will be removed in future. Please use {@link #getMoreGeocaches(ResultQuality, int, int, int, int)}
	 */
  @Deprecated
	List<Geocache> getMoreGeocaches(boolean isLite, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount) throws GeocachingApiException;

  /**
   * Retrieve next geocaches searched by searchForGeocaches method.
   *
   * @param resultQuality
   *            How much data will be returned
   * @param startIndex
   *            count of caches to skip
   * @param maxPerPage
   *            count of caches to get
   * @param geocacheLogCount
   *            count of caches to get
   * @param trackableLogCount
   *            count of trackables to get
   * @return list of found caches
   * @throws GeocachingApiException
   *             If error occurs during searching caches
   * @since 1.6
   */
  List<Geocache> getMoreGeocaches(ResultQuality resultQuality, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount) throws GeocachingApiException;

  /**
	 * Get an information about user
	 * 
	 * @param favoritePointData
	 *            include favorites points
	 * @param geocacheData
	 *            include information about caches
	 * @param publicProfileData
	 *            include public profile information
	 * @param souvenirData
	 *            include souvenirs
	 * @param trackableData
	 *            include trackables
	 * @return UserProfile object with selected information
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.2
	 * @deprecated use
	 *             {@link #getYourUserProfile(boolean, boolean, boolean, boolean, boolean, boolean, DeviceInfo)}
	 *             instead
	 */
	@Deprecated
	UserProfile getYourUserProfile(boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData, boolean trackableData) throws GeocachingApiException;

	/**
	 * Get an information about user
	 * 
	 * @param challengesData
	 *            include challanges data
	 * @param favoritePointData
	 *            include favorites points
	 * @param geocacheData
	 *            include information about caches
	 * @param publicProfileData
	 *            include public profile information
	 * @param souvenirData
	 *            include souvenirs
	 * @param trackableData
	 *            include trackables
	 * @param deviceInfo
	 *            information about used device
	 * @return UserProfile object with selected information
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.2
	 */
	UserProfile getYourUserProfile(boolean challengesData, boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData, boolean trackableData, DeviceInfo deviceInfo) throws GeocachingApiException;

	/**
	 * Create field note and publish them or store them to list of Field notes
	 * on Geocaching site.
	 * 
	 * @param cacheCode
	 *            geocache which can own this field note
	 * @param cacheLogType
	 *            type of log
	 * @param dateLogged
	 *            when cache was found
	 * @param note
	 *            text of field note
	 * @param publish
	 *            true if publish log to cache or false to add to Field note
	 *            list on Geocaching site
	 * @param imageData
	 *            data about image to publish together or null to not publish
	 *            any image
	 * @param favoriteThisCache
	 *            true if add cache to favorite list otherwise false
	 * @return cache log
	 * @throws GeocachingApiException
	 *             If error occurs during sending field note
	 * @since 1.1
	 */
	CacheLog createFieldNoteAndPublish(String cacheCode, CacheLogType cacheLogType, Date dateLogged, String note, boolean publish, ImageData imageData, boolean favoriteThisCache) throws GeocachingApiException;

	/**
	 * Create field note and publish them or store them to list of Field notes
	 * on Geocaching site.
	 * 
	 * @param fieldNote
	 *            Field note object
	 * @param publish
	 *            true if publish log to cache or false to add to Field note
	 *            list on Geocaching site
	 * @param imageData
	 *            data about image to publish together or null to not publish
	 *            any image
	 * @param favoriteThisCache
	 *            true if add cache to favorite list otherwise false
	 * @return cache log
	 * @throws GeocachingApiException
	 *             If error occurs during sending field note
	 * @since 1.1
	 */
	CacheLog createFieldNoteAndPublish(FieldNote fieldNote, boolean publish, ImageData imageData, boolean favoriteThisCache) throws GeocachingApiException;

	/**
	 * Store a personal note for specified cache. If note parameter is null or
	 * empty the note will be removed.
	 * 
	 * @param cacheCode
	 *            geocache which can own this personal note
	 * @param note
	 *            personal note
	 * @throws GeocachingApiException
	 *             If error occurs during personal note storing
	 * @since 1.4.2
	 */
	void setCachePersonalNote(String cacheCode, String note) throws GeocachingApiException;

	/**
	 * Get a list of trackable logs for given public trackable code.
	 * 
	 * @param trackableCode
	 *            public trackable code
	 * @param startIndex
	 *            count of trackable logs to skip
	 * @param maxPerPage
	 *            count of trackable logs to get
	 * @return list of trackable logs
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.5.1
	 */
	List<TrackableLog> getTrackableLogs(String trackableCode, int startIndex, int maxPerPage) throws GeocachingApiException;

	/**
	 * Returns the API limits applied on currently logged user or null if this
	 * information isn't available.
	 * 
	 * @return API limits object
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.5.10
	 */
	ApiLimits getApiLimits() throws GeocachingApiException;

	/**
	 * Return the cache limits received with a last Geocache request. If the
	 * last request was not Geocache request, returns null.
	 * 
	 * @return cache limits or null
	 * @since 1.5.10
	 */
	CacheLimits getLastCacheLimits();

  /**
   * Return count of Geocaches found in a last Geocache request. If the
   * last request was not Geocache request, returns <tt>0</tt>.
   * @return count of Geocaches found
   * @since 1.6
   */
  int getLastSearchResultsFound();

	/**
	 * Get a list of users geocache logs
	 * 
	 * @param userName
	 *            user name
	 * @param startDate
	 *            start date or null
	 * @param endDate
	 *            end date or null
	 * @param logTypes
	 *            array of geocache log types to filter (must contains at least one Geocache log type) 
	 * @param excludeArchived
	 *            exclude archived geocaches
	 * @param startIndex
	 *            count of logs to skip
	 * @param maxPerPage
	 *            count of logs to get
	 * @return list of cache logs
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 * @since 1.5.13
	 */
	List<CacheLog> getUsersGeocacheLogs(String userName, Date startDate, Date endDate, CacheLogType[] logTypes, boolean excludeArchived, int startIndex, int maxPerPage) throws GeocachingApiException;
	
	/**
	 * Get a user's owned trackables
	 * @param startIndex
	 *            count of trackables to skip
	 * @param maxPerPage
	 *            count of trackables to get
	 * @param trackableLogCount
	 *            count of trackables to get
	 * @param collectionOnly
	 *            gets only trackables which are in collection
	 * @return list of trackables
	 * @throws GeocachingApiException
	 *             If error occurs during getting information
	 */
	List<Trackable> getUsersTrackables(int startIndex, int maxPerPage, int trackableLogCount, boolean collectionOnly) throws GeocachingApiException;
}
