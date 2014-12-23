package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.onscommons.client.utils.OnsUtils;
import it.mate.onscommons.client.utils.callbacks.JSOCallback;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class OnsButton extends Widget {

  public OnsButton() {
    this(DOM.createElement("ons-button"));
//  setStyleName("gwt-Button");
  }

  protected OnsButton(Element element) {
    element.setId("onsButton");
    setElement(element);
  }
  
  public void setText(String text) {
    getElement().setInnerText(text);
  }
  
  public void onClickTest(JSOCallback callback) {
    OnsUtils.log("setting handler on element " + getElement());
//  onClickImpl(getElement(), callback);
    onClickImpl(JQuery.withElement(getElement()), callback);
//  onClickImpl(getElementImpl("onsButton"), callback);
  }
  
  protected static native JavaScriptObject getElementImpl(String id) /*-{
    return $doc.getElementById(id);
  }-*/;
  
  protected static native void onClickImpl(JavaScriptObject element, JSOCallback callback) /*-{
    var jsCallback = $entry(function(event) {
      @it.mate.onscommons.client.utils.OnsUtils::log(Ljava/lang/String;)('JS CALLBACK');
      callback.@it.mate.onscommons.client.utils.callbacks.JSOCallback::handle(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
    });
//  element.on('click', jsCallback);
//  element.addEventListener('click', jsCallback);
//  element.onclick(jsCallback);
    element.click(jsCallback);
  }-*/;

}
