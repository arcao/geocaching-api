package com.arcao.geocaching.api;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@AutoValue
public abstract class Status implements Serializable {
    private static final long serialVersionUID = -6603595166996374235L;

    public abstract int code();

    public StatusCode statusCode() {
        return StatusCode.getByCode(code());
    }

    public abstract String message();

    @Nullable
    public abstract String exceptionDetails();

    public static Builder builder() {
        return new AutoValue_Status.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder code(int code);

        public abstract Builder message(String message);

        public abstract Builder exceptionDetails(@Nullable String exceptionDetails);

        public abstract Status build();
    }
}
