package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Document;
import it.mate.econyx.shared.model.DocumentContent;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.DocumentTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=DocumentTx.class)
public class DocumentDs implements Document, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String code;
  
  @Persistent
  Integer orderNm;
  
  @Persistent
  String title;

  @Persistent (dependent="false", defaultFetchGroup="false")
  Key contentKey;

  @UnownedRelationship (key="contentKey")
  transient DocumentContentDs content;

  @Persistent (dependent="false", defaultFetchGroup="true")
  Key authorKey;
  
  @UnownedRelationship (key="authorKey")
  transient PortalUserDs author;
  
  @Persistent
  Date created;

  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = ( id != null ? KeyFactory.stringToKey(id) : null );
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public DocumentContent getContent() {
    return content;
  }

  @CloneableProperty (targetClass=DocumentContentDs.class)
  public void setContent(DocumentContent content) {
    this.content = (DocumentContentDs)content;
    this.contentKey = this.content != null ? this.content.getKey() : null;
  }

  public PortalUser getAuthor() {
    return author;
  }

  @CloneableProperty (targetClass=PortalUserDs.class)
  public void setAuthor(PortalUser author) {
    this.author = (PortalUserDs)author;
    this.authorKey = this.author != null ? this.author.getKey() : null;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getName() {
    return getTitle();
  }

  public void setName(String name) {
    setTitle(name);
  }
  
}
