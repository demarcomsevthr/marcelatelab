package it.mate.econyx.client.view.site.custom;

import it.mate.gwtcommons.client.ui.DivGridPanel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageMenuViewCustomizerImpl extends it.mate.econyx.client.view.custom.PortalPageMenuViewCustomizerImpl {

  @Override
  public ComplexPanel getMenuContainerPanel() {
    ComplexPanel result = new DivGridPanel() {
      @Override
      protected Element createContainerElement() {
        Element container1Element = DOM.createDiv();
        container1Element.setClassName("navPropertiesContainer");
        Element container2Element = DOM.createDiv();
        container2Element.setClassName("navSizeContainer");
        container1Element.appendChild(container2Element);
        return container2Element;
      }
      @Override
      public void add(Widget widget) {
        Element cellDivElem = DOM.createDiv();
        cellDivElem.setClassName("navElement");
        containerElement.appendChild(cellDivElem);
        super.add(widget, cellDivElem);
      }
    };
    return result;
  }
  
}
