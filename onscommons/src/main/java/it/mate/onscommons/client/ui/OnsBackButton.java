package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.onscommons.client.event.HasTapHandler;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class OnsBackButton extends Widget implements HasTapHandler {
  
  private HasTapHandlerImpl hasTapHandlerImpl;
  
  public OnsBackButton() {
    this(DOM.createElement("ons-back-button"));
  }

  protected OnsBackButton(Element element) {
    element.addClassName("ons-back-button");
    setElement(element);
    if (OnsenUi.isSlidingMenuLayout()) {
      addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          goToPrevious();
        }
      });
    }
  }
  
  public void setText(String text) {
    getElement().setInnerText(text);
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
