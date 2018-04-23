package com.arcao.geocaching.api.downloader;

import com.arcao.geocaching.api.exception.InvalidResponseException;
import com.arcao.geocaching.api.exception.NetworkException;
import com.google.gson.stream.JsonReader;

import java.net.URL;

/**
 * Interface to implement downloading JSON data with GET and POST method.
 *
 * @author arcao
 */
public interface JsonDownloader {
    /**
     * Send request to a specified url with GET method and returns instance of
     * {@link JsonReader} with a response.
     *
     * @param url url to request including query parameters
     * @return instance of {@link JsonReader} object with a response
     * @throws NetworkException         If Network I/O error occurs
     * @throws InvalidResponseException If response contains invalid data or HTTP response code isn't 200 (OK)
     */
    JsonReader get(URL url) throws NetworkException, InvalidResponseException;

    /**
     * Send request to a specified url with POST method including post data and returns
     * instance of {@link JsonReader} with a response.
     *
     * @param url      url to request including query parameters
     * @param postData post data
     * @return instance of {@link JsonReader} object with a response
     * @throws NetworkException         If Network I/O error occurs
     * @throws InvalidResponseException If response contains invalid data or HTTP response code isn't 200 (OK)
     */
    JsonReader post(URL url, byte[] postData) throws NetworkException, InvalidResponseException;
}
