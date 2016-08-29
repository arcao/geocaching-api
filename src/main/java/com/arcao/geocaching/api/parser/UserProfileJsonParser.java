package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheFindStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.ProfilePhoto;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;

import java.io.IOException;

import static com.arcao.geocaching.api.parser.JsonParserUtil.isNextNull;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseJsonDate;
import static com.arcao.geocaching.api.parser.JsonParserUtil.parseUser;
import static com.arcao.geocaching.api.parser.TrackableJsonParser.parseList;

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
                builder.geocacheFindStats(parseGeocacheFindStats(r));
            } else if ("PublicProfile".equals(name)) {
                builder.publicProfile(parsePublicProfile(r));
            } else if ("Stats".equals(name)) {
                builder.globalStats(parseGlobalStats(r));
            } else if ("Trackables".equals(name)) {
                builder.trackableStats(parseTrackableStats(r));
            } else if ("User".equals(name)) {
                builder.user(parseUser(r));
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

    private static GeocacheFindStats parseGeocacheFindStats(JsonReader r) throws IOException {
        if (isNextNull(r))
            return null;

        GeocacheFindStats.Builder builder = GeocacheFindStats.builder();

        r.beginObject();
        while (r.hasNext()) {
            r.nextName();
            r.skipValue();
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
                builder.trackableFindCount(r.nextInt());
            } else if ("TrackableFindTypes".equals(name)) {
                builder.trackableFindTypes(parseList(r));
            } else if ("TrackableOwnedCount".equals(name)) {
                builder.trackableOwnedCount(r.nextInt());
            } else if ("TrackableOwnedTypes".equals(name)) {
                builder.trackableOwnedTypes(parseList(r));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }

}
