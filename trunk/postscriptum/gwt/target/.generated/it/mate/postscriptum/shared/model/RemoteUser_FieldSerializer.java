package it.mate.postscriptum.shared.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class RemoteUser_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getAuthDomain(it.mate.postscriptum.shared.model.RemoteUser instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.RemoteUser::authDomain;
  }-*/;
  
  private static native void setAuthDomain(it.mate.postscriptum.shared.model.RemoteUser instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.RemoteUser::authDomain = value;
  }-*/;
  
  private static native java.lang.String getEmail(it.mate.postscriptum.shared.model.RemoteUser instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.RemoteUser::email;
  }-*/;
  
  private static native void setEmail(it.mate.postscriptum.shared.model.RemoteUser instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.RemoteUser::email = value;
  }-*/;
  
  private static native java.lang.String getFederatedIdentity(it.mate.postscriptum.shared.model.RemoteUser instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.RemoteUser::federatedIdentity;
  }-*/;
  
  private static native void setFederatedIdentity(it.mate.postscriptum.shared.model.RemoteUser instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.RemoteUser::federatedIdentity = value;
  }-*/;
  
  private static native boolean getIsNullUser(it.mate.postscriptum.shared.model.RemoteUser instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.RemoteUser::isNullUser;
  }-*/;
  
  private static native void setIsNullUser(it.mate.postscriptum.shared.model.RemoteUser instance, boolean value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.RemoteUser::isNullUser = value;
  }-*/;
  
  private static native java.lang.String getNickname(it.mate.postscriptum.shared.model.RemoteUser instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.RemoteUser::nickname;
  }-*/;
  
  private static native void setNickname(it.mate.postscriptum.shared.model.RemoteUser instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.RemoteUser::nickname = value;
  }-*/;
  
  private static native java.lang.String getUserId(it.mate.postscriptum.shared.model.RemoteUser instance) /*-{
    return instance.@it.mate.postscriptum.shared.model.RemoteUser::userId;
  }-*/;
  
  private static native void setUserId(it.mate.postscriptum.shared.model.RemoteUser instance, java.lang.String value) 
  /*-{
    instance.@it.mate.postscriptum.shared.model.RemoteUser::userId = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, it.mate.postscriptum.shared.model.RemoteUser instance) throws SerializationException {
    setAuthDomain(instance, streamReader.readString());
    setEmail(instance, streamReader.readString());
    setFederatedIdentity(instance, streamReader.readString());
    setIsNullUser(instance, streamReader.readBoolean());
    setNickname(instance, streamReader.readString());
    setUserId(instance, streamReader.readString());
    
  }
  
  public static it.mate.postscriptum.shared.model.RemoteUser instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new it.mate.postscriptum.shared.model.RemoteUser();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.postscriptum.shared.model.RemoteUser instance) throws SerializationException {
    streamWriter.writeString(getAuthDomain(instance));
    streamWriter.writeString(getEmail(instance));
    streamWriter.writeString(getFederatedIdentity(instance));
    streamWriter.writeBoolean(getIsNullUser(instance));
    streamWriter.writeString(getNickname(instance));
    streamWriter.writeString(getUserId(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.postscriptum.shared.model.RemoteUser_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.RemoteUser_FieldSerializer.deserialize(reader, (it.mate.postscriptum.shared.model.RemoteUser)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.RemoteUser_FieldSerializer.serialize(writer, (it.mate.postscriptum.shared.model.RemoteUser)object);
  }
  
}
