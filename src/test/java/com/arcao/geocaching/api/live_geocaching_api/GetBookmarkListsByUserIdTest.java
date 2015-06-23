package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GetBookmarkListsByUserIdTest extends AbstractGeocachingTest {
  public static final int USER_ID = 1672113; // user: Arcao

  @Test
  public void simpleGetBookmarkListsByUserIdTest() throws GeocachingApiException {
    List<BookmarkList> bookmarkList = api.getBookmarkListsByUserId(USER_ID);

    assertNotNull(bookmarkList);
    assertFalse(bookmarkList.isEmpty());

    BookmarkList item = bookmarkList.get(0);
    System.out.println(item.toString());

    assertThat(item.getId(), not(0));
    assertThat(item.getGuid(), not(isEmptyOrNullString()));
    assertThat(item.getName(), not(isEmptyOrNullString()));
    assertThat(item.getDescription(), notNullValue());
    assertThat(item.getItemCount(), greaterThan(0));
    assertTrue(item.isShared());
    assertTrue(item.isPublic());
    assertFalse(item.isArchived());
    assertFalse(item.isSpecial());
    assertThat(item.getType(), is(0));
  }
}
