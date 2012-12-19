package it.mate.econyx.client.ui;

import it.mate.econyx.client.util.UrlUtils;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class UploadFileDialog extends Composite {
  
  public interface ViewUiBinder extends UiBinder<Widget, UploadFileDialog> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField DialogBox dlg;
  @UiField FormPanel formPanel;
  @UiField FileUpload fileupdBtn;
  @UiField Hidden imageType;
  @UiField Hidden imageSize;
  @UiField Hidden objId;
  @UiField Hidden useCustomAdapter;
  @UiField HorizontalPanel anchorPanel;
  @UiField Label filenameLabel;
  
  private Delegate<String> okCallback;
  
  public UploadFileDialog(String imageType, String objId, String imageSize, Delegate<String> okCallback) {
    this(imageType, objId, imageSize, false, okCallback);
  }
  
  public UploadFileDialog(String imageType, String objId, String imageSize, boolean useCustomAdapter, Delegate<String> okCallback) {
    initUI();
    formPanel.setAction(UrlUtils.getUploadServletUrl());
    formPanel.setMethod(FormPanel.METHOD_POST);
    formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
    this.imageType.setValue(imageType);
    this.imageSize.setValue(imageSize);
    this.objId.setValue(objId);
    this.okCallback = okCallback;
    if (useCustomAdapter) {
      this.useCustomAdapter.setValue("true");
    }
    dlg.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        int left = (Window.getClientWidth() - offsetWidth) / 2;
        int top = (Window.getClientHeight() - offsetHeight) / 3;
        dlg.setPopupPosition(left, top);
      }
    });
    Element elem = fileupdBtn.getElement();
    if (GwtUtils.isIE()) {
      DOM.setStyleAttribute(elem, "filter", "alpha(opacity = 0)");
      DOM.setStyleAttribute(elem, "cursor", "pointer");
      DOM.setStyleAttribute(elem, "fontSize", "22px");
      DOM.setStyleAttribute(elem, "height", "24px");
      DOM.setStyleAttribute(elem, "position", "absolute");
//    DOM.setStyleAttribute(elem, "l", "0");
      DOM.setStyleAttribute(elem, "top", "50px");
      DOM.setStyleAttribute(elem, "right", "300px");
      DOM.setStyleAttribute(elem, "width", "70px");
    } else if (GwtUtils.isChrome()) {
      DOM.setStyleAttribute(elem, "opacity", "0");
      DOM.setStyleAttribute(elem, "cursor", "pointer");
      DOM.setStyleAttribute(elem, "fontSize", "22px");
      DOM.setStyleAttribute(elem, "height", "22px");
      DOM.setStyleAttribute(elem, "position", "absolute");
      DOM.setStyleAttribute(elem, "left", "40px");
      DOM.setStyleAttribute(elem, "top", "52px");
      DOM.setStyleAttribute(elem, "width", "88px");
    } else if (GwtUtils.isGecko()) {
      DOM.setStyleAttribute(elem, "opacity", "0");
      DOM.setStyleAttribute(elem, "cursor", "pointer");
      DOM.setStyleAttribute(elem, "fontSize", "22px");
      DOM.setStyleAttribute(elem, "height", "24px");
      DOM.setStyleAttribute(elem, "position", "absolute");
      DOM.setStyleAttribute(elem, "left", "-140px");
      DOM.setStyleAttribute(elem, "top", "48px");
      DOM.setStyleAttribute(elem, "width", "80px");
    } else {
      DOM.setStyleAttribute(elem, "opacity", "0");
      DOM.setStyleAttribute(elem, "cursor", "pointer");
      DOM.setStyleAttribute(elem, "fontSize", "22px");
      DOM.setStyleAttribute(elem, "height", "20px");
      DOM.setStyleAttribute(elem, "position", "absolute");
      DOM.setStyleAttribute(elem, "left", "-210px");
      DOM.setStyleAttribute(elem, "top", "48px");
      DOM.setStyleAttribute(elem, "width", "80px");
    }
    
    elem = anchorPanel.getElement();
    DOM.setStyleAttribute(elem, "marginTop", "20px");
    DOM.setStyleAttribute(elem, "marginBottom", "20px");
    
    
    fileupdBtn.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        String filename = purgePath(purgePath(fileupdBtn.getFilename(), "\\"), "/");
        filenameLabel.setText(filename);
      }
    });
    
  }
  
  private String purgePath(String text, String pathSeparator) {
    if (text.contains(pathSeparator)) {
      return text.substring(text.lastIndexOf(pathSeparator) + 1);
    }
    return text;
  }
  
  protected void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @UiHandler ("okBtn")
  public void onOkBtn (ClickEvent event) {
    formPanel.submit();
    formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      public void onSubmitComplete(SubmitCompleteEvent event) {
        if (okCallback != null)
          okCallback.execute(event.getResults());
        dlg.hide();
      }
    });
  }

  @UiHandler ("cancelBtn")
  public void onCancelBtn (ClickEvent event) {
    dlg.hide();
  }

  
}
