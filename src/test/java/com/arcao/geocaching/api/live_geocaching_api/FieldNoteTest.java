package com.arcao.geocaching.api.live_geocaching_api;

import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.exception.GeocachingApiException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FieldNoteTest extends AbstractGeocachingTest {
    private static final String CACHE_CODE = "GCY81P";

    @Ignore("Ignored: No way to delete created GeocacheLog")
    @Test
    public void simpleCreateFieldNoteAndPublishTest() throws GeocachingApiException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.JANUARY, 1, 2, 3, 4);
        c.set(Calendar.MILLISECOND, 0);

        Date date = c.getTime();

        System.out.println(date);

        GeocacheLog log = api.createFieldNoteAndPublish(CACHE_CODE, GeocacheLogType.WriteNote, date, date.toString(), true, null, false);

        Assert.assertNotNull(log);
        System.out.println(log);
    }

}
