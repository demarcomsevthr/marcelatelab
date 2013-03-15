package it.mate.econyx.server.services.impl;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import it.mate.econyx.server.services.TemplatesAdapter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TemplatesAdapterImpl implements TemplatesAdapter {

  public String processTemplateToString(String templateName, String templateText, Map<String, Object> model) throws Exception {
    Template template = createTemplate(templateName, templateText);
    Writer writer = processTemplateToWriter(template, model);
    return writer.toString();
  }
  
  public OutputStream processTemplateToOutputStream(String templateName, String templateText, Map<String, Object> model) throws Exception {
    Template template = createTemplate(templateName, templateText);
    Writer writer = processTemplateToWriter(template, model);
    String buffer = writer.toString();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    baos.write(buffer.getBytes());
    return baos;
  }
  
  private Template createTemplate(String name, String text) throws Exception {
    Configuration cfg = new Configuration();
    cfg.setObjectWrapper(new DefaultObjectWrapper());
    Template template = new Template(name, new StringReader(text), cfg);
    return template;
  }
  
  private Writer processTemplateToWriter (Template template, Map<String, Object> model) throws Exception {
    StringWriter writer = new StringWriter();
    template.process(model, writer);
    writer.flush();
    return writer;
  }
  
}
