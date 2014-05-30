package it.mate.phgcommons.client.utils;

import it.mate.gwtcommons.client.ui.SimpleContainer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.JQuery;
import it.mate.gwtcommons.client.utils.JQuery.StyleProperties;
import it.mate.phgcommons.client.ui.TouchAnchor;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;

public class PhgDialogUtils {
  
  public static final int BUTTONS_YESNOCANCEL = 1;
  public static final int BUTTONS_YESNO = 2;
  public static final int BUTTONS_OKCANCEL = 3;
  public static final int BUTTONS_OK = 4;
  
  private static PopinDialog messageDialog;
  
  public static void showMessageDialog(String message) {
    showMessageDialog(message, "Alert");
  }
  
  public static void showMessageDialog(String message, int buttonsType) {
    showMessageDialog(message, "Alert", buttonsType, null);
  }
  
  public static void showMessageDialog(String message, String title) {
    showMessageDialog(message, title, BUTTONS_OK, null);
  }
  
  public static void showMessageDialog(String message, String title, int buttonsType) {
    showMessageDialog(message, title, buttonsType, null);
  }
  
  public static void showMessageDialog(final String message, final String title, int buttonsType, final Delegate<Integer> delegate) {
    showMessageDialog(message, title, buttonsType, null, delegate);
  }
  
  public static void showMessageDialog(final String message, final String title, int buttonsType, Position position, final Delegate<Integer> delegate) {
    
    VerticalPanel dialogContainer = new VerticalPanel();
    dialogContainer.setSpacing(0);
    dialogContainer.setWidth("100%");
    dialogContainer.addStyleName("phg-PopinDialog-Container");
    
    SimplePanel row;
    
    row = new SimplePanel();
    row.addStyleName("phg-PopinDialog-MessageRow");
    dialogContainer.add(row);
    HTML messageHtml = new HTML(SafeHtmlUtils.fromTrustedString(message));
    row.add(messageHtml);
    
    row = new SimplePanel();
    row.addStyleName("phg-PopinDialog-Separator");
    dialogContainer.add(row);
    
    row = new SimplePanel();
    row.addStyleName("phg-PopinDialog-ButtonsRow");
    dialogContainer.add(row);
    HorizontalPanel buttonsPanel = new HorizontalPanel();
    row.add(buttonsPanel);
    
    createButtonsAndShow(dialogContainer, buttonsPanel, message, title, buttonsType, position, delegate);
    
  }
  
  private static void createButtonsAndShow(final Widget dialogContainer, Panel buttonsPanel, String message, final String title, final int buttonsType, final Position position, final Delegate<Integer> delegate) {
    String buttonMsg = null;
    
    if (buttonsPanel != null) {
      if (buttonsType == BUTTONS_YESNO || buttonsType == BUTTONS_YESNOCANCEL) {
        buttonMsg = "Yes";
      } else {
        buttonMsg = "OK";
      }
      TouchAnchor btn = new TouchAnchor(SafeHtmlUtils.fromTrustedString(buttonMsg), new TapHandler() {
        public void onTap(TapEvent event) {
          hideMessageDialog();
          if (delegate != null)
            delegate.execute(1);
        }
      });
      buttonsPanel.add(btn);
    }
    
    if (buttonsType == BUTTONS_OKCANCEL || buttonsType == BUTTONS_YESNO || buttonsType == BUTTONS_YESNOCANCEL) {
      if (buttonsType == BUTTONS_OKCANCEL) {
        buttonMsg = "Cancel";
      } else {
        buttonMsg = "No";
      }
      TouchAnchor btn = new TouchAnchor(SafeHtmlUtils.fromTrustedString(buttonMsg), new TapHandler() {
        public void onTap(TapEvent event) {
          hideMessageDialog();
          if (delegate != null)
            delegate.execute(2);
        }
      });
      buttonsPanel.add(btn);
      btn.getElement().getParentElement().addClassName("phg-PopinDialog-ButtonsRow-2ndBtn");
    }
    
    if (buttonsType == BUTTONS_YESNOCANCEL) {
      buttonMsg = "Cancel";
      TouchAnchor btn = new TouchAnchor(SafeHtmlUtils.fromTrustedString(buttonMsg), new TapHandler() {
        public void onTap(TapEvent event) {
          hideMessageDialog();
          if (delegate != null)
            delegate.execute(3);
        }
      });
      buttonsPanel.add(btn);
      btn.getElement().getParentElement().addClassName("phg-PopinDialog-ButtonsRow-2ndBtn");
    }

    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        if (messageDialog != null) {
          PhonegapUtils.log("message dialog is just opened!");
          return;
        }
        if (position != null) {
          messageDialog = MgwtDialogs.popin(title, dialogContainer, position.top, position.left);
        } else {
          messageDialog = MgwtDialogs.popin(title, dialogContainer);
        }
        
        StyleProperties prop = JQuery.createStyleProperties();
        if (buttonsType == BUTTONS_YESNOCANCEL) {
          prop.setWidth(33, Unit.PCT);
        } else if (buttonsType == BUTTONS_OK) {
          prop.setWidth(100, Unit.PCT);
        } else {
          prop.setWidth(50, Unit.PCT);
        }
        JQuery.select(".phg-PopinDialog-ButtonsRow td").css(prop);
        
      }
    });
  }
  
  public static void showMessageDialog_Div(final String message, final String title, int buttonsType, final Delegate<Integer> delegate) {
    
    final SimpleContainer dialogContainer = new SimpleContainer();
    
    SimplePanel row;
    
    row = new SimplePanel();
    row.addStyleName("phg-PopinDialog-MessageRow");
    dialogContainer.add(row);
    HTML messageHtml = new HTML(SafeHtmlUtils.fromTrustedString(message));
    row.add(messageHtml);
    
    row = new SimplePanel();
    row.addStyleName("phg-PopinDialog-ButtonsRow");
    dialogContainer.add(row);
    HorizontalPanel buttonsPanel = new HorizontalPanel();
    row.add(buttonsPanel);
    
    createButtonsAndShow(dialogContainer, buttonsPanel, message, title, buttonsType, null, delegate);

    /*
    String buttonMsg = null;
    if (buttonsType == BUTTONS_YESNO || buttonsType == BUTTONS_YESNOCANCEL) {
      buttonMsg = "Yes";
    } else {
      buttonMsg = "OK";
    }
    buttonsPanel.add(new TouchAnchor(SafeHtmlUtils.fromTrustedString(buttonMsg), new TapHandler() {
      public void onTap(TapEvent event) {
        hideMessageDialog();
        if (delegate != null)
          delegate.execute(1);
      }
    }));
    
    if (buttonsType == BUTTONS_OKCANCEL || buttonsType == BUTTONS_YESNO || buttonsType == BUTTONS_YESNOCANCEL) {
      if (buttonsType == BUTTONS_OKCANCEL) {
        buttonMsg = "Cancel";
      } else {
        buttonMsg = "No";
      }
      buttonsPanel.add(new TouchAnchor(SafeHtmlUtils.fromTrustedString(buttonMsg), new TapHandler() {
        public void onTap(TapEvent event) {
          hideMessageDialog();
          if (delegate != null)
            delegate.execute(2);
        }
      }));
    }
    
    if (buttonsType == BUTTONS_YESNOCANCEL) {
      buttonMsg = "Cancel";
      buttonsPanel.add(new TouchAnchor(SafeHtmlUtils.fromTrustedString(buttonMsg), new TapHandler() {
        public void onTap(TapEvent event) {
          hideMessageDialog();
          if (delegate != null)
            delegate.execute(3);
        }
      }));
    }

    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        if (messageDialog != null) {
          PhonegapUtils.log("message dialog is just opened!");
          return;
        }
        messageDialog = MgwtDialogs.popin(title, dialogContainer);
      }
    });
    */
    
  }
  
  private static void hideMessageDialog() {
    if (messageDialog != null) {
      messageDialog.hide();
      messageDialog = null;
    }
  }

  public static DialogBox create(Configuration config) {
    
    DialogBox dialog = new DialogBox();
    dialog.addStyleName("pgc-Dialog");
    
    dialog.setText(config.captionText);
    dialog.setAnimationEnabled(true);
    dialog.setGlassEnabled(true);
    
    VerticalPanel dialogPanel = new VerticalPanel();
    dialogPanel.addStyleName("pgc-DialogPanel");
    
    if (config.bodyText != null) {
      dialogPanel.add(new Label(config.bodyText));
    } else if (config.bodyHtml != null) {
      dialogPanel.add(new HTML(config.bodyHtml));
    } else if (config.bodyWidget != null) {
      dialogPanel.add(config.bodyWidget);
    }
    
    dialog.setWidget(dialogPanel);

    if (config.positionCallback != null) {
      dialog.setPopupPositionAndShow(config.positionCallback);
    } else if (config.objectRelativeTo != null) {
      dialog.showRelativeTo(config.objectRelativeTo);
    } else {
      dialog.show();
    }
    
    return dialog;
  }
  
  public static class Configuration {
    private String captionText = "Attenzione";
    private String bodyText = null;
    private SafeHtml bodyHtml = null;
    private Widget bodyWidget = null;
    private PositionCallback positionCallback;
    private UIObject objectRelativeTo;
    public Configuration setCaptionText(String captionText) {
      this.captionText = captionText;
      return this;
    }
    public Configuration setBodyText(String bodyText) {
      this.bodyText = bodyText;
      return this;
    }
    public Configuration setBodyHtml(SafeHtml bodyHtml) {
      this.bodyHtml = bodyHtml;
      return this;
    }
    public Configuration setBodyWidget(Widget bodyWidget) {
      this.bodyWidget = bodyWidget;
      return this;
    }
    public Configuration setPositionCallback(PositionCallback positionCallback) {
      this.positionCallback = positionCallback;
      return this;
    }
    public Configuration setObjectRelativeTo(UIObject objectRelativeTo) {
      this.objectRelativeTo = objectRelativeTo;
      return this;
    }
  }
  
}