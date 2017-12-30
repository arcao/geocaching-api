package com.arcao.geocaching.api.live_geocaching_api.mocked;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.LiveGeocachingApi;
import com.arcao.geocaching.api.downloader.JsonDownloader;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.parser.JsonReader;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.mockito.Mockito.mock;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractMockedGeocachingTest {
    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    GeocachingApi api = null;
    JsonDownloader downloader = null;

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    @Before
    public void setUpMock() throws GeocachingApiException {
        downloader = mock(JsonDownloader.class);

        api = LiveGeocachingApi.builder()
                .downloader(downloader)
                .build();

        api.openSession(ACCESS_TOKEN);
    }

    JsonReader createJsonReaderFromResource(String resourceFile) {
        return new JsonReader(new InputStreamReader(getClass().getResourceAsStream(resourceFile), CHARSET_UTF8));
    }

    byte[] createByteArrayFromResource(String resourceFile) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];

        try (InputStream inputStream = getClass().getResourceAsStream(resourceFile)) {
            while (true) {
                int len = inputStream.read(buffer);
                if (len < 0)
                    break;

                outputStream.write(buffer, 0, len);
            }
        }

        return outputStream.toByteArray();

    }
}
