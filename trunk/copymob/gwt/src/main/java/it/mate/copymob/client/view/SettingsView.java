package it.mate.copymob.client.view;

import it.mate.copymob.client.factories.AppClientFactory;
import it.mate.copymob.client.logic.MainDao;
import it.mate.copymob.client.logic.TimbriUtils;
import it.mate.copymob.client.view.SettingsView.Presenter;
import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.OrderItemTx;
import it.mate.copymob.shared.model.impl.OrderTx;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.utils.OnsDialogUtils;
import it.mate.phgcommons.client.plugins.FileSystemPlugin;
import it.mate.phgcommons.client.plugins.ImagePickerPlugin;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class SettingsView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToHomeView();
    public void resetDB();
    public void goToAccountEditView();
    public void testWaitingState(boolean flag);
    public void updateOrdersFromServer();
    public void registerPushNotifications();
    public void saveCustomerImageOnOrderItem(OrderItem orderItem, Delegate<OrderItem> delegate);
  }

  public interface ViewUiBinder extends UiBinder<Widget, SettingsView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label counterLbl;
  
  public SettingsView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setModel(Object model, String tag) {
    if (model instanceof String) {
      counterLbl.setText((String)model);
    }
  }

  @UiHandler("btnReset")
  public void onBtnReset(TapEvent event) {
    GwtUtils.deferredExecution(200, new Delegate<Void>() {
      public void execute(Void element) {
        getPresenter().resetDB();
      }
    });
  }
  
  @UiHandler("btnDialog")
  public void onBtnDialog(TapEvent event) {
    /*
    OnsDialogUtils.alert("Attenzione", "Marcello è grande!", null, "OK", null, new Delegate<Void>() {
      public void execute(Void element) {
        PhgUtils.log("ma va!");
      }
    });
    */
    /*
    OnsDialogUtils.confirm("Attenzione", "Marcello è grande!", null, new String[] {"Ma si", "Ma no"}, null, false, new Delegate<Integer>() {
      public void execute(Integer index) {
        PhgUtils.log("index = " + index);
      }
    });
    */
    OnsDialogUtils.showWaitingDialog();
    
    final MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
    
    dao.findAllTimbri(new Delegate<List<Timbro>>() {
      public void execute(List<Timbro> timbri) {
        
        final Timbro timbro = timbri.get(0);
        
        dao.findAccount(new Delegate<Account>() {
          public void execute(final Account account) {
            
            
            TimbriUtils.readFromLocalhost("http://127.0.0.1:8888/.image?name=timbro-test.jpg", new Delegate<String>() {
              public void execute(String fileContent) {
                
                OrderTx order = new OrderTx();
                List<OrderItem> items = new ArrayList<OrderItem>();
                items.add(new OrderItemTx(order));
                items.get(0).setTimbro(timbro);
                items.get(0).setCustomerImage(fileContent);
                order.setItems(items);
                order.setAccount(account);
                
                PhgUtils.log("BEFORE SAVE customerImage lenght = " + items.get(0).getCustomerImage().length());
                
                RpcMap map = order.toRpcMap();
                
                AppClientFactory.IMPL.getRemoteFacade().saveOrder(map, new AsyncCallback<RpcMap>() {
                  
                  public void onSuccess(RpcMap result) {
                    Order order = new OrderTx().fromRpcMap(result);
                    PhgUtils.log("SUCCESS");
                    PhgUtils.log("AFTER SAVE customerImage lenght = " + order.getItems().get(0).getCustomerImage().length());
                  }
                  
                  public void onFailure(Throwable caught) {
                    caught.printStackTrace();
                  }
                });

              }
            });
            
            
          }
        });
        
        
      }
    });
    
    
    
    
  }
  
  @UiHandler("btnAccount")
  public void onBtnAccount(TapEvent event) {
    getPresenter().goToAccountEditView();
  }
  
  boolean waiting = false;
  
  @UiHandler("btnWaiting")
  public void onBtnWaiting(TapEvent event) {
    waiting = !waiting;
    getPresenter().testWaitingState(waiting);
  }
  
  @UiHandler("btnUpdate")
  public void onBtnUpdate(TapEvent event) {
    getPresenter().updateOrdersFromServer();
  }
  
  @UiHandler("btnPush")
  public void onBtnPush(TapEvent event) {
    getPresenter().registerPushNotifications();
  }
  
  @UiHandler("btnPick")
  public void onBtnPick(TapEvent event) {
    if (ImagePickerPlugin.isInstalled()) {
      ImagePickerPlugin.getPictures(new ImagePickerPlugin.Options(), new Delegate<List<String>>() {
        public void execute(List<String> results) {
          if (results != null && results.size() > 0) {
            String url = results.get(0);
            String destFile = url.substring(url.lastIndexOf("/"));
            PhgUtils.log("destFile = " + destFile);
            FileSystemPlugin.readExternalFileAsEncodedData(url, /* "image.tmp" */ destFile, new Delegate<String>() {
              public void execute(String fileContent) {
                PhgUtils.log("fileContent: " + fileContent);
              }
            });
          }
        }
      });
    } else {
      PhgUtils.log("ImagePickerPlugin NOT INSTALLED");
    }
  }
  
}
