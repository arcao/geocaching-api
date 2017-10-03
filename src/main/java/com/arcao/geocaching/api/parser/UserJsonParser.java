package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonToken;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class UserJsonParser {
    private UserJsonParser() {
    }

    @NotNull
    public static List<User> parseList(@NotNull JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<User> list = new ArrayList<User>();
        r.beginArray();
        while (r.hasNext()) {
            User user = parse(r);
            if (user != null)
                list.add(user);
        }
        r.endArray();
        return list;
    }


    @Nullable
    public static User parse(@NotNull JsonReader r) throws IOException {
        if (JsonParserUtil.isNextNull(r))
            return null;

        User.Builder user = User.builder();
        String userName = null;

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("AvatarUrl".equals(name)) {
                user.avatarUrl(r.nextString());
            } else if ("FindCount".equals(name)) {
                user.findCount(r.nextInt());
            } else if ("HideCount".equals(name)) {
                user.hideCount(r.nextInt());
            } else if ("HomeCoordinates".equals(name)) {
                user.homeCoordinates(parseHomeCoordinates(r));
            } else if ("Id".equals(name)) {
                user.id(r.nextLong());
            } else if ("IsAdmin".equals(name)) {
                user.admin(r.nextBoolean());
            } else if ("MemberType".equals(name)) {
                user.memberType(JsonParserUtil.parseMemberType(r));
            } else if ("PublicGuid".equals(name)) {
                user.publicGuid(r.nextString());
            } else if ("UserName".equals(name)) {
                userName = r.nextString();
                if (userName == null) {
                    while (r.hasNext()) r.skipValue();
                    r.endObject();
                    return null;
                }
                user.userName(userName);
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return user.build();
    }

    private static Coordinates parseHomeCoordinates(JsonReader r) throws IOException {
        if (JsonParserUtil.isNextNull(r))
            return null;

        Coordinates.Builder coordinates = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Latitude".equals(name)) {
                coordinates.latitude((float) r.nextDouble());
            } else if ("Longitude".equals(name)) {
                coordinates.longitude((float) r.nextDouble());
            } else {
                r.skipValue();
            }
        }
        r.endObject();
        return coordinates.build();
    }
}
