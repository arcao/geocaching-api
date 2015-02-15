package com.arcao.geocaching.api.data.type;

public enum MemberType {
	Guest("Guest", 0),
	Basic("Basic", 1),
	Charter("Charter", 2),
	Premium("Premium", 3);

	private final String name;
	private final int id;
	
	private MemberType(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public static MemberType getById(int id) {
		for (MemberType memberType : values()) {
			if (memberType.id == id)
				return memberType;
		}
		
		return Guest;
	}
}
