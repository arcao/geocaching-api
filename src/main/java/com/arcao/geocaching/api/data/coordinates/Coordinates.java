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

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Coordinates) {
      return Math.abs(((Coordinates) obj).latitude - latitude) < 1e-8 && Math.abs(((Coordinates) obj).longitude - longitude) < 1e-8;
    }

    return false;
  }
}
