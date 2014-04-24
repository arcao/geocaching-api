package com.arcao.geocaching.api.data.userprofile;

import java.io.Serializable;
import java.lang.reflect.Method;

public class GlobalStats implements Serializable {
	private static final long serialVersionUID = 7066712324435905861L;

	private final long accountsLogged;
	private final long activeCaches;
	private final long activeCountries;
	private final long newLog;
	
	public GlobalStats(long accountsLogged, long activeCaches, long activeCountries, long newLog) {
	  this.accountsLogged = accountsLogged;
	  this.activeCaches = activeCaches;
	  this.activeCountries = activeCountries;
	  this.newLog = newLog;
  }

	public long getAccountsLogged() {
  	return accountsLogged;
  }

	public long getActiveCaches() {
  	return activeCaches;
  }

	public long getActiveCountries() {
  	return activeCountries;
  }

	public long getNewLog() {
  	return newLog;
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
			} catch (Exception e) {}
			sb.append(", ");
		}
		return sb.toString();
	}
}
