package it.mate.gwtcommons.server.utils;

import it.mate.gwtcommons.server.dao.JdoDao;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * 
 * @author marcello
 * 
 * 
 *
 * CHANGES LOG
 * 
 * 21/05/2012
 *   In memcache le entry vengono serializzate, se metto un ds perdo le proprietà transient (vedi unownedrelationships)
 *   quindi in cache ci devono finire i tx
 *   quindi nella put trasformo in tx class (definita sull'annotation) e mi salvo la classe del ds di provenienza
 *   per fare la trasformazione al contrario nella get
 * 
 *
 */


public class CacheUtils {
  
  private static Logger logger = Logger.getLogger(CacheUtils.class);
  
  private static Map<Object, Object> instanceCache;
  
  @SuppressWarnings("unchecked")
  public static Object get (Object key) {
    Object result = null;
    if (key != null) {
      result = getInstCache().get(keyToString(key));
      if (result == null) {
        logger.debug(String.format("INSTCACHE MISSED entity with key %s", formatKeyToString(key)));
        result = getMemCache().get(keyToString(key));
      }
      if (result == null) {
        logger.debug(String.format("MEMCACHE  MISSED entity with key %s", formatKeyToString(key)));
      }
      if (result != null && result instanceof CacheEntry) {
        CacheEntry cacheEntry = (CacheEntry)result;
        if (cacheEntry.dsClass != null) {
          result = CloneUtils.clone(cacheEntry.entity, cacheEntry.dsClass);
        }
      }
    }
    return result;
  }
  
  public static void put (Object entity) {
    if (entity != null && entity instanceof Serializable) {
      Serializable serializableEntity = (Serializable)entity;
      CacheableEntity cacheableEntityAnnotation = getCacheableEntityAnnotation(serializableEntity.getClass());
      if (entity instanceof HasKey && cacheableEntityAnnotation != null) {
        HasKey hasKeyEntity = (HasKey)entity;
        Class<?> txClass = cacheableEntityAnnotation.txClass();
        Object entityClone = entity;
        if (txClass != null) {
          entityClone = CloneUtils.clone(entity, txClass);
        }
        CacheEntry cacheEntry = new CacheEntry(entityClone, txClass, entity.getClass());
        if (cacheableEntityAnnotation.instanceCache()) {
          getInstCache().put(keyToString(hasKeyEntity.getKey()), cacheEntry);
        }
        // in mem cache sempre
        getMemCache().put(keyToString(hasKeyEntity.getKey()), cacheEntry);
      }
    }
  }
  
  public static void delete (Object entity) {
    if (entity != null && entity instanceof HasKey && entity.getClass().getAnnotation(CacheableEntity.class) != null) {
      try {
        HasKey hasKeyEntity = (HasKey)entity;
        deleteByKey(hasKeyEntity.getKey());
      } catch (Exception ex) {
        logger.error("error", ex);
      }
    }
  }
  
  public static void deleteByKey (Object key) {
    if (existsInInstCacheByKey(key)) {
      getInstCache().remove(keyToString(key));
    }
    if (existsInMemCacheByKey(key)) {
      getMemCache().delete(keyToString(key));
    }
  }
  
  private static boolean existsInMemCacheByKey (Object key) {
    return getMemCache().contains(keyToString(key));
  }
  
  private static boolean existsInInstCacheByKey (Object key) {
    return getInstCache().containsKey(keyToString(key));
  }
  
  public static void clearAll() {
    getInstCache().clear();
    getMemCache().clearAll();
  }
  
  public static CacheableEntity getCacheableEntityAnnotation(Class<? extends Serializable> entityClass) {
    CacheableEntity cacheableEntityAnnotation = entityClass.getAnnotation(CacheableEntity.class);
    if (cacheableEntityAnnotation == null) {
      List<Class<? extends Serializable>> subclasses = JdoDao.findSubClasses(entityClass);
      if (subclasses != null) {
        for (Class<? extends Serializable> subclass : subclasses) {
          CacheableEntity tmpCacheableEntityAnnotation = subclass.getAnnotation(CacheableEntity.class);
          if (tmpCacheableEntityAnnotation != null) {
            cacheableEntityAnnotation = tmpCacheableEntityAnnotation;
          }
        }
      }
    }
    return cacheableEntityAnnotation;
  }
  
  @SuppressWarnings({"serial", "rawtypes"})
  public static class CacheEntry implements Serializable {
    public Object entity; 
    public Class txClass;
    public Class dsClass;
    public CacheEntry(Object entity, Class txClass, Class dsClass) {
      super();
      this.entity = entity;
      this.txClass = txClass;
      this.dsClass = dsClass;
    }
    public String toString() {
      return "CacheEntry [txClass=" + txClass + ", dsClass=" + dsClass + ", entity=" + entity + "]";
    }
  }

  private static MemcacheService getMemCache() {
    return MemcacheServiceFactory.getMemcacheService();
  }
  
  private synchronized static Map<Object, Object> getInstCache() {
    if (PropertiesHolder.getBoolean("it.mate.gwtcommons.server.utils.CacheUtils.useInstanceCache", true)) {
      if (instanceCache == null) {
        instanceCache = new HashMap<Object, Object>();
      }
      return instanceCache;
    } else {
      // se voglio annullare la cache globalmente restituisco sempre una map vuota
      return new HashMap<Object, Object>();
    }
  }
  
  private static String keyToString(Object key) {
    if (key instanceof Key) {
      key = KeyFactory.keyToString((Key)key);
    }
    return key.toString();
  }
  
  public static String formatKeyToString(Object key) {
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
  
  public static Set<Object> instKeySet () {
    return getInstCache().keySet();
  }
  
  public static Object instGet (Object key) {
    return getInstCache().get(key);
  }
  
  public static Object instPut (Object key, Object value) {
    return getInstCache().put(key, value);
  }
  
}