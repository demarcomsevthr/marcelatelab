package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.gwtcommons.client.utils.ListPropertyWrapper;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.List;

@SuppressWarnings("serial")
public class BlogTx extends AbstractPortalResourceTx implements Blog {

  String code;
  
  List<BlogDiscussionTx> discussions;

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
    return ListPropertyWrapper.cast(discussions, BlogDiscussionTx.class);
  }

  @CloneableProperty (targetClass=BlogDiscussionTx.class)
  public void setDiscussions(List<BlogDiscussion> discussions) {
    this.discussions = ListPropertyWrapper.clone(discussions, BlogDiscussionTx.class).getItems();
  }

}
