package it.mate.econyx.server.model.converters;

import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.impl.ImageContentTx;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ImageContentConverter implements Converter {

  @Override
  public boolean canConvert(Class type) {
    return type.equals(ImageContentTx.class);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    ImageContent imageContent = (ImageContent)source;
    writer.startNode("type");
    writer.setValue(imageContent.getType().getCode());
    writer.endNode();
    writer.startNode("content");
    writer.setValue(imageContent.getContentString());
    writer.endNode();
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    ImageContent imageContent = new ImageContentTx();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if ("type".equals(reader.getNodeName())) {
        imageContent.setType(ImageContent.Type.valueOfCode(reader.getValue()));
      } else if ("content".equals(reader.getNodeName())) {
        imageContent.setContentString(reader.getValue());
      }
      reader.moveUp();
    }
    return imageContent;
  }
  
}
