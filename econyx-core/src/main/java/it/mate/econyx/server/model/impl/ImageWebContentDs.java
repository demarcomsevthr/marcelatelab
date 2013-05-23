package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.commons.server.utils.BlobUtils;
import it.mate.econyx.shared.model.ImageContent;
import it.mate.econyx.shared.model.WebContent;
import it.mate.econyx.shared.model.impl.ImageContentTx;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


// 21/06/2012
// Disabilitata la cache perchè con i blob non funziona correttamente

// 19/11/2012
@CacheableEntity (txClass=ImageContentTx.class, instanceCache=true)

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class ImageWebContentDs implements ImageContent, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  Key entityId;
  
  @UnownedRelationship (key="entityId")
  transient WebContent entity;
  
  @Persistent
  String type;
  
  @Persistent
  Blob content;
  

  public ImageWebContentDs() {
    super();
  }

  public ImageWebContentDs(Type type) {
    super();
    this.type = type.getCode();
  }

  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
  }

  public WebContent getEntity() {
    return entity;
  }

  public void setEntity(WebContent entity) {
    
  }

  public Type getType() {
    return type != null ? Type.valueOfCode(type) : null;
  }

  public void setType(Type type) {
    this.type = type.getCode();
  }

  public Blob getContent() {
    return content;
  }

  public void setContent(Blob content) {
    this.content = content;
  }

  public String getContentString() {
    return content != null ? BlobUtils.blobToString(content) : null;
  }

  public void setContentString(String content) {
    this.content = content != null ? BlobUtils.stringToBlob(content) : null;
  }
  
}
