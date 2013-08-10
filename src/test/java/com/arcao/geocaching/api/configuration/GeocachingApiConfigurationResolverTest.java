package com.arcao.geocaching.api.configuration;

import org.junit.Assert;
import org.junit.Test;

import com.arcao.geocaching.api.configuration.resolver.GeocachingApiConfigurationResolver;

public class GeocachingApiConfigurationResolverTest {

  @Test
  public void resolveStringTest() {
    GeocachingApiConfiguration conf = GeocachingApiConfigurationResolver.resolve(GeocachingApiConfiguration.class, InnerConfigurationClass.class.getName());
    Assert.assertNotNull(conf);
    
    conf = GeocachingApiConfigurationResolver.resolve(GeocachingApiConfiguration.class, PublicConfigurationClass.class.getName());
    Assert.assertNotNull(conf);
  }
  
  @Test
  public void resolveClassTest() {
    GeocachingApiConfiguration conf = GeocachingApiConfigurationResolver.resolve(GeocachingApiConfiguration.class, InnerConfigurationClass.class);
    Assert.assertNotNull(conf);
    
    conf = GeocachingApiConfigurationResolver.resolve(GeocachingApiConfiguration.class, PublicConfigurationClass.class);
    Assert.assertNotNull(conf);
  }
  
  protected static class InnerConfigurationClass implements OAuthGeocachingApiConfiguration {
    public String getApiServiceEntryPointUrl() {
      return null;
    }

    public String getConsumerKey() {
      return null;
    }

    public String getConsumerSecret() {
      return null;
    }

    public String getOAuthAuthorizeUrl() {
      return null;
    }

    public String getOAuthRequestUrl() {
      return null;
    }

    public String getOAuthAccessUrl() {
      return null;
    }

		public int getConnectTimeout() {
			return 0;
		}

		public int getReadTimeout() {
			return 0;
		}
    
  }
}