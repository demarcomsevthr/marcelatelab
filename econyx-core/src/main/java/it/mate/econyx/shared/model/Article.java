package it.mate.econyx.shared.model;

import java.util.Date;
import java.util.List;


public interface Article extends PortalEntity, PortalResource {
  
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

  public Integer getCommentsCount();

  public void setCommentsCount(Integer commentsCount);
  
  public String getTags();

  public void setTags(String tags);
  
  /*
  public ArticleFolder getFolder();

  public void setFolder(ArticleFolder folder);
  */
  
}
