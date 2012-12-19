package it.mate.portlets.client.ui;

import it.mate.portlets.client.layout.Container;
import it.mate.portlets.client.layout.LayoutConstraint;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;

public abstract class ContainerDelegatePortlet extends Portlet implements Container {

  protected abstract Container getDelegate();
  
  public void add(Widget w) {
    getDelegate().add(w);
  }

  public void clear() {
    getDelegate().clear();
  }

  public Iterator<Widget> iterator() {
    return getDelegate().iterator();
  }

  public boolean remove(Widget w) {
    return getDelegate().remove(w);
  }

  public void add(Widget w, LayoutConstraint constraints) {
    getDelegate().add(w, constraints);
  }
  
}
