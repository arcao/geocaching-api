package com.arcao.geocaching.api.impl.live_geocaching_api.builder;

import java.util.Date;
import java.util.Locale;

/**
 * Helper class during JSON serializing process
 *
 * @author arcao
 */
public class JsonBuilder {
    /**
     * Convert Date object to JSON date notation
     *
     * @param date date convert
     * @return JSON notation for date
     */
    public static String dateToJsonString(Date date) {
        if (date == null)
            return null;

        return String.format(Locale.US, "/Date(%d)/", date.getTime());
    }
}
