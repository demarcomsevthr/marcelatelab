package it.mate.gwtcommons.shared.services;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class ServiceException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, it.mate.gwtcommons.shared.services.ServiceException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.RuntimeException_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static it.mate.gwtcommons.shared.services.ServiceException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.mate.gwtcommons.shared.services.ServiceException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.gwtcommons.shared.services.ServiceException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.RuntimeException_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.gwtcommons.shared.services.ServiceException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.gwtcommons.shared.services.ServiceException_FieldSerializer.deserialize(reader, (it.mate.gwtcommons.shared.services.ServiceException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.gwtcommons.shared.services.ServiceException_FieldSerializer.serialize(writer, (it.mate.gwtcommons.shared.services.ServiceException)object);
  }
  
}
