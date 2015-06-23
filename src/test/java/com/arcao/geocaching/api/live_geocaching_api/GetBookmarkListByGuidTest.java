package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.bookmarks.Bookmark;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GetBookmarkListByGuidTest extends AbstractGeocachingTest {
  private static final String GUID = "55128775-a712-446d-8db0-147b964969aa";

  @Test
  public void simpleGetBookmarkListsByUserIdTest() throws GeocachingApiException {
    List<Bookmark> items = api.getBookmarkListByGuid(GUID);

    assertThat(items.size(), not(0));

    Bookmark item = items.get(0);

    System.out.println(item.toString());

    assertThat(item.getCacheCode(), not(isEmptyOrNullString()));
    assertThat(item.getCacheTitle(), not(isEmptyOrNullString()));
    assertThat(item.getGeocacheType(), notNullValue());
  }
}
