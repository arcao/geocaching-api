package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.sort.SortBy;
import com.arcao.geocaching.api.filter.Filter;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@AutoValue
public abstract class SearchForGeocachesRequest implements Serializable {
    private static final long serialVersionUID = 8209857729196134528L;

    /**
     * Get a required quality of result (mean what all has to be returned in result).
     */
    public abstract GeocachingApi.ResultQuality resultQuality();

    /**
     * Get a count of geocaches to be received.
     */
    public abstract int maxPerPage();

    /**
     * Get a count of logs to be retrieved for particular geocache.
     */
    public abstract int geocacheLogCount();

    /**
     * Get a count of logs to be retrieved for trackable in particular geocache.
     */
    public abstract int trackableLogCount();

    /**
     * Get an used filters for request.
     */
    public abstract Collection<Filter> filters();

    /**
     * Get a collection of used sort keys.
     */
    public abstract Collection<SortBy> sortKeys();

    /**
     * Get a coordinates used for sorting, can be null.
     */
    @Nullable public abstract Coordinates sortPoint();

    public static Builder builder() {
        return new AutoValue_SearchForGeocachesRequest.Builder()
                .trackableLogCount(0)
                .geocacheLogCount(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        private final Collection<Filter> filters = new HashSet<>();
        private final Collection<SortBy> sortKeys = new HashSet<>();

        /**
         * Set a required quality of result (mean what all has to be returned in result).
         */
        public abstract Builder resultQuality(GeocachingApi.ResultQuality resultQuality);

        /**
         * Set a count of geocaches to be received.
         */
        public abstract Builder maxPerPage(int maxPerPage);

        /**
         * Set a count of logs to be retrieved for particular geocache.
         */
        public abstract Builder geocacheLogCount(int geocacheLogCount);

        /**
         * Set a count of logs to be retrieved for trackable in particular geocache.
         */
        public abstract Builder trackableLogCount(int trackableLogCount);

        protected abstract Builder filters(Collection<Filter> filters);

        protected abstract Builder sortKeys(Collection<SortBy> sortKeys);

        /**
         * Set a coordinates used for sorting.
         */
        public abstract Builder sortPoint(Coordinates sortPoint);

        protected abstract SearchForGeocachesRequest realBuild();

        /**
         * Add a filter for request.
         */
        public Builder addFilter(Filter filter) {
            filters.add(filter);
            return this;
        }

        /**
         * Add a filters for request.
         */
        public Builder addFilters(@Nullable Collection<Filter> filters) {
            if (filters == null) {
                return this;
            }

            this.filters.addAll(filters);
            return this;
        }

        /**
         * Add a sort key for request.
         */
        public Builder addSortKey(SortBy sortKey) {
            sortKeys.add(sortKey);
            return this;
        }

        /**
         * Add a sort keys for request.
         */
        public Builder addSortKeys(@Nullable Collection<SortBy> sortKeys) {
            if (sortKeys == null) {
                return this;
            }

            this.sortKeys.addAll(sortKeys);
            return this;
        }

        public SearchForGeocachesRequest build() {
            return filters(Collections.unmodifiableCollection(filters))
                    .sortKeys(Collections.unmodifiableCollection(sortKeys))
                    .realBuild();
        }

    }
}
