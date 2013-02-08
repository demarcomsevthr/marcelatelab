package it.mate.gwtcommons.client.mvp;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class MvpException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, it.mate.gwtcommons.client.mvp.MvpException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.RuntimeException_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static it.mate.gwtcommons.client.mvp.MvpException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.mate.gwtcommons.client.mvp.MvpException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.gwtcommons.client.mvp.MvpException instance) throws SerializationException {
    
    com.google.gwt.user.client.rpc.core.java.lang.RuntimeException_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.gwtcommons.client.mvp.MvpException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.gwtcommons.client.mvp.MvpException_FieldSerializer.deserialize(reader, (it.mate.gwtcommons.client.mvp.MvpException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.gwtcommons.client.mvp.MvpException_FieldSerializer.serialize(writer, (it.mate.gwtcommons.client.mvp.MvpException)object);
  }
  
}
