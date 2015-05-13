package it.mate.onscommons.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.utils.TransitionUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class OnsList extends HTMLPanel {

  private final static String TAG_NAME = "ons-list";
  
  private Element listElement;
  
  public OnsList() {
    this(TAG_NAME, "");
  }
  
  public OnsList(String html) {
    this(TAG_NAME, html);
  }
  
  protected OnsList(String tag, String html) {
    super(tag, html);
    getElement().addClassName(TAG_NAME);
    OnsenUi.ensureId(getElement());
  }

  @Override
  public void add(final Widget widget) {
    super.add(widget, getElement());
    
    String id = getElement().getId();
    GwtUtils.onAvailable(id, new Delegate<Element>() {
      public void execute(Element listElem) {
        listElement = listElem;
        Element itemElem = widget.getElement();
        listElem.appendChild(itemElem);
        OnsenUi.compileElement(listElem);
        OnsenUi.compileElement(itemElem);
      }
    });
    
    /*
    Element listElem = DOM.getElementById(id);
    Element itemElem = widget.getElement();
    listElem.appendChild(itemElem);
    OnsenUi.compileElement(listElem);
    */
    
  }
  
  public void insert(final Widget widget, final int beforeIndex) {
    String id = getElement().getId();
    GwtUtils.onAvailable(id, new Delegate<Element>() {
      public void execute(Element listElem) {
        listElement = listElem;
        Element itemElem = widget.getElement();
        Node beforeChild = listElem.getChild(beforeIndex);
        listElem.insertBefore(itemElem, beforeChild);
        OnsenUi.compileElement(listElem);
        OnsenUi.compileElement(itemElem);
      }
    });
  }
  
  public int getItemCount() {
    return listElement.getChildCount();
  }
  
  public void setModifier(final String modifier) {
    OnsenUi.onAttachedElement(this, new Delegate<Element>() {
      public void execute(Element element) {
        element.setAttribute("modifier", modifier);
      }
    });
  }

  public void compile() {
    GwtUtils.onAvailable(getElement().getId(), new Delegate<Element>() {
      public void execute(Element listElement) {
        OnsenUi.compileElement(listElement);
      }
    });
  }
  
  public void setAnimation(String animation) {
    TransitionUtils.fadeIn(getElement(), TransitionUtils.parseAttributeValue(animation).setDelay(0));
  }
  
  public void clear() {
    OnsenUi.onAvailableElement(this, new Delegate<Element>() {
      public void execute(Element onsListElement) {
        onsListElement.removeAllChildren();
      }
    });
  }
  
}
