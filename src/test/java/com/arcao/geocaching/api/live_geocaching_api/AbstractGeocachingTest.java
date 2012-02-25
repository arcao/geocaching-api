package com.arcao.geocaching.api.live_geocaching_api;

import org.junit.BeforeClass;

import com.arcao.geocaching.api.GeocachingApi;
import com.arcao.geocaching.api.impl.LiveGeocachingApi;

public abstract class AbstractGeocachingTest {
  protected static GeocachingApi api = null;
  
  // Please do not use this keys in production. It's only for testing!!!
  protected static final String CONSUMER_KEY = "90C7F340-7998-477D-B4D3-AC48A9A0F560";
  protected static final String LICENCE_KEY = "40940392-0C8E-487B-BC40-EA250D6D9AE0";
  
  protected static final String ANONYMOUS_USER = "";
  protected static final String ANONYMOUS_PASSWORD = "";

  @BeforeClass
  public static void setUp() throws Exception {
    api = new LiveGeocachingApi(CONSUMER_KEY, LICENCE_KEY);
    
    String user = System.getProperty("gc.user", ANONYMOUS_USER);
    String password = System.getProperty("gc.password", ANONYMOUS_PASSWORD);
    
    api.openSession(user, password);
  }

}
