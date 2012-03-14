package com.arcao.geocaching.api.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.log4j.Logger;

import com.arcao.geocaching.api.AbstractGeocachingApi;
import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SimpleGeocache;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.type.CacheLogType;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.exception.InvalidCredentialsException;
import com.arcao.geocaching.api.exception.InvalidSessionException;
import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonBuilder;
import com.arcao.geocaching.api.impl.live_geocaching_api.exception.LiveGeocachingApiException;
import com.arcao.geocaching.api.impl.live_geocaching_api.filter.Filter;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.CacheLogJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.GeocacheJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.JsonReader;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.SimpleGeocacheJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.StatusJsonParser;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.TrackableJsonParser;
import com.google.gson.stream.JsonWriter;

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
  private static final Logger logger = Logger.getLogger(LiveGeocachingApi.class);
	protected static final String BASE_URL = "https://api.groundspeak.com/LiveV6/geocaching.svc/";
	
	protected final String consumerKey;
	protected final String licenseKey;
	
	/**
	 * Create a new instance of LiveGeocachingApi with specified consumer key and license key.
	 * @param consumerKey consumer key
	 * @param licenseKey license key
	 */
	public LiveGeocachingApi(String consumerKey, String licenseKey) {
    this.consumerKey = consumerKey;
    this.licenseKey = licenseKey;
  }

	public void openSession(String userName, String password) throws GeocachingApiException {
		try {
			JsonReader r = callGet(
					"GetUserCredentials?ConsumerKey=" + consumerKey +
					"&LicenseKey=" + licenseKey + 
					"&Username=" + URLEncoder.encode(userName, "UTF-8") +
					"&Password=" + URLEncoder.encode(password, "UTF-8") +
					"&format=json"
			);

			r.beginObject();
			checkError(r);
			
			while (r.hasNext()) {
				String name = r.nextName();
				if ("UserGuid".equals(name)) {
					session = r.nextString();
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			r.close();
			logger.debug("Session: " + session);
		} catch (UnsupportedEncodingException e) {
		  logger.error(e.toString(), e);
			session = null;
		} catch (IOException e) {
		  logger.error(e.toString(), e);
			throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
		}
	}
	
	public void closeSession() {
		session = null;
	}

	public boolean isSessionValid() {
		return true;
	}
	
	public List<SimpleGeocache> searchForGeocaches(boolean isLite, int maxPerPage, int geocacheLogCount, int trackableLogCount,
			Filter[] filters) throws GeocachingApiException {
		
		List<SimpleGeocache> list = new ArrayList<SimpleGeocache>();
		
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
			
			JsonReader r = callPost("SearchForGeocaches?format=json", sw.toString());
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
				} else {
					r.skipValue();
				}
			}
			r.endObject();
			r.close();
			return list;
		} catch (IOException e) {
			logger.error(e.toString(), e);
			throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
		}
	}
	
	public List<SimpleGeocache> getMoreGeocaches(boolean isLite, int startIndex, int maxPerPage, int geocacheLogCount, int trackableLogCount) throws GeocachingApiException {
	  List<SimpleGeocache> list = new ArrayList<SimpleGeocache>();
    
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
      
      JsonReader r = callPost("GetMoreGeocaches?format=json", sw.toString());
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
        } else {
          r.skipValue();
        }
      }
      r.endObject();
      r.close();
      return list;
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}

	public Trackable getTrackable(String trackableCode, int trackableLogCount) throws GeocachingApiException {
	  List<Trackable> list = null;
    
    try {
      JsonReader r;
      
      if (trackableCode.toLowerCase().startsWith("tb")) {
        r = callGet(
            "GetTrackablesByTBCode?AccessToken=" + session +
            "&TBCode=" + trackableCode + 
            "&TrackableLogCount=" + trackableLogCount +
            "&format=json"
        );
      } else {
        r = callGet(
            "GetTrackablesByTrackingNumber?AccessToken=" + session +
            "&TrackingNumber=" + trackableCode + 
            "&TrackableLogCount=" + trackableLogCount +
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
      r.close();
      
      if (list == null || list.size() == 0)
        return null;
      
      return list.get(0);
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}

	public List<Trackable> getTrackablesByCacheCode(String cacheCode, int startIndex, int maxPerPage, int trackableLogCount) throws GeocachingApiException {
	  List<Trackable> list = null;
    
    try {
      JsonReader r = callGet(
            "GetTrackablesInCache?AccessToken=" + session +
            "&CacheCode=" + cacheCode +
            "&StartIndex=" + startIndex + 
            "&MaxPerPage=" + maxPerPage + 
            "&TrackableLogCount=" + trackableLogCount +
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
      r.close();
      
      return list;
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}

	public List<CacheLog> getCacheLogsByCacheCode(String cacheCode,  int startIndex, int maxPerPage) throws GeocachingApiException {
	  List<CacheLog> list = null;
    
    try {
      JsonReader r = callGet(
            "GetGeocacheLogsByCacheCode?AccessToken=" + session +
            "&CacheCode=" + cacheCode +
            "&StartIndex=" + startIndex + 
            "&MaxPerPage=" + maxPerPage + 
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
      r.close();
      
      return list;
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}
	
	public CacheLog createFieldNoteAndPublish(String cacheCode, CacheLogType cacheLogType, Date dateLogged, String note, boolean publish, ImageData imageData,
			boolean favoriteThisCache) throws GeocachingApiException {
    CacheLog cacheLog = null;
    
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
      
      JsonReader r = callPost("CreateFieldNoteAndPublish?format=json", sw.toString());
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
      r.close();
      return cacheLog;
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}
	
	public UserProfile getYourUserProfile(boolean favoritePointData, boolean geocacheData, boolean publicProfileData, boolean souvenirData, boolean trackableData) throws GeocachingApiException {
		throw new GeocachingApiException("Not implemented.");
	}
	
	public void setCachePersonalNote(String cacheCode, String note) throws GeocachingApiException {
	  if (note == null || note.length() == 0) {
	    deleteCachePersonalNote(cacheCode);
	    return;
	  }
	  
	  try {
      StringWriter sw = new StringWriter();
      JsonWriter w = new JsonWriter(sw);
      w.beginObject();
      w.name("AccessToken").value(session);
      w.name("CacheCode").value(cacheCode);
      w.name("Note").value(note);      
      w.endObject();
      w.close();
      
      JsonReader r = callPost("UpdateCacheNote?format=json", sw.toString());
      r.beginObject();
      checkError(r);
      
      while(r.hasNext()) {
        r.nextName();
        r.skipValue();
      }
      r.endObject();
      r.close();
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}
	
	protected void deleteCachePersonalNote(String cacheCode) throws GeocachingApiException {
    try {
      JsonReader r = callGet(
          "GetTrackablesByTBCode?AccessToken=" + session +
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
      r.close();
    } catch (IOException e) {
      logger.error(e.toString(), e);
      throw new GeocachingApiException("Response is not valid JSON string: " + e.getMessage(), e);
    }
	}
	
	// -------------------- Helper methods ----------------------------------------
	
	protected void checkError(JsonReader r) throws GeocachingApiException, IOException {
		if ("Status".equals(r.nextName())) {
			StatusJsonParser.Status status = StatusJsonParser.parse(r);
			
			switch (status.getStatusCode()) {
				case OK:
					return;
				case NotAuthorized:
				case UserDidNotAuthorize:
				case UserTokenNotValid:
					throw new InvalidSessionException(status.getStatusMessage());
				case AccountNotFound:
					throw new InvalidCredentialsException(status.getStatusMessage());
				default:
					throw new LiveGeocachingApiException(status.getStatusCode(), status.getStatusMessage());
			}
		}
	}
	
	protected JsonReader callGet(String function) throws GeocachingApiException {
		InputStream is = null;
		InputStreamReader isr = null;

		logger.debug("Getting " + maskPassword(function));

		try {
			URL url = new URL(BASE_URL + function);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// important! sometimes GC API takes too long to get response
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);

			con.setRequestMethod("GET");
			//con.setRequestProperty("User-Agent", "Geocaching/4.0 CFNetwork/459 Darwin/10.0.0d3");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Accept-Language", "en-US");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");

			final String encoding = con.getContentEncoding();

			if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
				logger.debug("callGet(): GZIP OK");
				is = new GZIPInputStream(con.getInputStream());
			} else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
				logger.debug("callGet(): DEFLATE OK");
				is = new InflaterInputStream(con.getInputStream(), new Inflater(true));
			} else {
				logger.debug("callGet(): WITHOUT COMPRESSION");
				is = con.getInputStream();
			}

			isr = new InputStreamReader(is, "UTF-8");
			return new JsonReader(isr);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new GeocachingApiException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
		}
	}

	protected JsonReader callPost(String function, String postBody) throws GeocachingApiException {
		InputStream is = null;
		InputStreamReader isr = null;

		logger.debug("Posting " + maskPassword(function));
		
		try {
			byte[] data = postBody.getBytes("UTF-8");
			
			URL url = new URL(BASE_URL + function);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			
			// important! sometimes GC API takes too long to get response
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Content-Length", Integer.toString(data.length));
			//con.setRequestProperty("User-Agent", "Geocaching/4.0 CFNetwork/459 Darwin/10.0.0d3");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Accept-Language", "en-US");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");

			OutputStream os = con.getOutputStream();
						
			logger.debug("Body: " + postBody);
			os.write(data);
			os.flush();
			os.close();
			
			if (con.getResponseCode() < 400) {
		    is = con.getInputStream();
			} else {
				is = con.getErrorStream();
			}

			final String encoding = con.getContentEncoding();

			if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
				logger.debug("callPost(): GZIP OK");
				is = new GZIPInputStream(is);
			} else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
				logger.debug("callPost(): DEFLATE OK");
				is = new InflaterInputStream(is, new Inflater(true));
			} else {
				logger.debug("callPost(): WITHOUT COMPRESSION");
			}

			isr = new InputStreamReader(is, "UTF-8");
			
			return new JsonReader(isr);			
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new GeocachingApiException("Error while downloading data (" + e.getClass().getSimpleName() + "): " + e.getMessage(), e);
		}
	}
	
	protected String maskPassword(String input) {
		int start;
		if ((start = input.indexOf("&Password=")) == -1)
			return input;

		return input.substring(0, start + 10) + "******" + input.substring(input.indexOf('&', start + 10));
	}
}
