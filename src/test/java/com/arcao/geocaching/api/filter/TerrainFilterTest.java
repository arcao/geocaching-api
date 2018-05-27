package com.arcao.geocaching.api.filter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TerrainFilterTest {
    @Test
    public void testValidity1To5() {
        assertFalse(new TerrainFilter(1, 5).valid());
    }

    @Test
    public void testValidity1_1To5() {
        assertTrue(new TerrainFilter(1.5F, 5).valid());
    }

    @Test
    public void testValidity1To4_5() {
        assertTrue(new TerrainFilter(1, 4.5F).valid());
    }

    @Test
    public void testValidity1To1() {
        assertTrue(new TerrainFilter(1, 1).valid());
    }

    @Test
    public void testValidity5To5() {
        assertTrue(new TerrainFilter(5, 5).valid());
    }

    @Test
    public void testValidity2To4() {
        assertTrue(new TerrainFilter(2, 4).valid());
    }

    @Test
    public void testValidity3To3() {
        assertTrue(new TerrainFilter(3, 3).valid());
    }
}

