package com.arcao.geocaching.api.data.type;

public enum ContainerType {
	NotChosen("Not chosen", 1),
	Micro("Micro", 2),
	Small("Small", 8),
	Regular("Regular", 3),
	Large("Large", 4),
	Huge("Huge", 5),
	Other("Other", 6);

	/** Friendly name */
	public final String name;
	/** Groundspeak Id */
	public final int id;

	ContainerType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Get an ContainerType from Friendly name. If Friendly name is not valid, the Other value is returned.
	 * @param name Friendly name
	 * @return Container Type
	 */
	public static ContainerType fromName(String name) {
		for (ContainerType type : values()) {
			if (type.name.equals(name))
				return type;
		}

		return Other;
	}

	/**
	 * Get an ContainerType from Groundspeak Id. If Groundspeak Id is not valid, the Other value is returned.
	 * @param id Groundspeak Id
	 * @return	Container Type
   */
	public static ContainerType fromId(int id) {
		for (ContainerType type : values()) {
			if (type.id == id)
				return type;
		}

		return Other;
	}
}
