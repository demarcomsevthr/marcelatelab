package it.mate.econyx.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Post extends Serializable {

  public String getId();

  public String getCode();

  public String getBody();
  
  public List<PostComment> getPostComments();
  
  public String getTitle();

  public PortalUser getAuthor();

  public Date getCreated();

  public Integer getCommentsCount();

  public String getTags();

}
