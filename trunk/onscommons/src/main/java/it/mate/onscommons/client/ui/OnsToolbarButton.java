package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


public class OnsToolbarButton extends HTMLPanel implements HasTapHandler {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  public OnsToolbarButton() {
    this("");
  }

  public OnsToolbarButton(String html) {
    super("ons-toolbar-button", html);
    hasTapHandlerImpl = new HasTapHandlerImpl(this);
  }

  @Override
  public void add(Widget widget) {
    super.add(widget, getElement());
  }

  @Override
  public HandlerRegistration addTapHandler(TapHandler handler) {
    return hasTapHandlerImpl.addTapHandler(handler);
  }
  
}