package com.arcao.geocaching.api.filter;

import org.jetbrains.annotations.NotNull;

public class NotFoundByUsersFilter extends AbstractUsersFilter {
    private static final String NAME = "NotFoundByUsers";

    public NotFoundByUsersFilter(@NotNull String... userNames) {
        super(userNames);
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }
}
