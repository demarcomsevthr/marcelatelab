package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class BlogCommentDs implements BlogComment, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String code;
  
  @Persistent
  String name;
  
  @Persistent
  Integer orderNm;
  
  @Persistent (dependent="false", defaultFetchGroup="true")
  Key authorKey;
  
  @UnownedRelationship (key="authorKey")
  transient PortalUserDs author;
  
  @Persistent
  Date posted;
  
  @Persistent
  Text content;
  
  
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

  public PortalUser getAuthor() {
    return author;
  }

  @CloneableProperty (targetClass=PortalUserDs.class)
  public void setAuthor(PortalUser author) {
    this.author = (PortalUserDs)author;
    this.authorKey = this.author != null ? this.author.getKey() : null;
  }

  public Date getPosted() {
    return posted;
  }

  public void setPosted(Date posted) {
    this.posted = posted;
  }

  public String getContent() {
    return content != null ? content.getValue() : null;
  }

  public void setContent(String content) {
    this.content = content != null ? new Text(content) : null;
  }

}
