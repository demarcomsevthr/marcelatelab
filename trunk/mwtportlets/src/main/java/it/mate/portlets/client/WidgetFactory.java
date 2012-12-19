package it.mate.portlets.client;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Widget;

public interface WidgetFactory<W extends Widget> extends Serializable {

  public W createWidget();

  public void refresh(W widget);
  
}
