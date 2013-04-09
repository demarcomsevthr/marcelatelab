package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.gwtcommons.client.utils.ListPropertyWrapper;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BlogDiscussionTx extends AbstractPortalResourceTx implements BlogDiscussion {

  String code;
  
  String content;

  PortalUserTx author;
  
  Date created;
  
  List<BlogCommentTx> comments;
  
  public String getTitle() {
    return name;
  }

  public void setTitle(String title) {
    this.name = title;
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
    return ListPropertyWrapper.cast(comments, BlogCommentTx.class);
  }

  @CloneableProperty (targetClass=BlogCommentTx.class)
  public void setComments(List<BlogComment> comments) {
    ListPropertyWrapper.clone(comments, BlogCommentTx.class);
  }

}
