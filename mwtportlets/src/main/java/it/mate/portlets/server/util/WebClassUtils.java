package it.mate.portlets.server.util;

import java.util.ArrayList;
import java.util.List;

public class WebClassUtils {
  
  private static List<ClassLoader> webClassloaders = new ArrayList<ClassLoader>();
  
  public static void addClassLoader(Class<?> webClass) {
    ClassLoader classLoader = webClass.getClassLoader();
    if (!webClassloaders.contains(classLoader)) {
      webClassloaders.add(classLoader);
    }
  }
  
  public static List<ClassLoader> getWebClassLoaders() {
    return webClassloaders;
  }
  
  private static List<Class<?>> widgetFactoryClasses = new ArrayList<Class<?>>();
  
  public static void addWidgetFactoryClass(Class<?> wfClass) {
    if (!widgetFactoryClasses.contains(wfClass)) {
      widgetFactoryClasses.add(wfClass);
    }
  }
  public static List<Class<?>> getWidgetFactoryClasses() {
    return widgetFactoryClasses;
  }

}
