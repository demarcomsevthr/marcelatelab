package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ArticleComment;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.Date;

public class ArticleCommentTx implements ArticleComment {
  
  private static final long serialVersionUID = -8105988358558928176L;

  String id;
  
  String name;
  
  Integer orderNm;
  
  String content;

  PortalUserTx author;
  
  Date posted;
  
  public ArticleCommentTx() {
    super();
  }

  @Override
  public String toString() {
    return "ArticleCommentTx [id=" + id + ", name=" + name + ", orderNm=" + orderNm + ", content=" + content + ", author=" + author
        + ", posted=" + posted + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public PortalUser getAuthor() {
    return author;
  }

  @CloneableProperty (targetClass=PortalUserTx.class)
  public void setAuthor(PortalUser author) {
    if (author instanceof PortalUserTx) {
      this.author = (PortalUserTx)author;
    } else {
      throw new CloneablePropertyMissingException(author);
    }
  }

  public Date getPosted() {
    return posted;
  }

  public void setPosted(Date posted) {
    this.posted = posted;
  }
  
}
