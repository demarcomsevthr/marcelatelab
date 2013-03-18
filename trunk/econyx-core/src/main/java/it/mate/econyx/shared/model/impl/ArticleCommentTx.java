package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.ArticleComment;

@SuppressWarnings("serial")
public class ArticleCommentTx implements ArticleComment {

  String id;
  
  String name;
  
  Integer orderNm;
  
  String content;

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
  
}
