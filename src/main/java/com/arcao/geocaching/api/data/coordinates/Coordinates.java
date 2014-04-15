package com.arcao.geocaching.api.data.coordinates;

import java.text.ParseException;

/**
 * A class representing a geographic WGS-84 coordinates. Coordinates consist of
 * latitude and longitude.
 * 
 * @author arcao
 * @since 1.5
 */
public class Coordinates {
	protected static final double AVERAGE_RADIUS_OF_EARTH = 6372797.560856;
	protected static final CoordinatesFormatter LAT_LON_DECMINUTE_FORMAT = new CoordinatesFormatter(CoordinatesFormatter.LAT_LON_DECMINUTE);

	private final double latitude;
	private final double longitude;

	/**
	 * Create a new Coordinates object with specified WGS-84 latitude and
	 * longitude.
	 * 
	 * @param latitude
	 *          latitude
	 * @param longitude
	 *          longitude
	 */
	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Create a new Coordinates object from String using CoordinatesParser.
	 * 
	 * @param coordinates
	 *          coordinates written in a text
	 * @throws ParseException
	 *           If error occurs during parsing coordinates
	 * @see CoordinatesParser#parse(String)
	 */
	public Coordinates(String coordinates) throws ParseException {
		Coordinates c = CoordinatesParser.parse(coordinates);
		latitude = c.getLatitude();
		longitude = c.getLongitude();
	}

	/**
	 * Create a new Coordinates object from String using CoordinatesParser.
	 * 
	 * @param latitude
	 *          latitude
	 * @param longitude
	 *          longitude
	 * @throws ParseException
	 *           If error occurs during parsing coordinates
	 * @see CoordinatesParser#parse(String, String)
	 */
	public Coordinates(String latitude, String longitude) throws ParseException {
		Coordinates c = CoordinatesParser.parse(latitude, longitude);
		this.latitude = c.getLatitude();
		this.longitude = c.getLongitude();
	}

	/**
	 * Create a new Coordinates object from text representation of latitude and
	 * longitude using CoordinatesParser. Safe flag can change behavior of parser
	 * to support coordinates without cardinal point.
	 * 
	 * @param latitude
	 *          latitude
	 * @param longitude
	 *          longitude
	 * @param safe
	 *          use safe parsing (mean coordinates must start with cardinal point)
	 *          or not (allow to use negative char behind degree)
	 * @throws ParseException
	 *           If error occurs during parsing coordinates
	 * @see CoordinatesParser#parse(String, String, boolean)
	 */
	public Coordinates(String latitude, String longitude, boolean safe) throws ParseException {
		Coordinates c = CoordinatesParser.parse(latitude, longitude, safe);
		this.latitude = c.getLatitude();
		this.longitude = c.getLongitude();
	}

	/**
	 * Get a latitude
	 * 
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Get a longitude
	 * 
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Parse coordinates from text representation of latitude and longitude. If
	 * error occurs during parsing coordinates (e.g. wrong format) return null.
	 * 
	 * @param latitude
	 *          latitude
	 * @param longitude
	 *          longitude
	 * @return Coordinates object with parsed latitude and longitude
	 * @see Coordinates#Coordinates(String, String)
	 * @see CoordinatesParser#parse(String, String)
	 */
	public Coordinates parseCoordinates(String latitude, String longitude) {
		try {
			return new Coordinates(latitude, longitude);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Parse coordinates from text representation of latitude and longitude. If
	 * error occurs during parsing coordinates (e.g. wrong format) return null.
	 * Safe flag can change behavior of parser to support coordinates without
	 * cardinal point.
	 * 
	 * @param latitude
	 *          latitude
	 * @param longitude
	 *          longitude
	 * @param safe
	 *          use safe parsing (mean coordinates must start with cardinal point)
	 *          or not (allow to use negative char behind degree)
	 * @return Coordinates object with parsed latitude and longitude
	 * @see Coordinates#Coordinates(String, String, boolean)
	 * @see CoordinatesParser#parse(String, String, boolean)
	 */
	public Coordinates parseCoordinates(String latitude, String longitude, boolean safe) {
		try {
			return new Coordinates(latitude, longitude, safe);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Parse coordinates from text representation. If error occurs during parsing
	 * coordinates (e.g. wrong format) return null.
	 * 
	 * @param coordinates
	 *          coordinates in a text representation
	 * @return Coordinates object with parsed latitude and longitude
	 * @see Coordinates#Coordinates(String)
	 * @see CoordinatesParser#parse(String)
	 */
	public Coordinates parseCoordinates(String coordinates) {
		try {
			return new Coordinates(coordinates);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Return String representation of coordinates using specified
	 * CoordinatesFormatter instance.
	 * 
	 * @param format
	 *          instance of CoordinatesFormatter object used to formating
	 *          coordinates
	 * @return formatted string
	 */
	public String toString(CoordinatesFormatter format) {
		return format.format(this);
	}

	/**
	 * Return String representation of coordinates using specified
	 * CoordinatesFormatter format constant.
	 * 
	 * @param format
	 *          format constant from CoordinatesFormatter
	 * @return formatted string
	 */
	public String toString(int format) {
		return new CoordinatesFormatter(format).format(this);
	}

	@Override
	public String toString() {
		return toString(LAT_LON_DECMINUTE_FORMAT);
	}

	/**
	 * Get a distance in meters between this coordinates and specified coordinates  
	 * @param to
	 * @return distance in meters
	 * @since 1.20
	 */
	public double distanceTo(Coordinates to) {
		double[] results = new double[1];

		computeDistanceAndBearing(this, to, results);

		return results[0];
	}

	/**
	 * Computes the approximate distance in meters between two coordinates, and
	 * optionally the initial and final bearings bearings of the shortest path between
	 * them.
	 * 
	 * <p>
	 * The computed distance is stored in <code>results[0]</code>. If results has length 2,
	 * the initial bearing is stored in <code>results[1]</code>. If results has
	 * length 3, the final bearing is stored in <code>results[2]</code>.
	 * 
	 * <p>
	 * Compute is based on <a href="http://en.wikipedia.org/wiki/Haversine_formula">Haversine formula</a>.
	 * Precision is around 99.9%.
	 * 
	 * @param source
	 *          source coordinates
	 * @param destination
	 *          destination coordinates
	 * @param results
	 *          array where first index is distance in meters, second index is initial bearing in degree
	 *          and third index is final bearing in degree
	 * @since 1.20
	 */
	public static void computeDistanceAndBearing(Coordinates source, Coordinates destination, double[] results) {
		if (results == null || results.length < 1 || results.length > 3)
			throw new IllegalArgumentException("Results has to be initialized array of size 1, 2 or 3.");

		double lat1 = Math.toRadians(source.latitude);
		double lon1 = Math.toRadians(source.longitude);
		double lat2 = Math.toRadians(destination.latitude);
		double lon2 = Math.toRadians(destination.longitude);

		// prepare variables
		double cosLat1 = Math.cos(lat1);
		double cosLat2 = Math.cos(lat2);
		double sinDLat2 = Math.sin((lat2 - lat1) / 2.0);
		double sinDLon2 = Math.sin((lon2 - lon1) / 2.0);

		// compute values
		double a = sinDLat2 * sinDLat2 + cosLat1 * cosLat2 * sinDLon2 * sinDLon2;
		double d = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

		// convert to metres
		results[0] = d * AVERAGE_RADIUS_OF_EARTH;

		// compute initial bearing
		if (results.length > 1) {
			double sinLambda = Math.sin(lon2 - lon1);
			double cosLambda = Math.cos(lon2 - lon1);

			double y = sinLambda * cosLat2;
			double x = cosLat1 * Math.sin(lat2) - Math.sin(lat1) * cosLat2 * cosLambda;
			results[1] = Math.toDegrees(Math.atan2(y, x));

			// compute final bearing
			if (results.length > 2) {
				y = sinLambda * cosLat1;
				x = - Math.sin(lat1) * cosLat2 + cosLat1 * Math.sin(lat2) * cosLambda;
				results[2] = Math.toDegrees(Math.atan2(y, x));
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordinates) {
			return Math.abs(((Coordinates) obj).latitude - latitude) < 1e-8 && Math.abs(((Coordinates) obj).longitude - longitude) < 1e-8;
		}

		return false;
	}
}
