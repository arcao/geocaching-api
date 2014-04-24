package com.arcao.geocaching.api.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonSerializable;
import com.arcao.geocaching.api.util.Base64;
import com.arcao.geocaching.api.util.Base64OutputStream;
import com.google.gson.stream.JsonWriter;

public class ImageData implements JsonSerializable, Serializable {
	private static final long serialVersionUID = 1116404414881607691L;

	private final String description;
  private final String mobileUrl;
  private final String name;
  private final String thumbUrl;
  private final String url;
  private byte[] imageData = null;
  private String fileName;
  
  public ImageData(String description, String mobileUrl, String name, String thumbUrl, String url) {
    this.description = description;
    this.mobileUrl = mobileUrl;
    this.name = name;
    this.thumbUrl = thumbUrl;
    this.url = url;
  }
  
  public String getDescription() {
    return description;
  }
  
  public String getMobileUrl() {
    return mobileUrl;
  }
  
  public String getName() {
    return name;
  }
  
  public String getThumbUrl() {
    return thumbUrl;
  }
  
  public String getUrl() {
    return url;
  }
  
  /**
   * Return Base64 encoded data of local read image otherwise null if the image data belongs to image saved on internet.
   * @return Base 64 encoded image data
   */
  public byte[] getImageData() {
    return imageData;
  }
  
  public static ImageData fromInputStream(String description, String name, String fileName, InputStream is) throws IOException {
    
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Base64OutputStream b64os = new Base64OutputStream(bos, Base64.NO_CLOSE | Base64.NO_WRAP);
    
    int bytesReaded = 0;
    byte[] buffer = new byte[8192];
    while((bytesReaded = is.read(buffer)) != -1) {
      b64os.write(buffer, 0, bytesReaded);
    }
    b64os.flush();
    b64os.close();
    
    ImageData imageData = new ImageData(description, "", name, "", "");
    imageData.fileName = fileName;
    imageData.imageData = bos.toByteArray();
    
    return imageData;
  }
  
  public void writeJson(JsonWriter w) throws IOException {
    if (imageData == null)
      return;
    
    w.beginObject()
    	.name("FileCaption").value(name)
    	.name("FileDescription").value(description)
    	.name("FileName").value(fileName)
    	.name("base64ImageData").value(new String(imageData));
    w.endObject();
  }
  
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (final Method m : getClass().getMethods()) {
			if ((!m.getName().startsWith("get") && !m.getName().startsWith("is")) ||
					m.getParameterTypes().length != 0 ||
					void.class.equals(m.getReturnType()))
				continue;

			sb.append(m.getName());
			sb.append(':');
			try {
				sb.append(m.invoke(this, new Object[0]));
			} catch (final Exception e) {
			}
			sb.append(", ");
		}
		return sb.toString();
	}
}