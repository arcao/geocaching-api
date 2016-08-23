package com.arcao.geocaching.api.data.coordinates;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CoordinatesTest {
  private static final double refLongitude = 8.0 + 38.564 / 60.0;
  private static final double refLatitude = 49.0 + 56.031 / 60.0;
  private static final Coordinates refCoordinates = Coordinates.create(refLatitude, refLongitude);

  @Test
  public void testCoordinates() throws ParseException {
    assertTrue(refCoordinates.equals(Coordinates.parseCoordinates("N 49° 56.031 | E 8° 38.564")));
  }

  @Test
  public void testCoordinateMissingPart() {
    // we are trying to parse a _point_, but have only a latitude. Don't accept
    // the numerical part as longitude!
    Coordinates point = Coordinates.parseCoordinates("N 49° 56.031");
    assertNull(point);
  }

  @Test
  public void testVariousFormats() throws ParseException {
    final Coordinates point1 = Coordinates.parseCoordinates("N 49° 43' 57\" | E 2 12' 35");
    final Coordinates point2 = Coordinates.parseCoordinates("N 49 43.95 E2°12.5833333333");

    assertNotNull(point1);
    assertTrue(point1.equals(point2));
  }

  @Test
  public void testLatitudeLongitude() throws ParseException {
    final Coordinates point2 = Coordinates.parseCoordinates("N 49° 56.031", "E 8° 38.564");

    assertTrue(refCoordinates.equals(point2));
  }
}
