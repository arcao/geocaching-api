package com.arcao.geocaching.api;

import com.arcao.geocaching.api.data.DeviceInfo;
import com.arcao.geocaching.api.data.FavoritePointResult;
import com.arcao.geocaching.api.data.FavoritedGeocache;
import com.arcao.geocaching.api.data.FieldNote;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.GeocacheLimits;
import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.GeocacheStatus;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SearchForGeocachesRequest;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.data.TrackableTravel;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.apilimits.ApiLimitsResponse;
import com.arcao.geocaching.api.data.bookmarks.Bookmark;
import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.arcao.geocaching.api.data.sort.SortBy;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.filter.Filter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Interface of Geocaching API.
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
     * Gets a session id for current logged user.
     *
     * @return session id
     * @since 1.0
     */
    @Nullable
    String getSession();

    /**
     * Open a session with giving session id of already logged-in user.
     *
     * @param session session id
     * @throws GeocachingApiException If Geocaching API error occurs
     * @since 1.0
     */
    void openSession(@NotNull String session) throws GeocachingApiException;

    /**
     * Close current used session.
     *
     * @since 1.0
     */
    void closeSession();

    /**
     * Check if current used session is still valid.
     *
     * @return true if is valid otherwise false
     * @since 1.0
     */
    boolean isSessionValid();

    /**
     * Get a trackable object for given trackable code. This method works for
     * public trackable code and also for private (secret) trackable code.
     *
     * @param trackableCode     trackable code
     * @param trackableLogCount count of trackable logs to get
     * @return information about trackable
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.1
     */
    @Nullable
    Trackable getTrackable(@NotNull String trackableCode, int trackableLogCount) throws GeocachingApiException;

    /**
     * Get a list of trackables which is currently placed in a cache.
     *
     * @param cacheCode         cache code
     * @param startIndex        count of trackables to skip
     * @param maxPerPage        count of trackables to get
     * @param trackableLogCount count of trackable logs to get
     * @return list of trackables
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.1
     */
    @NotNull
    List<Trackable> getTrackablesByCacheCode(@NotNull String cacheCode, int startIndex, int maxPerPage,
                                             int trackableLogCount) throws GeocachingApiException;

    /**
     * Get a list of complete travel track of specified trackable.
     *
     * @param trackableCode trackable code
     * @return information about trackable travel track
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.20
     */
    @NotNull
    List<TrackableTravel> getTrackableTravelList(@NotNull String trackableCode) throws GeocachingApiException;

    /**
     * Get a list of geocache logs in given cache.
     *
     * @param cacheCode  cache code
     * @param startIndex count of logs to skip
     * @param maxPerPage count of logs to get
     * @return list of cache logs
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.1
     */
    @NotNull
    List<GeocacheLog> getGeocacheLogsByCacheCode(@NotNull String cacheCode, int startIndex,
                                                 int maxPerPage) throws GeocachingApiException;

    /**
     * Get an information about geocache.
     *
     * @param resultQuality     How much data will be returned
     * @param cacheCode         cache code
     * @param cacheLogCount     count of logs to get
     * @param trackableLogCount count of trackable logs to get
     * @return full cache information
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.0
     */
    @Nullable
    Geocache getGeocache(@NotNull ResultQuality resultQuality, @NotNull String cacheCode, int cacheLogCount,
                         int trackableLogCount) throws GeocachingApiException;

    /**
     * Search for geocaches and return list of found.
     *
     * @param resultQuality     How much data will be returned
     * @param maxPerPage        count of caches to get
     * @param geocacheLogCount  count of logs to get
     * @param trackableLogCount count of trackables to get
     * @param filters           used filters while searching
     * @param sortByList        sorting result list configuration
     * @return list of found caches
     * @throws GeocachingApiException If error occurs during searching caches
     * @since 1.6
     * @deprecated Use {@link #searchForGeocaches(SearchForGeocachesRequest)} instead.
     */
    @NotNull
    @Deprecated
    List<Geocache> searchForGeocaches(@NotNull ResultQuality resultQuality, int maxPerPage, int geocacheLogCount,
                                      int trackableLogCount, @NotNull Collection<Filter> filters,
                                      @Nullable Collection<SortBy> sortByList) throws GeocachingApiException;

    /**
     * Search for geocaches and return list of found.
     *
     * @param request search request object
     * @return list of found caches
     * @throws GeocachingApiException If error occurs during searching caches
     * @since 1.6
     */
    @NotNull
    List<Geocache> searchForGeocaches(@NotNull SearchForGeocachesRequest request) throws GeocachingApiException;

    /**
     * Retrieve next geocaches searched by searchForGeocaches method.
     *
     * @param resultQuality     How much data will be returned
     * @param startIndex        count of caches to skip
     * @param maxPerPage        count of caches to get
     * @param geocacheLogCount  count of caches to get
     * @param trackableLogCount count of trackables to get
     * @return list of found caches
     * @throws GeocachingApiException If error occurs during searching caches
     * @since 1.6
     */
    @NotNull
    List<Geocache> getMoreGeocaches(@NotNull ResultQuality resultQuality, int startIndex, int maxPerPage,
                                    int geocacheLogCount, int trackableLogCount) throws GeocachingApiException;

    /**
     * Get an information about user.
     *
     * @param challengesData    include challanges data
     * @param favoritePointData include favorites points
     * @param geocacheData      include information about caches
     * @param publicProfileData include public profile information
     * @param souvenirData      include souvenirs
     * @param trackableData     include trackables
     * @param deviceInfo        information about used device
     * @return UserProfile object with selected information
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.2
     */
    @Nullable
    UserProfile getYourUserProfile(boolean challengesData, boolean favoritePointData, boolean geocacheData,
                                   boolean publicProfileData, boolean souvenirData, boolean trackableData,
                                   @NotNull DeviceInfo deviceInfo) throws GeocachingApiException;

    /**
     * Create field note and publish them or store them to list of Field notes
     * on Geocaching site.
     *
     * @param cacheCode         geocache which can own this field note
     * @param geocacheLogType   type of log
     * @param dateLogged        when cache was found
     * @param note              text of field note
     * @param publish           true if publish log to cache or false to add to Field note
     *                          list on Geocaching site
     * @param imageData         data about image to publish together or null to not publish
     *                          any image
     * @param favoriteThisCache true if add cache to favorite list otherwise false
     * @return cache log
     * @throws GeocachingApiException If error occurs during sending field note
     * @since 1.1
     */
    @Nullable
    GeocacheLog createFieldNoteAndPublish(@NotNull String cacheCode, @NotNull GeocacheLogType geocacheLogType,
                                          @NotNull Date dateLogged, @NotNull String note, boolean publish,
                                          @Nullable ImageData imageData,
                                          boolean favoriteThisCache) throws GeocachingApiException;

    /**
     * Create field note and publish them or store them to list of Field notes
     * on Geocaching site.
     *
     * @param fieldNote         Field note object
     * @param publish           true if publish log to cache or false to add to Field note
     *                          list on Geocaching site
     * @param imageData         data about image to publish together or null to not publish
     *                          any image
     * @param favoriteThisCache true if add cache to favorite list otherwise false
     * @return cache log
     * @throws GeocachingApiException If error occurs during sending field note
     * @since 1.1
     */
    @Nullable
    GeocacheLog createFieldNoteAndPublish(@NotNull FieldNote fieldNote, boolean publish,
                                          @Nullable ImageData imageData,
                                          boolean favoriteThisCache) throws GeocachingApiException;

    /**
     * Store a personal note for specified geocache. If note parameter is null or
     * empty the note will be removed.
     *
     * @param cacheCode geocache which can own this personal note
     * @param note      personal note
     * @throws GeocachingApiException If error occurs during personal note storing
     * @since 1.4.2
     */
    void setGeocachePersonalNote(@NotNull String cacheCode, @Nullable String note) throws GeocachingApiException;

    /**
     * Remove existing personal note from a specified geocache.
     *
     * @param cacheCode geocache where the personal note has to be deleted
     * @throws GeocachingApiException If error occurs during personal note storing
     * @since 1.6.4
     */
    void deleteCachePersonalNote(@NotNull String cacheCode) throws GeocachingApiException;

    /**
     * Get a list of trackable logs for given public trackable code.
     *
     * @param trackableCode public trackable code
     * @param startIndex    count of trackable logs to skip
     * @param maxPerPage    count of trackable logs to get
     * @return list of trackable logs
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.5.1
     */
    @NotNull
    List<TrackableLog> getTrackableLogs(@NotNull String trackableCode, int startIndex,
                                        int maxPerPage) throws GeocachingApiException;

    /**
     * Returns the API limits applied on currently logged user or null if this
     * information isn't available.
     *
     * @return API limits response object
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.5.10
     */
    @NotNull
    ApiLimitsResponse getApiLimits() throws GeocachingApiException;

    /**
     * Return the cache limits received with a last Geocache request. If the
     * last request was not Geocache request, returns null.
     *
     * @return cache limits or null
     * @since 1.5.10
     */
    @Nullable
    GeocacheLimits getLastGeocacheLimits();

    /**
     * Return count of Geocaches found in a last Geocache request. If the
     * last request was not Geocache request, returns <tt>0</tt>.
     *
     * @return count of Geocaches found
     * @since 1.6
     */
    int getLastSearchResultsFound();

    /**
     * Get a list of users geocache logs.
     *
     * @param userName        user name
     * @param startDate       start date of created geocache log or null
     * @param endDate         end date of created geocache log or null
     * @param logTypes        array of geocache log types to filter (must contains at least one Geocache log type)
     * @param excludeArchived exclude archived geocaches
     * @param startIndex      count of logs to skip
     * @param maxPerPage      count of logs to get
     * @return list of cache logs
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.5.13
     */
    @NotNull
    List<GeocacheLog> getUsersGeocacheLogs(@NotNull String userName, @Nullable Date startDate,
                                           @Nullable Date endDate, @NotNull GeocacheLogType[] logTypes,
                                           boolean excludeArchived, int startIndex,
                                           int maxPerPage) throws GeocachingApiException;

    /**
     * Get a user's owned trackables.
     *
     * @param startIndex        count of trackables to skip
     * @param maxPerPage        count of trackables to get
     * @param trackableLogCount count of trackables to get
     * @param collectionOnly    gets only trackables which are in collection
     * @return list of trackables
     * @throws GeocachingApiException If error occurs during getting information
     */
    @NotNull
    List<Trackable> getUsersTrackables(int startIndex, int maxPerPage, int trackableLogCount,
                                       boolean collectionOnly) throws GeocachingApiException;

    /**
     * Returns a list of Bookmark lists for current user.
     *
     * @return list of Bookmark lists
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.6.2
     */
    @NotNull
    List<BookmarkList> getBookmarkListsForUser() throws GeocachingApiException;

    /**
     * Return a list of Bookmark lists for specified user id.
     *
     * @param userId numeric user id from User object
     * @return list of Bookmark lists
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.6.2
     */
    @NotNull
    List<BookmarkList> getBookmarkListsByUserId(int userId) throws GeocachingApiException;

    /**
     * Retrieve a list of Bookmarked caches by a guid.
     *
     * @param guid guid of that list
     * @return list of Bookmarked caches
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.6.2
     */
    @NotNull
    List<Bookmark> getBookmarkListByGuid(@NotNull String guid) throws GeocachingApiException;

    /**
     * Retrieve a list of Geocache Status.
     *
     * @param cacheCodes list of Geocache codes
     * @return list of Geocache status
     * @throws GeocachingApiException If error occurs during getting information
     * @since 1.6.3
     */
    @NotNull
    List<GeocacheStatus> getGeocacheStatus(@NotNull Collection<String> cacheCodes) throws GeocachingApiException;

    /**
     * Add the geocaches to list of Bookmarked caches.
     *
     * @param guid       bookmark list guid
     * @param cacheCodes list of geocaches
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    void addGeocachesToBookmarkList(@NotNull String guid,
                                    @NotNull Collection<String> cacheCodes) throws GeocachingApiException;

    /**
     * Add a favorite point to Geocache.
     *
     * @param cacheCode Geocache code
     * @return geocache favorite points info
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    @NotNull
    FavoritePointResult addFavoritePointToGeocache(@NotNull String cacheCode) throws GeocachingApiException;

    /**
     * Remove a favorite point from Geocache.
     *
     * @param cacheCode Geocache code
     * @return geocache favorite points info
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    @NotNull
    FavoritePointResult removeFavoritePointFromGeocache(@NotNull String cacheCode) throws GeocachingApiException;

    /**
     * Get a count of favorite points which user have.
     *
     * @return count of favorite points
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    int getUsersFavoritePoints() throws GeocachingApiException;

    /**
     * Get a list of users who favorited the Geocache.
     *
     * @param cacheCode Geocache code
     * @return list of users
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    @NotNull
    List<User> getUsersWhoFavoritedGeocache(@NotNull String cacheCode) throws GeocachingApiException;

    /**
     * Get a list of Geocache codes favorited by you.
     *
     * @return list of Geocache codes
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    @NotNull
    List<String> getGeocacheCodesFavoritedByUser() throws GeocachingApiException;

    /**
     * Get a list of Geocaches favorited by you.
     *
     * @return list of Geocaches
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    @NotNull
    List<FavoritedGeocache> getGeocachesFavoritedByUser() throws GeocachingApiException;

    /**
     * Get a list of images for Geocache.
     *
     * @param cacheCode Geocache code
     * @return list of images
     * @throws GeocachingApiException If error occurs during getting information
     * @since 2.1
     */
    @NotNull
    List<ImageData> getImagesForGeocache(@NotNull String cacheCode) throws GeocachingApiException;
}
