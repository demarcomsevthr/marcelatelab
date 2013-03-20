package it.mate.econyx.shared.model;

import java.util.List;

public interface ArticleFolder extends PortalEntity {

  public String getId();

  public void setId(String id);

  public String getName();

  public void setName(String name);
  
  public Integer getOrderNm();
  
  public void setOrderNm(Integer orderNm);

  public String getCode();

  public void setCode(String code);
  
  public List<Article> getArticles();
  
  public void setArticles(List<Article> articles);
  
  public String getSelectedArticleCode();

  public void setSelectedArticleCode(String selectedArticleCode);
  
}
