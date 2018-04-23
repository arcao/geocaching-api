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
import com.google.gson.stream.JsonReader;
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
        if (isNextNull(r)) {
            return null;
        }

        UserProfile.Builder builder = UserProfile.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "FavoritePoints":
                    builder.favoritePointsStats(parseFavoritePointStats(r));
                    break;
                case "Geocaches":
                    builder.geocacheStats(parseGeocacheStats(r));
                    break;
                case "PublicProfile":
                    builder.publicProfile(parsePublicProfile(r));
                    break;
                case "Stats":
                    builder.globalStats(parseGlobalStats(r));
                    break;
                case "Trackables":
                    builder.trackableStats(parseTrackableStats(r));
                    break;
                case "User":
                    builder.user(UserJsonParser.parse(r));
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static FavoritePointStats parseFavoritePointStats(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

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
        if (isNextNull(r)) {
            return null;
        }

        GeocacheStats.Builder builder = GeocacheStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "GeocacheFindCount":
                    builder.findCount(r.nextInt());
                    break;
                case "GeocacheFindTypes":
                    builder.findTypes(parseGeocacheTypeCounts(r));
                    break;
                case "GeocacheHideCount":
                    builder.hideCount(r.nextInt());
                    break;
                case "GeocacheHideTypes":
                    builder.hideTypes(parseGeocacheTypeCounts(r));
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<GeocacheTypeCount> parseGeocacheTypeCounts(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<GeocacheTypeCount> list = new ArrayList<>();
        r.beginArray();

        while (r.hasNext()) {
            list.add(parseGeocacheTypeCount(r));
        }
        r.endArray();
        return list;
    }

    private static GeocacheTypeCount parseGeocacheTypeCount(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

        GeocacheTypeCount.Builder builder = GeocacheTypeCount.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "GeocacheTypeId":
                    builder.type(GeocacheType.fromId(r.nextInt()));
                    break;
                case "UserCount":
                    builder.count(r.nextInt());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static PublicProfile parsePublicProfile(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

        PublicProfile.Builder builder = PublicProfile.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "ForumTitle":
                    builder.forumTitle(r.nextString());
                    break;
                case "LastVisit":
                    builder.lastVisit(parseJsonDate(r.nextString()));
                    break;
                case "Location":
                    builder.location(r.nextString());
                    break;
                case "MemberSince":
                    builder.memberSince(parseJsonDate(r.nextString()));
                    break;
                case "Occupation":
                    builder.occupation(r.nextString());
                    break;
                case "ProfilePhoto":
                    builder.profilePhoto(parseProfilePhoto(r));
                    break;
                case "ProfileText":
                    builder.profileText(r.nextString());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static ProfilePhoto parseProfilePhoto(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

        ProfilePhoto.Builder builder = ProfilePhoto.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "PhotoDescription":
                    builder.photoDescription(r.nextString());
                    break;
                case "PhotoFilename":
                    builder.photoFilename(r.nextString());
                    break;
                case "PhotoName":
                    builder.photoName(r.nextString());
                    break;
                case "PhotoUrl":
                    builder.photoUrl(r.nextString());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static GlobalStats parseGlobalStats(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

        GlobalStats.Builder builder = GlobalStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "AccountsLogged":
                    builder.accountsLogged(r.nextLong());
                    break;
                case "ActiveCaches":
                    builder.activeCaches(r.nextLong());
                    break;
                case "ActiveCountries":
                    builder.activeCountries(r.nextLong());
                    break;
                case "NewLogs":
                    builder.newLogs(r.nextLong());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static TrackableStats parseTrackableStats(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

        TrackableStats.Builder builder = TrackableStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "TrackableFindCount":
                    builder.findCount(r.nextInt());
                    break;
                case "TrackableFindTypes":
                    builder.findTypes(parseTrackableTypeCounts(r));
                    break;
                case "TrackableOwnedCount":
                    builder.ownedCount(r.nextInt());
                    break;
                case "TrackableOwnedTypes":
                    builder.ownedTypes(parseTrackableTypeCounts(r));
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }

    private static List<TrackableTypeCount> parseTrackableTypeCounts(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<TrackableTypeCount> list = new ArrayList<>();
        r.beginArray();

        while (r.hasNext()) {
            list.add(parseTrackableTypeCount(r));
        }
        r.endArray();
        return list;

    }

    private static TrackableTypeCount parseTrackableTypeCount(JsonReader r) throws IOException {
        if (isNextNull(r)) {
            return null;
        }

        TrackableTypeCount.Builder builder = TrackableTypeCount.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "BugTypeID":
                    builder.id(r.nextInt());
                    break;
                case "IconUrl":
                    builder.iconUrl(r.nextString());
                    break;
                case "TBTypeName":
                    builder.name(r.nextString());
                    break;
                case "UserCount":
                    builder.count(r.nextInt());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }
}
