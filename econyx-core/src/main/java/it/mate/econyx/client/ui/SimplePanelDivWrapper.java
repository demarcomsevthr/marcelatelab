package it.mate.econyx.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;

public class SimplePanelDivWrapper extends SimplePanel {

  private Element wrappedElement;
  
  public static SimplePanelDivWrapper create(Element wrappedElement) {
    if ("div".equalsIgnoreCase(wrappedElement.getTagName())) {
      return new SimplePanelDivWrapper(wrappedElement);
    }
    throw new IllegalArgumentException("Impossibile creare " + SimplePanelDivWrapper.class.getName() + " con element " + wrappedElement);
  }
  
  protected SimplePanelDivWrapper(Element wrappedElement) {
    super(wrappedElement);
    this.wrappedElement = wrappedElement;
  }
  
  public Element getWrappedElement() {
    return wrappedElement;
  }
  
}
