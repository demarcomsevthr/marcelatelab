package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;

public interface PostComment extends Serializable {

  public String getBody();
  
  public void setBody(String body);
  
  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getPosted();

  public void setPosted(Date posted);
  
}
