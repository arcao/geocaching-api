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
import com.arcao.geocaching.api.downloader.DefaultDownloader;
import com.arcao.geocaching.api.downloader.Downloader;
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
import com.arcao.geocaching.api.parser.MaxPerPageJsonParser;
import com.arcao.geocaching.api.parser.StatusJsonParser;
import com.arcao.geocaching.api.parser.TrackableJsonParser;
import com.arcao.geocaching.api.parser.TrackableLogJsonParser;
import com.arcao.geocaching.api.parser.TrackableTravelJsonParser;
import com.arcao.geocaching.api.parser.UserJsonParser;
import com.arcao.geocaching.api.parser.UserProfileJsonParser;
import com.arcao.geocaching.api.util.DefaultValueJsonReader;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
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
 * Implementation of Life Geocaching Api provided by Groundspeak. To use this class you need
 * consumer and license key, ask Groundspeak for them.<br>
 * <br>
 * <i>Note: Most of methods is limited from Groundspeak side. See Live Geocaching Api
 * agreement.</i><br>
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
    private final Downloader downloader;

    @Nullable private GeocacheLimits lastGeocacheLimits = null;
    private int lastSearchResultsFound = 0;

    private boolean sessionValid = false;

    /**
     * Create a new instance of LiveGeocachingApi with configuration specified by configuration
     * parameter.
     *
     * @param builder builder object
     */
    private LiveGeocachingApi(Builder builder) {
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
        List<Geocache> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        lastSearchResultsFound = 0;
        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                w.name("IsLite").value(request.resultQuality() == ResultQuality.LITE);
                w.name("IsSummaryOnly").value(request.resultQuality() == ResultQuality.SUMMARY);
                w.name("MaxPerPage").value(request.maxPerPage());

                if (request.geocacheLogCount() >= 0) {
                    w.name("GeocacheLogCount").value(request.geocacheLogCount());
                }

                if (request.trackableLogCount() >= 0) {
                    w.name("TrackableLogCount").value(request.trackableLogCount());
                }

                for (Filter filter : request.filters()) {
                    if (filter.valid()) {
                        filter.writeJson(w);
                    }
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
            }

            //noinspection Duplicates
            try (JsonReader r = callPost("SearchForGeocaches?format=json", sw.toString())) {
                r.beginObject();
                checkError(r);

                //noinspection Duplicates
                while (r.hasNext()) {
                    String name = r.nextName();
                    switch (name) {
                        case "Geocaches":
                            list = GeocacheJsonParser.parseList(r);
                            break;
                        case "CacheLimits":
                            lastGeocacheLimits = CacheLimitsJsonParser.parse(r);
                            break;
                        case "TotalMatchingCaches":
                            lastSearchResultsFound = r.nextInt();
                            break;
                        default:
                            r.skipValue();
                            break;
                    }
                }
                r.endObject();
                return list;
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public List<Geocache> getMoreGeocaches(@NotNull ResultQuality resultQuality, int startIndex, int maxPerPage,
                                           int geocacheLogCount, int trackableLogCount) throws GeocachingApiException {
        List<Geocache> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        lastSearchResultsFound = 0;
        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                w.name("IsLite").value(resultQuality == ResultQuality.LITE);
                w.name("IsSummaryOnly").value(resultQuality == ResultQuality.SUMMARY);
                w.name("StartIndex").value(startIndex);
                w.name("MaxPerPage").value(maxPerPage);

                if (geocacheLogCount >= 0) {
                    w.name("GeocacheLogCount").value(geocacheLogCount);
                }

                if (trackableLogCount >= 0) {
                    w.name("TrackableLogCount").value(trackableLogCount);
                }

                w.endObject();
            }

            //noinspection Duplicates
            try (JsonReader r = callPost("GetMoreGeocaches?format=json", sw.toString())) {
                r.beginObject();
                checkError(r);

                //noinspection Duplicates
                while (r.hasNext()) {
                    String name = r.nextName();
                    switch (name) {
                        case "Geocaches":
                            list = GeocacheJsonParser.parseList(r);
                            break;
                        case "CacheLimits":
                            lastGeocacheLimits = CacheLimitsJsonParser.parse(r);
                            break;
                        case "TotalMatchingCaches":
                            lastSearchResultsFound = r.nextInt();
                            break;
                        default:
                            r.skipValue();
                            break;
                    }
                }
                r.endObject();
                return list;
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @Override
    public Trackable getTrackable(@NotNull String trackableCode, int trackableLogCount) throws GeocachingApiException {
        List<Trackable> list = null;

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            JsonReader reader;
            //noinspection IfMayBeConditional
            if (trackableCode.toLowerCase(Locale.US).startsWith("tb")) {
                reader = callGet("GetTrackablesByTBCode?accessToken=" + urlEncode(session) +
                        "&tbCode=" + trackableCode +
                        "&trackableLogCount=" + trackableLogCount +
                        "&format=json"
                );
            } else {
                reader = callGet("GetTrackablesByTrackingNumber?accessToken=" + urlEncode(session) +
                        "&trackingNumber=" + trackableCode +
                        "&trackableLogCount=" + trackableLogCount +
                        "&format=json"
                );
            }

            try (JsonReader r = reader) {
                r.beginObject();
                checkError(r);

                //noinspection Duplicates
                while (r.hasNext()) {
                    String name = r.nextName();
                    if ("Trackables".equals(name)) {
                        list = TrackableJsonParser.parseList(r);
                    } else {
                        r.skipValue();
                    }
                }
                r.endObject();

                if (list == null || list.isEmpty()) {
                    return null;
                }

                return list.get(0);
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public List<Trackable> getTrackablesByCacheCode(@NotNull String cacheCode, int startIndex, int maxPerPage,
                                                    int trackableLogCount) throws GeocachingApiException {
        List<Trackable> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("GetTrackablesInCache?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&startIndex=" + startIndex +
                "&maxPerPage=" + maxPerPage +
                "&trackableLogCount=" + trackableLogCount +
                "&format=json")) {

            r.beginObject();
            checkError(r);

            //noinspection Duplicates
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
        }
    }

    @NotNull
    @Override
    public List<TrackableTravel> getTrackableTravelList(@NotNull String trackableCode) throws GeocachingApiException {
        List<TrackableTravel> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetTrackableTravelList?accessToken=" + urlEncode(session) +
                "&tbCode=" + trackableCode +
                "&format=json")) {

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
        }
    }

    private static String urlEncode(String session) throws UnsupportedEncodingException {
        return URLEncoder.encode(session, "UTF-8");
    }

    @NotNull
    @Override
    public List<GeocacheLog> getGeocacheLogsByCacheCode(@NotNull String cacheCode, int startIndex,
                                                        int maxPerPage) throws GeocachingApiException {
        List<GeocacheLog> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetGeocacheLogsByCacheCode?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&startIndex=" + startIndex +
                "&maxPerPage=" + maxPerPage +
                "&format=json")) {

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
        }
    }

    @Override
    public GeocacheLog createFieldNoteAndPublish(@NotNull String cacheCode, @NotNull GeocacheLogType geocacheLogType,
                                                 @NotNull Date dateLogged, @NotNull String note, boolean publish,
                                                 @Nullable ImageData imageData,
                                                 boolean favoriteThisCache) throws GeocachingApiException {
        GeocacheLog geocacheLog = null;

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
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
            }

            try (JsonReader r = callPost("CreateFieldNoteAndPublish?format=json", sw.toString())) {
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
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @Override
    public UserProfile getYourUserProfile(boolean challengesData, boolean favoritePointData, boolean geocacheData,
                                          boolean publicProfileData, boolean souvenirData, boolean trackableData,
                                          @NotNull DeviceInfo deviceInfo) throws GeocachingApiException {
        UserProfile userProfile = null;

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
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
            }

            try (JsonReader r = callPost("GetYourUserProfile?format=json", sw.toString())) {
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
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public List<Trackable> getUsersTrackables(int startIndex, int maxPerPage, int trackableLogCount,
                                              boolean collectionOnly) throws GeocachingApiException {
        List<Trackable> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                w.name("StartIndex").value(startIndex);
                w.name("MaxPerPage").value(maxPerPage);
                w.name("TrackableLogsCount").value(trackableLogCount);
                w.name("CollectionOnly").value(collectionOnly);
                w.endObject();
            }

            //noinspection Duplicates
            try (JsonReader r = callPost("GetUsersTrackables?format=json", sw.toString())) {
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
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public List<BookmarkList> getBookmarkListsForUser() throws GeocachingApiException {
        List<BookmarkList> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("GetBookmarkListsForUser?accessToken=" + urlEncode(session) +
                "&format=json")) {
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
        }
    }

    @NotNull
    @Override
    public List<BookmarkList> getBookmarkListsByUserId(int userId) throws GeocachingApiException {
        List<BookmarkList> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("GetBookmarkListsByUserID?accessToken=" + urlEncode(session) +
                "&userID=" + String.valueOf(userId) +
                "&format=json")) {
            r.beginObject();

            checkError(r);

            //noinspection Duplicates
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
        }
    }

    @NotNull
    @Override
    public List<Bookmark> getBookmarkListByGuid(@NotNull String guid) throws GeocachingApiException {
        List<Bookmark> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                w.name("BookmarkListGuid").value(guid);
                w.endObject();
            }

            try (JsonReader r = callPost("GetBookmarkListByGuid?format=json", sw.toString())) {
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
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public List<GeocacheStatus> getGeocacheStatus(@NotNull Collection<String> cacheCodes)
            throws GeocachingApiException {
        List<GeocacheStatus> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                JsonWriter arrayWriter = w.name("CacheCodes").beginArray();
                for (String cacheCode : cacheCodes) {
                    arrayWriter.value(cacheCode);
                }
                arrayWriter.endArray();
                w.endObject();
            }

            try (JsonReader r = callPost("GetGeocacheStatus?format=json", sw.toString())) {
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
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @Override
    public void addGeocachesToBookmarkList(@NotNull String guid,
                                           @NotNull Collection<String> cacheCodes) throws GeocachingApiException {
        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                w.name("BookmarkListGuid").value(guid);
                JsonWriter arrayWriter = w.name("CacheCodes").beginArray();
                for (String cacheCode : cacheCodes) {
                    arrayWriter.value(cacheCode);
                }
                arrayWriter.endArray();
                w.endObject();
            }

            try (JsonReader r = callPost("AddGeocachesToBookmarkList?format=json", sw.toString())) {
                checkError(r);
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }

    }

    @NotNull
    @Override
    public FavoritePointResult addFavoritePointToGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("AddFavoritePointToCache?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&format=json")) {
            r.beginObject();

            checkError(r);

            FavoritePointResult.Builder builder = FavoritePointResult.builder();

            //noinspection Duplicates
            while (r.hasNext()) {
                String name = r.nextName();
                switch (name) {
                    case "CacheFavoritePoints":
                        builder.cacheFavoritePoints(r.nextInt());
                        break;
                    case "UsersFavoritePoints":
                        builder.usersFavoritePoints(r.nextInt());
                        break;
                    default:
                        r.skipValue();
                        break;
                }
            }
            r.endObject();

            return builder.build();
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public FavoritePointResult removeFavoritePointFromGeocache(@NotNull String cacheCode)
            throws GeocachingApiException {
        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("RemoveFavoritePointFromCache?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&format=json")) {
            r.beginObject();

            checkError(r);

            FavoritePointResult.Builder builder = FavoritePointResult.builder();

            while (r.hasNext()) {
                String name = r.nextName();
                switch (name) {
                    case "CacheFavoritePoints":
                        builder.cacheFavoritePoints(r.nextInt());
                        break;
                    case "UsersFavoritePoints":
                        builder.usersFavoritePoints(r.nextInt());
                        break;
                    default:
                        r.skipValue();
                        break;
                }
            }
            r.endObject();

            return builder.build();
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @Override
    public int getUsersFavoritePoints() throws GeocachingApiException {
        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("GetUsersFavoritePoints?accessToken=" + urlEncode(session) +
                "&format=json")) {
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
        }
    }

    @NotNull
    @Override
    public List<User> getUsersWhoFavoritedGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        List<User> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        //noinspection Duplicates
        try (JsonReader r = callGet("GetUsersWhoFavoritedCache?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&format=json")) {
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
        }
    }

    @NotNull
    @Override
    public List<String> getGeocacheCodesFavoritedByUser() throws GeocachingApiException {
        List<String> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetCacheIdsFavoritedByUser?accessToken=" + urlEncode(session) +
                "&format=json")) {
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
        }
    }

    @NotNull
    @Override
    public List<FavoritedGeocache> getGeocachesFavoritedByUser() throws GeocachingApiException {
        List<FavoritedGeocache> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetCachesFavoritedByUser?accessToken=" + urlEncode(session) +
                "&format=json")) {
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
        }
    }

    @NotNull
    @Override
    public List<ImageData> getImagesForGeocache(@NotNull String cacheCode) throws GeocachingApiException {
        List<ImageData> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetImagesForGeocache?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&format=json")) {
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
        }
    }

    @Override
    public void setGeocachePersonalNote(@NotNull String cacheCode, String note) throws GeocachingApiException {
        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        if (note == null || note.isEmpty()) {
            deleteCachePersonalNote(cacheCode);
            return;
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
                w.beginObject();
                w.name("AccessToken").value(session);
                w.name("CacheCode").value(cacheCode);
                w.name("Note").value(note);
                w.endObject();
            }

            try (JsonReader r = callPost("UpdateCacheNote?format=json", sw.toString())) {
                checkError(r);
            }
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @Override
    public void deleteCachePersonalNote(@NotNull String cacheCode) throws GeocachingApiException {
        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("DeleteCacheNote?accessToken=" + urlEncode(session) +
                "&cacheCode=" + cacheCode +
                "&format=json")) {
            checkError(r);
        } catch (IOException e) {
            throw handleIOException(e);
        }
    }

    @NotNull
    @Override
    public List<TrackableLog> getTrackableLogs(@NotNull String trackableCode, int startIndex,
                                               int maxPerPage) throws GeocachingApiException {
        List<TrackableLog> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetTrackableLogsByTBCode?accessToken=" + urlEncode(session) +
                "&tbCode=" + trackableCode +
                "&startIndex=" + startIndex +
                "&maxPerPage=" + maxPerPage +
                "&format=json")) {
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
        }
    }

    @NotNull
    @Override
    public ApiLimitsResponse getApiLimits() throws GeocachingApiException {
        ApiLimits apiLimits = null;
        MaxPerPage maxPerPage = null;

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        try (JsonReader r = callGet("GetAPILimits?accessToken=" + urlEncode(session) +
                "&format=json")) {
            r.beginObject();

            checkError(r);
            while (r.hasNext()) {
                String name = r.nextName();
                switch (name) {
                    case "Limits":
                        apiLimits = ApiLimitsJsonParser.parse(r);
                        break;
                    case "MaxPerPage":
                        maxPerPage = MaxPerPageJsonParser.parse(r);
                        break;
                    default:
                        r.skipValue();
                        break;
                }
            }
            r.endObject();

            return ApiLimitsResponse.create(apiLimits, maxPerPage);
        } catch (IOException e) {
            throw handleIOException(e);
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
    public List<GeocacheLog> getUsersGeocacheLogs(@NotNull String userName, Date startDate, Date endDate,
                                                  @NotNull GeocacheLogType[] logTypes, boolean excludeArchived,
                                                  int startIndex, int maxPerPage) throws GeocachingApiException {
        List<GeocacheLog> list = new ArrayList<>();

        if (session == null) {
            throw new InvalidSessionException("Session is closed");
        }

        if (userName.isEmpty()) {
            throw new IllegalArgumentException("You must specify user name.");
        }

        if (logTypes.length == 0) {
            throw new IllegalArgumentException("You must specify at least one GeocacheLogType.");
        }

        try {
            StringWriter sw = new StringWriter();
            try (JsonWriter w = new JsonWriter(sw)) {
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
            }

            try (JsonReader r = callPost("GetUsersGeocacheLogs?format=json", sw.toString())) {
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

                return list;
            }
        } catch (IOException e) {
            throw handleIOException(e);
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

            if (status == null) {
                throw new GeocachingApiException("Missing Status in a response.");
            }

            if (status.statusCode() == StatusCode.OK) {
                return;
            }

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
            @SuppressWarnings("HardcodedFileSeparator")
            URL url = new URL(configuration.getApiServiceEntryPointUrl() + "/" + function);
            return new DefaultValueJsonReader(downloader.get(url));
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
            @SuppressWarnings("HardcodedFileSeparator")
            URL url = new URL(configuration.getApiServiceEntryPointUrl() + "/" + function);

            return new DefaultValueJsonReader(downloader.post(url, postData));
        } catch (MalformedURLException e) {
            logger.error(e.toString(), e);
            throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString(), e);
            throw new NetworkException("Error while downloading data (" +
                    e.getClass().getSimpleName() + "): " + e.getMessage(), e);
        }
    }

    private static String maskParameterValues(String function) {
        return function.replaceAll("([Aa]ccess[Tt]oken=)([^&]+)", "$1******");
    }

    private static String maskJsonParameterValues(String postBody) {
        return postBody.replaceAll("(\"[Aa]ccess[Tt]oken\"\\s*:\\s*\")([^\"]+)(\")", "$1******$3");
    }

    private static boolean isGsonException(Throwable t) {
        return t instanceof MalformedJsonException || t instanceof IllegalStateException
                || t instanceof NumberFormatException || t instanceof EOFException;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link LiveGeocachingApi}.
     *
     * @author arcao
     */
    public static class Builder {
        GeocachingApiConfiguration configuration;
        Downloader downloader;

        Builder() {
        }

        /**
         * Set a {@link GeocachingApiConfiguration} where is stored configuration.
         *
         * @param configuration instance of {@link GeocachingApiConfiguration} implementation
         * @return this Builder
         */
        public Builder configuration(GeocachingApiConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

        /**
         * Set a instance of {@link Downloader} which will take care for downloading data.
         *
         * @param downloader instance of {@link Downloader} implementation
         * @return this Builder
         */
        public Builder downloader(Downloader downloader) {
            this.downloader = downloader;
            return this;
        }

        /**
         * Create a new instance of LiveGeocachingApi.<br>
         * If configuration is not set, the instance of {@link DefaultProductionGeocachingApiConfiguration} is used.<br>
         * If downloader is not set, the instance of {@link DefaultDownloader} is used.
         *
         * @return new instance of LiveGeocachingApi
         */
        public GeocachingApi build() {
            if (configuration == null) {
                configuration = new DefaultProductionGeocachingApiConfiguration();
            }

            if (downloader == null) {
                downloader = new DefaultDownloader(configuration);
            }

            return new LiveGeocachingApi(this);
        }
    }
}
