package it.mate.econyx.server.services;

import java.io.OutputStream;
import java.util.Map;

public interface TemplatesAdapter {
  
  public String processTemplateToString(String templateName, String templateText, Map<String, Object> model) throws Exception;
  
  public OutputStream processTemplateToOutputStream(String templateName, String templateText, Map<String, Object> model) throws Exception;

}
