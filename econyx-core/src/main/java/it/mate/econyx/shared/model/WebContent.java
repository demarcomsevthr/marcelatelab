package it.mate.econyx.shared.model;

import java.util.List;

public interface WebContent extends PortalResource {

  public List<HtmlContent> getHtmls();
  
  public void setHtmls(List<HtmlContent> htmls);
  
  public boolean areHtmlsInitialized();
  
  public void ensureHtmls();
  
  public HtmlContent getHtmlContent(HtmlContent.Type type);
  
}
