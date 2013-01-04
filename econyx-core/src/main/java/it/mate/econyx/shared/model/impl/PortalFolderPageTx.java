package it.mate.econyx.shared.model.impl;

import it.mate.econyx.shared.model.PortalFolderPage;
import it.mate.econyx.shared.model.PortalPage;
import it.mate.gwtcommons.shared.model.CloneableBean;
import it.mate.gwtcommons.shared.model.CloneableProperty;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@CloneableBean (overrideTargetClassName="it.mate.econyx.server.model.impl.PortalFolderPageDs")
public class PortalFolderPageTx extends WebContentPageTx implements PortalFolderPage {

  private List<PortalPageTx> childreen = new ArrayList<PortalPageTx>();
  
  private Boolean hideChildreen;
  
  private Boolean showChildreenContent;
  
  public List<PortalPage> getChildreen() {
    if (childreen == null)
      return null;
    
    return new AbstractList<PortalPage>() {
      public int size() {
        return childreen.size();
      }
      public PortalPage get(int index) {
        return childreen.get(index);
      }
      public PortalPage set(int index, PortalPage element) {
        if (element instanceof PortalPageTx) {
          return childreen.set(index, (PortalPageTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean add(PortalPage element) {
        if (element instanceof PortalPageTx) {
          return childreen.add((PortalPageTx)element);
        }
        if (element == null) {
          throw new IllegalArgumentException("null");
        } else {
          throw new IllegalArgumentException(element.getClass().getName());
        }
      }
      public boolean remove(Object o) {
        return childreen.remove(o);
      }
    };
    
  }

  @CloneableProperty (targetClass=PortalPageTx.class)
  public void setChildreen(List<PortalPage> childreen) {
    this.childreen = new ArrayList<PortalPageTx>();
    if (childreen != null) {
      for (PortalPage child : childreen) {
        if (child instanceof PortalPageTx) {
          this.childreen.add((PortalPageTx)child);
          ((PortalPageTx)child).parent = this;
        }
      }
    }
  }

  public Boolean getHideChildreen() {
    return hideChildreen != null ? hideChildreen : false;
  }

  public void setHideChildreen(Boolean hideChildreen) {
    this.hideChildreen = hideChildreen;
  }

  public Boolean getShowChildreenContent() {
    return showChildreenContent != null ? showChildreenContent : false;
  }

  public void setShowChildreenContent(Boolean showChildreenContent) {
    this.showChildreenContent = showChildreenContent;
  }
  
}
