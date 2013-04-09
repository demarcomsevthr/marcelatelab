package it.mate.econyx.shared.model;

import java.util.List;


public interface Blog extends PortalResource, CodedEntity, PortalEntity {

  public String getTitle();

  public void setTitle(String title);

  public List<BlogDiscussion> getDiscussions();

  public void setDiscussions(List<BlogDiscussion> discussions);
  
}
