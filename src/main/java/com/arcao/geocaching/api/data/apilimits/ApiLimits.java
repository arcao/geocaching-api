package com.arcao.geocaching.api.data.apilimits;

import java.util.List;

import com.arcao.geocaching.api.data.type.MemberType;

/**
 * Container class for API limits related to currently logged user
 * @author arcao
 *
 */
public class ApiLimits {
  private final List<CacheLimit> cacheLimits;
  private final boolean enforceCacheLimits;
  private final boolean enforceLiteCacheLimits;
  private final boolean enforceMethodLimits;
  private final MemberType forMembershipType;
  private final String licenseKey;
  private final List<CacheLimit> liteCacheLimits;
  private final long maxCallsbyIPIn1Minute;
  private final List<MethodLimit> methodLimits;
  private final boolean restrictbyIP;
  private final boolean validateIPCounts;
  
  public ApiLimits(List<CacheLimit> cacheLimits, boolean enforceCacheLimits,
      boolean enforceLiteCacheLimits, boolean enforceMethodLimits,
      MemberType forMembershipType, String licenseKey,
      List<CacheLimit> liteCacheLimits, long maxCallsbyIPIn1Minute,
      List<MethodLimit> methodLimits, boolean restrictbyIP,
      boolean validateIPCounts) {
    this.cacheLimits = cacheLimits;
    this.enforceCacheLimits = enforceCacheLimits;
    this.enforceLiteCacheLimits = enforceLiteCacheLimits;
    this.enforceMethodLimits = enforceMethodLimits;
    this.forMembershipType = forMembershipType;
    this.licenseKey = licenseKey;
    this.liteCacheLimits = liteCacheLimits;
    this.maxCallsbyIPIn1Minute = maxCallsbyIPIn1Minute;
    this.methodLimits = methodLimits;
    this.restrictbyIP = restrictbyIP;
    this.validateIPCounts = validateIPCounts;
  }

  /**
   * Returns limits for downloading caches
   * @return cache limit object
   */
  public List<CacheLimit> getCacheLimits() {
    return cacheLimits;
  }

  /**
   * Returns if the cache limits are enforced
   * @return are enforced
   */
  public boolean isEnforceCacheLimits() {
    return enforceCacheLimits;
  }

  /**
   * Returns if the cache limits are enforced for a lite caches
   * @return are enforced
   */
  public boolean isEnforceLiteCacheLimits() {
    return enforceLiteCacheLimits;
  }

  /**
   * Returns if the method limits are enforced
   * @return are enforced
   */
  public boolean isEnforceMethodLimits() {
    return enforceMethodLimits;
  }

  /**
   * Returns a membership type for which are limits applied
   * @return member type
   */
  public MemberType getForMembershipType() {
    return forMembershipType;
  }

  /**
   * Returns a license key
   * @return license key
   */
  public String getLicenseKey() {
    return licenseKey;
  }

  /**
   * Returns a cache limits for lite caches
   * @return cache limits
   */
  public List<CacheLimit> getLiteCacheLimits() {
    return liteCacheLimits;
  }

  /**
   * Returns the number of max calls to be performed with same IP per minute
   * @return number of max calls
   */
  public long getMaxCallsbyIPIn1Minute() {
    return maxCallsbyIPIn1Minute;
  }

  /**
   * Returns the method limits
   * @return method limits
   */
  public List<MethodLimit> getMethodLimits() {
    return methodLimits;
  }

  /**
   * Returns whereas limits are restricted by IP
   * @return are restricted by IP
   */
  public boolean isRestrictbyIP() {
    return restrictbyIP;
  }

  /**
   * Returns if counts of IP are validated 
   * @return count of IP validated
   */
  public boolean isValidateIPCounts() {
    return validateIPCounts;
  }  
}
