package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.econyx.shared.model.DocumentFolderPage;
import it.mate.econyx.shared.model.impl.DocumentFolderPageTx;
import it.mate.gwtcommons.server.model.CacheableEntity;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=DocumentFolderPageTx.class, instanceCache=true)
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.DocumentFolderPageTx")
public class DocumentFolderPageDs extends AbstractPortalFolderPageDs implements DocumentFolderPage {

  @Persistent (dependentKey="false")
  Key entityKey;
  
  @UnownedRelationship (key="entityKey")
  transient DocumentFolderDs entity;

  public DocumentFolder getEntity() {
    return entity;
  }

  @CloneableProperty (targetClass=DocumentFolderDs.class)
  public void setEntity(DocumentFolder entity) {
    if (entity == null) {
      this.entityKey = null;
      this.entity = null;
    } else if (entity instanceof DocumentFolderDs) {
      this.entity = (DocumentFolderDs)entity;
      this.entityKey = this.entity.getKey();
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }
  
}
