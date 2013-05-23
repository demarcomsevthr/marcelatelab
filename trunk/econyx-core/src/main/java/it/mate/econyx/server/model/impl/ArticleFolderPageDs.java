package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.econyx.shared.model.ArticleFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.impl.ArticleFolderPageTx;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=ArticleFolderPageTx.class, instanceCache=true)
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.ArticleFolderPageTx")
public class ArticleFolderPageDs extends AbstractWebContentPageDs implements ArticleFolderPage {

  @Persistent (dependentKey="false")
  Key entityId;
  
  @UnownedRelationship (key="entityId")
  transient ArticleFolderDs entity;

  @Override
  public ArticleFolder getEntity() {
    return entity;
  }

  @Override
  @CloneableProperty (targetClass=ArticleFolderDs.class)
  public void setEntity(ArticleFolder entity) {
    if (entity == null) {
      this.entity = null;
      this.entityId = null;
    } else {
      this.entity = (ArticleFolderDs)entity;
      this.entityId = this.entity.getKey();
    }
  }

  @Override
  public List<PortalPage> getChildreen() {
    return null;
  }

  @Override
  public void setChildreen(List<PortalPage> childreen) {

  }

  @Override
  public Boolean getHideChildreen() {
    return null;
  }

  @Override
  public void setHideChildreen(Boolean flag) {
    
  }

  @Override
  public Boolean getShowChildreenContent() {
    return null;
  }

  @Override
  public void setShowChildreenContent(Boolean value) {
    
  }
 
}
