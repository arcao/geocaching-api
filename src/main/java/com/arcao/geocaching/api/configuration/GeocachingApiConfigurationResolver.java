package com.arcao.geocaching.api.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Resolver implementation for configuration classes
 * @author arcao
 *
 */
public class GeocachingApiConfigurationResolver {
  private static final Logger logger = Logger.getLogger(GeocachingApiConfigurationResolver.class);

  protected static Map<String, GeocachingApiConfiguration> configurationMap;

  /**
   * Resolve a configuration represented by implementationClass, store it to the
   * cache and returns it.<br>
   * <br>
   * The implementation class must meet this criteria:
   * <ul>
   * <li>Must implement interfaceClass
   * <li>Can't be an interface
   * <li>Can't be an abstract class
   * <li>Can't be a non-static inner class
   * </ul>
   * 
   * @param interfaceClass
   *          interface class
   * @param implementationClass
   *          implementation class
   * @return instance of implementationClass represent by interfaceClass
   * @throws IllegalArgumentException
   *           If the implementation class does not meet the specified criteria
   */
  public static <C extends GeocachingApiConfiguration, D extends C> C resolve(
      Class<C> interfaceClass, Class<D> implementationClass) {
    return resolve(interfaceClass, implementationClass.getName());
  }

  /**
   * Resolve a configuration represented by implementationClass, store it to the
   * cache and returns it.<br>
   * <br>
   * The implementation class must meet this criteria:
   * <ul>
   * <li>Must implement interfaceClass
   * <li>Can't be an interface
   * <li>Can't be an abstract class
   * <li>Can't be a non-static inner class
   * </ul>
   * 
   * @param interfaceClass
   *          interface class
   * @param implementationClass
   *          implementation class
   * @return instance of implementationClass represent by interfaceClass
   * @throws IllegalArgumentException
   *           If the implementation class does not meet the specified criteria
   */
  @SuppressWarnings("unchecked")
  public static <C extends GeocachingApiConfiguration> C resolve(
      Class<C> interfaceClass, String implementationClass) {
    try {
      if (configurationMap == null)
        configurationMap = new HashMap<String, GeocachingApiConfiguration>();

      if (configurationMap.containsKey(implementationClass)) {
        GeocachingApiConfiguration configuration = configurationMap.get(implementationClass);

        if (!interfaceClass.isAssignableFrom(configuration.getClass())) {
          throw new IllegalArgumentException("Implementation class "
              + configuration.getClass().getName()
              + " does not extend / implement " + interfaceClass.getName());
        }

        return (C) configurationMap.get(implementationClass);
      }

      Class<?> clazz = Class.forName(implementationClass);
      checkClass(interfaceClass, clazz);

      Constructor<?> constructor;
      try {
        constructor = clazz.getDeclaredConstructor();
      } catch (NoSuchMethodException e) {
        throw new IllegalArgumentException(
            "No default constructor found in a class " + implementationClass, e);
      }

      GeocachingApiConfiguration configuration = (GeocachingApiConfiguration) constructor.newInstance();
      configurationMap.put(implementationClass, configuration);
      return (C) configuration;
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return null;
    }
  }

  private static void checkClass(Class<?> interfaceClass,
      Class<?> implementationClass) {
    if (Modifier.isInterface(implementationClass.getModifiers())) {
      throw new IllegalArgumentException("Implementation class "
          + implementationClass.getName() + " can't be a interface");
    }

    if (Modifier.isAbstract(implementationClass.getModifiers())) {
      throw new IllegalArgumentException("Implementation class "
          + implementationClass.getName() + " can't be an abstract class");
    }

    if (implementationClass.getEnclosingClass() != null
        && !Modifier.isStatic(implementationClass.getModifiers())) {
      throw new IllegalArgumentException("Implementation class "
          + implementationClass.getName() + " can't be non-static inner class");
    }

    if (!interfaceClass.isAssignableFrom(implementationClass)) {
      throw new IllegalArgumentException("Implementation class "
          + implementationClass.getName() + " does not extend / implement "
          + interfaceClass.getName());
    }
  }
}
