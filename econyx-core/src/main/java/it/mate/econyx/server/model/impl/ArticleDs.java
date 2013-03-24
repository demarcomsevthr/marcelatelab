package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.ArticleTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

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
  
  @Persistent (dependentKey="false", defaultFetchGroup="false")
  List<Key> commentsKeys;
  
  @UnownedRelationship (key="commentsKeys", itemClass=ArticleCommentDs.class)
  transient List<ArticleCommentDs> comments;

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
          throw new CloneablePropertyMissingException(comment);
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
  
  
  public HtmlContent getHtml() {
    HtmlContentDs html = new HtmlWebContentDs();
    html.setType(HtmlContent.Type.MEDIUM);
    html.setContent(content.getValue());
    return html;
  }

  @CloneableProperty (targetClass=HtmlWebContentDs.class)
  public void setHtml(HtmlContent html) {
    if (html == null) {
      this.content = null;
    } else if (html instanceof HtmlWebContentDs) {
      this.content = new Text(html.getContent());
    } else {
      throw new CloneablePropertyMissingException(html);
    }
  }

}
