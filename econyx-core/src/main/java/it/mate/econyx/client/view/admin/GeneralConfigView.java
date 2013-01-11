package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.activities.GeneralActivity;
import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class GeneralConfigView extends AbstractBaseView<GeneralActivity> {

  public interface ViewUiBinder extends UiBinder<Widget, GeneralConfigView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) FormPanel exportForm;
  @UiField FormPanel uploadForm;
  @UiField IntegerBox generateRandomCustomersNumberBox;
  @UiField IntegerBox generateRandomOrdersNumberBox;
  @UiField Label buildIdLabel;
  
  @UiField HTMLPanel outputPanel;
  
  private String secretCode = PropertiesHolder.getString("client.GeneralConfigView.secretCode", "");
  
  public GeneralConfigView() {
    super();
    initUI();
  }

  private void initUI() {
    exportForm = new FormPanel("exportTab");
    initWidget(uiBinder.createAndBindUi(this));
    buildIdLabel.setText("Build id: " + PropertiesHolder.getString("shared.buildId", "not set"));
  }
  
  public void setModel(Object model, String tag) {
    
  }
  
  
  private void askSecretCode(final Delegate<Void> delegate) {
    if (secretCode.equals(""))
      delegate.execute(null);
    HorizontalPanel popupPanel = new HorizontalPanel();
    popupPanel.add(new Spacer("1px", "2em"));
    Label label = new Label("Secret code:");
    label.setWidth("6em");
    popupPanel.add(label);
    final PasswordTextBox secretBox = new PasswordTextBox();
    popupPanel.add(secretBox);
    MessageBoxUtils.popupOkCancel("Secret code", popupPanel, "400px", new Delegate<MessageBox.Callbacks>() {
      public void execute(Callbacks callbacks) {
        if (secretCode.equals(secretBox.getValue())) {
          delegate.execute(null);
        } else {
          Window.alert("Secret code errato!");
        }
      }
    });
    GwtUtils.deferredExecution(500, new Delegate<Void>() {
      public void execute(Void element) {
        secretBox.setFocus(true);
      }
    });
  }
  
  @UiHandler ("importPortalDataBtn")
  public void onImportPortalDataBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().importPortalData();
      }
    });
  }
  
  @UiHandler ("exportPortalDataBtn")
  public void onExportPortalDataBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().exportPortalData();
      }
    });
  }
  
  @UiHandler ("resetCacheBtn")
  public void onResetCacheBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().clearCache();
      }
    });
  }
  
  @UiHandler ("exportPortalDataXmlBtn")
  public void onExportPortalDataXmlBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        long ctm = System.currentTimeMillis();
        exportForm.clear();
        exportForm.setAction("/portalData.export?v="+ctm);
        exportForm.setMethod(FormPanel.METHOD_POST);
        HorizontalPanel inner = new HorizontalPanel();
        inner.add( new Hidden("oper", "portalData" ));
        exportForm.setWidget(inner);
        exportForm.submit();
      }
    });
  }
  
  @UiHandler ("resetAllBtn")
  public void onResetAllBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().deleteAll();
      }
    });
  }
  
  @UiHandler ("importPortalDataFileBtn")
  public void onImportPortalDataFileBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        uploadForm.submit();
      }
    });
  }
  
  @UiHandler ("getServerContextUrlBtn")
  public void getServerContextUrlBtn (ClickEvent event) {
    getPresenter().getServerContextUrl();
  }
  
  @UiHandler ("reloadPropertiesBtn")
  public void reloadPropertiesBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().reloadProperties();
      }
    });
  }
  
  @UiHandler ("generateRandomCustomersBtn")
  public void generateRandomCustomersBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().generateRandomCustomers(generateRandomCustomersNumberBox.getValue());
      }
    });
  }
  
  @UiHandler ("generateRandomOrdersBtn")
  public void generateRandomOrdersBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().generateRandomOrders(generateRandomOrdersNumberBox.getValue());
      }
    });
  }
  
  @UiHandler ("gdataSpreadsheetTestBtn")
  public void gdataSpreadsheetTestBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {

      }
    });
  }
  
  @UiHandler ("exceptionTestBtn")
  public void exceptionTestBtn (ClickEvent event) {
    AppClientFactory.IMPL.getGinjector().getGeneralService().testServiceException(new AsyncCallback<Void>() {
      public void onSuccess(Void result) {
        Window.alert("Siamo nella onSuccess");
      }
      public void onFailure(Throwable caught) {
        Window.alert("Siamo nella onFailure con " + caught.getMessage());
      }
    });
  }
  
  @UiHandler ("instCacheViewBtn")
  public void instCacheViewBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().instanceCacheDump(new Delegate<List<CacheDumpEntry>>() {
          public void execute(List<CacheDumpEntry> results) {
            outputPanel.clear();
            FlexTable table = new FlexTable();
            outputPanel.add(table);
            int row = 0;
            for (CacheDumpEntry entry : results) {
              table.setText(row, 0, ""+(row+1));
              table.setText(row, 1, entry.getKey());
              table.setText(row, 2, entry.getValue());
              row++;
            }
          }
        });
      }
    });
  }
  
  @UiHandler ("cobraTestBtn")
  public void cobraTestBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().cobraTest();
      }
    });
  }
  
  
  private boolean waiting = false;
  
  @UiHandler ("customTest1Btn")
  public void customTest1Btn (ClickEvent event) {
    
    if (waiting) {
      GwtUtils.hideWaitPanel();
      waiting = false;
    } else {
      PopupPanel defaultWaitPanel = new PopupPanel();
      GwtUtils.setStyleAttribute(defaultWaitPanel, "border", "none");
      GwtUtils.setStyleAttribute(defaultWaitPanel, "background", "transparent");
      defaultWaitPanel.setGlassEnabled(false);
      defaultWaitPanel.setAnimationEnabled(true);
      Image waitingImg = new Image(UriUtils.fromTrustedString("/images/commons/transp-loading.gif"));
      defaultWaitPanel.setWidget(waitingImg);
      GwtUtils.setDefaultWaitPanel(defaultWaitPanel);
      
      GwtUtils.showWaitPanel();
      waiting = true;
    }
    
  }
  
  
}
