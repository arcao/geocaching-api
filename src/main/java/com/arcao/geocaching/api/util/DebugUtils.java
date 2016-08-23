package com.arcao.geocaching.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class DebugUtils {
  private static final Logger logger = LoggerFactory.getLogger(DebugUtils.class);

  public static String toString(final Object o) {
    final StringBuilder sb = new StringBuilder();
    sb.append('{');

    for (final Method m : o.getClass().getMethods()) {
      String methodName = m.getName();

      if (m.getParameterTypes().length != 0)
        continue;

      if (methodName.equals("getClass") || methodName.equals("toString"))
        continue;

      if (void.class.equals(m.getReturnType()))
        continue;

      if (sb.length() > 1)
        sb.append(", ");

      sb.append(methodName).append(": ");

      try {
        sb.append(m.invoke(o, new Object[0]));
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }

    sb.append('}');
    return sb.toString();
  }
}
