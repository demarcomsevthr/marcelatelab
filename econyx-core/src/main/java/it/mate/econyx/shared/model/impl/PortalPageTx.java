package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.PortalPage;

@SuppressWarnings("serial")
public abstract class PortalPageTx implements PortalPage {

  private String id;
  
  private String name;
  
  private String code;
  
  private String url;
  
  private Integer orderNm;
  
  protected PortalPageTx parent;
  
  protected String parentId;
  
  protected Boolean visibleInExplorer = true;
  
  protected Boolean visibleInMenu = true;
  
  protected Boolean homePage = false;
  
  protected String templateName;
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PortalPageTx other = (PortalPageTx) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "PortalPageTx [name=" + name + ", url=" + url + ", parentName=" + (parent != null ? parent.getName() : "null") + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  
  public Integer getOrderNm() {
    return orderNm;
  }

  public void setOrderNm(Integer orderNm) {
    this.orderNm = orderNm;
  }

  public PortalPage getParent() {
    return parent;
  }
  
  public void setParent(PortalPageTx parent) {
    this.parent = parent;
  }
  
  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Boolean getVisibleInExplorer() {
    return visibleInExplorer;
  }
  
  public boolean isVisibleInExplorer() {
    return visibleInExplorer != null ? visibleInExplorer : false;
  }
  
  public void setVisibleInExplorer(Boolean visibleInExplorer) {
    this.visibleInExplorer = visibleInExplorer;
  }

  public Boolean getVisibleInMenu() {
    return visibleInMenu;
  }

  public void setVisibleInMenu(Boolean visibleInMenu) {
    this.visibleInMenu = visibleInMenu;
  }

  public Boolean getHomePage() {
    return homePage != null ? homePage : false;
  }

  public void setHomePage(Boolean homePage) {
    this.homePage = homePage;
  }
  
  public String getTemplateName() {
    return templateName != null ? templateName : "home";
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  
}
