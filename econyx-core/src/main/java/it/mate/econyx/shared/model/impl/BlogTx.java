package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.gwtcommons.client.utils.ListPropertyWrapper;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BlogTx implements Blog {

  String id;
  
  String code;
  
  Integer orderNm;
  
  String title;
  
  List<BlogDiscussionTx> discussions;

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

  public List<BlogDiscussion> getDiscussions() {
    return new ListPropertyWrapper<BlogDiscussion, BlogDiscussionTx>(discussions, BlogDiscussionTx.class);
  }

  @CloneableProperty (targetClass=BlogDiscussionTx.class)
  public void setDiscussions(List<BlogDiscussion> discussions) {
    if (discussions != null) {
      this.discussions = new ArrayList<BlogDiscussionTx>();
      for (BlogDiscussion discussion : discussions) {
        if (discussion instanceof BlogDiscussionTx) {
          this.discussions.add((BlogDiscussionTx)discussion);
        } else {
          throw new CloneablePropertyMissingException(discussion);
        }
      }
    } else {
      this.discussions = null;
    }
  }

}
