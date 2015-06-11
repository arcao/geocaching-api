package com.arcao.geocaching.api.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.arcao.geocaching.api.util.DebugUtils;

/**
 * GeocacheLog class keep all information cache log.
 * 
 * @author arcao
 * 
 */
public class GeocacheLog implements Serializable {
	private static final long serialVersionUID = 9088433857246687793L;

	private final long id;
	private final String cacheCode;
	private final Date visited;
	private final Date created;
	private final GeocacheLogType geocacheLogType;
	private final User author;
	private final String text;
	private final List<ImageData> images;
	private final Coordinates updatedCoordinates;
	private final boolean approved;
	private final boolean archived;
	private final boolean undeletable;

	/**
	 * Create a new instance of cache log and fill it with data
	 * @param id unique identificator
	 * @param cacheCode the gccode of the cache
	 * @param created date when the cache log was created (logged) on Geocaching site
	 * @param visited date when the cache was visited (found)
	 * @param geocacheLogType type of log
	 * @param author author of log
	 * @param text text of log
	 * @param updatedCoordinates updated coordinates
	 * @param approved true if log is approved
	 * @param archived true if log is archived
	 * @param undeletable true if log can not be deleted
	 */
	public GeocacheLog(long id, String cacheCode, Date created, Date visited, GeocacheLogType geocacheLogType, User author,
										 String text, List<ImageData> images, Coordinates updatedCoordinates, boolean approved, boolean archived, boolean undeletable) {
		this.id = id;
		this.cacheCode=cacheCode;
		this.created = created;
		this.visited = visited;
		this.geocacheLogType = geocacheLogType;
		this.author = author;
		this.text = text;
		this.images = images != null ? images : Collections.<ImageData>emptyList();
		this.updatedCoordinates = updatedCoordinates;
		this.approved = approved;
		this.archived = archived;
		this.undeletable = undeletable;
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
	public GeocacheLogType getLogType() {
		return geocacheLogType;
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

	/**
	 * Get an updated coordinates
	 * @return updated coordinates
	 */
	public Coordinates getUpdatedCoordinates() {
		return updatedCoordinates;
	}

	/**
	 * Return true if log is approved
	 * @return log approved?
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Return true if log is archived
	 * @return log archived?
	 */
	public boolean isArchived() {
		return archived;
	}

	/**
	 * Return true if log can not be deleted
	 * @return can not be deleted?
	 */
	public boolean isUndeletable() {
		return undeletable;
	}

	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}


	public static class Builder {
		private long id;
		private String cacheCode;
		private Date visited;
		private Date created;
		private GeocacheLogType geocacheLogType;
		private User author;
		private String text;
		private List<ImageData> images;
		private Coordinates updatedCoordinates;
		private boolean approved;
		private boolean archived;
		private boolean undeletable;

		private Builder() {
		}

		public static Builder geocacheLog() {
			return new Builder();
		}

		public Builder withId(long id) {
			this.id = id;
			return this;
		}

		public Builder withCacheCode(String cacheCode) {
			this.cacheCode = cacheCode;
			return this;
		}

		public Builder withVisited(Date visited) {
			this.visited = visited;
			return this;
		}

		public Builder withCreated(Date created) {
			this.created = created;
			return this;
		}

		public Builder withCacheLogType(GeocacheLogType geocacheLogType) {
			this.geocacheLogType = geocacheLogType;
			return this;
		}

		public Builder withAuthor(User author) {
			this.author = author;
			return this;
		}

		public Builder withText(String text) {
			this.text = text;
			return this;
		}

		public Builder withImages(List<ImageData> images) {
			this.images = images;
			return this;
		}

		public Builder withUpdatedCoordinates(Coordinates updatedCoordinates) {
			this.updatedCoordinates = updatedCoordinates;
			return this;
		}

		public Builder withApproved(boolean approved) {
			this.approved = approved;
			return this;
		}

		public Builder withArchived(boolean archived) {
			this.archived = archived;
			return this;
		}

		public Builder withUndeletable(boolean undeletable) {
			this.undeletable = undeletable;
			return this;
		}

		public GeocacheLog build() {
			return new GeocacheLog(
							id,
							cacheCode,
							created,
							visited,
							geocacheLogType,
							author,
							text,
							images,
							updatedCoordinates,
							approved,
							archived,
							undeletable);
		}
	}
}
