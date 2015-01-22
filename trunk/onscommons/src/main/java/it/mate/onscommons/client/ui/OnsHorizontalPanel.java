package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsHorizontalPanel extends HorizontalPanel {
  
  private boolean addDirect = false;
  
  private Element actualTableElement;
  
  public OnsHorizontalPanel() {
    super();
    PhgUtils.ensureId(getElement());
  }
  
  public void setAddDirect(boolean addDirect) {
    this.addDirect = addDirect;
  }
  
  public boolean isAddDirect() {
    return addDirect;
  }
  
  @Override
  public void add(final Widget w) {
    if (addDirect || actualTableElement != null) {
      OnsHorizontalPanel.this.internalAdd(w);
    } else {
      GwtUtils.onAvailable(getElement().getId(), new Delegate<com.google.gwt.dom.client.Element>() {
        public void execute(com.google.gwt.dom.client.Element tableElement) {
          actualTableElement = (Element)tableElement;
          OnsHorizontalPanel.this.internalAdd(w);
        }
      });
    }
  }
  
  private void internalAdd(Widget w) {
    super.add(w);
//  PhgUtils.log("added " + w.getElement());
    String childId = w.getElement().getId();
    if (childId != null && !"".equals(childId)) {
      GwtUtils.onAvailable(childId, new Delegate<com.google.gwt.dom.client.Element>() {
        public void execute(com.google.gwt.dom.client.Element element) {
          OnsenUi.compileElement(element);
        }
      });
    }
      
  }

  @Override
  protected Element getBody() {
    if (actualTableElement != null) {
      return (Element)(actualTableElement.getElementsByTagName("tbody").getItem(0));
    } else {
      return super.getBody();
    }
  }
  
  @Override
  public void setWidth(final String width) {
    GwtUtils.onAvailable(getElement().getId(), new Delegate<com.google.gwt.dom.client.Element>() {
      public void execute(com.google.gwt.dom.client.Element containerElement) {
        GwtUtils.setJsPropertyString(containerElement.getStyle(), "width", width) ;
      }
    });
  }
  
}
