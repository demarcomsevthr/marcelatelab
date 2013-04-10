package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.BlogDiscussion;
import it.mate.econyx.shared.model.BlogDiscussionPage;

@SuppressWarnings("serial")
public class BlogDiscussionPageTx extends WebContentPageTx implements BlogDiscussionPage {
  
  BlogDiscussion entity;
  
  @Override
  public String toString() {
    return "BlogDiscussionPageTx [entity=" + entity + "]";
  }

  public BlogDiscussionPageTx() {
    
  }
  
  public BlogDiscussionPageTx(BlogDiscussion discussion) {
    this.setId(BlogDiscussionPageTx.class.getName()+"@"+discussion.getCode());
    this.setEntity(discussion);
    this.setCode(discussion.getCode());
    this.setName(discussion.getName());
    this.setOrderNm(discussion.getOrderNm());
    this.setParent(this);
    this.setVisibleInExplorer(false);
  }
  
  public static boolean isVirtualId(String id) {
    return (id.startsWith(BlogDiscussionPageTx.class.getName()+"@"));
  }
  
  public static String getEntityCodeFromId(String id) {
    return id.substring((BlogDiscussionPageTx.class.getName()+"@").length());
  }

  @Override
  public BlogDiscussion getEntity() {
    return entity;
  }

  @Override
  public void setEntity(BlogDiscussion entity) {
    this.entity = entity;
  }
  
}
