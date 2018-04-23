package com.arcao.geocaching.api.data.apilimits;

import com.arcao.geocaching.api.data.type.MemberType;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.List;

/**
 * Container class for API limits related to currently logged user.
 *
 * @author arcao
 */
@AutoValue
public abstract class ApiLimits implements Serializable {
    private static final long serialVersionUID = -3608995080972521881L;

    /**
     * Returns limits for downloading caches.
     *
     * @return cache limit object
     */
    public abstract List<CacheLimit> cacheLimits();

    /**
     * Returns if the cache limits are enforced.
     *
     * @return are enforced
     */
    public abstract boolean enforceCacheLimits();

    /**
     * Returns if the cache limits are enforced for a lite caches.
     *
     * @return are enforced
     */
    public abstract boolean enforceLiteCacheLimits();

    /**
     * Returns if the method limits are enforced.
     *
     * @return are enforced
     */
    public abstract boolean enforceMethodLimits();

    /**
     * Returns a membership type for which are limits applied.
     *
     * @return member type
     */
    public abstract MemberType forMembershipType();

    /**
     * Returns a license key.
     *
     * @return license key
     */
    public abstract String licenseKey();

    /**
     * Returns a cache limits for lite caches.
     *
     * @return cache limits
     */
    public abstract List<CacheLimit> liteCacheLimits();

    /**
     * Returns the number of max calls to be performed with same IP per minute.
     *
     * @return number of max calls
     */
    public abstract long maxCallsbyIPIn1Minute();

    /**
     * Returns the method limits.
     *
     * @return method limits
     */
    public abstract List<MethodLimit> methodLimits();

    /**
     * Returns whereas limits are restricted by IP.
     *
     * @return are restricted by IP
     */
    public abstract boolean restrictByIp();

    /**
     * Returns if counts of IP are validated.
     *
     * @return count of IP validated
     */
    public abstract boolean validateIpCounts();

    public static Builder builder() {
        return new AutoValue_ApiLimits.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheLimits(List<CacheLimit> cacheLimits);
        public abstract Builder enforceCacheLimits(boolean enforceCacheLimits);
        public abstract Builder enforceLiteCacheLimits(boolean enforceLiteCacheLimits);
        public abstract Builder enforceMethodLimits(boolean enforceMethodLimits);
        public abstract Builder forMembershipType(MemberType forMembershipType);
        public abstract Builder licenseKey(String licenseKey);
        public abstract Builder liteCacheLimits(List<CacheLimit> liteCacheLimits);
        public abstract Builder maxCallsbyIPIn1Minute(long maxCallsbyIPIn1Minute);
        public abstract Builder methodLimits(List<MethodLimit> methodLimits);
        public abstract Builder restrictByIp(boolean restrictByIp);
        public abstract Builder validateIpCounts(boolean validateIpCounts);
        public abstract ApiLimits build();
    }
}
