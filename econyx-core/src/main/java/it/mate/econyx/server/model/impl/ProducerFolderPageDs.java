package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.PortalPage;
import it.mate.econyx.shared.model.ProducerFolderPage;
import it.mate.econyx.shared.model.Produttore;
import it.mate.econyx.shared.model.impl.ProducerFolderPageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=ProducerFolderPageTx.class, instanceCache=true)
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.ProducerFolderPageTx")
public class ProducerFolderPageDs extends AbstractPortalFolderPageDs implements ProducerFolderPage {

  @Persistent (dependentKey="false")
  Key entityKey;
  
  @UnownedRelationship (key="entityKey")
  transient ProduttoreDs entity;
  
  transient List<PortalPage> childreen;

  public Produttore getEntity() {
    return entity;
  }

  @CloneableProperty (targetClass=ProduttoreDs.class)
  public void setEntity(Produttore entity) {
    if (entity == null) {
      this.entityKey = null;
      this.entity = null;
    } else if (entity instanceof ProduttoreDs) {
      this.entity = (ProduttoreDs)entity;
      this.entityKey = this.entity.getKey();
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }
  
  @Override
  public List<PortalPage> getChildreen() {
    return childreen;
  }
  
  @Override
  public void setChildreen(List<PortalPage> childreen) {
    this.childreen = childreen;
  }
  
  @Override
  public void attachChild(AbstractPortalPageDs childDs) {

  }
  
  @Override
  public void removeChild(AbstractPortalPageDs childToRemoveDs) {

  }
  
}
