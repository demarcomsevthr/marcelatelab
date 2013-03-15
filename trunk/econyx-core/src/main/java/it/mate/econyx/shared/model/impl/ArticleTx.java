package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.gwtcommons.shared.model.CloneableProperty;

@SuppressWarnings("serial")
public class ArticleTx implements Article {
  
  String id;
  
  String code;
  
  String name;
  
  Integer orderNm;
  
  HtmlContentTx html;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  @Override
  public HtmlContent getHtml() {
    return html;
  }

  @CloneableProperty (targetClass=HtmlContentTx.class)
  public void setHtml(HtmlContent html) {
    if (html instanceof HtmlContentTx) {
      this.html = (HtmlContentTx)html;
    } else {
      throw new IllegalArgumentException("cannot add item of type " + html.getClass() + ", forget CloneableProperty annotation");
    }
  }
  
}
