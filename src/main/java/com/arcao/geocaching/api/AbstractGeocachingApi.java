package com.arcao.geocaching.api;

import com.arcao.geocaching.api.data.FieldNote;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SearchForGeocachesRequest;
import com.arcao.geocaching.api.data.sort.SortBy;
import com.arcao.geocaching.api.exception.GeocachingApiException;
import com.arcao.geocaching.api.filter.CacheCodeFilter;
import com.arcao.geocaching.api.filter.Filter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Abstract class of Geocaching API.
 *
 * @author arcao
 * @since 1.4.3
 */
abstract class AbstractGeocachingApi implements GeocachingApi {
    @Nullable String session;

    @Nullable
    @Override
    public String getSession() {
        return session;
    }

    @Override
    public void openSession(@NotNull String session) throws GeocachingApiException {
        this.session = session;
    }

    @Override
    public Geocache getGeocache(@NotNull ResultQuality resultQuality, @NotNull String cacheCode,
                                int cacheLogCount, int trackableLogCount) throws GeocachingApiException {
        final List<Geocache> list = searchForGeocaches(SearchForGeocachesRequest.builder()
                .resultQuality(resultQuality)
                .maxPerPage(1)
                .geocacheLogCount(cacheLogCount)
                .trackableLogCount(trackableLogCount)
                .addFilter(new CacheCodeFilter(cacheCode))
                .build()
        );


        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @NotNull
    @Deprecated
    @Override
    public List<Geocache> searchForGeocaches(@NotNull ResultQuality resultQuality, int maxPerPage, int geocacheLogCount,
                                             int trackableLogCount, @NotNull Collection<Filter> filters,
                                             @Nullable Collection<SortBy> sortByList) throws GeocachingApiException {
        return searchForGeocaches(SearchForGeocachesRequest.builder()
                .resultQuality(resultQuality)
                .maxPerPage(maxPerPage)
                .geocacheLogCount(geocacheLogCount)
                .trackableLogCount(trackableLogCount)
                .addFilters(filters)
                .addSortKeys(sortByList)
                .build()
        );
    }


    @Override
    public GeocacheLog createFieldNoteAndPublish(@NotNull FieldNote fieldNote, boolean publish, ImageData imageData,
                                                 boolean favoriteThisCache) throws GeocachingApiException {
        return createFieldNoteAndPublish(
                fieldNote.cacheCode(),
                fieldNote.logType(),
                fieldNote.dateLogged(),
                fieldNote.note(),
                publish,
                imageData,
                favoriteThisCache
        );
    }
}
