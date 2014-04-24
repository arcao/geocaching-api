package com.arcao.geocaching.api.data.userprofile;

import java.io.Serializable;
import java.lang.reflect.Method;

public class GeocacheFindStats implements Serializable {
	private static final long serialVersionUID = -3901690388175569693L;

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
			} catch (Exception e) {}
			sb.append(", ");
		}
		return sb.toString();
	}
}
