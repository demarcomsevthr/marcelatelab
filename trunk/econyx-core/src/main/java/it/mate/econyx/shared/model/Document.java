package it.mate.econyx.shared.model;

import java.util.Date;

public interface Document extends PortalResource {

  public String getCode();

  public void setCode(String code);
  
  public String getTitle();

  public void setTitle(String title);

  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getCreated();

  public void setCreated(Date created);
  
  public DocumentContent getContent();
  
  public void setContent(DocumentContent content);
    
}
