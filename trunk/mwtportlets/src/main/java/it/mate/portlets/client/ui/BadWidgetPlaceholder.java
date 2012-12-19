package it.mate.portlets.client.ui;

import it.mate.portlets.client.WidgetFactory;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BadWidgetPlaceholder extends VerticalPanel {

  public BadWidgetPlaceholder(WidgetFactory wf, Exception ex) {
    super();
    add(new Label("BadWidgetPlaceholder"));
    if (wf != null) {
      add(new Label("WidgetFactory = " + wf.getClass().getName()));
    }
    if (ex != null) {
      add(new Label("Exception = " + ex.getClass().getName()+ " - " + ex.getMessage()));
    }
  }
  
}
