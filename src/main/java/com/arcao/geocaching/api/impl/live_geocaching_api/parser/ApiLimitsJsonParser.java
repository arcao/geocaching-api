package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arcao.geocaching.api.data.apilimits.ApiLimits;
import com.arcao.geocaching.api.data.apilimits.CacheLimit;
import com.arcao.geocaching.api.data.apilimits.MethodLimit;
import com.arcao.geocaching.api.data.type.MemberType;

public class ApiLimitsJsonParser extends JsonParser {
  public static ApiLimits parse(JsonReader r) throws IOException {
    List<CacheLimit> cacheLimits = new ArrayList<CacheLimit>();
    boolean enforceCacheLimits = false;
    boolean enforceLiteCacheLimits = false;
    boolean enforceMethodLimits = false;
    MemberType forMembershipType = MemberType.Guest;
    String licenseKey = "";
    List<CacheLimit> liteCacheLimits = new ArrayList<CacheLimit>();
    long maxCallsbyIPIn1Minute = Long.MAX_VALUE;
    List<MethodLimit> methodLimits = new ArrayList<MethodLimit>();
    boolean restrictbyIP = false;
    boolean validateIPCounts = false;
    
    r.beginObject();
    while (r.hasNext()) {
      String name = r.nextName();
      if ("CacheLimits".equals(name)) {
        cacheLimits = parseCacheLimits(r);
      } else if ("EnforceCacheLimits".equals(name)) {
        enforceCacheLimits = r.nextBoolean();
      } else if ("EnforceLiteCacheLimits".equals(name)) {
        enforceLiteCacheLimits = r.nextBoolean();
      } else if ("EnforceMethodLimits".equals(name)) {
        enforceMethodLimits = r.nextBoolean();
      } else if ("ForMembershipType".equals(name)) {
        forMembershipType = parseMemberType(r);
      } else if ("LicenseKey".equals(name)) {
        licenseKey = r.nextString();
      } else if ("LiteCacheLimits".equals(name)) {
        liteCacheLimits = parseCacheLimits(r);
      } else if ("MaxCallsbyIPIn1Minute".equals(name)) {
        maxCallsbyIPIn1Minute = r.nextLong();
      } else if ("MethodLimits".equals(name)) {
        methodLimits = parseMethodLimits(r);
      } else if ("RestrictbyIP".equals(name)) {
        restrictbyIP = r.nextBoolean();
      } else if ("ValidateIPCounts".equals(name)) {
        validateIPCounts = r.nextBoolean();
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new ApiLimits(cacheLimits, enforceCacheLimits, enforceLiteCacheLimits, enforceMethodLimits, forMembershipType, licenseKey, liteCacheLimits, maxCallsbyIPIn1Minute, methodLimits, restrictbyIP, validateIPCounts);
  }

  protected static List<CacheLimit> parseCacheLimits(JsonReader r) throws IOException {
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

  protected static CacheLimit parseCacheLimit(JsonReader r) throws IOException {
    long limit = 0;
    long period = 0;

    r.beginObject();
    while (r.hasNext()) {
      String name = r.nextName();
      if ("CacheLimit".equals(name)) {
        limit = r.nextLong();
      } else if ("InMinutes".equals(name)) {
        period = r.nextLong();
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new CacheLimit(limit, period);
  }

  protected static List<MethodLimit> parseMethodLimits(JsonReader r) throws IOException {
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
    int period = 0;
    long limit = 0;
    String methodName = "";
    boolean partnerMethod = false;
    
    r.beginObject();
    while (r.hasNext()) {
      String name = r.nextName();
      if ("InMinutes".equals(name)) {
        period = r.nextInt();
      } else if ("MaxCalls".equals(name)) {
        limit = r.nextLong();
      } else if ("MethodName".equals(name)) {
        methodName = r.nextString();
      } else if ("PartnerMethod".equals(name)) {
        partnerMethod = r.nextBoolean();
      } else {
        r.skipValue();
      }
    }
    r.endObject();
    
    return new MethodLimit(period, limit, methodName, partnerMethod);
  }

}
