package com.arcao.geocaching.api.data.bookmarks;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@AutoValue
public abstract class BookmarkList implements Serializable {
    private static final long serialVersionUID = 2322622811124797813L;

    public abstract int id();

    public abstract String guid();

    public abstract String name();

    @Nullable
    public abstract String description();

    public abstract int itemCount();

    public abstract boolean shared();

    public abstract boolean publicList();

    public abstract boolean archived();

    public abstract boolean special();

    public abstract int type();

    public static Builder builder() {
        return new AutoValue_BookmarkList.Builder()
                .itemCount(0)
                .shared(false)
                .publicList(false)
                .archived(false)
                .special(false);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder guid(String guid);

        public abstract Builder name(String name);

        public abstract Builder description(String description);

        public abstract Builder itemCount(int itemCount);

        public abstract Builder shared(boolean shared);

        public abstract Builder archived(boolean archived);

        public abstract Builder special(boolean special);

        public abstract Builder type(int type);

        public abstract Builder publicList(boolean publicList);

        public abstract BookmarkList build();
    }
}
