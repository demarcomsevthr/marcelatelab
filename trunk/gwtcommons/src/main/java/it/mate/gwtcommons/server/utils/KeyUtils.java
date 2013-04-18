package it.mate.gwtcommons.server.utils;

import java.io.Serializable;
import java.util.UUID;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyUtils {

  public static String getRandomUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString().replace("-", "");
  }
  
  public static Key serializableToKey(Serializable id) {
    if (id == null)
      return null;
    if (id instanceof Key) {
      return (Key)id;
    } else {
      return KeyFactory.stringToKey((String)id);
    }
  }
  
  public static String castToString(Object key) {
    if (key instanceof Key) {
      key = KeyFactory.keyToString((Key)key);
    }
    return key.toString();
  }
  
  public static Key castToKey(Object key) {
    if (key instanceof Key) {
      return (Key)key;
    } else if (key instanceof String) {
      return KeyFactory.stringToKey((String)key);
    }
    throw new IllegalArgumentException(""+key);
  }
  
  public static String formatToString(Object key) {
    if (key == null) {
      return "null!";
    }
    Key dsKey = null;
    if (key instanceof Key) {
      dsKey = (Key)key;
    } else {
      try {
        dsKey = KeyFactory.stringToKey((String)key);
      } catch (Exception ex) {  }
    }
    String textKey = null;
    if (dsKey != null) {
      textKey = dsKey.getKind()+".";
      if (dsKey.getName() == null) {
        textKey += dsKey.getId();
      } else {
        textKey += dsKey.getName();
      }
    } else {
      textKey = key.toString();
    }
    return textKey;
  }
  
}
