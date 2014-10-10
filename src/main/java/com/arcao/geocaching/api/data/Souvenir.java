package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class Souvenir implements Serializable {
	private static final long serialVersionUID = -1185578859898344877L;

	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}
}
