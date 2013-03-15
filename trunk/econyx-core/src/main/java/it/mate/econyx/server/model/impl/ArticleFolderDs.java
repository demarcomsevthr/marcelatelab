package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.server.model.HasKey;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
public class ArticleFolderDs implements ArticleFolder, HasKey {

  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  Key id;

  @Persistent
  String code;
  
  @Persistent
  String name;
  
  @Persistent
  Integer orderNm;
  
  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> articlesKeys;
  
  @UnownedRelationship (key="articlesKeys", itemClass=ArticleDs.class)
  transient List<ArticleDs> articles;

  public Key getKey() {
    return id;
  }

  public String getId() {
    return id != null ? KeyFactory.keyToString(id) : null;
  }

  public void setId(String id) {
    this.id = id != null ? KeyFactory.stringToKey(id) : null;
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

  public List<Key> getArticlesKeys() {
    return articlesKeys;
  }

  public void setArticlesKeys(List<Key> articlesKeys) {
    this.articlesKeys = articlesKeys;
  }

  public List<Article> getArticles() {
    return articles != null ? new ArrayList<Article>(articles) : null;
  }

  @CloneableProperty (targetClass=ArticleDs.class)
  public void setArticles(List<Article> articles) {
    this.articles = new ArrayList<ArticleDs>();
    this.articlesKeys = new ArrayList<Key>();
    if (articles != null) {
      for (Article article : articles) {
        if (article instanceof ArticleDs) {
          attachArticle((ArticleDs)article);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + article.getClass() + ", forget CloneableProperty annotation?");
        }
      }
    }
  }
  
  private void attachArticle(ArticleDs article) {
    if (articles == null)
      articles = new ArrayList<ArticleDs>();
    this.articles.add(article);
    if (articlesKeys == null)
      articlesKeys = new ArrayList<Key>();
    this.articlesKeys.add(article.getKey());
  }

  @Override
  public String getSelectedArticleCode() {
    return null;
  }

  @Override
  public void setSelectedArticleCode(String selectedArticleCode) {
    
  }
  
}
