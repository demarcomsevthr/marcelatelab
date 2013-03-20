package it.mate.econyx.shared.model;

import java.io.Serializable;


public interface WebContentSimple extends PortalResource, Serializable {

  public HtmlContent getHtml();
  
  public void setHtml(HtmlContent html);
  
}
