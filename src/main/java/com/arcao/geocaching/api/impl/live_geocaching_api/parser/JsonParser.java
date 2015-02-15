package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.data.type.TrackableLogType;
import com.google.gson.stream.JsonToken;

public class JsonParser {
	private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);
	
	protected static Date parseJsonDate(String date) {
		Pattern DATE_PATTERN = Pattern.compile("/Date\\((-?\\d+)([-+]\\d{4})?\\)/");

    if (date == null)
      return null;

		Matcher m = DATE_PATTERN.matcher(date);
		if (m.matches()) {
			long time = Long.parseLong(m.group(1));
			long zone = 0;
			if (m.group(2) != null && m.group(2).length() > 0)
				zone = Integer.parseInt(m.group(2)) / 100 * 1000 * 60 * 60;
			return new Date(time + zone);
		}

		logger.error("parseJsonDate failed: " + date);
		return null;
	}
	
	protected static Date parseJsonUTCDate(String date) {
		Pattern DATE_PATTERN = Pattern.compile("/Date\\((-?\\d+)([-+]\\d{4})?\\)/");

    if (date == null)
      return null;

		Matcher m = DATE_PATTERN.matcher(date);
		if (m.matches()) {
			long time = Long.parseLong(m.group(1));
			// zone is always zero for UTC
			long zone = 0;
			return new Date(time + zone);
		}

		logger.error("parseJsonDate failed: " + date);
		return null;
	}
	
	protected static CacheType parseCacheType(JsonReader r) throws IOException {
		CacheType cacheType = null;
		
		if (isNextNull(r))
			return cacheType;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("GeocacheTypeId".equals(name)) {
				cacheType = CacheType.getById(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return cacheType;
	}
	
	protected static ContainerType parseContainerType(JsonReader r) throws IOException {
		ContainerType containerType = ContainerType.NotChosen;
		
		if (isNextNull(r))
			return containerType;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("ContainerTypeId".equals(name)) {
				containerType = ContainerType.getById(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return containerType;
	}
	
	protected static MemberType parseMemberType(JsonReader r) throws IOException {
		MemberType memberType = null;

		if (isNextNull(r))
			return memberType;
		
		if (r.peek() == JsonToken.NUMBER) {
		  memberType = MemberType.getById(r.nextInt() / 10);
		  return memberType;
    }
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("MemberTypeId".equals(name)) {
				memberType = MemberType.getById(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return memberType;
	}
	
	protected static Coordinates parseHomeCoordinates(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;

    Coordinates.Builder coordinates = Coordinates.Builder.coordinates();

		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("Latitude".equals(name)) {
        coordinates.withLatitude((float) r.nextDouble());
			} else if ("Longitude".equals(name)) {
        coordinates.withLongitude((float) r.nextDouble());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return coordinates.build();
	}
	
	protected static User parseUser(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;

    User.Builder user = User.Builder.user();

		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("AvatarUrl".equals(name)) {
				user.withAvatarUrl(r.nextString());
			} else if ("FindCount".equals(name)) {
        user.withFindCount(r.nextInt());
			} else if ("HideCount".equals(name)) {
        user.withHideCount(r.nextInt());
			} else if ("HomeCoordinates".equals(name)) {
        user.withHomeCoordinates(parseHomeCoordinates(r));
			} else if ("Id".equals(name)) {
        user.withId(r.nextLong());
			} else if ("IsAdmin".equals(name)) {
        user.withAdmin(r.nextBoolean());
			} else if ("MemberType".equals(name)) {
        user.withMemberType(parseMemberType(r));
			} else if ("PublicGuid".equals(name)) {
        user.withPublicGuid(r.nextString());
			} else if ("UserName".equals(name)) {
        user.withUserName(r.nextString());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return user.build();
	}
	
	protected static AttributeType parseAttribute(JsonReader r) throws IOException {
		int id = 1;
		boolean on = false;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("AttributeTypeID".equals(name)) {
				id = r.nextInt();
			} else if ("IsOn".equals(name)) {
				on = r.nextBoolean();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return AttributeType.getById(id, on);
	}
	
	protected static EnumSet<AttributeType> parseAttributeList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}

    EnumSet<AttributeType> attributeSet = EnumSet.noneOf(AttributeType.class);
		r.beginArray();
		while(r.hasNext()) {
      AttributeType attribute = parseAttribute(r);
      if (attribute != null) {
        attributeSet.add(attribute);
      }
		}
		r.endArray();
		return attributeSet;
	}
	
	protected static TrackableLogType parseTrackableLogType(JsonReader r) throws IOException {
    TrackableLogType trackableLogType = null;
    
		if (isNextNull(r))
			return trackableLogType;
    
    r.beginObject();
    while(r.hasNext()) {
      String name = r.nextName();
      if ("WptLogTypeId".equals(name)) {
        trackableLogType = TrackableLogType.getById(r.nextInt());
      } else {
        r.skipValue();
      }
    }
    r.endObject();
    return trackableLogType;
  }
	
	protected static boolean isNextNull(JsonReader r) throws IOException {
		if (r.peek() == JsonToken.NULL) {
			r.nextNull();
			return true;
		}
		
		return false;
	}
}
