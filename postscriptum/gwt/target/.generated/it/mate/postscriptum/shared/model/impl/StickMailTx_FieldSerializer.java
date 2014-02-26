package it.mate.postscriptum.shared.model.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class StickMailTx_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getBody(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::body;
  }-*/;
  
  private static native void setBody(it.mate.postscriptum.shared.model.impl.StickMailTx instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::body = value;
  }-*/;
  
  private static native java.util.Date getCreated(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::created;
  }-*/;
  
  private static native void setCreated(it.mate.postscriptum.shared.model.impl.StickMailTx instance, java.util.Date value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::created = value;
  }-*/;
  
  private static native java.lang.String getId(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::id;
  }-*/;
  
  private static native void setId(it.mate.postscriptum.shared.model.impl.StickMailTx instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::id = value;
  }-*/;
  
  private static native java.util.Date getScheduled(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::scheduled;
  }-*/;
  
  private static native void setScheduled(it.mate.postscriptum.shared.model.impl.StickMailTx instance, java.util.Date value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::scheduled = value;
  }-*/;
  
  private static native java.lang.String getState(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::state;
  }-*/;
  
  private static native void setState(it.mate.postscriptum.shared.model.impl.StickMailTx instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::state = value;
  }-*/;
  
  private static native java.lang.String getSubject(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::subject;
  }-*/;
  
  private static native void setSubject(it.mate.postscriptum.shared.model.impl.StickMailTx instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::subject = value;
  }-*/;
  
  private static native it.mate.postscriptum.shared.model.RemoteUser getUser(it.mate.postscriptum.shared.model.impl.StickMailTx instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::user;
  }-*/;
  
  private static native void setUser(it.mate.postscriptum.shared.model.impl.StickMailTx instance, it.mate.postscriptum.shared.model.RemoteUser value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.impl.StickMailTx::user = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.mate.postscriptum.shared.model.impl.StickMailTx instance) throws SerializationException {
    setBody(instance, streamReader.readString());
    setCreated(instance, (java.util.Date) streamReader.readObject());
    setId(instance, streamReader.readString());
    setScheduled(instance, (java.util.Date) streamReader.readObject());
    setState(instance, streamReader.readString());
    setSubject(instance, streamReader.readString());
    setUser(instance, (it.mate.postscriptum.shared.model.RemoteUser) streamReader.readObject());
    
  }
  
  public static it.mate.postscriptum.shared.model.impl.StickMailTx instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.mate.postscriptum.shared.model.impl.StickMailTx();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.postscriptum.shared.model.impl.StickMailTx instance) throws SerializationException {
    streamWriter.writeString(getBody(instance));
    streamWriter.writeObject(getCreated(instance));
    streamWriter.writeString(getId(instance));
    streamWriter.writeObject(getScheduled(instance));
    streamWriter.writeString(getState(instance));
    streamWriter.writeString(getSubject(instance));
    streamWriter.writeObject(getUser(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.postscriptum.shared.model.impl.StickMailTx_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.impl.StickMailTx_FieldSerializer.deserialize(reader, (it.mate.postscriptum.shared.model.impl.StickMailTx)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.impl.StickMailTx_FieldSerializer.serialize(writer, (it.mate.postscriptum.shared.model.impl.StickMailTx)object);
  }
  
}
