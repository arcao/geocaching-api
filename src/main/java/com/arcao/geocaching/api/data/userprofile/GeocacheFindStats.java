package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class GeocacheFindStats implements Serializable {
	private static final long serialVersionUID = -3901690388175569693L;

	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}
}
