package it.mate.copymob.client.activities;

import it.mate.copymob.client.factories.AppClientFactory;
import it.mate.copymob.client.logic.MainDao;
import it.mate.copymob.client.logic.TimbriInitializer;
import it.mate.copymob.client.places.MainPlace;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.MenuView;
import it.mate.copymob.client.view.OrderItemComposeView;
import it.mate.copymob.client.view.OrderItemEditView;
import it.mate.copymob.client.view.SettingsView;
import it.mate.copymob.client.view.TimbriListView;
import it.mate.copymob.client.view.TimbroDetailView;
import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.OrderItemTx;
import it.mate.copymob.shared.model.impl.OrderTx;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.gwtcommons.client.utils.ObjectWrapper;
import it.mate.onscommons.client.mvp.OnsAbstractActivity;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

@SuppressWarnings("rawtypes")
public class MainActivity extends OnsAbstractActivity implements 
  MenuView.Presenter, HomeView.Presenter, SettingsView.Presenter,
  TimbriListView.Presenter,
  TimbroDetailView.Presenter, OrderItemEditView.Presenter, OrderItemComposeView.Presenter
  {
  
  private MainPlace place;
  
  private BaseView view;
  
  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private final static boolean REMOTE_CALLS_DISABLED = true;
  
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
      PhgUtils.setDesktopDebugBorder(OsDetectionUtils.IPHONE_WIDTH, OsDetectionUtils.IPHONE_3INCH_HEIGHT - OsDetectionUtils.IOS_MARGIN_TOP);
    }
    
    daoTimer = GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        if (dao.isReady()) {
          daoTimer.cancel();
          TimbriInitializer.doRun();
        }
      }
    });

    if (place.getToken().equals(MainPlace.HOME)) {
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
    
    view.setPresenter(this);
    panel.setWidget(view.asWidget());

    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        retrieveModel();
      }
    });
    
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
  public void goToTimbroEditView(Timbro timbro) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ORDER_ITEM_COMPOSE, timbro));
  }

  @Override
  public void showMenu() {
    OnsenUi.getSlidingMenu().toggleMenu();
  }

  //TODO: WORK IN PROGRESS
  public void orderTimbro(final Timbro timbro) {

    dao.findOpenOrder(new Delegate<List<Order>>() {
      public void execute(List<Order> results) {
        
        if (results == null || results.size() == 0) {
          Order order = new OrderTx();
          order.setCodice("ORDERTEST");
          order.setState(Order.STATE_OPEN);
          order.getItems().add(createOrderItem(timbro, 1d));
          dao.saveOrder(order, new Delegate<Order>() {
            public void execute(Order order) {
              orderTimbro(timbro);
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
              goToTimbroDetailView(timbro);
              return;
            }

            //TODO: serve adesso per testarlo, poi va tolto e si va sempre in insert
            setCurrentOrderItem(item);
            goToTimbroDetailView(timbro);
            return;
            
          }
        }

        OrderItem item = createOrderItem(timbro, 1d);
        order.getItems().add(item);
        setCurrentOrderItem(item);
        goToTimbroDetailView(timbro);
        return;
        
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
    currentOrderItem = item;
    if (currentOrder != null) {
      for (int it = 0; it < currentOrder.getItems().size(); it++) {
        if (currentOrder.getItems().get(it).getId().equals(item.getId())) {
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
    
    if (REMOTE_CALLS_DISABLED) {
      PhgUtils.log("REMOTE CALLS DISABLED!");
    } else {
      AppClientFactory.IMPL.getRemoteFacade().sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, 
          new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
              GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
            }
            public void onSuccess(String devInfoId) {
              if (devInfoId != null) {
                PhgUtils.log("received devInfoId "+ devInfoId +" from remote facade");
                setDevInfoIdInLocalStorage(devInfoId);
                GwtUtils.removeClientAttribute(duringGenerateDevInfoSemaphore);
              }
            }
        });
    }
    
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
  
  public void getAccount(final Delegate<Account> delegate) {
//  delegate.execute(MainController.getInstance().getAccountFromLocalStorage());
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
  
}
