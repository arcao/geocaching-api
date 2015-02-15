package com.arcao.geocaching.api.data.type;

public enum ContainerType {
	NotChosen("Not chosen", 1),
	Micro("Micro", 2),
	Small("Small", 8),
	Regular("Regular", 3),
	Large("Large", 4),
	Huge("Huge", 5),
	Other("Other", 6);

	private final String name;
	private final int id;

	private ContainerType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	public static ContainerType getByName(String name) {
		for (ContainerType type : values()) {
			if (type.name.equals(name))
				return type;
		}

		return Other;
	}
	
	public static ContainerType getById(int id) {
		for (ContainerType type : values()) {
			if (type.id == id)
				return type;
		}

		return Other;
	}
}
