package it.mate.postscriptum.shared.model;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class StickMail_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, it.mate.postscriptum.shared.model.StickMail[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static it.mate.postscriptum.shared.model.StickMail[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new it.mate.postscriptum.shared.model.StickMail[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.postscriptum.shared.model.StickMail[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.postscriptum.shared.model.StickMail_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.StickMail_Array_Rank_1_FieldSerializer.deserialize(reader, (it.mate.postscriptum.shared.model.StickMail[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.StickMail_Array_Rank_1_FieldSerializer.serialize(writer, (it.mate.postscriptum.shared.model.StickMail[])object);
  }
  
}
