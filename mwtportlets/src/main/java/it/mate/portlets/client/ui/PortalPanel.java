package it.mate.portlets.client.ui;

import it.mate.portlets.client.events.BroadcastListener;
import it.mate.portlets.client.events.PageTemplateEvent;
import it.mate.portlets.client.layout.Container;
import it.mate.portlets.client.layout.LayoutConstraint;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPanel extends SimplePanel implements Container, BroadcastListener {
  
  public PortalPanel() {
    super();
  }

  @Override
  public void onBroadcast(PageTemplateEvent event) {
    
  }

  @Override
  public void add(Widget w, LayoutConstraint constraint) {
    this.clear();
    this.add(w);
  }

}
