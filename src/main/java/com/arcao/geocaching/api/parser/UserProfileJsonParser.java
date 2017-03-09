package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheTypeCount;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.ProfilePhoto;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;
import com.arcao.geocaching.api.data.userprofile.TrackableTypeCount;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.parser.JsonParserUtil.isNextNull;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonDate;

public final class UserProfileJsonParser {
    private UserProfileJsonParser() {
    }

    public static UserProfile parse(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        UserProfile.Builder builder = UserProfile.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("FavoritePoints".equals(name)) {
                builder.favoritePointsStats(parseFavoritePointStats(r));
            } else if ("Geocaches".equals(name)) {
                builder.geocacheStats(parseGeocacheStats(r));
            } else if ("PublicProfile".equals(name)) {
                builder.publicProfile(parsePublicProfile(r));
            } else if ("Stats".equals(name)) {
                builder.globalStats(parseGlobalStats(r));
            } else if ("Trackables".equals(name)) {
                builder.trackableStats(parseTrackableStats(r));
            } else if ("User".equals(name)) {
                builder.user(UserJsonParser.parse(r));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static FavoritePointStats parseFavoritePointStats(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        FavoritePointStats.Builder builder = FavoritePointStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            r.nextName();
            r.skipValue();
        }
        r.endObject();

        return builder.build();
    }

    private static GeocacheStats parseGeocacheStats(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        GeocacheStats.Builder builder = GeocacheStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("GeocacheFindCount".equals(name)) {
                builder.findCount(r.nextInt());
            } else if ("GeocacheFindTypes".equals(name)) {
                builder.findTypes(parseGeocacheTypeCounts(r));
            } else if ("GeocacheHideCount".equals(name)) {
                builder.hideCount(r.nextInt());
            } else if ("GeocacheHideTypes".equals(name)) {
                builder.hideTypes(parseGeocacheTypeCounts(r));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<GeocacheTypeCount> parseGeocacheTypeCounts(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<GeocacheTypeCount> list = new ArrayList<GeocacheTypeCount>();
        r.beginArray();

        while (r.hasNext()) {
            list.add(parseGeocacheTypeCount(r));
        }
        r.endArray();
        return list;
    }

    private static GeocacheTypeCount parseGeocacheTypeCount(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        GeocacheTypeCount.Builder builder = GeocacheTypeCount.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("GeocacheTypeId".equals(name)) {
                builder.type(GeocacheType.fromId(r.nextInt()));
            } else if ("UserCount".equals(name)) {
                builder.count(r.nextInt());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static PublicProfile parsePublicProfile(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        PublicProfile.Builder builder = PublicProfile.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("ForumTitle".equals(name)) {
                builder.forumTitle(r.nextString());
            } else if ("LastVisit".equals(name)) {
                builder.lastVisit(parseJsonDate(r.nextString()));
            } else if ("Location".equals(name)) {
                builder.location(r.nextString());
            } else if ("MemberSince".equals(name)) {
                builder.memberSince(parseJsonDate(r.nextString()));
            } else if ("Occupation".equals(name)) {
                builder.occupation(r.nextString());
            } else if ("ProfilePhoto".equals(name)) {
                builder.profilePhoto(parseProfilePhoto(r));
            } else if ("ProfileText".equals(name)) {
                builder.profileText(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static ProfilePhoto parseProfilePhoto(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        ProfilePhoto.Builder builder = ProfilePhoto.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("PhotoDescription".equals(name)) {
                builder.photoDescription(r.nextString());
            } else if ("PhotoFilename".equals(name)) {
                builder.photoFilename(r.nextString());
            } else if ("PhotoName".equals(name)) {
                builder.photoName(r.nextString());
            } else if ("PhotoUrl".equals(name)) {
                builder.photoUrl(r.nextString());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static GlobalStats parseGlobalStats(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        GlobalStats.Builder builder = GlobalStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("AccountsLogged".equals(name)) {
                builder.accountsLogged(r.nextLong());
            } else if ("ActiveCaches".equals(name)) {
                builder.activeCaches(r.nextLong());
            } else if ("ActiveCountries".equals(name)) {
                builder.activeCountries(r.nextLong());
            } else if ("NewLogs".equals(name)) {
                builder.newLogs(r.nextLong());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static TrackableStats parseTrackableStats(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        TrackableStats.Builder builder = TrackableStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("TrackableFindCount".equals(name)) {
                builder.findCount(r.nextInt());
            } else if ("TrackableFindTypes".equals(name)) {
                builder.findTypes(parseTrackableTypeCounts(r));
            } else if ("TrackableOwnedCount".equals(name)) {
                builder.ownedCount(r.nextInt());
            } else if ("TrackableOwnedTypes".equals(name)) {
                builder.ownedTypes(parseTrackableTypeCounts(r));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<TrackableTypeCount> parseTrackableTypeCounts(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<TrackableTypeCount> list = new ArrayList<TrackableTypeCount>();
        r.beginArray();

        while (r.hasNext()) {
            list.add(parseTrackableTypeCount(r));
        }
        r.endArray();
        return list;

    }

    private static TrackableTypeCount parseTrackableTypeCount(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        TrackableTypeCount.Builder builder = TrackableTypeCount.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("BugTypeID".equals(name)) {
                builder.id(r.nextInt());
            } else if ("IconUrl".equals(name)) {
                builder.iconUrl(r.nextString());
            } else if ("TBTypeName".equals(name)) {
                builder.name(r.nextString());
            } else if ("UserCount".equals(name)) {
                builder.count(r.nextInt());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}
