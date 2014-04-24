package com.arcao.geocaching.api.data;


import java.io.Serializable;
import java.lang.reflect.Method;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.MemberType;

public class User implements Serializable {
	private static final long serialVersionUID = 1808891631464643511L;

	public static final User EMPTY = new User("", 0, 0, new Coordinates(Double.NaN, Double.NaN), 0, false, MemberType.Guest, "", "");
  
	private final String avatarUrl;
	private final int findCount;
	private final int hideCount;
	private final Coordinates homeCoordinates;
	private final long id;
	private final boolean admin;
	private final MemberType memberType;
	private final String publicGuid;
	private final String userName;
	
	public User(String avatarUrl, int findCount, int hideCount, Coordinates homeCoordinates, long id, boolean admin, MemberType memberType, String publicGuid,
			String userName) {
		this.avatarUrl = avatarUrl;
		this.findCount = findCount;
		this.hideCount = hideCount;
		this.homeCoordinates = homeCoordinates;
		this.id = id;
		this.admin = admin;
		this.memberType = memberType;
		this.publicGuid = publicGuid;
		this.userName = userName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public int getFindCount() {
		return findCount;
	}

	public int getHideCount() {
		return hideCount;
	}

	public Coordinates getHomeCoordinates() {
		return homeCoordinates;
	}

	public long getId() {
		return id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public String getPublicGuid() {
		return publicGuid;
	}

	public String getUserName() {
		return userName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Method m : getClass().getMethods()) {
			if ((!m.getName().startsWith("get") && !m.getName().startsWith("is")) ||
					m.getParameterTypes().length != 0 ||
					void.class.equals(m.getReturnType()))
				continue;

			sb.append(m.getName());
			sb.append(':');
			try {
				sb.append(m.invoke(this, new Object[0]));
			} catch (Exception e) {
			}
			sb.append(", ");
		}
		return sb.toString();
	}
}
