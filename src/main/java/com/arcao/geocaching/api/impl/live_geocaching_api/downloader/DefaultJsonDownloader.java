package com.arcao.geocaching.api.impl.live_geocaching_api.downloader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.log4j.Logger;

import com.arcao.geocaching.api.configuration.GeocachingApiConfiguration;
import com.arcao.geocaching.api.exception.InvalidResponseException;
import com.arcao.geocaching.api.exception.NetworkException;
import com.arcao.geocaching.api.impl.live_geocaching_api.parser.JsonReader;

/**
 * Default implementation of {@link JsonDownloader} using {@link HttpURLConnection}
 * @author arcao
 */
public class DefaultJsonDownloader implements JsonDownloader {
	private static final Logger logger = Logger.getLogger(DefaultJsonDownloader.class);
	
	private final GeocachingApiConfiguration configuration;

	/**
	 * Create a new {@link DefaultJsonDownloader} using specified configuration
	 * @param configuration configuration
	 */
	public DefaultJsonDownloader(GeocachingApiConfiguration configuration) {
		this.configuration = configuration;
	}

	public JsonReader get(URL url) throws NetworkException, InvalidResponseException {
		InputStream is = null;
		InputStreamReader isr = null;

		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// important! sometimes GC API takes too long to return response
			con.setConnectTimeout(configuration.getConnectTimeout());
			con.setReadTimeout(configuration.getReadTimeout());

			con.setRequestMethod("GET");
			//con.setRequestProperty("User-Agent", "Geocaching/4.0 CFNetwork/459 Darwin/10.0.0d3");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Accept-Language", "en-US");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");

			if (con.getResponseCode() >= 400) {
				is = con.getErrorStream();
			} else {
				is = con.getInputStream();
			}

			final String encoding = con.getContentEncoding();

			if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
				logger.debug("get: GZIP OK");
				is = new GZIPInputStream(is);
			} else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
				logger.debug("get: DEFLATE OK");
				is = new InflaterInputStream(is, new Inflater(true));
			} else {
				logger.debug("get: WITHOUT COMPRESSION");
			}

			if (con.getResponseCode() >= 400) {
				isr = new InputStreamReader(is, "UTF-8");

				StringBuilder sb = new StringBuilder();
				char buffer[] = new char[1024];
				int len = 0;

				while ((len = isr.read(buffer)) != -1) {
					sb.append(buffer, 0, len);
				}

				isr.close();

				// read error response
				throw new InvalidResponseException(sb.toString());
			}

			isr = new InputStreamReader(is, "UTF-8");
			return new JsonReader(isr);
		} catch (InvalidResponseException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + ")", e);
		}
	}

	public JsonReader post(URL url, byte[] postData) throws NetworkException, InvalidResponseException {
		InputStream is = null;
		InputStreamReader isr = null;

		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);

			// important! sometimes GC API takes too long to return response
			con.setConnectTimeout(configuration.getConnectTimeout());
			con.setReadTimeout(configuration.getReadTimeout());

			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Content-Length", Integer.toString(postData.length));
			//con.setRequestProperty("User-Agent", "Geocaching/4.0 CFNetwork/459 Darwin/10.0.0d3");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Accept-Language", "en-US");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");

			OutputStream os = con.getOutputStream();

			os.write(postData);
			os.flush();
			os.close();
			
			if (con.getResponseCode() >= 400) {
				is = con.getErrorStream();
			} else {
				is = con.getInputStream();
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

			if (con.getResponseCode() >= 400) {
				isr = new InputStreamReader(is, "UTF-8");

				StringBuilder sb = new StringBuilder();
				char buffer[] = new char[1024];
				int len = 0;

				while ((len = isr.read(buffer)) != -1) {
					sb.append(buffer, 0, len);
				}

				isr.close();

				// read error response
				throw new InvalidResponseException(sb.toString());
			}

			isr = new InputStreamReader(is, "UTF-8");

			return new JsonReader(isr);
		} catch (InvalidResponseException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new NetworkException("Error while downloading data (" + e.getClass().getSimpleName() + "): " + e.getMessage(), e);
		}
	}

}
