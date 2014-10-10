package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class FavoritePointStats implements Serializable {
	private static final long serialVersionUID = -5759425542897341813L;

	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}
}
