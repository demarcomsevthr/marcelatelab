package it.mate.gwtcommons.server.utils;

import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.dao.EntityRelationshipsResolverHandler;
import it.mate.gwtcommons.server.dao.FindContext;
import it.mate.gwtcommons.server.dao.PersistenceException;
import it.mate.gwtcommons.server.model.HasUnownedRelationships;
import it.mate.gwtcommons.server.model.UnownedRelationship;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.Key;

public class EntityRelationshipsResolver {

  private static Logger logger = Logger.getLogger(EntityRelationshipsResolver.class);
  
  private Dao dao;
  
  private FindContext context;
  
  public EntityRelationshipsResolver(Dao dao, FindContext context) {
    this.dao = dao;
    this.context = context;
  }

  public Object resolveUnownedRelationships (Object entity) {
    if (entity == null)
      return null;
    
    // 01/03/2011
    // Introdotto per ovviare al fatto che su relazioni con liste di key sembra non funzionare
    // il default-fetch-group = false !!!!!
    if (entity instanceof EntityRelationshipsResolverHandler) {
      ((EntityRelationshipsResolverHandler) entity).onBeforeResolveRelationships();
    }
    
    if (entity instanceof List) {
      List results = (List)entity;
      for (Object item : results) {
        resolveUnownedRelationships(item);
      }
    } else if (entity instanceof HasUnownedRelationships) {
      HasUnownedRelationships hasDipendencies = (HasUnownedRelationships)entity;
      hasDipendencies.resolveUnownedRelationships();
    } else {
      resolveUnownedRelationshipsWithAnnotation(entity);
    }
    return entity;
  }
  
  
  private Field[] getAllHierarchyDeclaredFieldsInSamePackage(Class<?> entityClass) {
    Package entityPackage = entityClass.getPackage();
    List<Field> allFields = new ArrayList<Field>();
    Class<?> currentClass = entityClass;
    while (currentClass != null) {
      if (currentClass.getPackage().equals(entityPackage)) {
        allFields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
      }
      currentClass = currentClass.getSuperclass();
    }
    return allFields.toArray(new Field[0]);
  }
  
  
  private void resolveUnownedRelationshipsWithAnnotation (Object entity) {
    if (entity == null)
      return;
    // 28/02/2012: dopo l'introduzione delle gerarchie di classi ds, si rende necessario esaminare tutti i fields dichiarati nella gerarchia
    //    ATTENZIONE: la getFields NON FUNZIONA!!!!
//  Field[] fields = entity.getClass().getDeclaredFields();
//  Field[] fields = entity.getClass().getFields();
    Field[] fields = getAllHierarchyDeclaredFieldsInSamePackage(entity.getClass());
    for (Field field : fields) {
      UnownedRelationship unownedRelationshipAnnotation = field.getAnnotation(UnownedRelationship.class);
      if (unownedRelationshipAnnotation != null) {
        Field relationshipField = field;
        String keyName = unownedRelationshipAnnotation.key();
        try {
          for (Field keyField : fields) {
            if (keyName.equals(keyField.getName())) {
              keyField.setAccessible(true);
              relationshipField.setAccessible(true);

              //25.02.2011
              if (Collection.class.isAssignableFrom(relationshipField.getType())) {
                Class itemClass = unownedRelationshipAnnotation.itemClass();
                if (itemClass != Void.class) {
                  Collection<Key> keys = (List<Key>)keyField.get(entity);
                  if (keys != null) {
                    List relatedEntities = new ArrayList();
                    for (Key relatedKey : keys) {
                      Object relatedEntity = dao.findById((Class<Serializable>)itemClass, relatedKey);
                      relatedEntities.add(relatedEntity);
                    }
                    relationshipField.set(entity, relatedEntities);
                  }
                }
                
              } else {
                Key relatedKey = (Key)keyField.get(entity);
                if (relatedKey != null) {
                  Object relatedEntity = dao.findById((Class<Serializable>)relationshipField.getType(), relatedKey);
                  
                  if (relatedEntity == null) {
                    logger.error("ERROR", new PersistenceException(String.format("ALERT: cannot resolve unowned related entity in %s, relationship type is %s, id is %s ", entity.getClass().getName(), relationshipField.getType(), relatedKey)));
                  }
                  
                  relationshipField.set(entity, relatedEntity);

                  // 30/04/2012
                  resolveUnownedRelationships(relatedEntity);
                  
                } else {
                  relationshipField.set(entity, null);
                }
              }
              
              break;
            }
          }
        } catch (Exception ex) {
          logger.error("error", ex);
        }
      }
      if (Collection.class.isAssignableFrom(field.getType())) {
        try {
          Field collectionField = field;
          collectionField.setAccessible(true);
          Collection collection = (Collection)collectionField.get(entity);
          if (collection != null) {
            for (Object item : collection) {
              resolveUnownedRelationshipsWithAnnotation(item);
            }
          }
        } catch (Exception ex) {
          logger.error("error", ex);
        }
      }
    }
  }
  
  
}
