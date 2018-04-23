package com.arcao.geocaching.api.data.type;

import org.jetbrains.annotations.NotNull;

public enum MemberType {
    Guest("Guest", 0),
    Basic("Basic", 1),
    Charter("Charter", 2),
    Premium("Premium", 3);

    /**
     * Friendly name.
     */
    @NotNull public final String name;
    /**
     * Groundspeak Id.
     */
    public final int id;

    MemberType(@NotNull String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Get a MemberType from Groundspeak Id. If Groundspeak Id is not valid, the Guest value is returned.
     *
     * @param id Groundspeak Id
     * @return Member Type
     */
    @NotNull
    public static MemberType fromId(int id) {
        for (MemberType memberType : values()) {
            if (memberType.id == id) {
                return memberType;
            }
        }

        return Guest;
    }
}
