package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GetBookmarkListsForUserTest extends AbstractGeocachingTest {

  @Test
  public void simpleGetBookmarkListsForUserTest() throws GeocachingApiException {
    List<BookmarkList> bookmarkList = api.getBookmarkListsForUser();

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
