/*
  Some parts of this file contains work from c:geo application licensed under
  Apache License 2.0.
 */
package com.arcao.geocaching.api.data.coordinates;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/**
 * A class for formating geographic coordinates represented by Coordinates class
 * to user readable format.
 *
 * @author arcao
 * @since 1.5
 */
public class CoordinatesFormatter {
    private static final Logger logger = LoggerFactory.getLogger(CoordinatesFormatter.class);

    /**
     * Example: "10,123456 -0,123456".
     */
    public static final int LAT_LON_DECDEGREE = 0;

    /**
     * Example: "10.123456,-0.123456" (unlocalized).
     */
    public static final int LAT_LON_DECDEGREE_COMMA = 1;

    /**
     * Example: "N 10° 12,345 · W 5° 12,345".
     */
    public static final int LAT_LON_DECMINUTE = 2;

    /**
     * Example: "N 10° 12.345 W 5° 12.345" (unlocalized).
     */
    public static final int LAT_LON_DECMINUTE_RAW = 3;

    /**
     * Example: "N 10° 12' 34" W 5° 12' 34"".
     */
    public static final int LAT_LON_DECSECOND = 4;

    /**
     * Example: "-0.123456" (unlocalized latitude).
     */
    public static final int LAT_DECDEGREE_RAW = 5;

    /**
     * Example: "N 10° 12,345".
     */
    public static final int LAT_DECMINUTE = 6;

    /**
     * Example: "N 10 12,345".
     */
    public static final int LAT_DECMINUTE_RAW = 7;

    /**
     * Example: "-0.123456" (unlocalized longitude).
     */
    public static final int LON_DECDEGREE_RAW = 8;

    /**
     * Example: "W 5° 12,345".
     */
    public static final int LON_DECMINUTE = 9;

    /**
     * Example: "W 5 12,345".
     */
    public static final int LON_DECMINUTE_RAW = 10;

    private final int format;
    private final Locale locale;

    /**
     * Create a new instance of formatter with predefined format and system
     * default locale for formating numbers.
     *
     * @param format format to use
     */
    public CoordinatesFormatter(int format) {
        this(format, Locale.getDefault());
    }

    /**
     * Create a new instance of formatter with predefined format and locale for
     * formating numbers.
     *
     * @param format format to use
     * @param locale locale to use for formating numbers
     */
    public CoordinatesFormatter(int format, Locale locale) {
        this.format = format;
        this.locale = locale;
    }

    /**
     * Formats the Coordinates.
     *
     * @param coords the Coordinates to format
     * @return the formatted coordinates
     */
    public String format(final Coordinates coords) {
        final double latSigned = coords.latitude();
        final double lonSigned = coords.longitude();

        DMM dmm = new DMM(latSigned, lonSigned);
        DMS dms = new DMS(latSigned, lonSigned);

        switch (format) {
            case LAT_LON_DECDEGREE:
                return String.format(locale, "%.6f %.6f", latSigned, lonSigned);

            case LAT_LON_DECDEGREE_COMMA:
                return String.format((Locale) null, "%.6f,%.6f", latSigned, lonSigned);

            case LAT_LON_DECMINUTE:
                return String.format(locale, "%c %d° %06.3f · %c %d° %06.3f",
                        dmm.latDir, dmm.latDeg, dmm.latMinRaw, dmm.lonDir, dmm.lonDeg, dmm.lonMinRaw);

            case LAT_LON_DECMINUTE_RAW:
                return String.format((Locale) null, "%c %d° %06.3f %c %d° %06.3f",
                        dmm.latDir, dmm.latDeg, dmm.latMinRaw, dmm.lonDir, dmm.lonDeg, dmm.lonMinRaw);

            case LAT_LON_DECSECOND:
                return String.format(locale, "%c %d° %d' %06.3f\" · %c %d° %d' %06.3f\"",
                        dms.latDir, dms.latDeg, dms.latMin, dms.latSecRaw, dms.lonDir, dms.lonDeg, dms.lonMin, dms.lonSecRaw);

            case LAT_DECDEGREE_RAW:
                return String.format((Locale) null, "%.6f", latSigned);

            case LAT_DECMINUTE:
                return String.format(locale, "%c %d° %06.3f", dmm.latDir, dmm.latDeg, dmm.latMinRaw);

            case LAT_DECMINUTE_RAW:
                return String.format(locale, "%c %d %06.3f", dmm.latDir, dmm.latDeg, dmm.latMinRaw);

            case LON_DECDEGREE_RAW:
                return String.format((Locale) null, "%.6f", lonSigned);

            case LON_DECMINUTE:
                return String.format(locale, "%c %d° %06.3f", dmm.lonDir, dmm.lonDeg, dmm.lonMinRaw);

            case LON_DECMINUTE_RAW:
                return String.format(locale, "%c %d %06.3f", dmm.lonDir, dmm.lonDeg, dmm.lonMinRaw);
        }

        // Keep the compiler happy even though it cannot happen
        return null;
    }

    /* Constant values needed for calculation */
    private static final double D60 = 60.0d;
    private static final double D1000 = 1000.0d;
    private static final double D3600 = 3600.0d;
    static final BigDecimal BD_SIXTY = BigDecimal.valueOf(D60);
    static final BigDecimal BD_THOUSAND = BigDecimal.valueOf(D1000);
    static final BigDecimal BD_ONEHOUNDREDTHOUSAND = BigDecimal.valueOf(100000.0d);

    /**
     * Value type for the direction.
     */
    protected static class Direction {
        /**
         * latitude direction, 'N' or 'S'.
         */
        public final char latDir;
        /**
         * longitude direction, 'E' or 'W'.
         */
        public final char lonDir;

        protected Direction(final double latSigned, final double lonSigned) {
            latDir = latSigned < 0 ? 'S' : 'N';
            lonDir = lonSigned < 0 ? 'W' : 'E';
        }

        protected static String addZeros(final int value, final int len) {
            return StringUtils.leftPad(Integer.toString(value), len, '0');
        }
    }

    /**
     * Helper class to calculate degrees for coordinates.
     */
    protected static final class DDD extends Direction {

        /**
         * latitude degree value.
         */
        public final int latDeg;
        /**
         * fractional part of the latitude degree value.
         */
        public final int latDegFrac;

        public final int lonDeg;
        public final int lonDegFrac;

        public DDD(final double latSigned, final double lonSigned) {
            super(latSigned, lonSigned);
            BigDecimal bdLat = BigDecimal.valueOf(latSigned).abs();
            latDeg = bdLat.intValue();
            BigDecimal bdLatFrac = bdLat.subtract(BigDecimal.valueOf(latDeg)).multiply(BD_ONEHOUNDREDTHOUSAND);
            latDegFrac = bdLatFrac.setScale(0, RoundingMode.HALF_UP).intValue();

            BigDecimal bdlon = BigDecimal.valueOf(lonSigned).abs();
            lonDeg = bdlon.intValue();
            BigDecimal bdLonFrac = bdlon.subtract(BigDecimal.valueOf(lonDeg)).multiply(BD_ONEHOUNDREDTHOUSAND);
            lonDegFrac = bdLonFrac.setScale(0, RoundingMode.HALF_UP).intValue();
        }

        public static Coordinates createCoordinates(final String latDir, final String latDeg, final String latDegFrac,
                                                    final String lonDir, final String lonDeg, final String lonDegFrac) {
            double lat = 0.0d;
            double lon = 0.0d;
            try {
                lat = Double.parseDouble(latDeg + "." + addZeros(Integer.parseInt(latDegFrac), 5));
                lon = Double.parseDouble(lonDeg + "." + addZeros(Integer.parseInt(lonDegFrac), 5));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
            lat *= "S".equalsIgnoreCase(latDir) ? -1 : 1;
            lon *= "W".equalsIgnoreCase(lonDir) ? -1 : 1;
            return Coordinates.create(lat, lon);
        }
    }

    /**
     * Helper class to calculate degree and minutes for coordinates.
     */
    protected static final class DMM extends Direction {

        public final int latDeg;
        public final double latMinRaw;
        public final int latMin;
        public final int latMinFrac;

        public final int lonDeg;
        public final double lonMinRaw;
        public final int lonMin;
        public final int lonMinFrac;

        public DMM(final double latSigned, final double lonSigned) {
            super(latSigned, lonSigned);
            BigDecimal bdLat = BigDecimal.valueOf(latSigned).abs();
            latDeg = bdLat.intValue();
            BigDecimal bdLatMin = bdLat.subtract(BigDecimal.valueOf(latDeg)).multiply(BD_SIXTY);
            // Rounding here ...
            bdLatMin = bdLatMin.setScale(3, RoundingMode.HALF_UP);
            latMinRaw = bdLatMin.doubleValue();
            latMin = bdLatMin.intValue();
            BigDecimal bdLatMinFrac = bdLatMin.subtract(BigDecimal.valueOf(latMin)).multiply(BD_THOUSAND);
            latMinFrac = bdLatMinFrac.setScale(0, RoundingMode.HALF_UP).intValue();

            BigDecimal bdlon = BigDecimal.valueOf(lonSigned).abs();
            lonDeg = bdlon.intValue();
            BigDecimal bdLonMin = bdlon.subtract(BigDecimal.valueOf(lonDeg)).multiply(BD_SIXTY);
            // Rounding here ...
            bdLonMin = bdLonMin.setScale(3, RoundingMode.HALF_UP);
            lonMinRaw = bdLonMin.doubleValue();
            lonMin = bdLonMin.intValue();
            BigDecimal bdLonMinFrac = bdLonMin.subtract(BigDecimal.valueOf(lonMin)).multiply(BD_THOUSAND);
            lonMinFrac = bdLonMinFrac.setScale(0, RoundingMode.HALF_UP).intValue();
        }

        public static Coordinates createCoordinates(final String latDir, final String latDeg, final String latMin, final String latMinFrac,
                                                    final String lonDir, final String lonDeg, final String lonMin, final String lonMinFrac) {
            double lat = 0.0d;
            double lon = 0.0d;
            try {
                lat = Double.parseDouble(latDeg) + Double.parseDouble(latMin + "." + addZeros(Integer.parseInt(latMinFrac), 3)) / D60;
                lon = Double.parseDouble(lonDeg) + Double.parseDouble(lonMin + "." + addZeros(Integer.parseInt(lonMinFrac), 3)) / D60;
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
            lat *= "S".equalsIgnoreCase(latDir) ? -1 : 1;
            lon *= "W".equalsIgnoreCase(lonDir) ? -1 : 1;
            return Coordinates.create(lat, lon);
        }
    }

    /**
     * Helper class to calculate degree, minutes and seconds for coordinates.
     */
    protected static final class DMS extends Direction {

        public final int latDeg;
        public final int latMin;
        public final double latSecRaw;
        public final int latSec;
        public final int latSecFrac;

        public final int lonDeg;
        public final int lonMin;
        public final double lonSecRaw;
        public final int lonSec;
        public final int lonSecFrac;

        public DMS(final double latSigned, final double lonSigned) {
            super(latSigned, lonSigned);
            BigDecimal bdLat = BigDecimal.valueOf(latSigned).abs();
            latDeg = bdLat.intValue();
            BigDecimal bdLatMin = bdLat.subtract(BigDecimal.valueOf(latDeg)).multiply(BD_SIXTY);
            latMin = bdLatMin.intValue();
            BigDecimal bdLatSec = bdLatMin.subtract(BigDecimal.valueOf(latMin)).multiply(BD_SIXTY);
            // Rounding here ...
            bdLatSec = bdLatSec.setScale(3, RoundingMode.HALF_UP);
            latSecRaw = bdLatSec.doubleValue();
            latSec = bdLatSec.intValue();
            BigDecimal bdLatSecFrac = bdLatSec.subtract(BigDecimal.valueOf(latSec)).multiply(BD_THOUSAND);
            latSecFrac = bdLatSecFrac.setScale(0, RoundingMode.HALF_UP).intValue();

            BigDecimal bdlon = BigDecimal.valueOf(lonSigned).abs();
            lonDeg = bdlon.intValue();
            BigDecimal bdLonMin = bdlon.subtract(BigDecimal.valueOf(lonDeg)).multiply(BD_SIXTY);
            lonMin = bdLonMin.intValue();
            BigDecimal bdLonSec = bdLonMin.subtract(BigDecimal.valueOf(lonMin)).multiply(BD_SIXTY);
            // Rounding here ...
            bdLonSec = bdLonSec.setScale(3, RoundingMode.HALF_UP);
            lonSecRaw = bdLonSec.doubleValue();
            lonSec = bdLonSec.intValue();
            BigDecimal bdLonSecFrac = bdLonSec.subtract(BigDecimal.valueOf(lonSec)).multiply(BD_THOUSAND);
            lonSecFrac = bdLonSecFrac.setScale(0, RoundingMode.HALF_UP).intValue();
        }

        public static Coordinates createCoordinates(final String latDir, final String latDeg, final String latMin, final String latSec, final String latSecFrac,
                                                    final String lonDir, final String lonDeg, final String lonMin, final String lonSec, final String lonSecFrac) {
            double lat = 0.0d;
            double lon = 0.0d;
            try {
                lat = Double.parseDouble(latDeg) + Double.parseDouble(latMin) / D60 + Double.parseDouble(latSec + "." + addZeros(Integer.parseInt(latSecFrac), 3))
                        / D3600;
                lon = Double.parseDouble(lonDeg) + Double.parseDouble(lonMin) / D60 + Double.parseDouble(lonSec + "." + addZeros(Integer.parseInt(lonSecFrac), 3))
                        / D3600;
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
            lat *= "S".equalsIgnoreCase(latDir) ? -1 : 1;
            lon *= "W".equalsIgnoreCase(lonDir) ? -1 : 1;
            return Coordinates.create(lat, lon);
        }
    }
}
