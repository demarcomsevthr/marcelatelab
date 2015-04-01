package com.google.gwt.dom.client;

import it.mate.phgcommons.client.utils.PhgUtils;


public class DOMImplWebkitPatched extends DOMImplWebkit {
  
  public DOMImplWebkitPatched() {
    super();
    PhgUtils.log("----- USING " + DOMImplWebkitPatched.class);
  }
  
  @Override
  public Element createElement(Document doc, String tag) {
    Element element = super.createElement(doc, tag);
    ensureId(element);
    return element;
  }
  
  protected void ensureId(Element element) {
    if (element.getId() == null || "".equals(element.getId())) {
      element.setId(createUniqueId());
    }
  }
  
  protected final native String createUniqueId() /*-{
    // In order to force uid's to be document-unique across multiple modules,
    // we hang a counter from the document.
    if (!this.ons_uid) {
      this.ons_uid = 1;
    }
  
    return "ons-uid-" + this.ons_uid++;
  }-*/;
  
}
