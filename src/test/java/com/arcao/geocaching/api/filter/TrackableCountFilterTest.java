package com.arcao.geocaching.api.filter;

import org.junit.Assert;
import org.junit.Test;

public class TrackableCountFilterTest {
    @Test
    public void testValidityNullToBull() {
        Assert.assertFalse(new TrackableCountFilter(null, null).valid());
    }

    @Test
    public void testValidityNullTo10() {
        Assert.assertTrue(new TrackableCountFilter(null, 10).valid());
    }

    @Test
    public void testValidity10ToNull() {
        Assert.assertTrue(new TrackableCountFilter(10, null).valid());
    }

    @Test
    public void testValidity1To10() {
        Assert.assertTrue(new TrackableCountFilter(1, 10).valid());
    }

    @Test
    public void testValidity10To10() {
        Assert.assertTrue(new TrackableCountFilter(10, 10).valid());
    }

    @Test
    public void testValidity10To1() {
        Assert.assertFalse(new TrackableCountFilter(10, 1).valid());
    }

}
