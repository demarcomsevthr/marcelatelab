package it.mate.gwtcommons.server.dao;

import it.mate.gwtcommons.server.utils.EntityRelationshipsResolver;
import it.mate.gwtcommons.server.utils.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Persistent;

import org.apache.log4j.Logger;
import org.datanucleus.store.query.QueryResult;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("unchecked")
public class JdoDao implements Dao {

  protected static Logger logger = Logger.getLogger(JdoDao.class);
  
  private static final boolean USE_FIND_ALL_OLD_VERSION = false;
  
  private static final boolean USE_FIND_BY_ID_ALL_OLD_VERSION = false;
  
  // 04/01/2013
  private static boolean suppressExceptionThrowOnInternalFind = false;
  
  
  private static final Map<Class<? extends Serializable>, List<Class<? extends Serializable>>> entityClassSubTypesCache = 
      new HashMap<Class<? extends Serializable>, List<Class<? extends Serializable>>>();
  
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }
  
  public <E extends Serializable> List<E> findAll(Class<E> entityClass) {
    return findAll(entityClass, null);
  }
  
  public <E extends Serializable> List<E> findAll(Class<E> entityClass, FindCallback<E> callback) {
    if (USE_FIND_ALL_OLD_VERSION) {
      return findAll_OLD_VERSION(entityClass, callback);
    } else {
      return (List<E>)internalFind(new FindContext<E>(entityClass).setResultAsList(true).setCallback(callback));
    }
  }
  
  @Override
  public <E extends Serializable> E findSingle(Class<E> entityClass, String filter) {
    return (E)internalFind(new FindContext<E>(entityClass).setFilter(filter));
  }
  
  public <E extends Serializable> E findSingle(Class<E> entityClass, String filter, FindCallback<E> callback) {
    return (E)internalFind(new FindContext<E>(entityClass).setFilter(filter).setCallback(callback));
  }
  
  public <E extends Serializable> E findSingle(Class<E> entityClass, String filter, String parameters, Object... values) {
    return (E)internalFind(new FindContext<E>(entityClass).setFilter(filter).setParameters(parameters).setParamValues(values));
  }
  
  public <E extends Serializable> E findSingle(Class<E> entityClass, String filter, String parameters, FindCallback<E> callback, Object... values) {
    return (E)internalFind(new FindContext<E>(entityClass).setFilter(filter).setParameters(parameters).setCallback(callback).setParamValues(values));
  }
  
  public <E extends Serializable> E findSingle(FindContext<E> context) {
    return (E)internalFind(context);
  }
  
  public <E extends Serializable> List<E> findList(Class<E> entityClass, String filter, String parameters, FindCallback<E> callback, Object... values) {
    return (List<E>)internalFind(new FindContext<E>(entityClass).setResultAsList(true).setFilter(filter).setParameters(parameters).setCallback(callback).setParamValues(values));
  }
  
  public <E extends Serializable> List<E> findList(FindContext<E> context) {
    return (List<E>)internalFind(context.setResultAsList(true));
  }
  
  public <E extends Serializable> E findById(FindContext<E> context) {
    return (E)internalFind(context);
  }

  public <E extends Serializable> E findById(Class<E> entityClass, Serializable id) {
    return findById(entityClass, id, null);
  }

  public <E extends Serializable> E findById(Class<E> entityClass, Serializable id, FindCallback<E> callback) {
    if (USE_FIND_BY_ID_ALL_OLD_VERSION) {
      return findById_OLD_VERSION(entityClass, id, callback);
    } else {
      return (E)internalFind(new FindContext<E>(entityClass).setId(id).setCallback(callback));
    }
  }
  
  protected <E extends Serializable> Object internalFind(FindContext<E> context) {

    SubClassesResults subClassesResults = checkSubClassesResults(context);
    if (subClassesResults.hasSubclasses) {
      return subClassesResults.results;
    }
    
    // logging
    if (context.getId() != null) {
      String mec = context.getEntityClass().getName().substring(context.getEntityClass().getName().lastIndexOf(".") + 1);
      String mid = "";
      if (context.getId() != null) {
        Serializable id = context.getId();
        Key key = null;
        if (id instanceof Key) {
          key = (Key)id;
        } else {
          key = KeyFactory.stringToKey((String)id);
        }
        mid = "Id " + key.getKind()+(key.getName() != null ? ("."+key.getName()) : "")+"."+key.getId();
      }
      String mfl = "";
      if (context.getFilter() != null) {
        mfl = "Filter " + context.getFilter();
      }
      logger.debug(String.format("DAO >>>> fetching datastore Entity %s %s %s", mec, mid, mfl));
    }
    
    Object results = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    // 14/11/2012: tolta transazione
//  Transaction tx = pm.currentTransaction();
    Query query = null;
    try {
      
//    tx.begin();
      
      if (context.getId() != null) {
        try {
          Key key = null;
          if (context.getId() instanceof String) {
            key = KeyFactory.stringToKey((String)context.getId());
          } else if (context.getId() instanceof Key) {
            key = (Key)context.getId();
          } else {
            throw new IllegalArgumentException("unsupported key of type " + context.getId().getClass());
          }
          if (key.getKind().equals(context.getEntityClass().getSimpleName())) {
            results = pm.getObjectById(context.getEntityClass(), context.getId());
          } else {
            throw new DataNotFoundException();
          }
        } catch (Exception ne) {
          if (suppressExceptionThrowOnInternalFind) {
            results = null;
          } else {
            throw new DataNotFoundException(ne);
          }
        }
      } else {
        
        if (context.keysOnly()) {
          query = pm.newQuery("SELECT id FROM " + context.getEntityClass().getName());
        } else if (context.getWhereClause() != null) {
          query = pm.newQuery("SELECT FROM " + context.getEntityClass().getName() + " WHERE " + context.getWhereClause());
        } else {
          query = pm.newQuery(context.getEntityClass());
        }
        
        if (context.getFilter() != null) {
          query.setFilter(context.getFilter());
        }
        if (context.getParameters() != null) {
          query.declareParameters(context.getParameters());
        }
        if (context.getParamValues() != null) {
          results = query.executeWithArray(context.getParamValues());
        } else {
          results = query.execute();
        }
      }
      
      if (results == null)
        return null;

      /**
       *  PROCESSO IL RESULT SET IN BASE AL TIPO
       */
      
      if (results instanceof QueryResult) {
        QueryResult queryResult = (QueryResult)results;
        // 20/11/2012
        @SuppressWarnings("rawtypes")
        List detachedResults = null;
        if (context.keysOnly()) {
          detachedResults = new ArrayList<Key>();
        } else {
          detachedResults = new ArrayList<E>();
        }
//      List<E> detachedResults = new ArrayList<E>();
        if (!queryResult.isEmpty()) {
          for (Object result : queryResult) {
            detachedResults.add(result);
            applyPatchDefaultFetchGroup(result, context);
            if (context.getCallback() != null && !context.keysOnly()) {
              context.getCallback().processResultsInTransaction((E)result);
            }
          }
        }
        if (detachedResults.size() == 0) {
          if (context.isResultAsList()) {
            results = detachedResults;
          } else {
            results = null;
          }
        } else if (detachedResults.size() == 1) {
          if (context.isResultAsList()) {
            results = detachedResults;
          } else {
            results = detachedResults.iterator().next();
          }
        } else {
          results = detachedResults;
        }

        
      } else if (results instanceof List) {
        
        @SuppressWarnings("rawtypes")
//      List<E> resultsList = (List)results;
        List resultsList = (List)results;
        resultsList.size();
        for (Object result : resultsList) {
          applyPatchDefaultFetchGroup(result, context);
          if (context.getCallback() != null && !context.keysOnly()) {
            context.getCallback().processResultsInTransaction((E)result);
          }
        }


        
      } else {
        
        /**  Dovrebbe entrare qui in caso di entità singola (findById)  **/
        
        // 17/11/2012
        // per le query by id in genere dovrebbe servire sempre risolvere tutte le relazioni
        // quindi provo a commentarlo fisso
        //applyPatchDefaultFetchGroup(results, context);
        
        if (context.getCallback() != null)
          context.getCallback().processResultsInTransaction((E)results);
        
      }
      
    } finally {
      if (query != null)
        query.closeAll();
      /*
      if (tx.isActive())
        tx.rollback();
        */
      pm.close();
    }

    if (!context.isRelationshipsResolutionDisabled()) {
      results = resolveUnownedRelationships(results, context);
    }
    
    return results;
  }
  
  @SuppressWarnings("rawtypes")
  // 16/11/2012
  private <E extends Serializable> void applyPatchDefaultFetchGroup(Object result, FindContext<E> context) {
    if (result == null)
      return;
    if (result instanceof List) {
      List<?> results = (List)result;
      for (Object item : results) {
        applyPatchDefaultFetchGroup(item, context);
      }
      return;
    }
    Class<?> entityClass = result.getClass();
    List<Field> entityFields = ReflectionUtils.getAllPrivateFields(entityClass, null);
    for (Field field : entityFields) {
      Persistent persistentAnnotation = field.getAnnotation(Persistent.class);
      if (persistentAnnotation != null) {
        if ("false".equals(persistentAnnotation.defaultFetchGroup()) && !context.getIncludedFields().contains(field.getName())) {
          field.setAccessible(true);
          try {
            field.set(result, null);
          } catch (Exception ex) {
            logger.error("error", ex);
          }
        }
      }
    }
  }
 
  
  @SuppressWarnings("rawtypes")
  private <E extends Serializable> SubClassesResults checkSubClassesResults(FindContext<E> context) {
    SubClassesResults subClassesResults = new SubClassesResults();
    List<Class<? extends Serializable>> subClasses = findSubClasses(context.getEntityClass());
    if (subClasses.size() > 0) {
      subClassesResults.hasSubclasses = true;
      Object actualResults = null;
      for (Class<? extends Serializable> subClass : subClasses) {
        Object currentSubclassResults = null;
        try {
          currentSubclassResults = internalFind(context.setEntityClass((Class<E>)subClass));
        } catch (DataNotFoundException ex) {  }
        if (currentSubclassResults == null) {
          // nothing
        } else if (currentSubclassResults instanceof Collection) {
          if (actualResults == null) {
            actualResults = currentSubclassResults;
          } else if (actualResults instanceof Collection) {
            Collection prevCollection = (Collection)actualResults;
            try {
              prevCollection.addAll((Collection)currentSubclassResults);
            } catch (Exception ex) {
              new JDOException(String.format("Error merging subclass resultsets (%s)", context.getEntityClass()), ex);
            }
          } else {
            new JDOException(String.format("Incompatible subclass resultsets (%s)", context.getEntityClass()));
          }
        } else {
          actualResults = currentSubclassResults;
          break;
        }
      }
      subClassesResults.results = actualResults;
    }
    return subClassesResults;
  }
  
  public static <E extends Serializable> List<Class<? extends Serializable>> findSubClasses(Class<E> entityClass) {
    if (entityClassSubTypesCache.containsKey(entityClass)) {
      return entityClassSubTypesCache.get(entityClass);
    } else {
      ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
      provider.addIncludeFilter(new AssignableTypeFilter(entityClass));
      String pkgName = entityClass.getPackage().getName();
      Set<BeanDefinition> components = provider.findCandidateComponents(pkgName);
      List<Class<? extends Serializable>> subTypes = new ArrayList<Class<? extends Serializable>>();
      entityClassSubTypesCache.put(entityClass, subTypes);
      for (BeanDefinition component : components) {
        if (!entityClass.getName().equals(component.getBeanClassName())) {
          try {
            Class<? extends Serializable> subClass = (Class<? extends Serializable>)Class.forName(component.getBeanClassName());
            subTypes.add(subClass);
          } catch (Exception ex) {
            logger.error("error", ex);
          }
        }
      }
      return subTypes;
    }
  }
  
  public <E extends Serializable> E create(E entity) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      logger.debug("making persistent " + entity);
      entity = pm.makePersistent(entity);
      tx.commit();
    } finally {
      if (tx.isActive())
        tx.rollback();
      logger.debug("closing persistence manager...");
      pm.close();
    }
    logger.debug("created entity is " + entity);
    return entity;
  }
  
  public <E extends Serializable> E update(E entity) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      logger.debug("making persistent " + entity);
      entity = pm.makePersistent(entity);
      logger.debug("updated entity is " + entity);
      tx.commit();
    } finally {
      if (tx.isActive())
        tx.rollback();
      pm.close();
    }
    return entity;
  }

  public <E extends Serializable> E update(Class<E> entityClass, Serializable id, UpdateCallback<E> callback) {
    E updatedEntity = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      E attachedEntity = (E)pm.getObjectById(entityClass, id);
      updatedEntity = callback.updateEntityValues(pm, attachedEntity);
      updatedEntity = pm.makePersistent(updatedEntity);
      logger.debug("updated entity is " + updatedEntity);
      tx.commit();
    } finally {
      if (tx.isActive())
        tx.rollback();
      pm.close();
    }
    return updatedEntity;
  }

  public <E extends Serializable> void delete(E entity) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      entity = pm.makePersistent(entity);
      pm.deletePersistent(entity);
      tx.commit();
    } finally {
      if (tx.isActive())
        tx.rollback();
      pm.close();
    }
  }
  
  @SuppressWarnings("rawtypes")
  protected Object resolveUnownedRelationships (Object result, FindContext context) {
    EntityRelationshipsResolver resolver = new EntityRelationshipsResolver(this, context);
    return resolver.resolveUnownedRelationships(result);
  }

  private <E extends Serializable> E findById_OLD_VERSION(Class<E> entityClass, Serializable id, FindCallback<E> callback) {
    E result = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      result = pm.getObjectById(entityClass, id);
      if (callback != null)
        callback.processResultsInTransaction(result);
    } catch (Exception ne) {
      throw new DataNotFoundException(ne);
    } finally {
      if (tx.isActive())
        tx.rollback();
      pm.close();
    }
    return (E)resolveUnownedRelationships(result, null);
  }
  
  private class SubClassesResults {
    Object results = null;
    boolean hasSubclasses = false;
  }
  
  private <E extends Serializable> List<E> findAll_OLD_VERSION(Class<E> entityClass, FindCallback<E> callback) {
    logger.debug("finding all entities of type " + entityClass);
    List<E> results = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    logger.debug("pm = " + pm);
    Query query = null;
    try {
      tx.begin();
      query = pm.newQuery(entityClass);
      logger.debug("query = " + query);
      results = (List<E>)query.execute();
      if (results != null) {
        results.size();
        if (callback != null) {
          for (E result : results) {
            callback.processResultsInTransaction(result);
          }
        }
        logger.debug("results.size = " + results.size());
      }
    } finally {
      if (query != null)
        query.closeAll();
      if (tx.isActive())
        tx.rollback();
      pm.close();
    }
    return (List<E>)resolveUnownedRelationships(results, null);
  }
  
  public static void setSuppressExceptionThrowOnInternalFind(boolean suppressExceptionThrowOnInternalFind) {
    JdoDao.suppressExceptionThrowOnInternalFind = suppressExceptionThrowOnInternalFind;
  }
  
  public static boolean isSuppressExceptionThrowOnInternalFind() {
    return JdoDao.suppressExceptionThrowOnInternalFind;
  }
  
}
