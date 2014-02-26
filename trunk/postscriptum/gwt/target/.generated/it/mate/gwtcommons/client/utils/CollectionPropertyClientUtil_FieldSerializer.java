package it.mate.gwtcommons.client.utils;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class CollectionPropertyClientUtil_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil instance) throws SerializationException {
    
  }
  
  public static it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil instance) throws SerializationException {
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil_FieldSerializer.deserialize(reader, (it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil_FieldSerializer.serialize(writer, (it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil)object);
  }
  
}
