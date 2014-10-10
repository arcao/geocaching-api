package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class ProfilePhoto implements Serializable {
	private static final long serialVersionUID = 5557754921065357998L;

	private final String photoDescription;
	private final String photoFilename;
	private final String photoName;
	private final String photoUrl;
	
	public ProfilePhoto(String photoDescription, String photoFilename, String photoName, String photoUrl) {
    this.photoDescription = photoDescription;
    this.photoFilename = photoFilename;
    this.photoName = photoName;
    this.photoUrl = photoUrl;
  }

	public String getPhotoDescription() {
  	return photoDescription;
  }

	public String getPhotoFilename() {
  	return photoFilename;
  }

	public String getPhotoName() {
  	return photoName;
  }

	public String getPhotoUrl() {
  	return photoUrl;
  }
	
	@Override
	public String toString() {
    return DebugUtils.toString(this);
	}
}
