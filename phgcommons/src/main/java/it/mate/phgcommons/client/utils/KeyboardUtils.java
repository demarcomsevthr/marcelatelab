package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.phgcommons.client.ui.TouchButton;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.RootPanel;



public class KeyboardUtils {
  
  private static final String FOCUSED_ELEMENT_KEY = "phgFocusedElement";
  
  
  private static TouchButton doneButton;
  
  
  
  public static void enableDoneButtonSurrogate() {
    
    PhgUtils.log("attaching focus and blur handler to input fields");
    
    
    GwtUtils.createTimer(1000, new Delegate<Void>() {
      public void execute(Void element) {
        Element focusedElement = getActiveElementImpl();
        if (focusedElement != null && focusedElement.getNodeName().equalsIgnoreCase("input")) {
          String value = focusedElement.getAttribute(FOCUSED_ELEMENT_KEY);
          if (value == null || "".equals(value)) {
            PhgUtils.log("focusedElement = " + focusedElement);
            focusedElement.setAttribute(FOCUSED_ELEMENT_KEY, "true");
            showDoneButton();
          }
          int top = focusedElement.getParentElement().getAbsoluteTop() - 3;
          int left = focusedElement.getParentElement().getAbsoluteRight() - doneButton.getOffsetWidth();
          if (top > 0 && left > 0) {
            PhgUtils.log("focusedElement position = " + top + " " + left);
            doneButton.getElement().getStyle().setTop(top, Unit.PX);
            doneButton.getElement().getStyle().setLeft(left, Unit.PX);
          }
        }
      }
    });

    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        
        JQuery.select(":input").blur(new Delegate<Element>() {
          public void execute(Element bluredElement) {
            PhgUtils.log("bluredElement = " + bluredElement);
            bluredElement.setAttribute(FOCUSED_ELEMENT_KEY, "");
            hideDoneButton();
          }
        });
      }
    });
    
  }
  
  private static void showDoneButton() {
    hideDoneButton();
    doneButton = new TouchButton();
    doneButton.setStyleName("mgwt-Button phg-DoneButton");
    doneButton.setText("Done");
    RootPanel.get().add(doneButton);
  }
  
  private static void hideDoneButton() {
    if (doneButton != null) {
      doneButton.setVisible(false);
      doneButton.removeFromParent();
      doneButton = null;
    }
  }
  
  public static void disableDoneButtonSurrogate() {
    
  }
  
  private static native Element getActiveElementImpl() /*-{
    return $doc.activeElement;
  }-*/;


}
