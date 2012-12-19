package it.mate.econyx.server.model.converters;

import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.impl.HtmlContentTx;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class HtmlContentConverter implements Converter {

  @Override
  public boolean canConvert(Class type) {
    return type.equals(HtmlContentTx.class);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    HtmlContent htmlContent = (HtmlContent)source;
    writer.startNode("type");
    writer.setValue(htmlContent.getType().getCode());
    writer.endNode();
    writer.startNode("content");
    writer.setValue(htmlContent.getContent());
    writer.endNode();
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    HtmlContent htmlContent = new HtmlContentTx();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if ("type".equals(reader.getNodeName())) {
        htmlContent.setType(HtmlContent.Type.valueOfCode(reader.getValue()));
      } else if ("content".equals(reader.getNodeName())) {
        htmlContent.setContent(reader.getValue());
      }
      reader.moveUp();
    }
    return htmlContent;
  }
  
}
