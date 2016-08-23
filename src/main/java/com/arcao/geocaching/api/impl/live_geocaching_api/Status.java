package com.arcao.geocaching.api.impl.live_geocaching_api;

import com.arcao.geocaching.api.util.DebugUtils;
import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class Status implements Serializable {
    private static final long serialVersionUID = -6603595166996374235L;

    public abstract int code();

    public StatusCode statusCode() {
        return StatusCode.getByCode(code());
    }

    public abstract String message();

    public abstract String exceptionDetails();

    @Override
    public String toString() {
        return DebugUtils.toString(this);
    }

    public static Builder builder() {
        return new AutoValue_Status.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder code(int code);

        public abstract Builder message(String message);

        public abstract Builder exceptionDetails(String exceptionDetails);

        public abstract Status build();
    }
}
