package com.arcao.geocaching.api.filter;

import org.jetbrains.annotations.NotNull;

public class NotHiddenByUsersFilter extends AbstractUsersFilter {
    private static final String NAME = "NotHiddenByUsers";

    public NotHiddenByUsersFilter(@NotNull String... userNames) {
        super(userNames);
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }
}
