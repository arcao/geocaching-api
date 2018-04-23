package com.arcao.geocaching.api.filter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class CachePublishedDateFilter extends AbstractDateRangeFilter {
    private static final String NAME = "CachePublishedDate";

    public CachePublishedDateFilter(@Nullable Date startDate, @Nullable Date endDate) {
        super(startDate, endDate);
    }

    @NotNull
    @Override
    public String name() {
        return NAME;
    }
}
