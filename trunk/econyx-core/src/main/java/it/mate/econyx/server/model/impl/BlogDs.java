package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.impl.BlogTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.CollectionPropertyServerUtil;
import it.mate.gwtcommons.server.model.UnownedRelationship;
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
@CacheableEntity (txClass=BlogTx.class)
public class BlogDs implements Blog, HasKey {

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
  List<Key> discussionsKeys;
  
  @UnownedRelationship (key="discussionsKeys", itemClass=BlogDiscussionDs.class)
  transient List<BlogDiscussionDs> discussions;

  @Persistent
  String title;
  
  
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

  public List<BlogDiscussion> getDiscussions() {
    return discussions != null ? new ArrayList<BlogDiscussion>(discussions) : null;
  }

  @CloneableProperty (targetClass=BlogDiscussionDs.class)
  public void setDiscussions(List<BlogDiscussion> discussions) {
    CollectionPropertyServerUtil<BlogDiscussion, BlogDiscussionDs> wrapper = CollectionPropertyServerUtil.clone(discussions, BlogDiscussionDs.class);
    this.discussions = wrapper.getItems();
    this.discussionsKeys = wrapper.getKeys();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
