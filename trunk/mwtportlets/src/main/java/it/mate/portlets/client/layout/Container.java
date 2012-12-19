package it.mate.portlets.client.layout;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public interface Container extends HasWidgets {
  
  void add(Widget w, LayoutConstraint constraint);
  
}
