package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.impl.DocumentFolderTx;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=DocumentFolderTx.class)
public class DocumentFolderDs implements DocumentFolder, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String code;
  
  @Persistent
  String name;
  
  @Persistent
  Integer orderNm;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> documentsKeys;
  
  @UnownedRelationship (key="documentsKeys", itemClass=DocumentDs.class)
  transient List<DocumentDs> documents;

  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public List<Document> getDocuments() {
    return documents != null ? new ArrayList<Document>(documents) : null;
  }

  @CloneableProperty (targetClass=DocumentDs.class)
  public void setDocuments(List<Document> documents) {
    this.documents = new ArrayList<DocumentDs>();
    this.documentsKeys = new ArrayList<Key>();
    if (documents != null) {
      for (Document document : documents) {
        if (document instanceof DocumentDs) {
          attachDocument((DocumentDs)document);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + document.getClass() + ", forget CloneableProperty annotation?");
        }
      }
    }
  }
  
  private void attachDocument(DocumentDs document) {
    if (documents == null)
      documents = new ArrayList<DocumentDs>();
    this.documents.add(document);
    if (documentsKeys == null)
      documentsKeys = new ArrayList<Key>();
    this.documentsKeys.add(document.getKey());
  }
  
}
