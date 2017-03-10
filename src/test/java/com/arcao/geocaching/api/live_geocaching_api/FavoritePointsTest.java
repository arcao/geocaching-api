package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.FavoritedGeocache;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class FavoritePointsTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GC189E5";


    @Test
    public void getGeocacheCodesFavoritedByUserTest() throws GeocachingApiException {
        List<String> list = api.getGeocacheCodesFavoritedByUser();

        assertNotNull(list);
        assertNotSame(0, list.size());

        assertNotNull(list.get(0));
    }

    @Test
    public void getGeocachesFavoritedByUserTest() throws GeocachingApiException {
        List<FavoritedGeocache> list = api.getGeocachesFavoritedByUser();

        assertNotNull(list);
        assertNotSame(0, list.size());

        assertNotNull(list.get(0));
        assertNotNull(list.get(0).cacheCode());
        assertNotNull(list.get(0).cacheTitle());
        assertNotNull(list.get(0).geocacheType());
    }

    @Test
    public void getUsersWhoFavoritedGeocacheTest() throws GeocachingApiException {
        List<User> list = api.getUsersWhoFavoritedGeocache("GCY81P");

        assertNotNull(list);
        assertNotSame(0, list.size());

        assertNotNull(list.get(0));
    }
}
