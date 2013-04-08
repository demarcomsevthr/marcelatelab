package it.mate.econyx.shared.model;

import java.util.List;


public interface Blog extends PortalResource, PortalEntity {

  public String getCode();

  public void setCode(String code);
  
  public String getTitle();

  public void setTitle(String title);

  public List<BlogDiscussion> getDiscussions();

  public void setDiscussions(List<BlogDiscussion> discussions);
  
}
