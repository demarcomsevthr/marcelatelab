package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.utils.ListPropertyWrapper;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BlogDiscussionTx implements BlogDiscussion {

  String id;
  
  String code;
  
  Integer orderNm;
  
  String title;
  
  String content;

  PortalUserTx author;
  
  Date created;
  
  List<BlogCommentTx> comments;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return getTitle();
  }

  public void setName(String name) {
    setTitle(name);
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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<BlogComment> getComments() {
    return new ListPropertyWrapper<BlogComment, BlogCommentTx>(comments, BlogCommentTx.class);
  }

  @CloneableProperty (targetClass=BlogCommentTx.class)
  public void setComments(List<BlogComment> comments) {
    if (comments != null) {
      this.comments = new ArrayList<BlogCommentTx>();
      for (BlogComment comment : comments) {
        if (comment instanceof BlogCommentTx) {
          this.comments.add((BlogCommentTx)comment);
        } else {
          throw new CloneablePropertyMissingException(comment);
        }
      }
    } else {
      this.comments = null;
    }
  }

}
