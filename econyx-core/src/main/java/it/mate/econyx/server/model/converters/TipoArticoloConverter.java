package it.mate.econyx.server.model.converters;

import it.mate.econyx.shared.model.TipoArticolo;
import it.mate.econyx.shared.model.impl.TipoArticoloTx;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TipoArticoloConverter implements Converter {

  @Override
  public boolean canConvert(Class type) {
    return type.equals(TipoArticoloTx.class);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    TipoArticolo productType = (TipoArticolo)source;
    writer.startNode("codice");
    writer.setValue(productType.getCodice());
    writer.endNode();
    writer.startNode("codiceInterno");
    writer.setValue(""+productType.getCodiceInterno());
    writer.endNode();
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    TipoArticolo productType = new TipoArticoloTx();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if ("codice".equals(reader.getNodeName())) {
        productType.setCodice(reader.getValue());
      } else if ("codiceInterno".equals(reader.getNodeName())) {
        productType.setCodiceInterno(TipoArticolo.Decoder.valueOfCode(reader.getValue()));
      }
      reader.moveUp();
    }
    return productType;
  }
  
}
