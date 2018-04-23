package com.arcao.geocaching.api.filter;

import org.jetbrains.annotations.NotNull;

public class HiddenByUsersFilter extends AbstractUsersFilter {
    private static final String NAME = "HiddenByUsers";

    public HiddenByUsersFilter(@NotNull String... userNames) {
        super(userNames);
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }
}
