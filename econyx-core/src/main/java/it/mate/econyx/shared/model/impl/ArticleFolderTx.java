package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.utils.CollectionPropertyClientUtil;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ArticleFolderTx implements ArticleFolder {

  String id;
  
  String code;
  
  String name;
  
  Integer orderNm;
  
  List<ArticleTx> articles = new ArrayList<ArticleTx>();
  
  String selectedArticleCode;
  
  public ArticleFolderTx() {
    super();
  }

  @Override
  public String toString() {
    return "ArticleFolderTx [id=" + id + ", code=" + code + ", name=" + name + ", orderNm=" + orderNm + ", articles=" + articles
        + ", selectedArticleCode=" + selectedArticleCode + "]";
  }

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

  public List<Article> getArticles() {
    return new CollectionPropertyClientUtil<Article, ArticleTx>(articles, ArticleTx.class);
  }

  @CloneableProperty (targetClass=ArticleTx.class)
  public void setArticles(List<Article> articles) {
    if (articles != null) {
      this.articles = new ArrayList<ArticleTx>();
      for (Article article : articles) {
        if (article instanceof ArticleTx) {
          this.articles.add((ArticleTx)article);
        } else {
          throw new CloneablePropertyMissingException(article);
        }
      }
    } else {
      this.articles = null;
    }
  }

  public String getSelectedArticleCode() {
    return selectedArticleCode;
  }

  public void setSelectedArticleCode(String selectedArticleCode) {
    this.selectedArticleCode = selectedArticleCode;
  }

}
