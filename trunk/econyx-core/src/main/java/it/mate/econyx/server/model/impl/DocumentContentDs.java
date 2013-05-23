package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.utils.BlobUtils;
import it.mate.econyx.shared.model.DocumentContent;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class DocumentContentDs implements DocumentContent, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  Blob content;

  public Key getKey() {
    return id;
  }
  
  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = ( id != null ? KeyFactory.stringToKey(id) : null );
  }

  public String getContent() {
    return content != null ? BlobUtils.blobToString(content) : null;
  }

  public void setContent(String content) {
    this.content = content != null ? BlobUtils.stringToBlob(content) : null;
  }
  
  public void setContentBlob(Blob content) {
    this.content = content;
  }
  
  public Blob getContentBlob() {
    return content;
  }
  
}
