package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.BlogComment;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.Post;
import it.mate.econyx.shared.model.PostComment;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BlogDiscussionTx extends AbstractPortalResourceTx implements BlogDiscussion, Post {

  String code;
  
  String content;

  PortalUserTx author;
  
  Date created;
  
  List<BlogCommentTx> comments;
  
  Integer commentsCount;
  
  String tags;
  
  
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

  @Override
  public Date getPostDate() {
    return getCreated();
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
    return CollectionPropertyClientUtil.cast(comments, BlogCommentTx.class);
  }

  @CloneableProperty (targetClass=BlogCommentTx.class)
  public void setComments(List<BlogComment> comments) {
    this.comments = CollectionPropertyClientUtil.clone(comments, BlogCommentTx.class);
  }

  @Override
  public String getBody() {
    return getContent();
  }

  @Override
  public List<PostComment> getPostComments() {
    return new CollectionPropertyClientUtil<PostComment, BlogCommentTx>(comments, BlogCommentTx.class);
  }

  public Integer getCommentsCount() {
    return commentsCount != null ? commentsCount : 0;
  }

  public void setCommentsCount(Integer commentsCount) {
    this.commentsCount = commentsCount;
  }

  public String getTags() {
    return tags != null ? tags : "";
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

}
