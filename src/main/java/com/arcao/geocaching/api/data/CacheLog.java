package com.arcao.geocaching.api.data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.type.CacheLogType;

/**
 * CacheLog class keep all information cache log.
 * 
 * @author arcao
 * 
 */
public class CacheLog implements Serializable {
	private static final long serialVersionUID = 9088433857246687793L;

	private final long id;
	private final String cacheCode;
	private final Date visited;
	private final Date created;
	private final CacheLogType cacheLogType;
	private final User author;
	private final String text;
	private final List<ImageData> images;

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
	public CacheLog(long id, String cacheCode, Date created, Date visited, CacheLogType cacheLogType, User author, String text, List<ImageData> images) {
		this.id = id;
		this.cacheCode=cacheCode;
		this.created = created;
		this.visited = visited;
		this.cacheLogType = cacheLogType;
		this.author = author;
		this.text = text;
		this.images = images;
	}

	/**
	 * Get a unique identificator of the cache log
	 * @return unique identifiactor
	 */
	public long getId() {
	  return id;
  }

	/**
	 * Get a gccode of the cache
	 * @return gccode of the cache
	 */
	public String getCacheCode() {
		return cacheCode;
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
	
	/**
	 * Get the images attached to log 
	 * @return images
	 */
	public List<ImageData> getImages() {
    return images;
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
