package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GetImagesForGeocacheTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GC189E5";

    @Test
    public void getImagesForGeocacheTest() throws GeocachingApiException {
        List<ImageData> list = api.getImagesForGeocache(CACHE_CODE);

        assertNotNull(list);
        assertNotSame(0, list.size());

        ImageData image = list.get(0);
        assertNotNull(image);

        assertNotNull(image.created());
        assertNotNull(image.name());
        assertNotNull(image.description());
        assertNotNull(image.thumbUrl());
        assertNotNull(image.mobileUrl());
        assertNotNull(image.url());

        // have not to be parsed
        assertNull(image.fileName());
        assertNull(image.imageData());
    }

}
