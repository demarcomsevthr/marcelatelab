package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.event.NativeGestureEvent;
import it.mate.onscommons.client.event.NativeGestureHandler;
import it.mate.onscommons.client.event.OnsEventUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.text.shared.testing.PassthroughParser;
import com.google.gwt.text.shared.testing.PassthroughRenderer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ValueBoxBase;

public abstract class OnsTextBoxBase extends /* Widget */ ValueBoxBase<String>{
  
  private HandlerRegistration onsChangeHandler = null;
  
  protected OnsTextBoxBase(String type) {
    this(DOM.createInputText(), type, "ons-textbox");
  }

  protected OnsTextBoxBase(Element element, String type, String className) {
    super(element, PassthroughRenderer.instance(), PassthroughParser.instance());
    OnsenUi.ensureId(element);
    if (type != null) {
      element.setAttribute("type", type);
    }
    if (className != null) {
      element.addClassName(className);
    }
  }
  
  public void setPlaceholder(String placeholder) {
    getElement().setAttribute("placeholder", placeholder);
  }
  
  @Override
  public String getValue() {
    return getText();
  }
  
  @Override
  public String getText() {
    String id = getElement().getId();
    String text = null;
    Element elem = DOM.getElementById(id);
    if (elem != null) {
      text = elem.getPropertyString("value");
    }
    if (text == null) {
      text = "";
    }
    return text;
  }
  
  @Override
  public void setValue(String value, boolean fireEvents) {
    setText(value);
    if (fireEvents) {
      ValueChangeEvent.fire(OnsTextBoxBase.this, value);
    }
  }
  
  @Override
  public void setText(final String text) {
    /* 20/05/2015 */
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        element.setPropertyString("value", text);
        element.setAttribute("value", text);
        OnsenUi.compileElement(element);
      }
    });
    /* PREV VERSION:
    String id = getElement().getId();
    Element elem = getElementByIdImpl(id);
    GwtUtils.onAvailable(id, new Delegate<Element>() {
      public void execute(Element elem) {
        elem.setPropertyString("value", text);
        OnsenUi.compileElement(elem);
      }
    });
    */
  }

  protected static native Element getElementByIdImpl(String elementId) /*-{
    return $doc.getElementById(elementId);
  }-*/;
  
  public void setDisabled(String disabled) {
    if (Boolean.parseBoolean(disabled)) {
      OnsenUi.onAvailableElement(this, new Delegate<Element>() {
        public void execute(Element element) {
          element.setAttribute("disabled", "");
        }
      });
    }
  }
  
  public void setReadonly(String readonly) {
    if (Boolean.parseBoolean(readonly)) {
      OnsenUi.onAvailableElement(this, new Delegate<Element>() {
        public void execute(Element element) {
          element.setAttribute("readonly", "");
        }
      });
    }
  }
  
  public void setType(final String type) {
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        element.setAttribute("type", type);
      }
    });
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> handler) {
    if (onsChangeHandler == null) {
      OnsenUi.onAvailableElement(this, new Delegate<Element>() {
        public void execute(Element element) {
          onsChangeHandler = OnsEventUtils.addChangeHandler(element, new NativeGestureHandler() {
            public void on(NativeGestureEvent event) {
              PhgUtils.log("ONS CHANGE EVENT ");
              String value = event.getTarget().getPropertyString("value");
              PhgUtils.log("ONS CHANGE EVENT " + value);
              ValueChangeEvent.fire(OnsTextBoxBase.this, value);
            }
          });
        }
      });
    }
    return super.addValueChangeHandler(handler);
  }
  
}
