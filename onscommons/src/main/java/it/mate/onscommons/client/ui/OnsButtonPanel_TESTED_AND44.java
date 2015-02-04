package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


public class OnsButtonPanel_TESTED_AND44 extends HTMLPanel implements HasTapHandler {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  public OnsButtonPanel_TESTED_AND44() {
    this("");
  }

  public OnsButtonPanel_TESTED_AND44(String html) {
    super("ons-button", "");
    getElement().setInnerHTML(html);
    getElement().addClassName("ons-button");
    PhgUtils.ensureId(getElement());
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
