package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonToken;

public class GeocacheJsonParser extends JsonParser {
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

	public static Geocache parse(JsonReader r) throws IOException {
    Geocache.Builder geocache = Geocache.Builder.geocache();
    Coordinates.Builder coordinates = Coordinates.Builder.coordinates();

		r.beginObject();
		while (r.hasNext()) {
			String name = r.nextName();
			if ("ID".equals(name)) {
        geocache.withId(r.nextLong());
			} else if ("Code".equals(name)) {
        geocache.withCode(r.nextString());
			} else if ("Name".equals(name)) {
        geocache.withName(r.nextString());
			} else if ("Longitude".equals(name)) {
        coordinates.withLongitude(r.nextDouble());
			} else if ("Latitude".equals(name)) {
        coordinates.withLatitude(r.nextDouble());
			} else if ("CacheType".equals(name)) {
        geocache.withCacheType(parseGeocacheType(r));
			} else if ("Difficulty".equals(name)) {
        geocache.withDifficulty((float) r.nextDouble());
			} else if ("Terrain".equals(name)) {
        geocache.withTerrain((float) r.nextDouble());
			} else if ("Owner".equals(name)) {
        geocache.withOwner(parseUser(r));
			} else if ("Available".equals(name)) {
        geocache.withAvailable(r.nextBoolean());
			} else if ("Archived".equals(name)) {
        geocache.withArchived(r.nextBoolean());
			} else if ("IsPremium".equals(name)) {
        geocache.withPremium(r.nextBoolean());
			} else if ("Country".equals(name)) {
        geocache.withCountryName(r.nextString());
			} else if ("State".equals(name)) {
        geocache.withStateName(r.nextString());
			} else if ("DateCreated".equals(name)) {
        geocache.withCreateDate(JsonParser.parseJsonDate(r.nextString()));
      } else if ("PublishDateUtc".equals(name)) {
        geocache.withPublishDate(JsonParser.parseJsonUTCDate(r.nextString()));
			} else if ("UTCPlaceDate".equals(name)) {
        geocache.withPlaceDate(JsonParser.parseJsonUTCDate(r.nextString()));
			} else if ("DateLastUpdate".equals(name)) {
        geocache.withLastUpdateDate(JsonParser.parseJsonDate(r.nextString()));
			} else if ("DateLastVisited".equals(name)) {
        geocache.withLastVisitDate(JsonParser.parseJsonDate(r.nextString()));
      } else if ("PlacedBy".equals(name)) {
        geocache.withPlacedBy(r.nextString());
			} else if ("ContainerType".equals(name)) {
        geocache.withContainerType(parseContainerType(r));
			} else if ("TrackableCount".equals(name)) {
        geocache.withTrackableCount(r.nextInt());
			} else if ("HasbeenFoundbyUser".equals(name)) {
        geocache.withFoundByUser(r.nextBoolean());
			} else if ("ShortDescription".equals(name)) {
        geocache.withShortDescription(r.nextString());
			} else if ("ShortDescriptionIsHtml".equals(name)) {
        geocache.withShortDescriptionHtml(r.nextBoolean());
			} else if ("LongDescription".equals(name)) {
        geocache.withLongDescription(r.nextString());
			} else if ("LongDescriptionIsHtml".equals(name)) {
        geocache.withLongDescriptionHtml(r.nextBoolean());
			} else if ("EncodedHints".equals(name)) {
        geocache.withHint(r.nextString());
			} else if ("GeocacheLogs".equals(name)) {
        geocache.withCacheLogs(GeocacheLogJsonParser.parseList(r));
			} else if ("Trackables".equals(name)) {
        geocache.withTrackables(TrackableJsonParser.parseList(r));
			} else if ("AdditionalWaypoints".equals(name)) {
        geocache.withWaypoints(WaypointJsonParser.parseList(r));
			} else if ("Attributes".equals(name)) {
        geocache.withAttributes(parseAttributeList(r));
			} else if ("UserWaypoints".equals(name)) {
        geocache.withUserWaypoints(UserWaypointsJsonParser.parseList(r));
			} else if ("GeocacheNote".equals(name)) {
        geocache.withPersonalNote(r.nextString());
			} else if ("Images".equals(name)) {
        geocache.withImages(ImageDataJsonParser.parseList(r));
			} else if ("FavoritePoints".equals(name)) {
        geocache.withFavoritePoints(r.nextInt());
      } else if ("CanCacheBeFavorited".equals(name)) {
        geocache.withFavoritable(r.nextBoolean());
      } else if ("FoundDate".equals(name)) {
        geocache.withFoundDate(JsonParser.parseJsonDate(r.nextString()));
      } else if ("HasbeenFavoritedbyUser".equals(name)) {
        geocache.withFavoritedByUser(r.nextBoolean());
      } else if ("ImageCount".equals(name)) {
        geocache.withImageCount(r.nextInt());
      } else if ("IsRecommended".equals(name)) {
        geocache.withRecommended(r.nextBoolean());
      } else if ("TrackableCount".equals(name)) {
        geocache.withTrackableCount(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();

    geocache.withCoordinates(coordinates.build());

		return geocache.build();
	}
}
