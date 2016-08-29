package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GetBookmarkListsByUserIdTest extends AbstractGeocachingTest {
    private static final int USER_ID = 1672113; // user: Arcao

    @Test
    public void simpleGetBookmarkListsByUserIdTest() throws GeocachingApiException {
        List<BookmarkList> bookmarkList = api.getBookmarkListsByUserId(USER_ID);

        assertNotNull(bookmarkList);
        assertFalse(bookmarkList.isEmpty());

        BookmarkList item = bookmarkList.get(0);
        System.out.println(item);

        assertThat(item.id(), not(0));
        assertThat(item.guid(), not(isEmptyOrNullString()));
        assertThat(item.name(), not(isEmptyOrNullString()));
        assertThat(item.description(), notNullValue());
        assertThat(item.itemCount(), greaterThan(0));
        assertFalse(item.shared());
        assertFalse(item.publicList());
        assertFalse(item.archived());
        assertFalse(item.special());
        assertThat(item.type(), is(0));
    }
}
