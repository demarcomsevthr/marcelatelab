package com.googlecode.gwtphonegap.client.file.browser;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class FileErrorException_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native int getErrorCode(com.googlecode.gwtphonegap.client.file.browser.FileErrorException instance) /*-{
    return instance.@com.googlecode.gwtphonegap.client.file.browser.FileErrorException::errorCode;
  }-*/;
  
  private static native void setErrorCode(com.googlecode.gwtphonegap.client.file.browser.FileErrorException instance, int value) 
  /*-{
    instance.@com.googlecode.gwtphonegap.client.file.browser.FileErrorException::errorCode = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.googlecode.gwtphonegap.client.file.browser.FileErrorException instance) throws SerializationException {
    setErrorCode(instance, streamReader.readInt());
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static com.googlecode.gwtphonegap.client.file.browser.FileErrorException instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.googlecode.gwtphonegap.client.file.browser.FileErrorException();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.googlecode.gwtphonegap.client.file.browser.FileErrorException instance) throws SerializationException {
    streamWriter.writeInt(getErrorCode(instance));
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.googlecode.gwtphonegap.client.file.browser.FileErrorException_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.googlecode.gwtphonegap.client.file.browser.FileErrorException_FieldSerializer.deserialize(reader, (com.googlecode.gwtphonegap.client.file.browser.FileErrorException)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.googlecode.gwtphonegap.client.file.browser.FileErrorException_FieldSerializer.serialize(writer, (com.googlecode.gwtphonegap.client.file.browser.FileErrorException)object);
  }
  
}
