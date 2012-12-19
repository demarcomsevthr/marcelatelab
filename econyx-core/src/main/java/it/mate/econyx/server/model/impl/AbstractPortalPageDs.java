package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.impl.PortalPageTx;
import it.mate.gwtcommons.server.dao.EntityRelationshipsResolverHandler;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.HasKey;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@CacheableEntity (txClass=PortalPageTx.class)
@SuppressWarnings("serial")
@PersistenceCapable (detachable="true", identityType=IdentityType.APPLICATION)
@Inheritance (strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class AbstractPortalPageDs implements PortalPage, HasKey, EntityRelationshipsResolverHandler {
  
  @PrimaryKey
  @Persistent
  Key id;
  
  @Persistent
  String name;
  
  @Persistent
  String code;
  
  @Persistent
  String url;
  
  @Persistent
  Integer orderNm;
  
  @Persistent (dependentKey="false")
  Key parentId;
  
  @Persistent
  String parentKind;

  @Persistent
  Boolean visibleInExplorer = true;

  @Persistent
  Boolean visibleInMenu = true;
  
  @Persistent
  Boolean homePage;
  
  @Persistent
  String templateName;

  
  @Override
  public String toString() {
    return "id=" + getKey() + "name=" + name + ", url=" + url + ", parentId=" + parentId;
  }
  
  @Override
  public void onBeforeResolveRelationships() {
    
  }
  
  public String getId() {
    return getKey() != null ? KeyFactory.keyToString(getKey()) : null;
  }

  public void setId(String id) {
    setKey( id != null ? KeyFactory.stringToKey(id) : null );
  }
  
  public Key getKey() {
    return id;
  }
  
  public void setKey(Key key) {
    this.id = key;
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
    return null;
  }
  
  public void setParentId(String parentId) {
    this.parentId = parentId != null ? KeyFactory.stringToKey(parentId) : null;
  }
  
  public String getParentId() {
    return parentId != null ? KeyFactory.keyToString(parentId) : null;
  }

  public Boolean getVisibleInExplorer() {
    return visibleInExplorer;
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
    return homePage;
  }
  
  public void setHomePage(Boolean homePage) {
    this.homePage = homePage;
  }

  public String getTemplateName() {
    return templateName;
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
