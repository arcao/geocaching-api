/*
  Some parts of this file contains work from c:geo licensed under
  Apache License 2.0.
 */
package com.arcao.geocaching.api.data.coordinates;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author arcao
 * @since 1.5
 */
public final class CoordinatesParser {
    private static final Logger logger = LoggerFactory.getLogger(CoordinatesParser.class);
    private static final double MINUTES_PER_DEGREE = 60.0;
    private static final double SECONDS_PER_DEGREE = 3600.0;

    //                                                                   ( 1 )     ( 2 )          ( 3 )        ( 4 )        ( 5 )
    private static final Pattern LATITUDE_PATTERN = Pattern.compile("\\b([NS])\\s*(\\d+)°?(?:\\s*(\\d+)(?:[.,](\\d+)|'?\\s*(\\d+(?:[.,]\\d+)?)(?:''|\")?)?)?", Pattern.CASE_INSENSITIVE);
    private static final Pattern LONGITUDE_PATTERN = Pattern.compile("\\b([WE])\\s*(\\d+)°?(?:\\s*(\\d+)(?:[.,](\\d+)|'?\\s*(\\d+(?:[.,]\\d+)?)(?:''|\")?)?)?", Pattern.CASE_INSENSITIVE);

    //                                                                                                                   ( 1 )      ( 2 )            ( 3 )        ( 4 )        ( 5 )
    private static final Pattern LATITUDE_PATTERN_UNSAFE = Pattern.compile("(?:(?=[\\-\\w])(?<![\\-\\w])|(?<![^\\-\\w]))([NS]|)\\s*(-?\\d+)°?(?:\\s*(\\d+)(?:[.,](\\d+)|'?\\s*(\\d+(?:[.,]\\d+)?)(?:''|\")?)?)?", Pattern.CASE_INSENSITIVE);
    private static final Pattern LONGITUDE_PATTERN_UNSAFE = Pattern.compile("(?:(?=[\\-\\w])(?<![\\-\\w])|(?<![^\\-\\w]))([WE]|)\\s*(-?\\d+)°?(?:\\s*(\\d+)(?:[.,](\\d+)|'?\\s*(\\d+(?:[.,]\\d+)?)(?:''|\")?)?)?", Pattern.CASE_INSENSITIVE);

    private CoordinatesParser() {
    }

    private enum CoordinateType {
        LAT,
        LON,
        LAT_UNSAFE,
        LON_UNSAFE
    }

    public static Coordinates parse(String latitude, String longitude) throws ParseException {
        return parse(latitude, longitude, true);
    }

    public static Coordinates parse(String latitude, String longitude, boolean safe) throws ParseException {
        return Coordinates.create(
                parseLatitude(latitude, safe),
                parseLongitude(longitude, safe)
        );
    }

    public static double parseLatitude(String latitude) throws ParseException {
        return parseLatitude(latitude, true);
    }

    public static double parseLatitude(String latitude, boolean safe) throws ParseException {
        return parse(latitude, safe ? CoordinateType.LAT : CoordinateType.LAT_UNSAFE).result;
    }

    public static double parseLongitude(String longitude) throws ParseException {
        return parseLongitude(longitude, true);
    }

    public static double parseLongitude(String longitude, boolean safe) throws ParseException {
        return parse(longitude, safe ? CoordinateType.LON : CoordinateType.LON_UNSAFE).result;
    }

    public static Coordinates parse(String coordinates) throws ParseException {
        final ParseResult latitudeWrapper = parse(coordinates, CoordinateType.LAT);
        final double lat = latitudeWrapper.result;
        // cut away the latitude part when parsing the longitude
        final ParseResult longitudeWrapper = parse(coordinates.substring(latitudeWrapper.matcherPos + latitudeWrapper.matcherLen), CoordinateType.LON);

        if (longitudeWrapper.matcherPos - (latitudeWrapper.matcherPos + latitudeWrapper.matcherLen) >= 10) {
            throw new ParseException("Distance between latitude and longitude text is to large.", latitudeWrapper.matcherPos + latitudeWrapper.matcherLen + longitudeWrapper.matcherPos);
        }

        final double lon = longitudeWrapper.result;
        return Coordinates.create(lat, lon);
    }

    protected static ParseResult parse(String coordinate, CoordinateType coordinateType) throws ParseException {
        Pattern pattern;

        switch (coordinateType) {
            case LAT_UNSAFE:
                pattern = LATITUDE_PATTERN_UNSAFE;
                break;
            case LON_UNSAFE:
                pattern = LONGITUDE_PATTERN_UNSAFE;
                break;
            case LON:
                pattern = LONGITUDE_PATTERN;
                break;
            case LAT:
            default:
                pattern = LATITUDE_PATTERN;
                break;
        }

        final Matcher matcher = pattern.matcher(coordinate);

        if (matcher.find()) {
            double sign = "S".equalsIgnoreCase(matcher.group(1)) || "W".equalsIgnoreCase(matcher.group(1)) ? -1.0 : 1.0;
            double degree = Double.parseDouble(matcher.group(2));

            if (degree < 0) {
                sign = -1;
                degree = Math.abs(degree);
            }

            double minutes = 0.0;
            double seconds = 0.0;

            if (matcher.group(3) != null) {
                minutes = Double.parseDouble(matcher.group(3));

                if (matcher.group(4) != null) {
                    seconds = Double.parseDouble("0." + matcher.group(4)) * MINUTES_PER_DEGREE;
                } else if (matcher.group(5) != null) {
                    seconds = Double.parseDouble(matcher.group(5).replace(",", "."));
                }
            }

            double result = sign * (degree + minutes / MINUTES_PER_DEGREE + seconds / SECONDS_PER_DEGREE);

            // normalize result
            switch (coordinateType) {
                case LON_UNSAFE:
                case LON:
                    result = normalize(result, -180, 180);
                    break;
                case LAT_UNSAFE:
                case LAT:
                default:
                    result = normalize(result, -90, 90);
                    break;
            }

            return new ParseResult(result, matcher.start(), matcher.group().length());
        } else {

            // Nothing found with "N 52...", try to match string as decimaldegree
            try {
                final String[] items = StringUtils.split(coordinate.trim());
                if (items.length > 0) {
                    final int index = (coordinateType == CoordinateType.LON ? items.length - 1 : 0);
                    final int pos = (coordinateType == CoordinateType.LON ? coordinate.lastIndexOf(items[index]) : coordinate.indexOf(items[index]));
                    return new ParseResult(Double.parseDouble(items[index]), pos, items[index].length());
                }
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        throw new ParseException("Could not parse coordinate: \"" + coordinate + "\"", 0);
    }

    /**
     * Normalizes any number to an arbitrary range by assuming the range wraps around when going below min or above max.
     *
     * @param value input
     * @param start range start
     * @param end   range end
     * @return normalized number
     */
    private static double normalize(double value, double start, double end) {
        final double width = end - start;
        final double offsetValue = value - start; // value relative to 0

        return (offsetValue - (Math.floor(offsetValue / width) * width)) + start; // + start to reset back to start of original range
    }

    private static class ParseResult {
        final double result;
        final int matcherPos;
        final int matcherLen;

        ParseResult(double result, int matcherPos, int matcherLen) {
            this.result = result;
            this.matcherPos = matcherPos;
            this.matcherLen = matcherLen;
        }
    }
}
