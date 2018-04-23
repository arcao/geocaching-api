package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonReader;
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

        List<User> list = new ArrayList<>();
        r.beginArray();
        while (r.hasNext()) {
            User user = parse(r);
            if (user != null) {
                list.add(user);
            }
        }
        r.endArray();
        return list;
    }


    @Nullable
    public static User parse(@NotNull JsonReader r) throws IOException {
        if (JsonParserUtil.isNextNull(r)) {
            return null;
        }

        User.Builder user = User.builder();
        String userName;

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "AvatarUrl":
                    user.avatarUrl(r.nextString());
                    break;
                case "FindCount":
                    user.findCount(r.nextInt());
                    break;
                case "HideCount":
                    user.hideCount(r.nextInt());
                    break;
                case "HomeCoordinates":
                    user.homeCoordinates(parseHomeCoordinates(r));
                    break;
                case "Id":
                    user.id(r.nextLong());
                    break;
                case "IsAdmin":
                    user.admin(r.nextBoolean());
                    break;
                case "MemberType":
                    user.memberType(JsonParserUtil.parseMemberType(r));
                    break;
                case "PublicGuid":
                    user.publicGuid(r.nextString());
                    break;
                case "UserName":
                    userName = r.nextString();
                    if (userName == null) {
                        while (r.hasNext()) {
                            r.skipValue();
                        }
                        r.endObject();
                        return null;
                    }
                    user.userName(userName);
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return user.build();
    }

    private static Coordinates parseHomeCoordinates(JsonReader r) throws IOException {
        if (JsonParserUtil.isNextNull(r)) {
            return null;
        }

        Coordinates.Builder coordinates = Coordinates.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "Latitude":
                    coordinates.latitude((float) r.nextDouble());
                    break;
                case "Longitude":
                    coordinates.longitude((float) r.nextDouble());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();
        return coordinates.build();
    }
}
