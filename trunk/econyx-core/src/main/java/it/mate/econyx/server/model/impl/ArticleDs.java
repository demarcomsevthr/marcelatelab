package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.impl.ArticleTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
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
@CacheableEntity (txClass=ArticleTx.class)
public class ArticleDs implements Article, HasKey {

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
  Key htmlKey;
  
  @UnownedRelationship (key="htmlKey")
  transient HtmlWebContentDs html;

  @Persistent (dependentKey="false", defaultFetchGroup="false")
  List<Key> commentsKeys;
  
  @UnownedRelationship (key="commentsKeys", itemClass=ArticleCommentDs.class)
  transient List<ArticleCommentDs> comments;

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

  public Key getHtmlId() {
    return htmlKey;
  }

  public void setHtmlId(Key htmlId) {
    this.htmlKey = htmlId;
  }

  public HtmlContent getHtml() {
    return html;
  }

  @CloneableProperty (targetClass=HtmlWebContentDs.class)
  public void setHtml(HtmlContent html) {
    this.html = (HtmlWebContentDs)html;
    this.htmlKey = this.html != null ? this.html.getKey() : null;
  }
  
  public List<ArticleComment> getComments() {
    return comments != null ? new ArrayList<ArticleComment>(comments) : null;
  }

  @CloneableProperty (targetClass=ArticleCommentDs.class)
  public void setComments(List<ArticleComment> comments) {
    this.comments = new ArrayList<ArticleCommentDs>();
    this.commentsKeys = new ArrayList<Key>();
    if (comments != null) {
      for (ArticleComment comment : comments) {
        if (comment instanceof ArticleCommentDs) {
          attachComment((ArticleCommentDs)comment);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + comment.getClass() + ", forget CloneableProperty annotation?");
        }
      }
    }
  }

  private void attachComment(ArticleCommentDs comment) {
    if (comments == null)
      comments = new ArrayList<ArticleCommentDs>();
    this.comments.add(comment);
    if (commentsKeys == null)
      commentsKeys = new ArrayList<Key>();
    this.commentsKeys.add(comment.getKey());
  }

}
