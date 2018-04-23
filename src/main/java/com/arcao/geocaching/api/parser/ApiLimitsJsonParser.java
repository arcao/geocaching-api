package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.apilimits.CacheLimit;
import com.arcao.geocaching.api.data.apilimits.MethodLimit;
import com.google.gson.stream.JsonReader;

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
            switch (name) {
                case "CacheLimits":
                    builder.cacheLimits(parseCacheLimits(r));
                    break;
                case "EnforceCacheLimits":
                    builder.enforceCacheLimits(r.nextBoolean());
                    break;
                case "EnforceLiteCacheLimits":
                    builder.enforceLiteCacheLimits(r.nextBoolean());
                    break;
                case "EnforceMethodLimits":
                    builder.enforceMethodLimits(r.nextBoolean());
                    break;
                case "ForMembershipType":
                    builder.forMembershipType(parseMemberType(r));
                    break;
                case "LicenseKey":
                    builder.licenseKey(r.nextString());
                    break;
                case "LiteCacheLimits":
                    builder.liteCacheLimits(parseCacheLimits(r));
                    break;
                case "MaxCallsbyIPIn1Minute":
                    builder.maxCallsbyIPIn1Minute(r.nextLong());
                    break;
                case "MethodLimits":
                    builder.methodLimits(parseMethodLimits(r));
                    break;
                case "RestrictbyIP":
                    builder.restrictByIp(r.nextBoolean());
                    break;
                case "ValidateIPCounts":
                    builder.validateIpCounts(r.nextBoolean());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<CacheLimit> parseCacheLimits(JsonReader r) throws IOException {
        List<CacheLimit> list = new ArrayList<>();

        if (isNextNull(r)) {
            return list;
        }

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
            switch (name) {
                case "CacheLimit":
                    builder.limit(r.nextLong());
                    break;
                case "InMinutes":
                    builder.period(r.nextLong());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<MethodLimit> parseMethodLimits(JsonReader r) throws IOException {
        List<MethodLimit> list = new ArrayList<>();

        if (isNextNull(r)) {
            return list;
        }

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
            switch (name) {
                case "InMinutes":
                    builder.period(r.nextInt());
                    break;
                case "MaxCalls":
                    builder.limit(r.nextLong());
                    break;
                case "MethodName":
                    builder.methodName(r.nextString());
                    break;
                case "PartnerMethod":
                    builder.partnerMethod(r.nextBoolean());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

}
