package it.mate.copymob.client.activities;

import it.mate.copymob.client.factories.AppClientFactory;
import it.mate.copymob.client.logic.MainDao;
import it.mate.copymob.client.logic.TimbriInitializer;
import it.mate.copymob.client.places.MainPlace;
import it.mate.copymob.client.view.AccountEditView;
import it.mate.copymob.client.view.CartListView;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.MenuView;
import it.mate.copymob.client.view.MessageListView;
import it.mate.copymob.client.view.OrderItemComposeView;
import it.mate.copymob.client.view.OrderItemEditView;
import it.mate.copymob.client.view.SettingsView;
import it.mate.copymob.client.view.TimbriListView;
import it.mate.copymob.client.view.TimbroDetailView;
import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.AccountTx;
import it.mate.copymob.shared.model.impl.DevInfoTx;
import it.mate.copymob.shared.model.impl.OrderItemTx;
import it.mate.copymob.shared.model.impl.OrderTx;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.onscommons.client.mvp.OnsAbstractActivity;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.HasTapHandlerImpl;
import it.mate.onscommons.client.ui.OnsToolbar;
import it.mate.onscommons.client.utils.OnsDialogUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

@SuppressWarnings("rawtypes")
public class MainActivity extends OnsAbstractActivity implements 
  MenuView.Presenter, HomeView.Presenter, SettingsView.Presenter,
  TimbriListView.Presenter,
  TimbroDetailView.Presenter, OrderItemEditView.Presenter, OrderItemComposeView.Presenter,
  MessageListView.Presenter, AccountEditView.Presenter,
  CartListView.Presenter
  {
  
  private MainPlace place;
  
  private BaseView view;
  
  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private Timer daoTimer;
  
  private static Order currentOrder;
  
  private static OrderItem currentOrderItem;

  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    if (place.getToken().equals(MainPlace.HOME)) {
//    PhgUtils.setDesktopDebugBorder(OsDetectionUtils.IPHONE_WIDTH, OsDetectionUtils.IPHONE_3INCH_HEIGHT - OsDetectionUtils.IOS_MARGIN_TOP);
      PhgUtils.setDesktopDebugBorder(384, 682); // LG G3 5,5' RATIO (1440x2560)
//    PhgUtils.setDesktopDebugBorder(384, 610); // NEXUS 4 4,7' RATIO ()
    }
    
    daoTimer = GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        if (dao.isReady()) {
          daoTimer.cancel();
          TimbriInitializer.doRun();
        }
      }
    });
    
    boolean deferred = true;

    if (place.getToken().equals(MainPlace.HOME)) {
      testServerConnection();
      getDevInfoId(new Delegate<String>() {
        public void execute(String devInfoId) {
          PhgUtils.log("devInfoId is " + devInfoId);
        }
      });
      this.view = AppClientFactory.IMPL.getGinjector().getHomeView();
    }
    
    if (place.getToken().equals(MainPlace.MENU)) {
      this.view = AppClientFactory.IMPL.getGinjector().getMenuView();
    }
    
    if (place.getToken().equals(MainPlace.SETTINGS)) {
      this.view = AppClientFactory.IMPL.getGinjector().getSettingsView();
    }
    
    if (place.getToken().equals(MainPlace.TIMBRI_LIST)) {
      this.view = AppClientFactory.IMPL.getGinjector().getTimbriListView();
    }
    
    if (place.getToken().equals(MainPlace.TIMBRO_DETAIL)) {
      this.view = AppClientFactory.IMPL.getGinjector().getTimbroDetailView();
    }
    
    if (place.getToken().equals(MainPlace.ORDER_ITEM_EDIT)) {
      this.view = AppClientFactory.IMPL.getGinjector().getOrderItemEditView();
    }
    
    if (place.getToken().equals(MainPlace.ORDER_ITEM_COMPOSE)) {
      this.view = AppClientFactory.IMPL.getGinjector().getOrderItemComposeView();
    }
    
    if (place.getToken().equals(MainPlace.MESSAGE_LIST)) {
      this.view = AppClientFactory.IMPL.getGinjector().getMessageListView();
    }
    
    if (place.getToken().equals(MainPlace.ACCOUNT_EDIT)) {
      this.view = AppClientFactory.IMPL.getGinjector().getAccountEditView();
    }
    
    if (place.getToken().equals(MainPlace.CART_LIST)) {
      this.view = AppClientFactory.IMPL.getGinjector().getCartListView();
    }
    
    view.setPresenter(this);
    panel.setWidget(view.asWidget());

    if (deferred) {
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          retrieveModel();
        }
      });
    } else {
      retrieveModel();
    }
    
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(MainPlace.TIMBRI_LIST)) {
      dao.findAllTimbri(new Delegate<List<Timbro>>() {
        public void execute(List<Timbro> timbri) {
          view.setModel(timbri, "timbri");
        }
      });
    }
    if (place.getToken().equals(MainPlace.TIMBRO_DETAIL)) {
      view.setModel(place.getModel());
    }
    if (place.getToken().equals(MainPlace.ORDER_ITEM_EDIT)) {
      view.setModel(getCurrentOrderItem());
    }
    if (place.getToken().equals(MainPlace.ORDER_ITEM_COMPOSE)) {
      view.setModel(getCurrentOrderItem());
    }
    if (place.getToken().equals(MainPlace.ACCOUNT_EDIT)) {
      getAccount(new Delegate<Account>() {
        public void execute(Account account) {
          view.setModel(account);
        }
      });
    }
    if (place.getToken().equals(MainPlace.CART_LIST)) {
      dao.findOrderInCart(new Delegate<List<Order>>() {
        public void execute(List<Order> results) {
          if (results != null && results.size() == 1) {
            view.setModel(results.get(0));
          } else {
            view.setModel(null);
          }
        }
      });
    }
  }
  
  @Override
  public BaseView getView() {
    return this.view;
  }

  @Override
  public void goToPrevious() {
    OnsenUi.goToPreviousPlace(AppClientFactory.IMPL.getPlaceController(), new MainPlace());
  }

  @Override
  public void goToHomeView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  @Override
  public void goToSettingsView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.SETTINGS));
  }

  @Override
  public void goToTimbriListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.TIMBRI_LIST));
  }

  @Override
  public void goToTimbroDetailView(Timbro timbro) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.TIMBRO_DETAIL, timbro));
  }

  @Override
  public void goToTimbroPreviewView(Timbro timbro) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ORDER_ITEM_EDIT, timbro));
  }

  @Override
  public void goToTimbroComposeView(Timbro timbro) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ORDER_ITEM_COMPOSE, timbro));
  }

  @Override
  public void goToMessageListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.MESSAGE_LIST));
  }

  @Override
  public void goToAccountEditView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ACCOUNT_EDIT));
  }

  @Override
  public void goToCartListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CART_LIST));
  }

  @Override
  public void showMenu() {
    OnsenUi.getSlidingMenu().toggleMenu();
  }

  public void addTimbroToOrder(final Timbro timbro, Delegate<Timbro> delegate) {
    
    if (delegate == null) {
      delegate = new Delegate<Timbro>() {
        public void execute(Timbro timbro) {
//        goToTimbroDetailView(timbro);
          goToTimbroPreviewView(timbro);
        }
      };
    }
    
    final Delegate<Timbro> fDelegate = delegate;

    dao.findOrderInCart(new Delegate<List<Order>>() {
      public void execute(List<Order> results) {
        
        if (results == null || results.size() == 0) {
          Order order = new OrderTx();
          order.setCodice("CARTORDER");
          order.setState(Order.STATE_IN_CART);
          order.getItems().add(createOrderItem(timbro, 1d));
          dao.saveOrder(order, new Delegate<Order>() {
            public void execute(Order order) {
              addTimbroToOrder(timbro, fDelegate);
            }
          });
          return;
        }
        
        Order order = results.get(0);
        setCurrentOrder(order);
        
        for (OrderItem item : order.getItems()) {
          if (item.getTimbro().getId().equals(timbro.getId())) {
            if (item.getRows() == null || item.getRows().size() == 0) {
              setCurrentOrderItem(item);
              fDelegate.execute(timbro);
              return;
            }

            //TODO: serve adesso per testarlo, poi va tolto e si va sempre in insert
            setCurrentOrderItem(item);
            fDelegate.execute(timbro);
            return;
            
          }
        }

        OrderItem item = createOrderItem(timbro, 1d);
        order.getItems().add(item);
        setCurrentOrderItem(item);
        fDelegate.execute(timbro);
        return;
        
      }
    });
    
  }
  
  @Override
  public void addItemToCart(final OrderItem orderItem) {
    orderItem.setInCart(true);
    saveCurrentOrderItem(orderItem, new Delegate<Order>() {
      public void execute(Order element) {
        goToCartListView();
      }
    });
  }
  
  private OrderItem createOrderItem(Timbro timbro, double qty) {
    OrderItem item = new OrderItemTx();
    item.setQuantity(1d);
    item.setTimbro(timbro);
    return item;
  }

  public Order getCurrentOrder() {
    return currentOrder;
  }

  protected void setCurrentOrder(Order currentOrder) {
    MainActivity.currentOrder = currentOrder;
  }

  public OrderItem getCurrentOrderItem() {
    return currentOrderItem;
  }

  protected void setCurrentOrderItem(OrderItem currentOrderItem) {
    MainActivity.currentOrderItem = currentOrderItem;
  }
  
  public void saveCurrentOrderItem(OrderItem item, Delegate<Order> delegate) {
    PhgUtils.log("saving item " + item);
    for (OrderItemRow row : item.getRows()) {
      PhgUtils.log("   with row " + row);
    }
    currentOrderItem = item;
    if (currentOrder != null) {
      for (int it = 0; it < currentOrder.getItems().size(); it++) {
        if (currentOrder.getItems().get(it).equals(item)) {
//      if (currentOrder.getItems().get(it).getId().equals(item.getId())) {
          currentOrder.getItems().set(it, item);
        }
      }
      dao.saveOrder(currentOrder, delegate);
    }
  }

  private void ensureDevInfoId() {
    String devInfoId = getDevInfoIdFromLocalStorage();
    if (devInfoId != null)
      return;
    
    final String duringGenerateDevInfoSemaphore = "duringGenerateDevInfo";
    
    String duringGenerateDevInfo = (String)GwtUtils.getClientAttribute(duringGenerateDevInfoSemaphore);
    if (duringGenerateDevInfo != null)
      return;
    
    GwtUtils.setClientAttribute(duringGenerateDevInfoSemaphore, "true");
    
    String os = (OsDetectionUtils.isAndroid() ? "android" : OsDetectionUtils.isIOs() ? "ios" : "other");
    String layout = PhgUtils.getLayoutInfo();
    String devName = PhgUtils.getDeviceName();
    String phgVersion = PhgUtils.getDevicePhonegap();
    String platform = PhgUtils.getDevicePlatform();
    String devUuid = PhgUtils.getDeviceUuid();
    String devVersion = PhgUtils.getDeviceVersion();
    
    DevInfoTx devInfo = new DevInfoTx();
    devInfo.setOs(os);
    devInfo.setLayout(layout);
    devInfo.setDevName(devName);
    devInfo.setPhgVersion(phgVersion);
    devInfo.setPlatform(platform);
    devInfo.setDevUuid(devUuid);
    devInfo.setDevVersion(devVersion);
    
    AppClientFactory.IMPL.getRemoteFacade().sendDevInfo(devInfo.toRpcMap(), 
        new AsyncCallback<RpcMap>() {
          public void onFailure(Throwable caught) {
            GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
          }
          public void onSuccess(RpcMap map) {
            if (map != null) {
              DevInfoTx devInfo = new DevInfoTx().fromRpcMap(map);
              if (devInfo.getId() != null) {
                PhgUtils.log("received devInfoId "+ devInfo.getId() +" from remote facade");
                setDevInfoIdInLocalStorage(devInfo.getId());
                GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
              }
            }
          }
      });
    
  }
  
  private void testServerConnection() {
    AppClientFactory.IMPL.getRemoteFacade().getServerTime(new AsyncCallback<Date>() {
      public void onSuccess(Date result) {
        PhgUtils.log("SERVER CONNECTION SUCCESS: " + GwtUtils.dateToString(result, "dd/MM/yyyy HH:mm:ss,SSS"));
      }
      public void onFailure(Throwable caught) {
        PhgUtils.log("SERVER CONNECTION FAILURE");
      }
    });
  }

  public void getDevInfoId(final Delegate<String> delegate) {
    final ObjectWrapper<Boolean> delegateFired = new ObjectWrapper<Boolean>(false);
    GwtUtils.createTimerDelegate(500, true, new Delegate<Timer>() {
      public void execute(Timer timer) {
        ensureDevInfoId();
        String devInfoId = getDevInfoIdFromLocalStorage();
        if (devInfoId != null) {
          timer.cancel();
          if (!delegateFired.get()) {
            delegateFired.set(true);
            delegate.execute(devInfoId);
          }
        }
      }
    });
  }
  
  protected String getDevInfoIdFromLocalStorage() {
    return PhgUtils.getLocalStorageItem("devInfoId");
  }

  protected void setDevInfoIdInLocalStorage(String devInfoId) {
    PhgUtils.setLocalStorageItem("devInfoId", devInfoId);
  }
  
  public void resetDB() {
    dao.dropDB(new Delegate<Void>() {
      public void execute(Void element) {
        PhgUtils.reloadApp();
      }
    });
  }
  
  public void saveAccount(Account account, final Delegate<Account> delegate) {
    AccountTx tx = (AccountTx)account;
    String devInfoId = getDevInfoIdFromLocalStorage();
    tx.setDevInfoId(devInfoId);
    setWaitingState(true);
    AppClientFactory.IMPL.getRemoteFacade().saveAccount(tx.toRpcMap(), new AsyncCallback<RpcMap>() {
      public void onSuccess(RpcMap rpc) {
        if (rpc == null) {
          PhgUtils.log("SAVE ACCOUNT SERVER ERROR");
          setWaitingState(false);
          OnsDialogUtils.alert("Error", "Account saving error!");
        } else {
          dao.saveAccount(new AccountTx().fromRpcMap(rpc), new Delegate<Account>() {
            public void execute(Account account) {
              setWaitingState(false);
              delegate.execute(account);
            }
          });
        }
      }
      public void onFailure(Throwable caught) {
        PhgUtils.log("SAVE ACCOUNT SERVER ERROR");
        setWaitingState(false);
        OnsDialogUtils.alert("Error", "Account saving error ("+ caught.getMessage() +")!");
      }
    });
  }
  
  private void getAccount(Delegate<Account> delegate) {
    dao.findAccount(delegate);
  }

  public void testWaitingState(boolean flag) {
    setWaitingState(flag);
  }
  
  private void setWaitingState(boolean waiting) {
    OnsToolbar.setWaitingButtonVisible(waiting);
    HasTapHandlerImpl.setAllHandlersDisabled(waiting);
  }
  
  public void saveOrderOnServer(final Order order, final Delegate<Order> delegate) {
    
    getAccount(new Delegate<Account>() {
      public void execute(Account account) {
        
        if (account == null) {
          OnsDialogUtils.alert("Attenzione", "Devi registrare un account per proseguire", new Delegate<Void>() {
            public void execute(Void element) {
              goToAccountEditView();
            }
          });
        } else {
          setWaitingState(true);
          OrderTx tx = (OrderTx)order;
          AppClientFactory.IMPL.getRemoteFacade().saveOrder(tx.toRpcMap(), new AsyncCallback<RpcMap>() {
            public void onSuccess(RpcMap map) {
              Order result = new OrderTx().fromRpcMap(map);
              dao.saveOrder(result, new Delegate<Order>() {
                public void execute(Order result) {
                  setWaitingState(false);
                  PhgUtils.log("SAVE ORDER RESULT >> " + result);
                  OnsDialogUtils.alert("Info", "Ordine salvato");
                  delegate.execute(result);
                }
              });
            }
            public void onFailure(Throwable caught) {
              setWaitingState(false);
              PhgUtils.log("SAVE ORDER SERVER ERROR >> TODO: DIALOG");
              OnsDialogUtils.alert("Error", "Order saving error ("+ caught.getMessage() +")!");
            }
          });
        }
        
      }
    });
    
  }
  
}
