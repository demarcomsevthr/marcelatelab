package it.mate.onscommons.client.onsen.dom;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

public class Page extends JavaScriptObject {

  protected Page() { }
  
  public final String getName() {
    return GwtUtils.getJsPropertyString(this, "name");
  }
  
  public final Element getPageElement() {
    JavaScriptObject pageElement = GwtUtils.getJsPropertyJso(this, "element");
    Element pageContextElement = GwtUtils.getJsPropertyJso(pageElement, "context").cast();
    return pageContextElement;
  }
  
  public final Element getInnerPageElement() {
    Element pageElement = getPageElement();
    NodeList<Element> nodeList = pageElement.getElementsByTagName("div");
    for (int it = 0; it < nodeList.getLength(); it++) {
      Element innerElem = nodeList.getItem(it);
      if (innerElem.getClassName().contains("ons-page-inner")) {
        return innerElem;
      }
    }
    return null;
  }
  
  public final Integer getIndex() {
    JsArray<Page> pages = OnsenUi.getPages();
    for (int it = 0; it < pages.length(); it++) {
      Page page = pages.get(it);
      if (page.getName().equals(this.getName())) {
        return it;
      }
    }
    return null;
  }
  
  
}
