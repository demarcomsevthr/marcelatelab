package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.utils.ListPropertyWrapper;
import it.mate.gwtcommons.shared.model.CloneableProperty;

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
//  return (List<Article>)CollectionUtilsClient.wrapConcreteList(Article.class, articles, ArticleTx.class);
    return new ListPropertyWrapper<Article, ArticleTx>(articles, ArticleTx.class);
  }

  /*
  public List<Article> getArticles() {
    if (articles == null)
      return null;
    return new AbstractList<Article>() {
      public int size() {
        return articles.size();
      }
      public Article get(int index) {
        return articles.get(index);
      }
      public Article set(int index, Article element) {
        if (element instanceof ArticleTx) {
          return articles.set(index, (ArticleTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(Article element) {
        if (element instanceof ArticleTx) {
          return articles.add((ArticleTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return articles.remove(o);
      }
      public Article remove(int index) {
        return articles.remove(index);
      }
    };
  }
  */

  @CloneableProperty (targetClass=ArticleTx.class)
  public void setArticles(List<Article> articles) {
    if (articles != null) {
      this.articles = new ArrayList<ArticleTx>();
      for (Article article : articles) {
        if (article instanceof ArticleTx) {
          this.articles.add((ArticleTx)article);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + article.getClass() + ", must use CloneableProperty annotation");
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
