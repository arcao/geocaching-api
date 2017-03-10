package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.StatusCode;
import com.arcao.geocaching.api.data.FavoritePointResult;
import com.arcao.geocaching.api.data.FavoritedGeocache;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.exception.LiveGeocachingApiException;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class FavoritePointsModificationTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GC189E5";

    @Before
    @After
    public void cleanUp() throws GeocachingApiException {
        try {
            api.removeFavoritePointFromGeocache(CACHE_CODE);
        } catch (LiveGeocachingApiException e) {
            if (e.getStatusCode() != StatusCode.CacheNotFavoritedYet)
                throw e;
        }
    }


    @Test
    public void addAndRemoveFavoritePointTest() throws GeocachingApiException {
        // test if user has some favorite points
        int favoritePoints = api.getUsersFavoritePoints();
        assertNotEquals(0, favoritePoints);

        // test that cache is not favorited by user
        List<String> cacheCodes = api.getGeocacheCodesFavoritedByUser();
        assertThat(cacheCodes, not(hasItem(CACHE_CODE)));

        // test that cache is not favorited by user
        List<FavoritedGeocache> geocaches = api.getGeocachesFavoritedByUser();
        assertThat(geocaches, not(hasItem(
                cacheCode(is(CACHE_CODE))
        )));

        // add favorite point to geocache
        FavoritePointResult result = api.addFavoritePointToGeocache(CACHE_CODE);
        assertEquals(favoritePoints - 1, result.usersFavoritePoints());

        // test that user has remaining favorite points equals to result from previous call
        favoritePoints = api.getUsersFavoritePoints();
        assertEquals(favoritePoints, result.usersFavoritePoints());

        // test that cache is favorited by user
        cacheCodes = api.getGeocacheCodesFavoritedByUser();
        assertThat(cacheCodes, hasItem(CACHE_CODE));

        // test that cache is favorited by user
        geocaches = api.getGeocachesFavoritedByUser();
        assertThat(geocaches, hasItem(
                cacheCode(is(CACHE_CODE))
        ));

        // remove favorite point from geocache
        result = api.removeFavoritePointFromGeocache(CACHE_CODE);
        assertEquals(favoritePoints + 1, result.usersFavoritePoints());

        // test that user has remaining favorite points equals to result from previous call
        favoritePoints = api.getUsersFavoritePoints();
        assertEquals(favoritePoints, result.usersFavoritePoints());

        // test that cache is not favorited by user
        cacheCodes = api.getGeocacheCodesFavoritedByUser();
        assertThat(cacheCodes, not(hasItem(CACHE_CODE)));

        // test that cache is not favorited by user
        geocaches = api.getGeocachesFavoritedByUser();
        assertThat(geocaches, not(hasItem(
                cacheCode(is(CACHE_CODE))
        )));

    }

    private static Matcher cacheCode(Matcher<String> matcher) {
        return new FeatureMatcher<FavoritedGeocache, String>(matcher, "cacheCode", "cacheCode") {
            @Override
            protected String featureValueOf(FavoritedGeocache actual) {
                return actual.cacheCode();
            }
        };
    }
}
