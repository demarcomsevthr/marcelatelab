package it.mate.econyx.shared.model;

import java.util.Date;
import java.util.List;


public interface Article extends PortalEntity {
  
  public String getId();

  public void setId(String id);

  public String getName();

  public void setName(String name);
  
  public Integer getOrderNm();
  
  public void setOrderNm(Integer orderNm);

  public String getCode();

  public void setCode(String code);
  
  public HtmlContent getHtml();
  
  public void setHtml(HtmlContent html);
  
  public List<ArticleComment> getComments();
  
  public void setComments(List<ArticleComment> comments);
  
  public String getTitle();

  public void setTitle(String title);

  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getCreated();

  public void setCreated(Date created);
  
}
