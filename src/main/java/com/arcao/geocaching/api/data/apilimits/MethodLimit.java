package com.arcao.geocaching.api.data.apilimits;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Container class for a method limit information.
 *
 * @author arcao
 */
@AutoValue
public abstract class MethodLimit implements Serializable {
    private static final long serialVersionUID = 3914171011300602155L;

    /**
     * Returns a period for this limit in the minutes.
     *
     * @return period in a minutes
     */
    public abstract int period();

    /**
     * Returns a limit for this method calling.
     *
     * @return limit for a method
     */
    public abstract long limit();

    /**
     * Returns a method name for which is applied this limit.
     *
     * @return method name
     */
    public abstract String methodName();

    /**
     * Returns if this method is partner method.
     *
     * @return is partner method
     */
    public abstract boolean partnerMethod();

    public static Builder builder() {
        return new AutoValue_MethodLimit.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder period(int period);

        public abstract Builder limit(long limit);

        public abstract Builder methodName(String methodName);

        public abstract Builder partnerMethod(boolean partnerMethod);

        public abstract MethodLimit build();
    }
}
