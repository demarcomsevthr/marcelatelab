package it.mate.phgcommons.client.utils;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DialogUtil {
  
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
  

}
