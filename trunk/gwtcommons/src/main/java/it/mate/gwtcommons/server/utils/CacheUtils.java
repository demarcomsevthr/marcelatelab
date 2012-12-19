package it.mate.gwtcommons.server.utils;

import it.mate.gwtcommons.server.dao.JdoDao;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    if (key != null) {
      
      Object result = null;
      
      result = getInstCache().get(keyToString(key));
      
      if (result == null) {
        result = getMemCache().get(keyToString(key));
      }
      
      if (result instanceof CacheEntry) {
        CacheEntry cacheEntry = (CacheEntry)result;
        if (cacheEntry.dsClass != null) {
          result = CloneUtils.clone(cacheEntry.entity, cacheEntry.dsClass);
        }
      }
      if (result == null) {
        Key kk = null;
        if (key instanceof Key) {
          kk = (Key)key;
        } else {
          kk = KeyFactory.stringToKey((String)key);
        }
        String k = kk.getKind()+"."+kk.getId();
        logger.debug(String.format("MISSING entity in cache with key %s", k));
      }
      if (result != null) {
//      logger.debug(String.format("retrieved from cache object %s with key %s", result, key));
      }
      return result;
    }
    return null;
  }
  
  public static void put (Object entity) {
    if (entity != null && entity instanceof Serializable) {
      Serializable serializableEntity = (Serializable)entity;
      CacheableEntity cacheableEntityAnnotation = getCacheableEntityAnnotation(serializableEntity.getClass());
      if (entity instanceof HasKey && cacheableEntityAnnotation != null) {
        
        HasKey hasKeyEntity = (HasKey)entity;
        
        Class<?> txClass = cacheableEntityAnnotation.txClass();
        
        Key k = hasKeyEntity.getKey();
//      logger.debug("updating entity in cache with key "+k.getKind()+"."+k.getId());

//      applyBlobPatch(entity);

        Object entityClone = entity;
        if (txClass != null) {
          entityClone = CloneUtils.clone(entity, txClass);
        }
        
        CacheEntry cacheEntry = new CacheEntry(entityClone, txClass, entity.getClass());
        
        if (cacheableEntityAnnotation.instanceCache()) {
          getInstCache().put(keyToString(hasKeyEntity.getKey()), cacheEntry);
        } else {
          getMemCache().put(keyToString(hasKeyEntity.getKey()), cacheEntry);
        }
        
//      logger.debug(String.format("updated in cache object %s", entity));
      }
    }
  }
  
  public static void delete (Object entity) {
    if (entity != null && entity instanceof HasKey && entity.getClass().getAnnotation(CacheableEntity.class) != null) {
      try {
        HasKey hasKeyEntity = (HasKey)entity;
        deleteByKey(hasKeyEntity.getKey());
//      logger.debug(String.format("deleted from cache object %s", entity));
      } catch (Exception ex) {
        logger.error("error", ex);
      }
    }
  }
  
  public static void deleteByKey (Object key) {
    if (existsInCacheByKey(key)) {
      getMemCache().delete(keyToString(key));
    }
  }
  
  public static void clearAll() {
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
  }

  /*
  private static void applyBlobPatch (Object entity) {
    List<Field> entityFields = ReflectionUtils.getAllPrivateFields(entity.getClass(), null);
    for (Field field : entityFields) {
      if (field.getType().equals(Blob.class)) {
        field.setAccessible(true);
        try {
          field.set(entity, null);
        } catch (Exception ex) {
          logger.error("error", ex);
        }
      }
    }
  }
  */
  
  private static boolean existsInCacheByKey (Object key) {
    return getMemCache().contains(keyToString(key));
  }
  
  private static MemcacheService getMemCache() {
    return MemcacheServiceFactory.getMemcacheService();
  }
  
  private synchronized static Map<Object, Object> getInstCache() {
    if (instanceCache == null) {
      instanceCache = new HashMap<Object, Object>();
    }
    return instanceCache;
  }
  
  private static String keyToString(Object key) {
    if (key instanceof Key) {
      key = KeyFactory.keyToString((Key)key);
    }
    return key.toString();
  }
  
}
