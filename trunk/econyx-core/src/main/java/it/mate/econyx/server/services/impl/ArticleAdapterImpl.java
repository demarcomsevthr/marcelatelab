package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.model.impl.ArticleDs;
import it.mate.econyx.server.model.impl.ArticleFolderDs;
import it.mate.econyx.server.services.ArticleAdapter;
import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.HtmlContent;
import it.mate.econyx.shared.model.impl.ArticleFolderTx;
import it.mate.econyx.shared.model.impl.ArticleTx;
import it.mate.gwtcommons.server.dao.Dao;
import it.mate.gwtcommons.server.model.utils.OneToOneAdapterSupport;
import it.mate.gwtcommons.server.utils.CloneUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleAdapterImpl implements ArticleAdapter {

  private static Logger logger = Logger.getLogger(ArticleAdapterImpl.class);

  @Autowired private Dao dao;
  
  OneToOneAdapterSupport<ArticleDs, HtmlContent> htmlRelationshipSupport;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    htmlRelationshipSupport = new OneToOneAdapterSupport<ArticleDs, HtmlContent>(dao, "getHtml", "setHtml");
  }

  @Override
  public List<ArticleFolder> findAll() {
    List<ArticleFolderDs> articleFolders = dao.findAll(ArticleFolderDs.class);
    Collections.sort(articleFolders, new Comparator<ArticleFolder>() {
      public int compare(ArticleFolder f1, ArticleFolder f2) {
        return f1.getOrderNm() != null ? f1.getOrderNm().compareTo(f2.getOrderNm()) : 0;
      }
    });
    return CloneUtils.clone(articleFolders, ArticleFolderTx.class, ArticleFolder.class);
  }

  @Override
  public ArticleFolder update(ArticleFolder entity) {
    ArticleFolderDs articleFolderDs = CloneUtils.clone(entity, ArticleFolderDs.class);
    articleFolderDs.setArticles(updateArticles(articleFolderDs.getArticles()));
    articleFolderDs = dao.update(articleFolderDs);
    return CloneUtils.clone (articleFolderDs, ArticleFolderTx.class);
  }

  @Override
  public void delete(ArticleFolder entity) {
    ArticleFolderDs articleFolderDs = dao.findById(ArticleFolderDs.class, entity.getId());
    deleteArticles(articleFolderDs.getArticles());
    dao.delete(articleFolderDs);
  }

  @Override
  public ArticleFolder create(ArticleFolder entity) {
    ArticleFolderDs articleFolderDs = CloneUtils.clone(entity, ArticleFolderDs.class);
    articleFolderDs.setArticles(createArticles(articleFolderDs.getArticles()));
    articleFolderDs = dao.create(articleFolderDs);
    return CloneUtils.clone (articleFolderDs, ArticleFolderTx.class);
  }

  @Override
  public ArticleFolder findById(String id) {
    ArticleFolder ds = dao.findById(ArticleFolderDs.class, id);
    return CloneUtils.clone(ds, ArticleFolderTx.class);
  }
  
  private List<Article> createArticles(List<Article> articles) {
    if (articles != null) {
      for (int it = 0; it < articles.size(); it++) {
        ArticleDs articleDs = CloneUtils.clone(articles.get(it), ArticleDs.class);
        articleDs = createOrUpdateArticleDs(articleDs);
        articles.set(it, articleDs);
      }
    }
    return articles;
  }
  
  private void deleteArticles(List<Article> articles) {
    if (articles != null) {
      for (Article article : articles) {
        ArticleDs articleDs = CloneUtils.clone(article, ArticleDs.class);
        if (htmlRelationshipSupport != null) {
          htmlRelationshipSupport.onBeforeDelete(articleDs);
        }
        dao.delete(articleDs);
      }
    }
  }

  private List<Article> updateArticles(List<Article> articles) {
    if (articles != null) {
      for (int it = 0; it < articles.size(); it++) {
        ArticleDs articleDs = CloneUtils.clone(articles.get(it), ArticleDs.class);
        articleDs = createOrUpdateArticleDs(articleDs);
        articles.set(it, articleDs);
      }
    }
    return articles;
  }
  
  private ArticleDs createOrUpdateArticleDs (ArticleDs articleDs) {
    if (articleDs.getKey() == null) {
      if (htmlRelationshipSupport != null) {
        htmlRelationshipSupport.onBeforeCreate(articleDs);
      }
      articleDs = dao.create(articleDs);
    } else {
      if (htmlRelationshipSupport != null) {
        htmlRelationshipSupport.onBeforeUpdate(articleDs);
      }
      articleDs = dao.update(articleDs);
    }
    return articleDs;
  }
  
  public Article findByCode(String code) {
    ArticleDs articleDs = dao.findSingle(ArticleDs.class, "code == codeParam", String.class.getName() + " codeParam", null, code);
    return CloneUtils.clone(articleDs, ArticleTx.class);
  }

}
