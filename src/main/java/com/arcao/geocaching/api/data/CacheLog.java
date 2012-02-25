package com.arcao.geocaching.api.data;


import java.lang.reflect.Method;
import java.util.Date;

import com.arcao.geocaching.api.data.type.CacheLogType;

/**
 * CacheLog class keep all information cache log.
 * @author arcao
 *
 */
public class CacheLog {
	private final Date date;
	private final CacheLogType cacheLogType;
	private final String author;
	private final String text;

	/**
	 * Create a new instance of cache log and fill it with data 
	 * @param date date when the cache was found
	 * @param cacheLogType type of log
	 * @param author author of log
	 * @param text text of log
	 */
	public CacheLog(Date date, CacheLogType cacheLogType, String author, String text) {
		this.date = date;
		this.cacheLogType = cacheLogType;
		this.author = author;
		this.text = text;
	}

	/**
	 * Get a date when the cache was found
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Get a type of log
	 * @return type of log
	 */
	public CacheLogType getLogType() {
		return cacheLogType;
	}

	/**
	 * Get an author of log
	 * @return author of log
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Get a text of log
	 * @return text of log
	 */
	public String getText() {
		return text;
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
