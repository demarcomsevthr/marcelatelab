package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.CollectionPropertyServerUtil;
import it.mate.commons.server.model.HasKey;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.BlogDiscussionTx;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=BlogDiscussionTx.class)
public class BlogDiscussionDs implements BlogDiscussion, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;
  
  @Persistent
  String code;
  
  @Persistent
  String name;
  
  @Persistent
  Integer orderNm;
  
  @Persistent (dependentKey="false", defaultFetchGroup="false")
  List<Key> commentsKeys;
  
  @UnownedRelationship (key="commentsKeys", itemClass=BlogCommentDs.class)
  transient List<BlogCommentDs> comments;

  @Persistent
  String title;
  
  @Persistent (dependent="false", defaultFetchGroup="true")
  Key authorKey;
  
  @UnownedRelationship (key="authorKey")
  transient PortalUserDs author;
  
  @Persistent
  Date created;
  
  @Persistent
  Text content;
  
  @Persistent
  Integer commentsCount;
  
  @Persistent
  String tags;
  
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

  public List<BlogComment> getComments() {
    return comments != null ? new ArrayList<BlogComment>(comments) : null;
  }

  @CloneableProperty (targetClass=BlogCommentDs.class)
  public void setComments(List<BlogComment> comments) {
    CollectionPropertyServerUtil<BlogComment, BlogCommentDs> wrapper = CollectionPropertyServerUtil.clone(comments, BlogCommentDs.class);
    this.comments = wrapper.getItems();
    this.commentsKeys = wrapper.getKeys();
    this.commentsCount = this.comments.size();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public String getContent() {
    return content != null ? content.getValue() : null;
  }

  public void setContent(String content) {
    this.content = content != null ? new Text(content) : null;
  }

  public Integer getCommentsCount() {
    return commentsCount != null ? commentsCount : 0;
  }

  public void setCommentsCount(Integer commentsCount) {
    this.commentsCount = commentsCount;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }
  
}
