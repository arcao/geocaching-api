package com.arcao.geocaching.api.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonSerializable;
import com.arcao.geocaching.api.util.Base64;
import com.arcao.geocaching.api.util.Base64OutputStream;
import com.google.gson.stream.JsonWriter;

public class ImageData implements JsonSerializable {
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
    
    w.beginObject();
    w.name("FileCaption").value(name);
    w.name("FileDescription").value(description);
    w.name("FileName").value(fileName);
    w.name("base64ImageData").value(new String(imageData));
    w.endObject();
  } 
}