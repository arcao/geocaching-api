package com.arcao.geocaching.api.data;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;

import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonSerializable;
import com.google.gson.stream.JsonWriter;

public class DeviceInfo implements JsonSerializable, Serializable {
	private static final long serialVersionUID = 7352443462642812711L;

	private final int applicationCurrentMemoryUsage;
	private final int applicationPeakMemoryUsage;
	private final String applicationSoftwareVersion;
	private final String deviceManufacturer;
	private final String deviceName;
	private final String deviceOperatingSystem;
	private final float deviceTotalMemoryInMB;
	private final String deviceUniqueId;
	private final String mobileHardwareVersion;
	private final String webBrowserVersion;
	
	public DeviceInfo(int applicationCurrentMemoryUsage, int applicationPeakMemoryUsage, String applicationSoftwareVersion, String deviceManufacturer, String deviceName, String deviceOperatingSystem, float deviceTotalMemoryInMB, String deviceUniqueId, String mobileHardwareVersion, String webBrowserVersion) {
	  this.applicationCurrentMemoryUsage = applicationCurrentMemoryUsage;
	  this.applicationPeakMemoryUsage = applicationPeakMemoryUsage;
	  this.applicationSoftwareVersion = applicationSoftwareVersion;
	  this.deviceManufacturer = deviceManufacturer;
	  this.deviceName = deviceName;
	  this.deviceOperatingSystem = deviceOperatingSystem;
	  this.deviceTotalMemoryInMB = deviceTotalMemoryInMB;
	  this.deviceUniqueId = deviceUniqueId;
	  this.mobileHardwareVersion = mobileHardwareVersion;
	  this.webBrowserVersion = webBrowserVersion;
  }

	public int getApplicationCurrentMemoryUsage() {
  	return applicationCurrentMemoryUsage;
  }

	public int getApplicationPeakMemoryUsage() {
  	return applicationPeakMemoryUsage;
  }

	public String getApplicationSoftwareVersion() {
  	return applicationSoftwareVersion;
  }

	public String getDeviceManufacturer() {
  	return deviceManufacturer;
  }

	public String getDeviceName() {
  	return deviceName;
  }

	public String getDeviceOperatingSystem() {
  	return deviceOperatingSystem;
  }

	public float getDeviceTotalMemoryInMB() {
  	return deviceTotalMemoryInMB;
  }

	public String getDeviceUniqueId() {
  	return deviceUniqueId;
  }

	public String getMobileHardwareVersion() {
  	return mobileHardwareVersion;
  }

	public String getWebBrowserVersion() {
  	return webBrowserVersion;
  }

	public void writeJson(JsonWriter w) throws IOException {
	  w.beginObject();
	  
	  if (applicationCurrentMemoryUsage > 0)
	  	w.name("ApplicationCurrentMemoryUsage").value(applicationCurrentMemoryUsage);	
	  if (applicationPeakMemoryUsage > 0)
	  	w.name("ApplicationPeakMemoryUsage").value(applicationPeakMemoryUsage);
	  if (applicationSoftwareVersion != null)
	  	w.name("ApplicationSoftwareVersion").value(applicationSoftwareVersion);
	  if (deviceManufacturer != null)
	  	w.name("DeviceManufacturer").value(deviceManufacturer);
	  if (deviceName != null)
	  	w.name("DeviceName").value(deviceName);
	  if (deviceOperatingSystem != null)
	  	w.name("DeviceOperatingSystem").value(deviceOperatingSystem);
	  if (deviceTotalMemoryInMB > 0)
	  	w.name("DeviceTotalMemoryInMB").value(deviceTotalMemoryInMB);
	  if (deviceUniqueId != null)
	  	w.name("DeviceUniqueId").value(deviceUniqueId);
	  if (mobileHardwareVersion != null)
	  	w.name("MobileHardwareVersion").value(mobileHardwareVersion);
	  if (webBrowserVersion != null)
	  	w.name("WebBrowserVersion").value(webBrowserVersion);
	  
	  w.endObject();
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
