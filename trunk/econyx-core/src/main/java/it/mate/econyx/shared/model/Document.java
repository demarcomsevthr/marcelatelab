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
  
  public String getBlobKey();

  public void setBlobKey(String blobKey);
    
}
