package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.apilimits.CacheLimit;
import com.arcao.geocaching.api.data.apilimits.MethodLimit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.isNextNull;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseMemberType;

public final class ApiLimitsJsonParser {
    private ApiLimitsJsonParser() {
    }

    public static ApiLimits parse(JsonReader r) throws IOException {
        ApiLimits.Builder builder = ApiLimits.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("CacheLimits".equals(name)) {
                builder.cacheLimits(parseCacheLimits(r));
            } else if ("EnforceCacheLimits".equals(name)) {
                builder.enforceCacheLimits(r.nextBoolean());
            } else if ("EnforceLiteCacheLimits".equals(name)) {
                builder.enforceLiteCacheLimits(r.nextBoolean());
            } else if ("EnforceMethodLimits".equals(name)) {
                builder.enforceMethodLimits(r.nextBoolean());
            } else if ("ForMembershipType".equals(name)) {
                builder.forMembershipType(parseMemberType(r));
            } else if ("LicenseKey".equals(name)) {
                builder.licenseKey(r.nextString());
            } else if ("LiteCacheLimits".equals(name)) {
                builder.liteCacheLimits(parseCacheLimits(r));
            } else if ("MaxCallsbyIPIn1Minute".equals(name)) {
                builder.maxCallsbyIPIn1Minute(r.nextLong());
            } else if ("MethodLimits".equals(name)) {
                builder.methodLimits(parseMethodLimits(r));
            } else if ("RestrictbyIP".equals(name)) {
                builder.restrictByIp(r.nextBoolean());
            } else if ("ValidateIPCounts".equals(name)) {
                builder.validateIpCounts(r.nextBoolean());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<CacheLimit> parseCacheLimits(JsonReader r) throws IOException {
        List<CacheLimit> list = new ArrayList<CacheLimit>();

        if (isNextNull(r))
            return list;

        r.beginArray();
        while (r.hasNext()) {
            list.add(parseCacheLimit(r));
        }
        r.endArray();
        return list;
    }

    private static CacheLimit parseCacheLimit(JsonReader r) throws IOException {
        CacheLimit.Builder builder = CacheLimit.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("CacheLimit".equals(name)) {
                builder.limit(r.nextLong());
            } else if ("InMinutes".equals(name)) {
                builder.period(r.nextLong());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<MethodLimit> parseMethodLimits(JsonReader r) throws IOException {
        List<MethodLimit> list = new ArrayList<MethodLimit>();

        if (isNextNull(r))
            return list;

        r.beginArray();
        while (r.hasNext()) {
            list.add(parseMethodLimit(r));
        }
        r.endArray();
        return list;

    }

    private static MethodLimit parseMethodLimit(JsonReader r) throws IOException {
        MethodLimit.Builder builder = MethodLimit.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("InMinutes".equals(name)) {
                builder.period(r.nextInt());
            } else if ("MaxCalls".equals(name)) {
                builder.limit(r.nextLong());
            } else if ("MethodName".equals(name)) {
                builder.methodName(r.nextString());
            } else if ("PartnerMethod".equals(name)) {
                builder.partnerMethod(r.nextBoolean());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

}
