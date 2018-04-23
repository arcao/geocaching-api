package com.arcao.geocaching.api.builder;

import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Locale;

/**
 * Helper class during JSON serializing process.
 *
 * @author arcao
 */
public final class JsonBuilder {
    private JsonBuilder() {
    }

    /**
     * Convert Date object to JSON date notation.
     *
     * @param date date convert
     * @return JSON notation for date
     */
    public static String dateToJsonString(@Nullable Date date) {
        if (date == null)
            return null;

        //noinspection HardcodedFileSeparator
        return String.format(Locale.US, "/Date(%d)/", date.getTime());
    }
}
