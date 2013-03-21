package it.mate.econyx.shared.model;

import java.util.List;

public interface ArticleFolder extends PortalEntity, PortalResource {

  public String getCode();

  public void setCode(String code);
  
  public List<Article> getArticles();
  
  public void setArticles(List<Article> articles);
  
  public String getSelectedArticleCode();

  public void setSelectedArticleCode(String selectedArticleCode);
  
}
