package it.mate.econyx.client.view.admin;

import it.mate.econyx.client.activities.GeneralActivity;
import it.mate.econyx.server.model.PortalDataExportModel;
import it.mate.econyx.shared.model.impl.CacheDumpEntry;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.ui.MessageBox;
import it.mate.gwtcommons.client.ui.MessageBox.Callbacks;
import it.mate.gwtcommons.client.ui.MessageBoxUtils;
import it.mate.gwtcommons.client.ui.Spacer;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.utils.PropertiesHolder;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class GeneralConfigView extends AbstractBaseView<GeneralActivity> {

  public interface ViewUiBinder extends UiBinder<Widget, GeneralConfigView> { }
  
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);

  @UiField (provided=true) FormPanel exportForm;
  @UiField FormPanel uploadForm;
  @UiField Label buildIdLabel;
  
  @UiField HTMLPanel outputPanel;
  
  private String secretCode = PropertiesHolder.getString("client.GeneralConfigView.secretCode", "");
  
  private boolean isDevServer = Window.Location.getHostName().contains("localhost");
  
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
  
  
  @UiHandler ("importPortalDataBtn")
  public void onImportPortalDataBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().importPortalData();
      }
    });
  }

  /*
  @UiHandler ("exportPortalDataBtn")
  public void onExportPortalDataBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().exportPortalData();
      }
    });
  }
  */
  
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
        setExportFormAndSubmit(PortalDataExportModel.LOAD_METHOD_ALL);
      }
    });
  }
  
  @UiHandler ("exportOrderDataXmlBtn")
  public void exportOrderDataXmlBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        setExportFormAndSubmit(PortalDataExportModel.LOAD_METHOD_ORDERS);
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
        askGenerateInformations(new Delegate<GenerateInformations>() {
          public void execute(GenerateInformations info) {
            getPresenter().generateRandomCustomers(info.number, info.date);
          }
        });
      }
    });
  }
  
  @UiHandler ("generateRandomOrdersBtn")
  public void generateRandomOrdersBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        askGenerateInformations(new Delegate<GenerateInformations>() {
          public void execute(GenerateInformations info) {
            getPresenter().generateRandomOrders(info.number, info.date);
          }
        });
      }
    });
  }
  
  @UiHandler ("refreshUsersCacheBtn")
  public void refreshUsersCacheBtn(ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().refreshUsersCache();
      }
    });
  }
  
  private void askSecretCode(final Delegate<Void> delegate) {
    if (isDevServer || secretCode.equals("")) {
      delegate.execute(null);
      return;
    }
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
  
  class GenerateInformations {
    int number;
    Date date;
    public GenerateInformations(int number, Date date) {
      super();
      this.number = number;
      this.date = date;
    }
  }
  
  private void askGenerateInformations (final Delegate<GenerateInformations> delegate) {
    VerticalPanel popupPanel = new VerticalPanel();
    final IntegerBox numberBox = new IntegerBox();
    popupPanel.add(GwtUtils.createPopupPanelItem("Numero:", numberBox, "2em", "8em"));
    final DateBox dateBox = new DateBox();
    GwtUtils.setDateBoxFormat(dateBox, "dd/MM/yyyy");
    dateBox.setValue(new Date());
    popupPanel.add(GwtUtils.createPopupPanelItem("Data:", dateBox, "2em", "8em"));
    MessageBoxUtils.popupOkCancel("Inserire gli estremi per l'inserimento", popupPanel, "400px", new Delegate<MessageBox.Callbacks> () {
      public void execute(MessageBox.Callbacks callbacks) {
        delegate.execute(new GenerateInformations(numberBox.getValue(), dateBox.getValue()));
      }
    });
  }
  
  /*
  @UiHandler ("gdataSpreadsheetTestBtn")
  public void gdataSpreadsheetTestBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {

      }
    });
  }
  */
  
  /*
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
  */
  
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
  
  private void setExportFormAndSubmit(int exportMode) {
    long ctm = System.currentTimeMillis();
    exportForm.clear();
    exportForm.setAction("/portalData.export?v="+ctm);
    exportForm.setMethod(FormPanel.METHOD_POST);
    HorizontalPanel inner = new HorizontalPanel();
    inner.add( new Hidden("oper", "portalData" ));
    inner.add( new Hidden("exportMode", ""+exportMode ));
    exportForm.setWidget(inner);
    exportForm.submit();
  }
  
  /*
  @UiHandler ("cobraTestBtn")
  public void cobraTestBtn (ClickEvent event) {
    askSecretCode(new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().cobraTest();
      }
    });
  }
  */
  

  /*
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
  */
  
}
