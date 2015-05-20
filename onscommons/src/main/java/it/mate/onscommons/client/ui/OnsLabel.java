package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Label;

public class OnsLabel extends Label {

  public OnsLabel() {
    this("");
  }

  public OnsLabel(String text) {
    super(text);
    OnsenUi.ensureId(getElement());
    addStyleName("ons-label");
  }
  
  @Override
  public void setText(final String text) {
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        element.setInnerText(text);
      }
    });
    /*
    String id = getElement().getId();
    if (id != null && !"".equals(id.trim())) {
      GwtUtils.onAvailable(id, new Delegate<Element>() {
        public void execute(Element element) {
          element.setInnerText(text);
        }
      });
    } else {
      super.setText(text);
    }
    */
  }
  
  public void setHtml(final String html) {
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        element.setInnerHTML(html);
      }
    });
  }

}
