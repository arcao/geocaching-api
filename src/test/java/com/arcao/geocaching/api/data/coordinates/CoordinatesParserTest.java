package com.arcao.geocaching.api.data.coordinates;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("HardcodedFileSeparator")
public class CoordinatesParserTest {
  private static final double refLongitude = 8.0 + 38.564 / 60.0;
  private static final double refLatitude = 49.0 + 56.031 / 60.0;
  private static final Coordinates refCoordinates = Coordinates.create(refLatitude, refLongitude);
  private static final double PRECISION = 1e-8;

  @Test
  public void testParseLatitude() throws ParseException {
    assertEquals(refLatitude, CoordinatesParser.parseLatitude("N 49° 56.031"), PRECISION);
  }

  @Test
  public void testParseLongitude() throws ParseException {
    assertEquals(refLongitude, CoordinatesParser.parseLongitude("E 8° 38.564"), PRECISION);
  }

  @Test
  public void testFullCoordinates() {
    assertTrue(refCoordinates.equals(Coordinates.parseCoordinates("N 49° 56.031 | E 8° 38.564")));
  }

  @Test
  public void testCoordinateMissingPart() {
    // we are trying to parse a _point_, but have only a latitude. Don't accept
    // the numerical part as longitude!
    Coordinates point = null;
    try {
      point = CoordinatesParser.parse("N 49° 56.031");
    } catch (ParseException e) {
      // expected
    }
    assertEquals(null, point);
  }

  @Test
  public void testSouth() throws ParseException {
    assertEquals(-refLatitude, CoordinatesParser.parseLatitude("S 49° 56.031"), PRECISION);
  }

  @Test
  public void testWest() throws ParseException {
    assertEquals(-refLongitude, CoordinatesParser.parseLongitude("W 8° 38.564"), PRECISION);
  }

  @Test
  public void testLowerCase() throws ParseException {
    assertEquals(refLongitude, CoordinatesParser.parseLongitude("e 8° 38.564"), PRECISION);
  }

  @Test
  public void testVariousFormats() throws ParseException {
    final Coordinates point1 = CoordinatesParser.parse("N 49° 43' 57\" | E 2 12' 35");
    final Coordinates point2 = CoordinatesParser.parse("N 49 43.95 E2°12.5833333333");
    assertTrue(point1.equals(point2));
  }

  @Test
  public void testInSentence() throws ParseException {
    final Coordinates p1 = CoordinatesParser.parse("Station3: N51 21.523 / E07 02.680");
    final Coordinates p2 = CoordinatesParser.parse("N51 21.523", "E07 02.680");
    assertNotNull(p1);
    assertNotNull(p2);
    assertTrue(p1.equals(p2));
  }

  @Test
  public void testUnrelatedParts() {
    Coordinates point = null;
    try {
      point = CoordinatesParser.parse("N51 21.523 and some words in between, so there is no relation E07 02.680");
    } catch (ParseException e) {
      // expected
    }
    assertEquals(null, point);
  }

  @Test
  public void testComma() throws ParseException {
    final Coordinates pointComma = CoordinatesParser.parse("N 46° 27' 55,65''\n" +
        "E 15° 53' 41,68''");
    final Coordinates pointDot = CoordinatesParser.parse("N 46° 27' 55.65''\n" +
        "E 15° 53' 41.68''");
    assertNotNull(pointComma);
    assertNotNull(pointDot);
    assertTrue(pointComma.equals(pointDot));
  }
  
  @Test
  public void testWithoutCardinalPoint() throws ParseException {
    assertEquals(refLatitude, CoordinatesParser.parseLatitude("49° 56.031", false), PRECISION);
    assertEquals(refLongitude, CoordinatesParser.parseLongitude("8° 38.564", false), PRECISION);
    
    assertEquals(-refLatitude, CoordinatesParser.parseLatitude("-49° 56.031", false), PRECISION);
    assertEquals(-refLongitude, CoordinatesParser.parseLongitude("-8° 38.564", false), PRECISION);
  }

  @Test
  public void testParseTooBigCoordinatesNumber() throws ParseException {
    Coordinates point = CoordinatesParser.parse("N 123456789123456789 | N 123456789123456789");
    assertNotNull(point);
  }

  @Test
  public void testParseTooBigLatitudeNumber() throws ParseException {
    CoordinatesParser.parseLatitude("N 123456789123456789");
    assertTrue(true);
  }

  @Test
  public void testParseTooBigLongitudeNumber() throws ParseException {
    CoordinatesParser.parseLongitude("W 123456789123456789");
    assertTrue(true);
  }

  @Test
  public void testParseCoordinatesNumberAndNormalize() throws ParseException {
    assertTrue(refCoordinates.equals(CoordinatesParser.parse("N 229° 56.031 | E 368° 38.564")));
  }
}
