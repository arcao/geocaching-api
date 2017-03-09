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
    public void getCacheIdsFavoritedByUserTest() throws GeocachingApiException {
        List<String> list = api.getCacheIdsFavoritedByUser();

        assertNotNull(list);
        assertNotSame(0, list.size());

        assertNotNull(list.get(0));
    }

    @Test
    public void getCachesFavoritedByUserTest() throws GeocachingApiException {
        List<FavoritedGeocache> list = api.getCachesFavoritedByUser();

        assertNotNull(list);
        assertNotSame(0, list.size());

        assertNotNull(list.get(0));
        assertNotNull(list.get(0).cacheCode());
        assertNotNull(list.get(0).cacheTitle());
        assertNotNull(list.get(0).geocacheType());
    }

    @Test
    public void getUsersWhoFavoritedCacheTest() throws GeocachingApiException {
        List<User> list = api.getUsersWhoFavoritedCache("GCY81P");

        assertNotNull(list);
        assertNotSame(0, list.size());

        assertNotNull(list.get(0));
    }
}
