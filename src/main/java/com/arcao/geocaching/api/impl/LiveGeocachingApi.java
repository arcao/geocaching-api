package com.arcao.geocaching.api.impl;


import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcao.geocaching.api.AbstractGeocachingApi;
import com.arcao.geocaching.api.configuration.GeocachingApiConfiguration;
import com.arcao.geocaching.api.configuration.impl.DefaultProductionGeocachingApiConfiguration;
import com.arcao.geocaching.api.data.CacheLimits;
import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.DeviceInfo;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SimpleGeocache;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.data.TrackableTravel;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.type.CacheLogType;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.exception.InvalidCredentialsException;
import com.arcao.geocaching.api.exception.InvalidResponseException;
import com.arcao.geocaching.api.exception.InvalidSessionException;
import com.arcao.geocaching.api.exception.NetworkException;
import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonBuilder;
import com.arcao.geocaching.api.impl.live_geocaching_api.downloader.DefaultJsonDownloader;
import com.arcao.geocaching.api.impl.live_geocaching_api.downloader.JsonDownloader;
import com.arcao.geocaching.api.impl.live_geocaching_api.exception.LiveGeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.ApiLimitsJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.CacheLimitsJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.CacheLogJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.GeocacheJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.JsonReader;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.SimpleGeocacheJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.StatusJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.TrackableJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.TrackableLogJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.TrackableTravelJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.UserProfileParser;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

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
 * @author arcao
 *
 */
public class LiveGeocachingApi extends AbstractGeocachingApi {
	private static final Logger logger = LoggerFactory.getLogger(LiveGeocachingApi.class);
	
	protected final GeocachingApiConfiguration configuration;
	protected final JsonDownloader downloader;
	
	protected CacheLimits lastCacheLimits = null;

	private boolean sessionValid = false;
		
	/**
	 * Create a new instance of LiveGeocachingApi with configuration specified by configuration parameter
	 * @param configuration configuration object
	 */
	protected LiveGeocachingApi(GeocachingApiConfiguration configuration, JsonDownloader downloader) {
		this.configuration = configuration;
		this.downloader = downloader;
	}

	@Override
	public void openSession(String session) throws GeocachingApiException {
		super.openSession(session);
		sessionValid = true;
	}

	public void closeSession() {
		session = null;
		sessionValid = false;
	}

	public boolean isSessionValid() {
		return sessionValid;
	}

	public List<SimpleGeocache> searchForGeocaches(boolean isLite, int maxPerPage, int geocacheLogCount, int trackableLogCount,
			Filter[] filters) throws GeocachingApiException {

		List<SimpleGeocache> list = new ArrayList<SimpleGeocache>();

		JsonReader r = null;
		try {
			StringWriter sw = new StringWriter();
			JsonWriter w = new JsonWriter(sw);
			w.beginObject();
			w.name("AccessToken").value(session);
			w.name("IsLite").value(isLite);
			w.name("MaxPerPage").value(maxPerPage);

			if (geocacheLogCount >= 0)
				w.name("GeocacheLogCount").value(geocacheLogCount);

			if (trackableLogCount >= 0)
				w.name("TrackableLogCount").value(trackableLogCount);

			for (Filter filter : filters) {
				if (filter.isValid())
					filter.writeJson(w);
			}
			w.endObject();
			w.close();

			r = callPost("SearchForGeocaches?format=json", sw.toString());
			r.beginObject();
			checkError(r);

			while(r.hasNext()) {
				String name = r.nextName();
				if ("Geocaches".equals(name)) {
					if (isLite) {
						list = SimpleGeocacheJsonParser.parseList(r);
					} else {
						list = GeocacheJsonParser.parseList(r);
					}
				} else if ("CacheLimits".equals(name)) {
					lastCacheLimits = CacheLimitsJsonParser.parse(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			return list;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public List<SimpleGeocache> getMoreGeocaches(boolean isLite, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount) throws GeocachingApiException {
		List<SimpleGeocache> list = new ArrayList<SimpleGeocache>();

		JsonReader r = null;
		try {
			StringWriter sw = new StringWriter();
			JsonWriter w = new JsonWriter(sw);
			w.beginObject();
			w.name("AccessToken").value(session);
			w.name("IsLite").value(isLite);
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

			while(r.hasNext()) {
				String name = r.nextName();
				if ("Geocaches".equals(name)) {
					if (isLite) {
						list = SimpleGeocacheJsonParser.parseList(r);
					} else {
						list = GeocacheJsonParser.parseList(r);
					}
				} else if ("CacheLimits".equals(name)) {
					lastCacheLimits = CacheLimitsJsonParser.parse(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			return list;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public Trackable getTrackable(String trackableCode, int trackableLogCount) throws GeocachingApiException {
		List<Trackable> list = null;

		JsonReader r = null;
		try {

			if (trackableCode.toLowerCase().startsWith("tb")) {
				r = callGet(
						"GetTrackablesByTBCode?accessToken=" + URLEncoder.encode(session, "UTF-8") +
						"&tbCode=" + trackableCode + 
						"&trackableLogCount=" + trackableLogCount +
						"&format=json"
						);
			} else {
				r = callGet(
						"GetTrackablesByTrackingNumber?accessToken=" + URLEncoder.encode(session, "UTF-8") +
						"&trackingNumber=" + trackableCode + 
						"&trackableLogCount=" + trackableLogCount +
						"&format=json"
						);
			}

			r.beginObject();
			checkError(r);
			while(r.hasNext()) {
				String name = r.nextName();
				if ("Trackables".equals(name)) {
					list = TrackableJsonParser.parseList(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();

			if (list == null || list.size() == 0)
				return null;

			if (!trackableCode.toLowerCase().startsWith("tb")) {
				list.get(0).setLookupCode(trackableCode);
			}

			return list.get(0);
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public List<Trackable> getTrackablesByCacheCode(String cacheCode, int startIndex, int maxPerPage, int trackableLogCount) throws GeocachingApiException {
		List<Trackable> list = new ArrayList<Trackable>();

		JsonReader r = null;
		try {
			r = callGet(
					"GetTrackablesInCache?accessToken=" + URLEncoder.encode(session, "UTF-8") +
					"&cacheCode=" + cacheCode +
					"&startIndex=" + startIndex + 
					"&maxPerPage=" + maxPerPage + 
					"&trackableLogCount=" + trackableLogCount +
					"&format=json"
					);

			r.beginObject();
			checkError(r);
			while(r.hasNext()) {
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
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}
	
	public List<TrackableTravel> getTrackableTravelList(String trackableCode) throws GeocachingApiException {
		List<TrackableTravel> list = new ArrayList<TrackableTravel>();

		JsonReader r = null;
		try {
			r = callGet(
					"GetTrackableTravelList?accessToken=" + URLEncoder.encode(session, "UTF-8") +
					"&tbCode=" + trackableCode +
					"&format=json"
					);

			r.beginObject();
			checkError(r);
			while(r.hasNext()) {
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
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}

	}

	public List<CacheLog> getCacheLogsByCacheCode(String cacheCode, int startIndex, int maxPerPage) throws GeocachingApiException {
		List<CacheLog> list = new ArrayList<CacheLog>();

		JsonReader r = null;
		try {
			r = callGet(
					"GetGeocacheLogsByCacheCode?accessToken=" + URLEncoder.encode(session, "UTF-8") +
					"&cacheCode=" + cacheCode +
					"&startIndex=" + startIndex + 
					"&maxPerPage=" + maxPerPage + 
					"&format=json"
					);

			r.beginObject();
			checkError(r);
			while(r.hasNext()) {
				String name = r.nextName();
				if ("Logs".equals(name)) {
					list = CacheLogJsonParser.parseList(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();

			return list;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public CacheLog createFieldNoteAndPublish(String cacheCode, CacheLogType cacheLogType, Date dateLogged, String note, boolean publish, ImageData imageData,
			boolean favoriteThisCache) throws GeocachingApiException {
		CacheLog cacheLog = null;

		JsonReader r = null;
		try {
			StringWriter sw = new StringWriter();
			JsonWriter w = new JsonWriter(sw);
			w.beginObject();
			w.name("AccessToken").value(session);
			w.name("CacheCode").value(cacheCode);
			w.name("WptLogTypeId").value(cacheLogType.getFriendlyName());
			w.name("UTCDateLogged").value(JsonBuilder.dateToJsonString(dateLogged));
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

			while(r.hasNext()) {
				String name = r.nextName();
				if ("Log".equals(name)) {
					cacheLog = CacheLogJsonParser.parse(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			return cacheLog;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public UserProfile getYourUserProfile(boolean challengesData, boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData,
			boolean trackableData, DeviceInfo deviceInfo) throws GeocachingApiException {
		UserProfile userProfile = null;
		
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
			
			if (deviceInfo != null) {
				w.name("DeviceInfo");
				deviceInfo.writeJson(w);
			}
			
			w.endObject();
			w.close();

			r = callPost("GetYourUserProfile?format=json", sw.toString());
			r.beginObject();
			checkError(r);

			while(r.hasNext()) {
				String name = r.nextName();
				if ("Profile".equals(name)) {
					userProfile = UserProfileParser.parse(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			return userProfile;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}
	
	public List<Trackable> getUsersTrackables(int startIndex, int maxPerPage, int trackableLogCount, boolean collectionOnly) throws GeocachingApiException {
		List<Trackable> list = new ArrayList<Trackable>();

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
			while(r.hasNext()) {
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
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public void setCachePersonalNote(String cacheCode, String note) throws GeocachingApiException {
		if (note == null || note.length() == 0) {
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
			r.beginObject();
			checkError(r);

			while(r.hasNext()) {
				r.nextName();
				r.skipValue();
			}
			r.endObject();
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	protected void deleteCachePersonalNote(String cacheCode) throws GeocachingApiException {
		JsonReader r = null;
		try {
			r = callGet(
					"DeleteCacheNote?accessToken=" + URLEncoder.encode(session, "UTF-8") +
					"&cacheCode=" + cacheCode +
					"&format=json"
					);
			r.beginObject();
			checkError(r);

			while (r.hasNext()) {
				r.nextName();
				r.skipValue();
			}
			r.endObject();
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	public List<TrackableLog> getTrackableLogs(String trackableCode, int startIndex, int maxPerPage) throws GeocachingApiException {
		List<TrackableLog> list = new ArrayList<TrackableLog>();

		JsonReader r = null;
		try {
			r = callGet(
					"GetGeocacheLogsByCacheCode?accessToken=" + URLEncoder.encode(session, "UTF-8") +
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
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}
	
	public ApiLimits getApiLimits() throws GeocachingApiException {
		ApiLimits apiLimits = null;
		
		JsonReader r = null;
		try {
			r = callGet(
					"GetAPILimits?accessToken=" + URLEncoder.encode(session, "UTF-8") +
					"&format=json"
					);

			r.beginObject();
			checkError(r);
			while (r.hasNext()) {
				String name = r.nextName();
				if ("Limits".equals(name)) {
					apiLimits = ApiLimitsJsonParser.parse(r);
				} else {
					r.skipValue();
				}
			}
			r.endObject();

			return apiLimits;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new InvalidResponseException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}
	
	public CacheLimits getLastCacheLimits() {
		return lastCacheLimits;
	}
	
	public List<CacheLog> getUsersGeocacheLogs(String userName, Date startDate, Date endDate, CacheLogType[] logTypes, boolean excludeArchived, int startIndex, int maxPerPage) throws GeocachingApiException {
		List<CacheLog> list = new ArrayList<CacheLog>();
		
		if (userName == null || userName.length() == 0)
			throw new IllegalArgumentException("You must specify user name.");
		
		if (logTypes == null || logTypes.length == 0)
			throw new IllegalArgumentException("You must specify at least one CacheLogType.");

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

			for (CacheLogType cacheLogType : logTypes) {
				logs.value(cacheLogType.getGroundspeakId());
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
					list = CacheLogJsonParser.parseList(r);
					break;
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			r.close();

			return list;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			if (!isGsonException(e)) {
				throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
			}

			throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
		} finally {
			closeJsonReader(r); 
		}
	}

	// -------------------- Helper methods ----------------------------------------
	protected void prepareRequest() {
		lastCacheLimits = null;
	}	

	protected void checkError(JsonReader r) throws GeocachingApiException, IOException {
		if ("Status".equals(r.nextName())) {
			StatusJsonParser.Status status = StatusJsonParser.parse(r);

			switch (status.getStatusCode()) {
				case OK:
					return;
				case UserDidNotAuthorize:
				case UserTokenNotValid:
				case SessionExpired:
				case AccessTokenNotValid:
					sessionValid = false;
					throw new InvalidSessionException(status.getStatusMessage());
				case AccountNotFound:
					sessionValid = false;
					throw new InvalidCredentialsException(status.getStatusMessage());
				default:
					throw new LiveGeocachingApiException(status);
			}
		} else {
			throw new GeocachingApiException("Missing Status in a response.");
		}
	}

	protected JsonReader callGet(String function) throws NetworkException, InvalidResponseException {
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

	protected JsonReader callPost(String function, String postBody) throws NetworkException, InvalidResponseException {
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

	protected String maskParameterValues(String function) {
		function = function.replaceAll("([Aa]ccess[Tt]oken=)([^&]+)", "$1******");
		return function;
	}

	protected String maskJsonParameterValues(String postBody) {
		postBody = postBody.replaceAll("(\"[Aa]ccess[Tt]oken\"\\s*:\\s*\")([^\"]+)(\")", "$1******$3");
		return postBody;
	}

	protected boolean isGsonException(Throwable t) {
		// This IOException mess will be fixed in a next GSON release
		return (IOException.class.equals(t.getClass()) && t.getMessage() != null && t.getMessage().startsWith("Expected JSON document")) || t instanceof MalformedJsonException || t instanceof IllegalStateException || t instanceof NumberFormatException;
	}
	
	protected void closeJsonReader(JsonReader r) {
		if (r == null)
			return;
		
		try {
			r.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Builder for {@link LiveGeocachingApi}
	 * @author arcao
	 */
	public static class Builder {
		protected GeocachingApiConfiguration configuration;
		protected JsonDownloader downloader;
		
		/**
		 * Set a {@link GeocachingApiConfiguration} where is stored configuration
		 * @param configuration instance of {@link GeocachingApiConfiguration} implementation
		 * @return this Builder
		 */
		public Builder setConfiguration(GeocachingApiConfiguration configuration) {
			this.configuration = configuration;
			return this;
		}
		
		/**
		 * Set a instance of {@link JsonDownloader} which will take care for downloading data
		 * @param downloader instance of {@link JsonDownloader} implementation
		 * @return this Builder
		 */
		public Builder setDownloader(JsonDownloader downloader) {
			this.downloader = downloader;
			return this;
		}
		
		/**
		 * Create a new instance of LiveGeocachingApi.<br>
		 * If configuration is not set, the instance of {@link DefaultProductionGeocachingApiConfiguration} is used.<br>
		 * If downloader is not set, the instance of {@link DefaultJsonDownloader} is used. 
		 * @return new instance of LiveGeocachingApi
		 */
		public LiveGeocachingApi build() {
			if (configuration == null) {
				configuration = new DefaultProductionGeocachingApiConfiguration();
			}
			
			if (downloader == null) {
				downloader = new DefaultJsonDownloader(configuration);
			}
			
			return new LiveGeocachingApi(configuration, downloader);
		}
	}
}
