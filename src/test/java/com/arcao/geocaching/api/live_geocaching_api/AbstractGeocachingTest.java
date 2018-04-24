package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.LiveGeocachingApi;
import com.arcao.geocaching.api.configuration.GeocachingApiConfiguration;
import com.arcao.geocaching.api.downloader.DefaultDownloader;
import com.arcao.geocaching.api.downloader.Downloader;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;

public abstract class AbstractGeocachingTest {
    protected static GeocachingApi api = null;

    // generated via oAuth example, see: https://github.com/arcao/geocaching-api-examples
    private static final String STAGING_AUTH_TOKEN = "JvJcmhBJ88iDoWR107RIKpQgLLU=";

    @SuppressWarnings("CallToSystemGetenv")
    @BeforeClass
    public static void setUp() throws GeocachingApiException {
        LiveGeocachingApi.Builder builder = LiveGeocachingApi.builder();

        GeocachingApiConfiguration configuration = GeocachingApiConfiguration.STAGING;

        if (!Boolean.parseBoolean(StringUtils.defaultIfEmpty(System.getenv("GEOCACHING_STAGING"), "true"))) {
            configuration = GeocachingApiConfiguration.PRODUCTION;
        }

        Downloader downloader = new DefaultDownloader(configuration).debug(true);

        builder.configuration(configuration);
        builder.downloader(downloader);

        api = builder.build();

        api.openSession(StringUtils.defaultIfEmpty(System.getenv("GEOCACHING_TOKEN"), STAGING_AUTH_TOKEN));
    }
}
