package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

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
    return DebugUtils.toString(this);
	}
}
