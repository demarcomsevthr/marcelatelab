package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Image;
import it.mate.econyx.shared.model.impl.ImageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.utils.BlobUtils;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=ImageTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class ImageDs implements Image, HasKey {
  
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String name;
  
  @Persistent
  String code;
  
  @Persistent
  Integer orderNm;
  
  @Persistent
  Blob content;
  
  public Key getKey() {
    return id;
  }

  public void setKey(Key key) {
    this.id = key;
  }
  
  public String getId() {
    return getKey() != null ? KeyFactory.keyToString(getKey()) : null;
  }

  public void setId(String id) {
    setKey( id != null ? KeyFactory.stringToKey(id) : null );
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

  public Blob getContent() {
    return content;
  }

  public void setContent(Blob content) {
    this.content = content;
  }

  public String getContentString() {
    return content != null ? BlobUtils.blobToString(content) : null;
  }

  public void setContentString(String contentString) {
    this.content = (contentString != null && contentString.length() > 0) ? BlobUtils.stringToBlob(contentString) : null;
  }
  
}
