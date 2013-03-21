package it.mate.econyx.shared.model;

import java.util.Date;

public interface ArticleComment extends PortalResource {

  public String getContent();
  
  public void setContent(String content);
  
  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getPosted();

  public void setPosted(Date posted);
  
}
