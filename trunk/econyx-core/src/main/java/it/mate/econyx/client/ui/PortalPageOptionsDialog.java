package it.mate.econyx.client.ui;

import it.mate.econyx.server.services.impl.PortalPageAdapterImpl.PortalPageTypes;
import it.mate.econyx.shared.model.impl.PortalFolderPageTx;
import it.mate.econyx.shared.model.impl.ProductPageTx;
import it.mate.econyx.shared.model.impl.WebContentPageTx;
import it.mate.gwtcommons.client.utils.Delegate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PortalPageOptionsDialog extends Composite {

  public class Options {
    private String portalPageType;
    private String portalPageName;
    public String getPortalPageType() {
      return portalPageType;
    }
    public void setPortalPageType(String portalPageType) {
      this.portalPageType = portalPageType;
    }
    public String getPortalPageName() {
      return portalPageName;
    }
    public void setPortalPageName(String portalPageName) {
      this.portalPageName = portalPageName;
    }
  }
  
  public interface ViewUiBinder extends UiBinder<Widget, PortalPageOptionsDialog> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField DialogBox dlg;
  @UiField ListBox pageTypeList;
  @UiField TextBox pageName;
  
  private Delegate<Options> okCallback;
  
  public PortalPageOptionsDialog(Delegate<Options> okCallback) {
    initUI();
    this.okCallback = okCallback;
    dlg.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        int left = (Window.getClientWidth() - offsetWidth) / 2;
        int top = (Window.getClientHeight() - offsetHeight) / 3;
        dlg.setPopupPosition(left, top);
      }
    });
    
    pageTypeList.addItem("Pagina di contenuto", WebContentPageTx.class.getName());
    pageTypeList.addItem("Pagina di categoria", PortalFolderPageTx.class.getName());
    pageTypeList.addItem("Pagina prodotto", ProductPageTx.class.getName());
    
  }
  
  protected void initUI() {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @UiHandler ("okBtn")
  public void onOkBtn (ClickEvent event) {
    Options options = new Options();
    options.setPortalPageType(pageTypeList.getValue(pageTypeList.getSelectedIndex()));
    options.setPortalPageName(pageName.getText());
    okCallback.execute(options);
    dlg.hide();
  }

  @UiHandler ("cancelBtn")
  public void onCancelBtn (ClickEvent event) {
    dlg.hide();
  }

  
}
