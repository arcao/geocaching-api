package com.arcao.geocaching.api;


import com.arcao.geocaching.api.builder.JsonBuilder;
import com.arcao.geocaching.api.configuration.GeocachingApiConfiguration;
import com.arcao.geocaching.api.configuration.impl.DefaultProductionGeocachingApiConfiguration;
import com.arcao.geocaching.api.data.DeviceInfo;
import com.arcao.geocaching.api.data.FavoritePointResult;
import com.arcao.geocaching.api.data.FavoritedGeocache;
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
import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.apilimits.ApiLimitsResponse;
import com.arcao.geocaching.api.data.apilimits.MaxPerPage;
import com.arcao.geocaching.api.data.bookmarks.Bookmark;
import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.sort.SortBy;
import com.arcao.geocaching.api.data.sort.SortOrder;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.downloader.DefaultJsonDownloader;
import com.arcao.geocaching.api.downloader.JsonDownloader;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.exception.InvalidCredentialsException;
import com.arcao.geocaching.api.exception.InvalidResponseException;
import com.arcao.geocaching.api.exception.InvalidSessionException;
import com.arcao.geocaching.api.exception.LiveGeocachingApiException;
import com.arcao.geocaching.api.exception.NetworkException;
import com.arcao.geocaching.api.filter.Filter;
import com.arcao.geocaching.api.parser.ApiLimitsJsonParser;
import com.arcao.geocaching.api.parser.BookmarkJsonParser;
import com.arcao.geocaching.api.parser.BookmarkListJsonParser;
import com.arcao.geocaching.api.parser.CacheLimitsJsonParser;
import com.arcao.geocaching.api.parser.FavoritedGeocacheJsonParser;
import com.arcao.geocaching.api.parser.GeocacheJsonParser;
import com.arcao.geocaching.api.parser.GeocacheLogJsonParser;
import com.arcao.geocaching.api.parser.GeocacheStatusJsonParser;
import com.arcao.geocaching.api.parser.ImageDataJsonParser;
import com.arcao.geocaching.api.parser.JsonReader;
import com.arcao.geocaching.api.parser.MaxPerPageJsonParser;
import com.arcao.geocaching.api.parser.StatusJsonParser;
import com.arcao.geocaching.api.parser.TrackableJsonParser;
import com.arcao.geocaching.api.parser.TrackableLogJsonParser;
import com.arcao.geocaching.api.parser.TrackableTravelJsonParser;
import com.arcao.geocaching.api.parser.UserJsonParser;
import com.arcao.geocaching.api.parser.UserProfileJsonParser;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of Life Geocaching Api provided by Groundspeak. To use this class you need consumer and license key, ask Groundspeak for them.<br>
 * <br>
 * <i>Note: Most of methods is limited from Groundspeak side. See Live Geocaching Api agreement.</i><br>
 * <br>
 * Some limits for Premium User:<ul>
 * <li>maxPerPage = 50</li>
 * <li>geocacheLogCount = 30</li>
 * <li>trackableLogCount = 30</li>
 * </ul>
 *
 * @author arcao
 */
public class LiveGeocachingApi extends AbstractGeocachingApi {
    private static final Logger logger = LoggerFactory.getLogger(LiveGeocachingApi.class);

    private final GeocachingApiConfiguration configuration;
    private final JsonDownloader downloader;

    @Nullable private GeocacheLimits lastGeocacheLimits = null;
    private int lastSearchResultsFound = 0;

    private boolean sessionValid = false;

    /**
     * Create a new instance of LiveGeocachingApi with configuration specified by configuration parameter
     *
     * @param builder builder object
     */
    LiveGeocachingApi(Builder builder) {
        this.configuration = builder.configuration;
        this.downloader = builder.downloader;
    }

    @Override
    public void openSession(@NotNull String session) throws GeocachingApiException {
        super.openSession(session);
        sessionValid = true;
    }

    @Override
    public void closeSession() {
        session = null;
        sessionValid = false;
    }

    @Override
    public boolean isSessionValid() {
        return sessionValid;
    }

    @NotNull
    @Override
    public List<Geocache> searchForGeocaches(@NotNull SearchForGeocachesRequest request) throws GeocachingApiException {
        List<Geocache> list = new ArrayList<Geocache>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        lastSearchResultsFound = 0;
        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("IsLite").value(request.resultQuality() == ResultQuality.LITE);
            w.name("IsSummaryOnly").value(request.resultQuality() == ResultQuality.SUMMARY);
            w.name("MaxPerPage").value(request.maxPerPage());

            if (request.geocacheLogCount() >= 0)
                w.name("GeocacheLogCount").value(request.geocacheLogCount());

            if (request.trackableLogCount() >= 0)
                w.name("TrackableLogCount").value(request.trackableLogCount());

            for (Filter filter : request.filters()) {
                if (filter.valid())
                    filter.writeJson(w);
            }

            Collection<SortBy> sortKeys = request.sortKeys();
            if (!sortKeys.isEmpty()) {
                w.name("SortBys").beginArray();
                for (SortBy sortBy : sortKeys) {
                    w.name("SortFilterId").value(sortBy.key().id);
                    w.name("AscendingOrder").value(sortBy.order() == SortOrder.ASCENDING);
                }
                w.endArray();
            }

            Coordinates sortPoint = request.sortPoint();
            if (sortPoint != null) {
                w.name("SortPoint").beginObject()
                        .name("Latitude").value(sortPoint.latitude())
                        .name("Longitude").value(sortPoint.longitude())
                        .endObject();
            }

            w.endObject();
            w.close();

            r = callPost("SearchForGeocaches?format=json", sw.toString());
            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Geocaches".equals(name)) {
                    list = GeocacheJsonParser.parseList(r);
                } else if ("CacheLimits".equals(name)) {
                    lastGeocacheLimits = CacheLimitsJsonParser.parse(r);
                } else if ("TotalMatchingCaches".equals(name)) {
                    lastSearchResultsFound = r.nextInt();
                } else {
                    r.skipValue();
                }
            }
            r.endObject();
            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<Geocache> getMoreGeocaches(@NotNull ResultQuality resultQuality, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount) throws GeocachingApiException {
        List<Geocache> list = new ArrayList<Geocache>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        lastSearchResultsFound = 0;
        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("IsLite").value(resultQuality == ResultQuality.LITE);
            w.name("IsSummaryOnly").value(resultQuality == ResultQuality.SUMMARY);
            w.name("StartIndex").value(startIndex);
            w.name("MaxPerPage").value(maxPerPage);

            if (geocacheLogCount >= 0)
                w.name("GeocacheLogCount").value(geocacheLogCount);

            if (trackableLogCount >= 0)
                w.name("TrackableLogCount").value(trackableLogCount);

            w.endObject();
            w.close();

            r = callPost("GetMoreGeocaches?format=json", sw.toString());
            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Geocaches".equals(name)) {
                    list = GeocacheJsonParser.parseList(r);
                } else if ("CacheLimits".equals(name)) {
                    lastGeocacheLimits = CacheLimitsJsonParser.parse(r);
                } else if ("TotalMatchingCaches".equals(name)) {
                    lastSearchResultsFound = r.nextInt();
                } else {
                    r.skipValue();
                }
            }
            r.endObject();
            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public Trackable getTrackable(@NotNull String trackableCode, int trackableLogCount) throws GeocachingApiException {
        List<Trackable> list = null;

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            //noinspection IfMayBeConditional
            if (trackableCode.toLowerCase(Locale.US).startsWith("tb")) {
                r = callGet("GetTrackablesByTBCode?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                        "&tbCode=" + trackableCode +
                        "&trackableLogCount=" + trackableLogCount +
                        "&format=json"
                );
            } else {
                r = callGet("GetTrackablesByTrackingNumber?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                        "&trackingNumber=" + trackableCode +
                        "&trackableLogCount=" + trackableLogCount +
                        "&format=json"
                );
            }

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("Trackables".equals(name)) {
                    list = TrackableJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            if (list == null || list.isEmpty())
                return null;

            return list.get(0);
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<Trackable> getTrackablesByCacheCode(@NotNull String cacheCode, int startIndex, int maxPerPage, int trackableLogCount) throws GeocachingApiException {
        List<Trackable> list = new ArrayList<Trackable>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetTrackablesInCache?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&startIndex=" + startIndex +
                    "&maxPerPage=" + maxPerPage +
                    "&trackableLogCount=" + trackableLogCount +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("Trackables".equals(name)) {
                    list = TrackableJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<TrackableTravel> getTrackableTravelList(@NotNull String trackableCode) throws GeocachingApiException {
        List<TrackableTravel> list = new ArrayList<TrackableTravel>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetTrackableTravelList?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&tbCode=" + trackableCode +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("TrackableTravels".equals(name)) {
                    list = TrackableTravelJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }

    }

    @NotNull
    @Override
    public List<GeocacheLog> getGeocacheLogsByCacheCode(@NotNull String cacheCode, int startIndex, int maxPerPage) throws GeocachingApiException {
        List<GeocacheLog> list = new ArrayList<GeocacheLog>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetGeocacheLogsByCacheCode?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&startIndex=" + startIndex +
                    "&maxPerPage=" + maxPerPage +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("Logs".equals(name)) {
                    list = GeocacheLogJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public GeocacheLog createFieldNoteAndPublish(@NotNull String cacheCode, @NotNull GeocacheLogType geocacheLogType, @NotNull Date dateLogged, @NotNull String note, boolean publish, ImageData imageData,
                                                 boolean favoriteThisCache) throws GeocachingApiException {
        GeocacheLog geocacheLog = null;

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("CacheCode").value(cacheCode);
            w.name("WptLogTypeId").value(geocacheLogType.id);
            w.name("UTCDateLogged").value(JsonBuilder.dateToJsonString(dateLogged));
            w.name("Note").value(note);
            w.name("PromoteToLog").value(publish);
            if (imageData != null) {
                w.name("ImageData");
                imageData.writeJson(w);
            }
            w.name("FavoriteThisCache").value(favoriteThisCache);

            w.endObject();
            w.close();

            r = callPost("CreateFieldNoteAndPublish?format=json", sw.toString());
            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Log".equals(name)) {
                    geocacheLog = GeocacheLogJsonParser.parse(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();
            return geocacheLog;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public UserProfile getYourUserProfile(boolean challengesData, boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData,
                                          boolean trackableData, @NotNull DeviceInfo deviceInfo) throws GeocachingApiException {
        UserProfile userProfile = null;

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("ProfileOptions").beginObject()
                    .name("ChallengesData").value(challengesData)
                    .name("FavoritePointData").value(favoritePointData)
                    .name("GeocacheData").value(geocacheData)
                    .name("PublicProfileData").value(publicProfileData)
                    .name("SouvenirData").value(souvenirData)
                    .name("TrackableData").value(trackableData);
            w.endObject();

            w.name("DeviceInfo");
            deviceInfo.writeJson(w);

            w.endObject();
            w.close();

            r = callPost("GetYourUserProfile?format=json", sw.toString());
            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Profile".equals(name)) {
                    userProfile = UserProfileJsonParser.parse(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();
            return userProfile;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<Trackable> getUsersTrackables(int startIndex, int maxPerPage, int trackableLogCount, boolean collectionOnly) throws GeocachingApiException {
        List<Trackable> list = new ArrayList<Trackable>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("StartIndex").value(startIndex);
            w.name("MaxPerPage").value(maxPerPage);
            w.name("TrackableLogsCount").value(trackableLogCount);
            w.name("CollectionOnly").value(collectionOnly);
            w.endObject();
            w.close();

            r = callPost("GetUsersTrackables?format=json", sw.toString());

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("Trackables".equals(name)) {
                    list = TrackableJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<BookmarkList> getBookmarkListsForUser() throws GeocachingApiException {
        List<BookmarkList> list = new ArrayList<BookmarkList>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetBookmarkListsForUser?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("BookmarkLists".equals(name)) {
                    list = BookmarkListJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<BookmarkList> getBookmarkListsByUserId(int userId) throws GeocachingApiException {
        List<BookmarkList> list = new ArrayList<BookmarkList>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetBookmarkListsByUserID?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&userID=" + String.valueOf(userId) +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("BookmarkLists".equals(name)) {
                    list = BookmarkListJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<Bookmark> getBookmarkListByGuid(@NotNull String guid) throws GeocachingApiException {
        List<Bookmark> list = new ArrayList<Bookmark>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("BookmarkListGuid").value(guid);
            w.endObject();
            w.close();

            r = callPost("GetBookmarkListByGuid?format=json", sw.toString());

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("BookmarkList".equals(name)) {
                    list = BookmarkJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<GeocacheStatus> getGeocacheStatus(@NotNull Collection<String> cacheCodes) throws GeocachingApiException {
        List<GeocacheStatus> list = new ArrayList<GeocacheStatus>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            JsonWriter arrayWriter = w.name("CacheCodes").beginArray();
            for (String cacheCode : cacheCodes) {
                arrayWriter.value(cacheCode);
            }
            arrayWriter.endArray();
            w.endObject();
            w.close();

            r = callPost("GetGeocacheStatus?format=json", sw.toString());

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("GeocacheStatuses".equals(name)) {
                    list = GeocacheStatusJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public void addGeocachesToBookmarkList(@NotNull String guid, @NotNull Collection<String> cacheCodes) throws GeocachingApiException {
        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("BookmarkListGuid").value(guid);
            JsonWriter arrayWriter = w.name("CacheCodes").beginArray();
            for (String cacheCode : cacheCodes) {
                arrayWriter.value(cacheCode);
            }
            arrayWriter.endArray();
            w.endObject();
            w.close();

            r = callPost("AddGeocachesToBookmarkList?format=json", sw.toString());
            checkError(r);
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }

    }

    @NotNull
    @Override
    public FavoritePointResult addFavoritePointToGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("AddFavoritePointToCache?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            FavoritePointResult.Builder builder = FavoritePointResult.builder();
            while (r.hasNext()) {
                String name = r.nextName();
                if ("CacheFavoritePoints".equals(name)) {
                    builder.cacheFavoritePoints(r.nextInt());
                } else if ("UsersFavoritePoints".equals(name)) {
                    builder.usersFavoritePoints(r.nextInt());
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return builder.build();
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public FavoritePointResult removeFavoritePointFromGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("RemoveFavoritePointFromCache?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            FavoritePointResult.Builder builder = FavoritePointResult.builder();
            while (r.hasNext()) {
                String name = r.nextName();
                if ("CacheFavoritePoints".equals(name)) {
                    builder.cacheFavoritePoints(r.nextInt());
                } else if ("UsersFavoritePoints".equals(name)) {
                    builder.usersFavoritePoints(r.nextInt());
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return builder.build();
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public int getUsersFavoritePoints() throws GeocachingApiException {
        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetUsersFavoritePoints?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            int favoritePoints = 0;
            while (r.hasNext()) {
                String name = r.nextName();
                if ("FavoritePoints".equals(name)) {
                    favoritePoints = r.nextInt();
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return favoritePoints;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<User> getUsersWhoFavoritedGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        List<User> list = new ArrayList<User>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetUsersWhoFavoritedCache?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("UsersWhoFavoritedCache".equals(name)) {
                    list = UserJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<String> getGeocacheCodesFavoritedByUser() throws GeocachingApiException {
        List<String> list = new ArrayList<String>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetCacheIdsFavoritedByUser?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("CacheCodes".equals(name)) {
                    r.beginArray();
                    while (r.hasNext()) {
                        list.add(r.nextString());
                    }
                    r.endArray();
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<FavoritedGeocache> getGeocachesFavoritedByUser() throws GeocachingApiException {
        List<FavoritedGeocache> list = new ArrayList<FavoritedGeocache>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetCachesFavoritedByUser?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Geocaches".equals(name)) {
                    list = FavoritedGeocacheJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<ImageData> getImagesForGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        List<ImageData> list = new ArrayList<ImageData>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetImagesForGeocache?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Images".equals(name)) {
                    list = ImageDataJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public void setGeocachePersonalNote(@NotNull String cacheCode, String note) throws GeocachingApiException {
        if (session == null)
            throw new InvalidSessionException("Session is closed");

        if (note == null || note.isEmpty()) {
            deleteCachePersonalNote(cacheCode);
            return;
        }

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("CacheCode").value(cacheCode);
            w.name("Note").value(note);
            w.endObject();
            w.close();

            r = callPost("UpdateCacheNote?format=json", sw.toString());
            checkError(r);
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    public void deleteCachePersonalNote(@NotNull String cacheCode) throws GeocachingApiException {
        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("DeleteCacheNote?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&cacheCode=" + cacheCode +
                    "&format=json"
            );
            checkError(r);
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public List<TrackableLog> getTrackableLogs(@NotNull String trackableCode, int startIndex, int maxPerPage) throws GeocachingApiException {
        List<TrackableLog> list = new ArrayList<TrackableLog>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetTrackableLogsByTBCode?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&tbCode=" + trackableCode +
                    "&startIndex=" + startIndex +
                    "&maxPerPage=" + maxPerPage +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("TrackableLogs".equals(name)) {
                    list = TrackableLogJsonParser.parseList(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @NotNull
    @Override
    public ApiLimitsResponse getApiLimits() throws GeocachingApiException {
        ApiLimits apiLimits = null;
        MaxPerPage maxPerPage = null;

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        JsonReader r = null;
        try {
            r = callGet("GetAPILimits?accessToken=" + URLEncoder.encode(session, "UTF-8") +
                    "&format=json"
            );

            r.beginObject();
            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                if ("Limits".equals(name)) {
                    apiLimits = ApiLimitsJsonParser.parse(r);
                } else if ("MaxPerPage".equals(name)) {
                    maxPerPage = MaxPerPageJsonParser.parse(r);
                } else {
                    r.skipValue();
                }
            }
            r.endObject();

            return ApiLimitsResponse.create(apiLimits, maxPerPage);
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    @Override
    @Nullable
    public GeocacheLimits getLastGeocacheLimits() {
        return lastGeocacheLimits;
    }

    @Override
    public int getLastSearchResultsFound() {
        return lastSearchResultsFound;
    }

    @NotNull
    @Override
    public List<GeocacheLog> getUsersGeocacheLogs(@NotNull String userName, Date startDate, Date endDate, @NotNull GeocacheLogType[] logTypes, boolean excludeArchived, int startIndex, int maxPerPage) throws GeocachingApiException {
        List<GeocacheLog> list = new ArrayList<GeocacheLog>();

        if (session == null)
            throw new InvalidSessionException("Session is closed");

        if (userName.isEmpty())
            throw new IllegalArgumentException("You must specify user name.");

        if (logTypes.length == 0)
            throw new IllegalArgumentException("You must specify at least one GeocacheLogType.");

        JsonReader r = null;
        try {
            StringWriter sw = new StringWriter();
            JsonWriter w = new JsonWriter(sw);
            w.beginObject();
            w.name("AccessToken").value(session);
            w.name("Username").value(userName);

            // Date filters
            if (startDate != null || endDate != null) {
                JsonWriter range = w.name("Range").beginObject();

                if (startDate != null) {
                    range.name("StartDate").value(JsonBuilder.dateToJsonString(startDate));
                }

                if (endDate != null) {
                    range.name("EndDate").value(JsonBuilder.dateToJsonString(endDate));
                }

                range.endObject();
            }

            // Cache log type filter
            JsonWriter logs = w.name("LogTypes").beginArray();

            for (GeocacheLogType geocacheLogType : logTypes) {
                logs.value(geocacheLogType.id);
            }

            logs.endArray();

            w.name("ExcludeArchived").value(excludeArchived);
            w.name("StartIndex").value(startIndex);
            w.name("MaxPerPage").value(maxPerPage);
            w.endObject();
            w.close();


            r = callPost("GetUsersGeocacheLogs?format=json", sw.toString());
            r.beginObject();
            checkError(r);

            while (r.hasNext()) {
                String name = r.nextName();
                if ("Logs".equals(name)) {
                    list = GeocacheLogJsonParser.parseList(r);
                    break;
                } else {
                    r.skipValue();
                }
            }
            r.endObject();
            r.close();

            return list;
        } catch (IOException e) {
            throw handleIOException(e);
        } finally {
            closeJsonReader(r);
        }
    }

    // -------------------- Helper methods ----------------------------------------
    private void prepareRequest() {
        lastGeocacheLimits = null;
        lastSearchResultsFound = 0;
    }

    private void checkError(JsonReader r) throws GeocachingApiException, IOException {
        if (r.peek() == JsonToken.BEGIN_OBJECT || "Status".equals(r.nextName())) {
            Status status = StatusJsonParser.parse(r);

            if (status == null)
                throw new GeocachingApiException("Missing Status in a response.");

            if (status.statusCode() == StatusCode.OK)
                return;

            GeocachingApiException cause = new LiveGeocachingApiException(status);

            switch (status.statusCode()) {
                case UserDidNotAuthorize:
                case UserTokenNotValid:
                case SessionExpired:
                case AccessTokenNotValid:
                case AccessTokenExpired:
                case NotAuthorized:
                    sessionValid = false;
                    throw new InvalidSessionException(status.message(), cause);
                case AccountNotFound:
                    sessionValid = false;
                    throw new InvalidCredentialsException(status.message(), cause);
                default:
                    throw cause;
            }
        } else {
            throw new InvalidResponseException("Missing Status in a response.");
        }
    }

    private static GeocachingApiException handleIOException(IOException e) {
        logger.error(e.toString(), e);
        if (!isGsonException(e)) {
            return new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
        }

        return new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
    }

    private JsonReader callGet(String function) throws NetworkException, InvalidResponseException {
        prepareRequest();

        logger.debug("Getting " + maskParameterValues(function));

        try {
            URL url = new URL(configuration.getApiServiceEntryPointUrl() + "/" + function);
            return downloader.get(url);
        } catch (MalformedURLException e) {
            logger.error(e.toString(), e);
            throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
        }
    }

    private JsonReader callPost(String function, String postBody) throws NetworkException, InvalidResponseException {
        prepareRequest();

        logger.debug("Posting " + maskParameterValues(function));
        logger.debug("Body: " + maskJsonParameterValues(postBody));


        try {
            byte[] postData = postBody.getBytes("UTF-8");
            URL url = new URL(configuration.getApiServiceEntryPointUrl() + "/" + function);

            return downloader.post(url, postData);
        } catch (MalformedURLException e) {
            logger.error(e.toString(), e);
            throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString(), e);
            throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + "): " + e.getMessage(), e);
        }
    }

    private static String maskParameterValues(String function) {
        return function.replaceAll("([Aa]ccess[Tt]oken=)([^&]+)", "$1******");
    }

    private static String maskJsonParameterValues(String postBody) {
        return postBody.replaceAll("(\"[Aa]ccess[Tt]oken\"\\s*:\\s*\")([^\"]+)(\")", "$1******$3");
    }

    private static boolean isGsonException(Throwable t) {
        // This IOException mess will be fixed in a next GSON release
        return (IOException.class.equals(t.getClass()) && t.getMessage() != null && t.getMessage().startsWith("Expected JSON document")) || t instanceof MalformedJsonException || t instanceof IllegalStateException || t instanceof NumberFormatException;
    }

    private static void closeJsonReader(JsonReader r) {
        if (r == null)
            return;

        try {
            r.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link LiveGeocachingApi}
     *
     * @author arcao
     */
    public static class Builder {
        GeocachingApiConfiguration configuration;
        JsonDownloader downloader;

        Builder() {
        }

        /**
         * Set a {@link GeocachingApiConfiguration} where is stored configuration
         *
         * @param configuration instance of {@link GeocachingApiConfiguration} implementation
         * @return this Builder
         */
        public Builder configuration(GeocachingApiConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Set a instance of {@link JsonDownloader} which will take care for downloading data
         *
         * @param downloader instance of {@link JsonDownloader} implementation
         * @return this Builder
         */
        public Builder downloader(JsonDownloader downloader) {
            this.downloader = downloader;
            return this;
        }

        /**
         * Create a new instance of LiveGeocachingApi.<br>
         * If configuration is not set, the instance of {@link DefaultProductionGeocachingApiConfiguration} is used.<br>
         * If downloader is not set, the instance of {@link DefaultJsonDownloader} is used.
         *
         * @return new instance of LiveGeocachingApi
         */
        public GeocachingApi build() {
            if (configuration == null) {
                configuration = new DefaultProductionGeocachingApiConfiguration();
            }

            if (downloader == null) {
                downloader = new DefaultJsonDownloader(configuration);
            }

            return new LiveGeocachingApi(this);
        }
    }
}
