package it.mate.onscommons.client.ui;

import it.mate.onscommons.client.utils.CdvUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class OnsButton extends Widget {
  
  public OnsButton() {
    this(DOM.createElement("ons-button"));
  }

  protected OnsButton(Element element) {
    if (element.getId() == null || "".equals(element.getId())) {
      element.setId(DOM.createUniqueId());
    }
    element.addClassName("ons-button");
    setElement(element);
  }
  
  public void setText(String text) {
    getElement().setInnerText(text);
  }
  
  public void onClickTest(final JSOCallback callback) {
    CdvUtils.log("setting handler on element " + getElement());
    
    /*
    HammerUtils.onPress(getElement(), new Delegate<Element>() {
      public void execute(Element element) {
        callback.handle(element);
      }
    });
    */
    
    onClickImpl(getElement(), callback);
    
  }
  
  protected static native void onClickImpl(JavaScriptObject element, JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      @it.mate.onscommons.client.utils.CdvUtils::log(Ljava/lang/String;)('JS CALLBACK');
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
    $doc.addEventListener("click", function(e) {
      if (@it.mate.onscommons.client.ui.OnsButton::isContained(Lcom/google/gwt/dom/client/Element;Lcom/google/gwt/dom/client/Element;)(e.target, element)) {
        callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(e);
      }
    }, false);    
  }-*/;
  
  private static native boolean isContained(Element elem, Element container) /*-{
    do {
      if (elem.id == container.id) {
        return true;
      }
    } while(elem = elem.offsetParent );
    return false;
  }-*/;

}
