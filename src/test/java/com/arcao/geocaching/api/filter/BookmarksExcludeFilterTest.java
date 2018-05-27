package com.arcao.geocaching.api.filter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookmarksExcludeFilterTest {
    @Test
    public void testValidityExcludeNull() {
        assertFalse(new BookmarksExcludeFilter(null).valid());
    }

    @Test
    public void testValidityExcludeNullAndNotEmptyList() {
        assertTrue(new BookmarksExcludeFilter(null, 1).valid());
    }

    @Test
    public void testValidityExcludeNotNullAndNotEmptyList() {
        assertTrue(new BookmarksExcludeFilter(true, 1, 2, 3).valid());
    }
}
