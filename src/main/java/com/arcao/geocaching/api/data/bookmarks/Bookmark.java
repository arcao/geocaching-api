package com.arcao.geocaching.api.data.bookmarks;

import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.util.DebugUtils;
import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class Bookmark implements Serializable {
    private static final long serialVersionUID = -2198982416375777824L;

    public abstract String cacheCode();

    public abstract String cacheTitle();

    public abstract GeocacheType geocacheType();

    @Override
    public String toString() {
        return DebugUtils.toString(this);
    }

    public static Builder builder() {
        return new AutoValue_Bookmark.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheCode(String cacheCode);

        public abstract Builder cacheTitle(String cacheTitle);

        public abstract Builder geocacheType(GeocacheType geocacheType);

        public abstract Bookmark build();
    }
}
