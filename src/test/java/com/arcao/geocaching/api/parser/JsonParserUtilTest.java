package com.arcao.geocaching.api.parser;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class JsonParserUtilTest {
    @Test
    public void parseIsoDate() {
        Date expected = new Date(1406394000000L);

        Date current = JsonParserUtil.parseIsoDate("2014-07-26T12:00:00.0000000-05:00");

        assertEquals("Date is not same.", expected, current);
    }
}