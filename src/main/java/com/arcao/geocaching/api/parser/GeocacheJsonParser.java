package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.parseAttributeList;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseContainerType;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseGeocacheType;

public final class GeocacheJsonParser {
    private GeocacheJsonParser() {
    }

    public static List<Geocache> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<Geocache> list = new ArrayList<Geocache>();
        r.beginArray();

        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    private static Geocache parse(JsonReader r) throws IOException {
        Geocache.Builder builder = Geocache.builder();
        Coordinates.Builder coordinatesBuilder = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("ID".equals(name)) {
                builder.id(r.nextLong());
            } else if ("Code".equals(name)) {
                builder.code(r.nextString());
            } else if ("Name".equals(name)) {
                builder.name(r.nextString());
            } else if ("Longitude".equals(name)) {
                coordinatesBuilder.longitude(r.nextDouble());
            } else if ("Latitude".equals(name)) {
                coordinatesBuilder.latitude(r.nextDouble());
            } else if ("CacheType".equals(name)) {
                builder.geocacheType(parseGeocacheType(r));
            } else if ("Difficulty".equals(name)) {
                builder.difficulty((float) r.nextDouble());
            } else if ("Terrain".equals(name)) {
                builder.terrain((float) r.nextDouble());
            } else if ("Owner".equals(name)) {
                builder.owner(UserJsonParser.parse(r));
            } else if ("Available".equals(name)) {
                builder.available(r.nextBoolean());
            } else if ("Archived".equals(name)) {
                builder.archived(r.nextBoolean());
            } else if ("IsPremium".equals(name)) {
                builder.premium(r.nextBoolean());
            } else if ("Country".equals(name)) {
                builder.countryName(r.nextString());
            } else if ("State".equals(name)) {
                builder.stateName(r.nextString());
            } else if ("DateCreated".equals(name)) {
                builder.createDate(JsonParserUtil.parseJsonDate(r.nextString()));
            } else if ("PublishDateUtc".equals(name)) {
                builder.publishDate(JsonParserUtil.parseJsonUTCDate(r.nextString()));
            } else if ("UTCPlaceDate".equals(name)) {
                builder.placeDate(JsonParserUtil.parseJsonUTCDate(r.nextString()));
            } else if ("DateLastUpdate".equals(name)) {
                builder.lastUpdateDate(JsonParserUtil.parseJsonDate(r.nextString()));
            } else if ("DateLastVisited".equals(name)) {
                builder.lastVisitDate(JsonParserUtil.parseJsonDate(r.nextString()));
            } else if ("PlacedBy".equals(name)) {
                builder.placedBy(r.nextString());
            } else if ("ContainerType".equals(name)) {
                builder.containerType(parseContainerType(r));
            } else if ("TrackableCount".equals(name)) {
                builder.trackableCount(r.nextInt());
            } else if ("HasbeenFoundbyUser".equals(name)) {
                builder.foundByUser(r.nextBoolean());
            } else if ("ShortDescription".equals(name)) {
                builder.shortDescription(r.nextString());
            } else if ("ShortDescriptionIsHtml".equals(name)) {
                builder.shortDescriptionHtml(r.nextBoolean());
            } else if ("LongDescription".equals(name)) {
                builder.longDescription(r.nextString());
            } else if ("LongDescriptionIsHtml".equals(name)) {
                builder.longDescriptionHtml(r.nextBoolean());
            } else if ("EncodedHints".equals(name)) {
                builder.hint(r.nextString());
            } else if ("GeocacheLogs".equals(name)) {
                builder.geocacheLogs(GeocacheLogJsonParser.parseList(r));
            } else if ("Trackables".equals(name)) {
                builder.trackables(TrackableJsonParser.parseList(r));
            } else if ("AdditionalWaypoints".equals(name)) {
                builder.waypoints(WaypointJsonParser.parseList(r));
            } else if ("Attributes".equals(name)) {
                builder.attributes(parseAttributeList(r));
            } else if ("UserWaypoints".equals(name)) {
                builder.userWaypoints(UserWaypointsJsonParser.parseList(r));
            } else if ("GeocacheNote".equals(name)) {
                builder.personalNote(r.nextString());
            } else if ("Images".equals(name)) {
                builder.images(ImageDataJsonParser.parseList(r));
            } else if ("FavoritePoints".equals(name)) {
                builder.favoritePoints(r.nextInt());
            } else if ("CanCacheBeFavorited".equals(name)) {
                builder.favoritable(r.nextBoolean());
            } else if ("FoundDate".equals(name)) {
                builder.foundDate(JsonParserUtil.parseJsonDate(r.nextString()));
            } else if ("HasbeenFavoritedbyUser".equals(name)) {
                builder.favoritedByUser(r.nextBoolean());
            } else if ("ImageCount".equals(name)) {
                builder.imageCount(r.nextInt());
            } else if ("IsRecommended".equals(name)) {
                builder.recommended(r.nextBoolean());
            } else if ("TrackableCount".equals(name)) {
                builder.trackableCount(r.nextInt());
            } else if ("Url".equals(name)) {
                builder.url(r.nextString());
            } else if ("GUID".equals(name)) {
                builder.guid(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        builder.coordinates(coordinatesBuilder.build());

        return builder.build();
    }
}
