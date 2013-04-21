package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.Article;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.ArticleFolderPageDs")
public class ArticleFolderPageTx extends WebContentPageTx implements ArticleFolderPage {

  private ArticleFolder entity;
  
  @Override
  public String toString() {
    return "ArticleFolderPageTx [entity=" + entity + "]";
  }

  @Override
  public ArticleFolder getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=ArticleFolderTx.class)
  public void setEntity(ArticleFolder entity) {
    this.entity = entity;
  }
  
  @Override
  public String getLocationHash() {
    if (entity != null && entity.getSelectedArticleCode() != null) {
      return "page="+getCode()+",article="+entity.getSelectedArticleCode();
    }
    return super.getLocationHash();
  }

  @Override
  public List<PortalPage> getChildreen() {
    List<PortalPage> childreen = new ArrayList<PortalPage>();
    if (entity != null && entity.getArticles() != null) {
      for (Article article : entity.getArticles()) {
        ArticlePageTx articlePage = new ArticlePageTx(article);
        articlePage.setParent(this);
        childreen.add(articlePage);
      }
    }
    return childreen;
  }

  @Override
  public void setChildreen(List<PortalPage> childreen) {
    
  }

  @Override
  public Boolean getHideChildreen() {
    return false;
  }

  @Override
  public void setHideChildreen(Boolean flag) {
    
  }

  @Override
  public Boolean getShowChildreenContent() {
    return false;
  }

  @Override
  public void setShowChildreenContent(Boolean value) {
    
  }
  
}
