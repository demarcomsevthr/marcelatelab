package it.mate.econyx.shared.model;

import java.util.Date;


public interface CalEvent extends PortalEntity, PortalResource {
  
  public String getCode();

  public void setCode(String code);
  
  public Date getDate();

  public void setDate(Date date);
  
  public HtmlContent getHtml();
  
  public void setHtml(HtmlContent html);
  
  public String getTitle();

  public void setTitle(String title);

  public PortalUser getAuthor();

  public void setAuthor(PortalUser author);

  public Date getCreated();

  public void setCreated(Date created);
  
}
