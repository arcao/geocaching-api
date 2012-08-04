package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.Souvenir;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.UserProfile;
import com.arcao.geocaching.api.data.userprofile.ChallengeStats;
import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheFindStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.ProfilePhoto;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;

public class UserProfileParser extends JsonParser {
	public static UserProfile parse(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;

		ChallengeStats challengesStats = null;
		FavoritePointStats favoritePointsStats = null;
		GeocacheFindStats geocacheFindStats = null;
		PublicProfile publicProfile = null;
		List<Souvenir> souvenirs = new ArrayList<Souvenir>();
		GlobalStats globalStats = null;
		TrackableStats trackableStats = null;
		User user = null;

		r.beginObject();
    while(r.hasNext()) {
      String name = r.nextName();
      if ("Challenges".equals(name)) {
      	challengesStats = parseChallengeStats(r);
      } else if ("FavoritePoints".equals(name)) {
      	favoritePointsStats = parseFavoritePointStats(r);
      } else if ("Geocaches".equals(name)) {
      	geocacheFindStats = parseGeocacheFindStats(r);
      } else if ("PublicProfile".equals(name)) {
      	publicProfile = parsePublicProfile(r);
      } else if ("Stats".equals(name)) {
      	globalStats = parseGlobalStats(r);
      } else if ("Trackables".equals(name)) {
      	trackableStats = parseTrackableStats(r);      	
      } else if ("User".equals(name)) {
        user = parseUser(r);
      } else {
        r.skipValue();
      }
    }
    r.endObject();

		
		return new UserProfile(challengesStats, favoritePointsStats, geocacheFindStats, publicProfile, souvenirs, globalStats, trackableStats, user);
	}
	
	protected static ChallengeStats parseChallengeStats(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;

		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
      r.skipValue();
    }
    r.endObject();
		
		return new ChallengeStats();
	}
	
	protected static FavoritePointStats parseFavoritePointStats(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;
		
		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
      r.skipValue();
    }
    r.endObject();
		
		return new FavoritePointStats();
	}
	
	protected static GeocacheFindStats parseGeocacheFindStats(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;
		
		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
      r.skipValue();
    }
    r.endObject();
		
		return new GeocacheFindStats();
	}
	
	protected static PublicProfile parsePublicProfile(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;
		
		String forumTitle = "";
		Date lastVisit = new Date(0);
		String location = "";
		Date memberSince = new Date(0); 
		String occupation = "";
		ProfilePhoto profilePhoto = null;
		String profileText = "";
		
		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
    	if ("ForumTitle".equals(name)) {
    		forumTitle = r.nextString();
    	} else if ("LastVisit".equals(name)) {
    		lastVisit = parseJsonDate(r.nextString());
    	} else if ("Location".equals(name)) {
    		location = r.nextString();
    	} else if ("MemberSince".equals(name)) {
    		memberSince = parseJsonDate(r.nextString());
    	} else if ("Occupation".equals(name)) {
    		occupation = r.nextString();
    	} else if ("ProfilePhoto".equals(name)) {
    		profilePhoto = parseProfilePhoto(r);
    	} else if ("ProfileText".equals(name)) {
    		profileText = r.nextString();
    	} else {
    		r.skipValue();
    	}
    }
    r.endObject();
		
		return new PublicProfile(forumTitle, lastVisit, location, memberSince, occupation, profilePhoto, profileText);
	}
		
	protected static ProfilePhoto parseProfilePhoto(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;

		String photoDescription = "";
		String photoFilename = "";
		String photoName = "";
		String photoUrl = "";
		
		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
    	if ("PhotoDescription".equals(name)) {
    		photoDescription = r.nextString();
    	} else if ("PhotoFilename".equals(name)) {
    		photoFilename = r.nextString();
    	} else if ("PhotoName".equals(name)) {
    		photoName = r.nextString();
    	} else if ("PhotoUrl".equals(name)) {
    		photoUrl = r.nextString();
    	} else {
    		r.skipValue();
    	}
    }
    r.endObject();
    
    return new ProfilePhoto(photoDescription, photoFilename, photoName, photoUrl);
  }

	protected static GlobalStats parseGlobalStats(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;
		
		long accountsLogged = 0;
		long activeCaches = 0;
		long activeCountries = 0;
		long newLog = 0;

		
		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
    	if ("AccountsLogged".equals(name)) {
    		accountsLogged = r.nextLong();
    	} else if ("ActiveCaches".equals(name)) {
    		activeCaches = r.nextLong();
    	} else if ("ActiveCountries".equals(name)) {
    		activeCountries = r.nextLong();
    	} else if ("NewLog".equals(name)) {
    		newLog = r.nextLong();
    	} else {
    		r.skipValue();
    	}
    }
    r.endObject();
		
		return new GlobalStats(accountsLogged, activeCaches, activeCountries, newLog);
	}
	
	protected static TrackableStats parseTrackableStats(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;
		
		int trackableFindCount = 0;
		List<Trackable> trackableFindTypes = new ArrayList<Trackable>();
		int trackableOwnedCount = 0;
		List<Trackable> trackableOwnedTypes = new ArrayList<Trackable>();
		
		r.beginObject();
    while(r.hasNext()) {
    	String name = r.nextName();
    	if ("TrackableFindCount".equals(name)) {
    		trackableFindCount = r.nextInt();
    	} else if ("TrackableFindTypes".equals(name)) {
    		trackableFindTypes = TrackableJsonParser.parseList(r);
    	} else if ("TrackableOwnedCount".equals(name)) {
    		trackableOwnedCount = r.nextInt();
    	} else if ("TrackableOwnedTypes".equals(name)) {
    		trackableOwnedTypes = TrackableJsonParser.parseList(r);
    	} else {
    		r.skipValue();
    	}
    }
    r.endObject();
		
		return new TrackableStats(trackableFindCount, trackableFindTypes, trackableOwnedCount, trackableOwnedTypes);
	}

}
