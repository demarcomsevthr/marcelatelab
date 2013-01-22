package it.mate.econyx.shared.model;


public interface PortalPage extends PortalResource {
  
  public String getCode();
  
  public void setCode(String code);

  public String getUrl();

  public void setUrl(String url);
  
  public PortalPage getParent();
  
  public void setParentId(String parentId);
  
  public String getParentId();
  
  
  public Boolean getVisibleInExplorer();

  public void setVisibleInExplorer(Boolean visibleInExplorer);
  
  public Boolean getVisibleInMenu();

  public void setVisibleInMenu(Boolean visibleInMenu);
  
  
  public Boolean getHomePage();

  public void setHomePage(Boolean homePage);
  
  
  public String getTemplateName();
  
  public void setTemplateName(String templateName);

}
