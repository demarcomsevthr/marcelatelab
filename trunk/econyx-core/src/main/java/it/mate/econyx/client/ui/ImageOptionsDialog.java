package it.mate.econyx.client.ui;

import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImageOptionsDialog extends Composite {

  public class Options {
    private String name;
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
  }
  
  public interface ViewUiBinder extends UiBinder<Widget, ImageOptionsDialog> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField DialogBox dlg;
  
  @UiField TextBox name;
  
  private Delegate<Options> okCallback;
  
  public ImageOptionsDialog(Delegate<Options> okCallback) {
    initUI();
    this.okCallback = okCallback;
    dlg.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        int left = (Window.getClientWidth() - offsetWidth) / 2;
        int top = (Window.getClientHeight() - offsetHeight) / 3;
        dlg.setPopupPosition(left, top);
      }
    });
  }
  
  protected void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @UiHandler ("okBtn")
  public void onOkBtn (ClickEvent event) {
    Options options = new Options();
    options.setName(name.getText());
    okCallback.execute(options);
    dlg.hide();
  }

  @UiHandler ("cancelBtn")
  public void onCancelBtn (ClickEvent event) {
    dlg.hide();
  }

  
}
