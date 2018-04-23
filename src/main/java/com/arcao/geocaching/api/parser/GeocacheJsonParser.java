package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonReader;
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

        List<Geocache> list = new ArrayList<>();
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
            switch (name) {
                case "ID":
                    builder.id(r.nextLong());
                    break;
                case "Code":
                    builder.code(r.nextString());
                    break;
                case "Name":
                    builder.name(r.nextString());
                    break;
                case "Longitude":
                    coordinatesBuilder.longitude(r.nextDouble());
                    break;
                case "Latitude":
                    coordinatesBuilder.latitude(r.nextDouble());
                    break;
                case "CacheType":
                    builder.geocacheType(parseGeocacheType(r));
                    break;
                case "Difficulty":
                    builder.difficulty((float) r.nextDouble());
                    break;
                case "Terrain":
                    builder.terrain((float) r.nextDouble());
                    break;
                case "Owner":
                    builder.owner(UserJsonParser.parse(r));
                    break;
                case "Available":
                    builder.available(r.nextBoolean());
                    break;
                case "Archived":
                    builder.archived(r.nextBoolean());
                    break;
                case "IsPremium":
                    builder.premium(r.nextBoolean());
                    break;
                case "Country":
                    builder.countryName(r.nextString());
                    break;
                case "State":
                    builder.stateName(r.nextString());
                    break;
                case "DateCreated":
                    builder.createDate(JsonParserUtil.parseJsonDate(r.nextString()));
                    break;
                case "PublishDateUtc":
                    builder.publishDate(JsonParserUtil.parseJsonUTCDate(r.nextString()));
                    break;
                case "UTCPlaceDate":
                    builder.placeDate(JsonParserUtil.parseJsonUTCDate(r.nextString()));
                    break;
                case "DateLastUpdate":
                    builder.lastUpdateDate(JsonParserUtil.parseJsonDate(r.nextString()));
                    break;
                case "DateLastVisited":
                    builder.lastVisitDate(JsonParserUtil.parseJsonDate(r.nextString()));
                    break;
                case "PlacedBy":
                    builder.placedBy(r.nextString());
                    break;
                case "ContainerType":
                    builder.containerType(parseContainerType(r));
                    break;
                case "HasbeenFoundbyUser":
                    builder.foundByUser(r.nextBoolean());
                    break;
                case "ShortDescription":
                    builder.shortDescription(r.nextString());
                    break;
                case "ShortDescriptionIsHtml":
                    builder.shortDescriptionHtml(r.nextBoolean());
                    break;
                case "LongDescription":
                    builder.longDescription(r.nextString());
                    break;
                case "LongDescriptionIsHtml":
                    builder.longDescriptionHtml(r.nextBoolean());
                    break;
                case "EncodedHints":
                    builder.hint(r.nextString());
                    break;
                case "GeocacheLogs":
                    builder.geocacheLogs(GeocacheLogJsonParser.parseList(r));
                    break;
                case "Trackables":
                    builder.trackables(TrackableJsonParser.parseList(r));
                    break;
                case "AdditionalWaypoints":
                    builder.waypoints(WaypointJsonParser.parseList(r));
                    break;
                case "Attributes":
                    builder.attributes(parseAttributeList(r));
                    break;
                case "UserWaypoints":
                    builder.userWaypoints(UserWaypointsJsonParser.parseList(r));
                    break;
                case "GeocacheNote":
                    builder.personalNote(r.nextString());
                    break;
                case "Images":
                    builder.images(ImageDataJsonParser.parseList(r));
                    break;
                case "FavoritePoints":
                    builder.favoritePoints(r.nextInt());
                    break;
                case "CanCacheBeFavorited":
                    builder.favoritable(r.nextBoolean());
                    break;
                case "FoundDate":
                    builder.foundDate(JsonParserUtil.parseJsonDate(r.nextString()));
                    break;
                case "HasbeenFavoritedbyUser":
                    builder.favoritedByUser(r.nextBoolean());
                    break;
                case "ImageCount":
                    builder.imageCount(r.nextInt());
                    break;
                case "IsRecommended":
                    builder.recommended(r.nextBoolean());
                    break;
                case "TrackableCount":
                    builder.trackableCount(r.nextInt());
                    break;
                case "Url":
                    builder.url(r.nextString());
                    break;
                case "GUID":
                    builder.guid(r.nextString());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        builder.coordinates(coordinatesBuilder.build());

        return builder.build();
    }
}
