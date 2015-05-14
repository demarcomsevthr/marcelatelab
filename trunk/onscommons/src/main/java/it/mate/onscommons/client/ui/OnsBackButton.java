package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.utils.TransitionUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class OnsBackButton extends Widget implements HasTapHandler {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  
  public OnsBackButton() {
    this(DOM.createElement("ons-toolbar-button"), true);
  }

  protected OnsBackButton(Element element, boolean doFadeIn) {
    
    Element icon = DOM.createElement("ons-icon");
    icon.setAttribute("icon", "fa-chevron-left");
    element.appendChild(icon);
    
    element.addClassName("ons-back-button");
    
    if (doFadeIn) {
      TransitionUtils.fadeIn(element, new TransitionUtils.Options().setDeferring(400).setDelay(400).setDuration(200));
    }
    
    setElement(element);
    
    addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        goToPrevious();
      }
    });
    
  }
  
  public void setText(String text) {
    OnsButtonBase.addElementText(getElement(), text);
  }
  
  @SuppressWarnings("rawtypes")
  protected void goToPrevious() {
    Widget parent = getParent();
    while (parent != null) {
      if (parent instanceof AbstractBaseView) {
        AbstractBaseView view = (AbstractBaseView)parent;
        view.getPresenter().goToPrevious();
        break;
      }
      parent = parent.getParent();
    }
  }

  public HandlerRegistration addTapHandler(TapHandler handler) {
    if (hasTapHandlerImpl == null) {
      hasTapHandlerImpl = new HasTapHandlerImpl(this);
    }
    return hasTapHandlerImpl.addTapHandler(handler);
  }
  
}
