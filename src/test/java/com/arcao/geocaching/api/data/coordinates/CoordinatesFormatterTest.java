package com.arcao.geocaching.api.data.coordinates;

import org.junit.Test;

import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;


public class CoordinatesFormatterTest {
  private static final double refLatitude = 49.0 + 56.031 / 60.0;
  private static final double refLongitude = 8.0 + 38.564 / 60.0;
  private static final Coordinates refCoordinates = Coordinates.create(refLatitude, refLongitude);
  private static final char decSymbol = new DecimalFormatSymbols().getDecimalSeparator(); // some tests are Locale specific!!!
  
  @Test
  public void testLatLonDecDegree() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_LON_DECDEGREE);
    assertEquals("49" + decSymbol + "933850 8" + decSymbol + "642733", f.format(refCoordinates));
  }
  
  @Test
  public void testLatLonDecDegreeComma() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_LON_DECDEGREE_COMMA);
    assertEquals("49.933850,8.642733", f.format(refCoordinates));
  }
  
  @Test
  public void testLatLonDecMinute() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_LON_DECMINUTE);
    assertEquals("N 49° 56" + decSymbol + "031 · E 8° 38" + decSymbol + "564", f.format(refCoordinates));
  }
  
  @Test
  public void testLatLonDecMinuteRaw() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_LON_DECMINUTE_RAW);
    assertEquals("N 49° 56.031 E 8° 38.564", f.format(refCoordinates));
  }
  
  @Test
  public void testLatLonDecSecond() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_LON_DECSECOND);
    assertEquals("N 49° 56' 01" + decSymbol + "860\" · E 8° 38' 33" + decSymbol + "840\"", f.format(refCoordinates));
  }
  
  @Test
  public void testLatDecDegreeRaw() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_DECDEGREE_RAW);
    assertEquals("49.933850", f.format(refCoordinates));
  }
  
  @Test
  public void testLatDecMinute() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_DECMINUTE);
    assertEquals("N 49° 56" + decSymbol + "031", f.format(refCoordinates));
  }
  
  @Test
  public void testLatDecMinuteRaw() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LAT_DECMINUTE_RAW);
    assertEquals("N 49 56" + decSymbol + "031", f.format(refCoordinates));
  }
  
  @Test
  public void testLonDecDegreeRaw() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LON_DECDEGREE_RAW);
    assertEquals("8.642733", f.format(refCoordinates));
  }
  
  @Test
  public void testLonDecMinute() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LON_DECMINUTE);
    assertEquals("E 8° 38" + decSymbol + "564", f.format(refCoordinates));
  }
  
  @Test
  public void testLonDecMinuteRaw() throws ParseException {
    CoordinatesFormatter f = new CoordinatesFormatter(CoordinatesFormatter.LON_DECMINUTE_RAW);
    assertEquals("E 8 38" + decSymbol + "564", f.format(refCoordinates));
  }
}
