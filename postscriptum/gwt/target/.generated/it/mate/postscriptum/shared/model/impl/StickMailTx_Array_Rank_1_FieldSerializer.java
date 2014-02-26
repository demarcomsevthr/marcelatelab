package it.mate.postscriptum.shared.model.impl;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class StickMailTx_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, it.mate.postscriptum.shared.model.impl.StickMailTx[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static it.mate.postscriptum.shared.model.impl.StickMailTx[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new it.mate.postscriptum.shared.model.impl.StickMailTx[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, it.mate.postscriptum.shared.model.impl.StickMailTx[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return it.mate.postscriptum.shared.model.impl.StickMailTx_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.impl.StickMailTx_Array_Rank_1_FieldSerializer.deserialize(reader, (it.mate.postscriptum.shared.model.impl.StickMailTx[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    it.mate.postscriptum.shared.model.impl.StickMailTx_Array_Rank_1_FieldSerializer.serialize(writer, (it.mate.postscriptum.shared.model.impl.StickMailTx[])object);
  }
  
}
