package it.mate.econyx.server.model.converters;

import it.mate.econyx.shared.model.impl.ArticoloTx;
import it.mate.econyx.shared.model.impl.ProductPageTx;
import it.mate.gwtcommons.server.utils.CloneUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class ProductPageConverter implements Converter {
  
  ReflectionConverter defaultConverter;

  public ProductPageConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
    super();
    this.defaultConverter = new ReflectionConverter(mapper, reflectionProvider);
  }

  @Override
  public boolean canConvert(Class type) {
    return ProductPageTx.class.isAssignableFrom(type);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    ProductPageTx clonedSource = CloneUtils.clone(source, ProductPageTx.class);
    
    ArticoloTx smallArticolo = new ArticoloTx();
    smallArticolo.setCodice(clonedSource.getEntity().getCodice());
    
    clonedSource.setEntity(smallArticolo);
    
    defaultConverter.marshal(clonedSource, writer, context);
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return defaultConverter.unmarshal(reader, context);
  }
  
}
