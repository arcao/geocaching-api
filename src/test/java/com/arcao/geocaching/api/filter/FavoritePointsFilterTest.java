package com.arcao.geocaching.api.filter;

import org.junit.Assert;
import org.junit.Test;

public class FavoritePointsFilterTest {
    @Test
    public void testValidityNullToBull() {
        Assert.assertFalse(new FavoritePointsFilter(null, null).valid());
    }

    @Test
    public void testValidityNullTo10() {
        Assert.assertTrue(new FavoritePointsFilter(null, 10).valid());
    }

    @Test
    public void testValidity10ToNull() {
        Assert.assertTrue(new FavoritePointsFilter(10, null).valid());
    }

    @Test
    public void testValidity1To10() {
        Assert.assertTrue(new FavoritePointsFilter(1, 10).valid());
    }

    @Test
    public void testValidity10To10() {
        Assert.assertTrue(new FavoritePointsFilter(10, 10).valid());
    }

    @Test
    public void testValidity10To1() {
        Assert.assertFalse(new FavoritePointsFilter(10, 1).valid());
    }
}
