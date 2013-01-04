package it.mate.econyx.server.model.impl;

import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.server.dao.EntityRelationshipsResolverHandler;
import it.mate.gwtcommons.server.model.UnownedRelationship;
import it.mate.gwtcommons.server.utils.CollectionUtils;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable (detachable="true", identityType=IdentityType.APPLICATION)
@Inheritance (strategy = InheritanceStrategy.SUBCLASS_TABLE)
@CloneableBean (overrideTargetClassName="it.mate.econyx.shared.model.impl.PortalFolderPageTx")
public abstract class AbstractPortalFolderPageDs extends AbstractWebContentPageDs implements PortalFolderPage, EntityRelationshipsResolverHandler {

  @Persistent (dependentKey="false", defaultFetchGroup="true")
  List<Key> childreenKeys;
  @UnownedRelationship (key="childreenKeys", itemClass=AbstractPortalPageDs.class)
  transient List<AbstractPortalPageDs> childreen;
  transient private boolean childreenResolved = false;

  @Persistent
  private Boolean hideChildreen;
  
  @Persistent
  private Boolean showChildreenContent;
  
  @Override
  public void setId(String id) {
    super.setId(id);
    if (childreen != null) {
      for (AbstractPortalPageDs child : childreen) {
        child.parentId = getKey();
      }
    }
  }

  @Override
  public void onBeforeResolveRelationships() {
    super.onBeforeResolveRelationships();
    if (!childreenResolved) {
      childreenKeys = null;
    }
  }
  
  public void resolveChildreen () {
    CollectionUtils.traverseCollection(childreenKeys);
    childreenResolved = true;
  }
  
  public List<PortalPage> getChildreen() {
    return childreen != null ? new ArrayList<PortalPage>(childreen) : null;
  }
  
  @CloneableProperty (targetClass=AbstractPortalPageDs.class)
  public void setChildreen(List<PortalPage> childreen) {
    this.childreen = new ArrayList<AbstractPortalPageDs>();
    this.childreenKeys = new ArrayList<Key>();
    if (childreen != null) {
      for (PortalPage child : childreen) {
        if (child instanceof AbstractPortalPageDs) {
          AbstractPortalPageDs childDs = (AbstractPortalPageDs)child;
          attachChild(childDs);
        } else {
          throw new IllegalArgumentException("cannot add item of type " + child.getClass() + ", you have to use CloneableProperty");
        }
      }
    }
  }
  
  public void attachChild(AbstractPortalPageDs childDs) {
    this.childreen.add(childDs);
    this.childreenKeys.add(childDs.getKey());
    childDs.parentId = this.getKey();
    childDs.parentKind = this.getClass().getSimpleName();
  }
  
  public void removeChild(AbstractPortalPageDs childToRemoveDs) {
    if (childreen != null) {
      for (Iterator<AbstractPortalPageDs> it = childreen.iterator(); it.hasNext();) {
        AbstractPortalPageDs childDs = it.next();
        if (childDs.getId().equals(childToRemoveDs.getId())) {
          if (childreenKeys != null) {
            childreenKeys.remove(childDs.getKey());
          }
          it.remove();
        }
      }
    }
  }

  public Boolean getHideChildreen() {
    return hideChildreen;
  }

  public void setHideChildreen(Boolean hideChildreen) {
    this.hideChildreen = hideChildreen;
  }

  public Boolean getShowChildreenContent() {
    return showChildreenContent;
  }

  public void setShowChildreenContent(Boolean showChildreenContent) {
    this.showChildreenContent = showChildreenContent;
  }
  
}
