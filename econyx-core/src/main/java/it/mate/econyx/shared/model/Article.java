package it.mate.econyx.shared.model;

import java.util.List;

public interface Article extends WebContentSimple, PortalEntity {
  
  public String getCode();

  public void setCode(String code);
  
  public List<ArticleComment> getComments();
  
  public void setComments(List<ArticleComment> comments);
  
}
