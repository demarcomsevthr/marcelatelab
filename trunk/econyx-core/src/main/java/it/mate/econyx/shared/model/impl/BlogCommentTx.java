package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.Date;

@SuppressWarnings("serial")
public class BlogCommentTx implements BlogComment {
  
  String id;
  
  String name;
  
  Integer orderNm;
  
  String content;

  PortalUserTx author;
  
  Date posted;
  
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
