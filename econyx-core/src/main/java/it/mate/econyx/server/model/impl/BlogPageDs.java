package it.mate.econyx.server.model.impl;

import it.mate.commons.server.model.CacheableEntity;
import it.mate.commons.server.model.UnownedRelationship;
import it.mate.econyx.shared.model.Blog;
import it.mate.econyx.shared.model.BlogPage;
import it.mate.econyx.shared.model.impl.BlogPageTx;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;
import it.mate.gwtcommons.shared.model.CloneablePropertyMissingException;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true")
@CacheableEntity (txClass=BlogPageTx.class, instanceCache=true)
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.BlogPageTx")
public class BlogPageDs extends AbstractPortalFolderPageDs implements BlogPage {

  @Persistent (dependentKey="false")
  Key entityKey;
  
  @UnownedRelationship (key="entityKey")
  transient BlogDs entity;

  public Blog getEntity() {
    return entity;
  }

  @CloneableProperty (targetClass=BlogDs.class)
  public void setEntity(Blog entity) {
    if (entity == null) {
      this.entityKey = null;
      this.entity = null;
    } else if (entity instanceof BlogDs) {
      this.entity = (BlogDs)entity;
      this.entityKey = this.entity.getKey();
    } else {
      throw new CloneablePropertyMissingException(entity);
    }
  }
  
}
