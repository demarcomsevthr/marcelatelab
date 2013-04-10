package it.mate.econyx.shared.model;

import java.util.Date;
import java.util.List;


public interface BlogDiscussion extends PortalResource, CodedEntity, PortalEntity {

  public String getTitle();

  public void setTitle(String title);

  public String getContent();
  
  public void setContent(String content);
  
  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getCreated();

  public void setCreated(Date created);
  
  public List<BlogComment> getComments();

  public void setComments(List<BlogComment> comments);
  
}
