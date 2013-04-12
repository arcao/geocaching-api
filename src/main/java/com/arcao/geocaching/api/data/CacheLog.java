package com.arcao.geocaching.api.data;

import java.lang.reflect.Method;
import java.util.Date;

import com.arcao.geocaching.api.data.type.CacheLogType;

/**
 * CacheLog class keep all information cache log.
 * 
 * @author arcao
 * 
 */
public class CacheLog {
	private final long id;
	private final Date visited;
	private final Date created;
	private final CacheLogType cacheLogType;
	private final User author;
	private final String text;
	private final String cacheCode;

	/**
	 * Create a new instance of cache log and fill it with data
	 * @param id unique identificator
	 * @param cacheCode the gccode of the cache
	 * @param created date when the cache log was created (logged) on Geocaching site
	 * @param visited date when the cache was visited (found)
	 * @param cacheLogType type of log
	 * @param author author of log
	 * @param text text of log
	 */
	public CacheLog(long id, String cacheCode, Date created, Date visited, CacheLogType cacheLogType, User author, String text) {
		this.id = id;
		this.created = created;
		this.visited = visited;
		this.cacheLogType = cacheLogType;
		this.author = author;
		this.text = text;
		this.cacheCode=cacheCode;
	}

	/**
	 * Get a unique identificator of the cache log
	 * @return unique identifiactor
	 */
	public long getId() {
	  return id;
  }

	/**
	 * Get a date when the cache log was created (logged) on Geocaching site
	 * @return date
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Get a date when the cache was visited (found)
	 * @return date
	 */
	public Date getVisited() {
		return visited;
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
	public User getAuthor() {
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
			} catch (Exception e) {}
			sb.append(", ");
		}
		return sb.toString();
	}

	public CacheLogType getCacheLogType() {
		return cacheLogType;
	}

	public String getCacheCode() {
		return cacheCode;
	}
}
