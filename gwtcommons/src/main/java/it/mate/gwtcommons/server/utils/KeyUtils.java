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
  
}
