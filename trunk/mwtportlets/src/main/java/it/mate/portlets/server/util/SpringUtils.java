package it.mate.portlets.server.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;

public class SpringUtils {

  public static ApplicationContext getStringApplicationContext(String definitionText) {
    ByteArrayResource resource = new ByteArrayResource(definitionText.getBytes());
    GenericXmlApplicationContext context = new GenericXmlApplicationContext(resource);
    return context;
  }
  
}
