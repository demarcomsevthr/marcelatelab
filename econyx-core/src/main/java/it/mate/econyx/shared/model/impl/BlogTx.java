package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.List;

@SuppressWarnings("serial")
public class BlogTx extends AbstractPortalResourceTx implements Blog {

  String code;
  
  List<BlogDiscussionTx> discussions;

  // serve solo lato client
  transient String pageId;
  
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

  public List<BlogDiscussion> getDiscussions() {
    return CollectionPropertyClientUtil.cast(discussions, BlogDiscussionTx.class);
  }

  @CloneableProperty (targetClass=BlogDiscussionTx.class)
  public void setDiscussions(List<BlogDiscussion> discussions) {
    this.discussions = CollectionPropertyClientUtil.clone(discussions, BlogDiscussionTx.class);
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

}
