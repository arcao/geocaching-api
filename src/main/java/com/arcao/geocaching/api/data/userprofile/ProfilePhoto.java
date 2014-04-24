package com.arcao.geocaching.api.data.userprofile;

import java.io.Serializable;
import java.lang.reflect.Method;

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
