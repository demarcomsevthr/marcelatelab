package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticlePage;

@SuppressWarnings("serial")
public class ArticlePageTx extends WebContentPageTx implements ArticlePage {
  
  Article entity;
  
  public ArticlePageTx() {
    
  }
  
  public ArticlePageTx(Article article) {
    this.setId(ArticlePageTx.class.getName()+"@"+article.getCode());
    this.setEntity(article);
    this.setCode(article.getCode());
    this.setName(article.getName());
    this.setOrderNm(article.getOrderNm());
    this.setParent(this);
  }
  
  public static boolean isVirtualId(String id) {
    return (id.startsWith(ArticlePageTx.class.getName()+"@"));
  }
  
  public static String getEntityCodeFromId(String id) {
    return id.substring((ArticlePageTx.class.getName()+"@").length());
  }

  @Override
  public Article getEntity() {
    return entity;
  }

  @Override
  public void setEntity(Article entity) {
    this.entity = entity;
  }
  
}
