package it.mate.copymob.client.activities;

import it.mate.copymob.client.factories.AppClientFactory;
import it.mate.copymob.client.logic.MainDao;
import it.mate.copymob.client.logic.TimbriUtils;
import it.mate.copymob.client.places.MainPlace;
import it.mate.copymob.client.view.AccountEditView;
import it.mate.copymob.client.view.CartListView;
import it.mate.copymob.client.view.CategorieListView;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.MenuView;
import it.mate.copymob.client.view.MessageListView;
import it.mate.copymob.client.view.OrderItemComposeView;
import it.mate.copymob.client.view.OrderItemEditView;
import it.mate.copymob.client.view.OrderItemImageView;
import it.mate.copymob.client.view.SettingsView;
import it.mate.copymob.client.view.TimbriListView;
import it.mate.copymob.client.view.TimbroDetailView;
import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.Categoria;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
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
import it.mate.phgcommons.client.plugins.FileSystemPlugin;
import it.mate.phgcommons.client.plugins.ImagePickerPlugin;
import it.mate.phgcommons.client.plugins.PushPlugin;
import it.mate.phgcommons.client.plugins.PushPlugin.Notification;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

@SuppressWarnings("rawtypes")
public class MainActivity extends OnsAbstractActivity implements 
    MenuView.Presenter, HomeView.Presenter, SettingsView.Presenter, TimbriListView.Presenter, TimbroDetailView.Presenter, 
    OrderItemEditView.Presenter, OrderItemComposeView.Presenter, OrderItemImageView.Presenter,
    MessageListView.Presenter, AccountEditView.Presenter,
    CartListView.Presenter, CategorieListView.Presenter
  {
  
  private MainPlace place;
  
  private BaseView view;
  
  private MainDao dao = AppClientFactory.IMPL.getGinjector().getMainDao();
  
  private Timer daoTimer;

  
  private static OrderItem selectedOrderItem;
  
  public OrderItem getSelectedOrderItem() {
    return selectedOrderItem;
  }
  protected void setSelectedOrderItem(OrderItem selectedOrderItem) {
    MainActivity.selectedOrderItem = selectedOrderItem;
  }
  
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    
    if (place.getToken().equals(MainPlace.HOME)) {
//    PhgUtils.setDesktopDebugBorder(384, 682); // LG G3 5.5' RATIO (1440x2560)
      PhgUtils.setDesktopDebugBorder(384, 568); // NEXUS 4 4.7' RATIO (768 x 1280)
    }
    
    daoTimer = GwtUtils.createTimer(500, new Delegate<Void>() {
      public void execute(Void element) {
        if (dao.isReady()) {
          daoTimer.cancel();
          TimbriUtils.doRun();
        }
      }
    });
    
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
    
    if (place.getToken().equals(MainPlace.CATEGORIE_LIST)) {
      this.view = AppClientFactory.IMPL.getGinjector().getCategorieListView();
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
    
    if (place.getToken().equals(MainPlace.ORDER_ITEM_IMAGE)) {
      this.view = AppClientFactory.IMPL.getGinjector().getOrderItemImageView();
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

    GwtUtils.deferredExecution(new Delegate<Void>() {
      public void execute(Void element) {
        PhgUtils.log("activity - setting model deferred");;
        retrieveModel();
      }
    });
    
  }
  
  private void retrieveModel() {
    if (place.getToken().equals(MainPlace.CATEGORIE_LIST)) {
      dao.findAllCategorie(new Delegate<List<Categoria>>() {
        public void execute(List<Categoria> categorie) {
          view.setModel(categorie, "categorie");
        }
      });
    }
    if (place.getToken().equals(MainPlace.TIMBRI_LIST)) {
      if (place.getModel() instanceof Categoria) {
        final Categoria categoria = (Categoria)place.getModel();
        dao.findTimbriByCategoria(categoria.getCodice(), new Delegate<List<Timbro>>() {
          public void execute(List<Timbro> timbri) {
            view.setModel(categoria.getDescrizione(), "categoria");
            view.setModel(timbri, "timbri");
          }
        });
      } else {
        dao.findAllTimbri(new Delegate<List<Timbro>>() {
          public void execute(List<Timbro> timbri) {
            view.setModel(timbri, "timbri");
          }
        });
      }
    }
    if (place.getToken().equals(MainPlace.TIMBRO_DETAIL)) {
      view.setModel(place.getModel());
    }
    if (place.getToken().equals(MainPlace.ORDER_ITEM_EDIT)) {
      if (place.getModel() instanceof OrderItem) {
        view.setModel(place.getModel());
      } else {
        view.setModel(getSelectedOrderItem());
      }
    }
    if (place.getToken().equals(MainPlace.ORDER_ITEM_COMPOSE)) {
      if (place.getModel() instanceof OrderItem) {
        view.setModel(place.getModel());
      } else {
        view.setModel(getSelectedOrderItem());
      }
    }
    if (place.getToken().equals(MainPlace.ORDER_ITEM_IMAGE)) {
      if (place.getModel() instanceof OrderItem) {
        view.setModel(place.getModel());
      } else {
        view.setModel(getSelectedOrderItem());
      }
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
            setSelectedOrderItem(null);
            view.setModel(results.get(0));
          } else {
            view.setModel(null);
          }
        }
      });
    }
    if (place.getToken().equals(MainPlace.MESSAGE_LIST)) {
      if (place.getModel() instanceof OrderItem) {
        OrderItem orderItem = (OrderItem)place.getModel();
        view.setModel(orderItem);
      } else {

      }
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
  public void goToTimbriListView(Categoria categoria) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.TIMBRI_LIST, categoria));
  }

  @Override
  public void goToCategorieListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CATEGORIE_LIST));
  }

  @Override
  public void goToTimbroDetailView(Timbro timbro) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.TIMBRO_DETAIL, timbro));
  }

  @Override
  public void goToOrderItemEditView(OrderItem orderItem) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ORDER_ITEM_EDIT, orderItem));
  }

  @Override
  public void goToTimbroComposeView(Timbro timbro) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ORDER_ITEM_COMPOSE, timbro));
  }

  @Override
  public void goToOrderItemImageView(OrderItem orderItem) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.ORDER_ITEM_IMAGE, orderItem));
  }

  @Override
  public void goToMessageListView() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.MESSAGE_LIST));
  }

  @Override
  public void goToMessageListView(OrderItem orderItem) {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.MESSAGE_LIST, orderItem));
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

  @Override
  public void addTimbroToCurrentOrder(final Timbro timbro, Delegate<OrderItem> delegate) {
    
    if (delegate == null) {
      delegate = new Delegate<OrderItem>() {
        public void execute(OrderItem orderItem) {
          goToOrderItemEditView(orderItem);
        }
      };
    }
    
    final Delegate<OrderItem> fDelegate = delegate;

    dao.findOrderInCart(new Delegate<List<Order>>() {
      public void execute(List<Order> results) {
        
        if (results == null || results.size() == 0) {
          Order order = new OrderTx();
          order.setCodice("CARTORDER");
          order.setState(Order.STATE_IN_CART);
          order.getItems().add(createOrderItem(order, timbro, 1d));
          dao.saveOrder(order, new Delegate<Order>() {
            public void execute(Order order) {
              addTimbroToCurrentOrder(timbro, fDelegate);
            }
          });
          return;
        }
        
        Order order = results.get(0);
        
        for (OrderItem orderItem : order.getItems()) {
          if (orderItem.getTimbro().getId().equals(timbro.getId())) {
            if (orderItem.getRows() == null || orderItem.getRows().size() == 0) {
              setSelectedOrderItem(orderItem);
              fDelegate.execute(orderItem);
              return;
            }

            // TODO: serve adesso per testarlo, poi va tolto e si va sempre in insert
            //
            setSelectedOrderItem(orderItem);
            fDelegate.execute(orderItem);
            return;
            
          }
        }

        OrderItem orderItem = createOrderItem(order, timbro, 1d);
        order.getItems().add(orderItem);
        setSelectedOrderItem(orderItem);
        fDelegate.execute(orderItem);
        return;
        
      }
    });
    
  }
  
  @Override
  public void setOrderItemInCart(final OrderItem orderItem) {
    orderItem.setInCart(true);
    saveOrderItemOnDevice(orderItem, new Delegate<Order>() {
      public void execute(Order element) {
        goToCartListView();
      }
    });
  }
  
  private OrderItem createOrderItem(Order order, Timbro timbro, double qty) {
    OrderItem item = new OrderItemTx(order);
    item.setQuantity(1d);
    item.setTimbro(timbro);
    return item;
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
  
  @Override
  public void resetDB() {
    dao.dropDB(new Delegate<Void>() {
      public void execute(Void element) {
        PhgUtils.reloadApp();
      }
    });
  }
  
  @Override
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
  
  public static void setWaitingState(boolean waiting) {
    OnsToolbar.setWaitingButtonVisible(waiting);
    HasTapHandlerImpl.setAllHandlersDisabled(waiting);
  }
  
  @Override
  public void saveOrderItemOnDevice(OrderItem orderItem, Delegate<Order> delegate) {
    PhgUtils.log("saving item " + orderItem);
    setSelectedOrderItem(orderItem);
    OrderItemTx orderItemTx = (OrderItemTx)orderItem;
    if (orderItemTx.getOrder() == null) {
      PhgUtils.log("ERRORE FATALE - order non settato!");
      PhgUtils.log("ERRORE FATALE - order non settato!");
      PhgUtils.log("ERRORE FATALE - order non settato!");
      PhgUtils.log("ERRORE FATALE - order non settato!");
      throw new IllegalArgumentException("Item senza reference all'ordine - " + orderItem);
    }
    dao.saveOrder(orderItemTx.getOrder(), delegate);
  }

  @Override
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
          order.setAccount(account);
          OrderTx tx = (OrderTx)order;
          
          RpcMap orderMap = tx.toRpcMap();
          
          AppClientFactory.IMPL.getRemoteFacade().saveOrder(orderMap, new AsyncCallback<RpcMap>() {
            public void onSuccess(RpcMap map) {
              Order result = new OrderTx().fromRpcMap(map);
              
              dao.saveOrder(result, new Delegate<Order>() {
                public void execute(final Order result) {
                  setWaitingState(false);
                  PhgUtils.log("SAVE ORDER RESULT >> " + result);
                  OnsDialogUtils.alert("Info", "Ordine salvato", new Delegate<Void>() {
                    public void execute(Void element) {
                      delegate.execute(result);
                    }
                  });
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
  
  @Override
  public void updateOrdersFromServer() {
    dao.findAllOrders(new Delegate<List<Order>>() {
      public void execute(List<Order> ordiniDevice) {
        if (ordiniDevice != null && ordiniDevice.size() > 0) {
          Date minLastUpdate = new Date();
          for (Order order : ordiniDevice) {
            if (order.getLastUpdate() != null && order.getLastUpdate().before(minLastUpdate)) {
              minLastUpdate = order.getLastUpdate();
            }
          }
          final Date fLastUpdate = minLastUpdate;
          getAccount(new Delegate<Account>() {
            public void execute(Account account) {
              setWaitingState(true);
              AppClientFactory.IMPL.getRemoteFacade().findOrdersByAccount(account.getId(), fLastUpdate, new AsyncCallback<List<RpcMap>>() {
                public void onSuccess(List<RpcMap> results) {
                  setWaitingState(false);
                  if (results != null) {
                    List<Order> ordiniDaAggiornare = new ArrayList<Order>();
                    for (RpcMap map : results) {
                      Order order = new OrderTx().fromRpcMap(map);
                      ordiniDaAggiornare.add(order);
                    }
                    iterateOrdersForUpdate(ordiniDaAggiornare.iterator(), new Delegate<Void>() {
                      public void execute(Void element) {
                        PhgUtils.log(">>> UPDATE COMPLETATO");
                      }
                    });
                  }
                }
                public void onFailure(Throwable caught) {
                  setWaitingState(false);
                  OnsDialogUtils.alert("Error", "Order update error ("+ caught.getMessage() +")!");
                }
              });
            }
          });
        }
      }
    });
  }
  
  private void iterateOrdersForUpdate(final Iterator<Order> it, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      Order order = it.next();
      PhgUtils.log(">>> UPDATING ORDER " + order);
      dao.saveOrder(order, new Delegate<Order>() {
        public void execute(Order updatedOrder) {
          iterateOrdersForUpdate(it, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }
  
  private static final String DEBUG_REG_ID = "APA91bH9kMBuTNn32SJho3ZqjJlManVvsd8KtM9Tp1jiwYpdQXE8DdM8FXPlVil46HhQiZCP-Rvwf2qp6XeCnD89qHqF3wWo7dfH0VFY5iuuXSm7o0OKMSaLFCvsYVOBo2iPhHMARnWO";
  
  @Override
  public void registerPushNotifications() {
    getAccount(new Delegate<Account>() {
      public void execute(final Account account) {
        if (account != null) {
          if (PushPlugin.isInstalled()) {
            PhgUtils.log("Push Plugin installed");
            PushPlugin.register("106218079007", new Delegate<PushPlugin.Notification>() {
              public void execute(final Notification notification) {
                if (notification.isRegisteredEvent()) {
                  savePushNotificationIdOnAccount(account, notification.getRegId());
                }
              }
            });
          } else {
            PhgUtils.log("Push Plugin NOT INSTALLED (saving debug regId)");
            savePushNotificationIdOnAccount(account, DEBUG_REG_ID);
          }
        }
      }
    });
  }
  
  private void savePushNotificationIdOnAccount(Account account, final String pushNotifRegId) {
    PhgUtils.log("received pushNotRegId " + pushNotifRegId);
    if (!pushNotifRegId.equals(account.getPushNotifRegId())) {
      PhgUtils.log("registering pushNotRegId " + pushNotifRegId);
      account.setPushNotifRegId(pushNotifRegId);
      dao.saveAccount(account, new Delegate<Account>() {
        public void execute(Account account) {
          AccountTx atx = (AccountTx)account;
          setWaitingState(true);
          AppClientFactory.IMPL.getRemoteFacade().saveAccount(atx.toRpcMap(), new AsyncCallback<RpcMap>() {
            public void onSuccess(RpcMap result) {
              setWaitingState(false);
              PhgUtils.log("registered pushNotRegId " + pushNotifRegId);
              OnsDialogUtils.alert("Registered push notifications");
            }
            public void onFailure(Throwable caught) {
              setWaitingState(false);
              OnsDialogUtils.alert("Error", "Order update error ("+ caught.getMessage() +")!");
            }
          });
        }
      });
    }
  }
  
  public void saveCustomerImageOnOrderItem(final OrderItem orderItem, final Delegate<OrderItem> delegate) {
    if (ImagePickerPlugin.isInstalled()) {
      ImagePickerPlugin.getPictures(new ImagePickerPlugin.Options(), new Delegate<List<String>>() {
        public void execute(List<String> results) {
          if (results != null && results.size() > 0) {
            String url = results.get(0);
            String destFile = url.substring(url.lastIndexOf("/"));
            PhgUtils.log("destFile = " + destFile);
            FileSystemPlugin.readExternalFileAsEncodedData(url, /* "image.tmp" */ destFile, new Delegate<String>() {
              public void execute(String fileContent) {
//              PhgUtils.log("fileContent: " + fileContent);
                orderItem.setCustomerImage(fileContent);
                saveOrderItemOnDevice(orderItem, new Delegate<Order>() {
                  public void execute(Order element) {
                    delegate.execute(orderItem);
                  }
                });
              }
            });
          }
        }
      });
    } else {
      PhgUtils.log("ImagePickerPlugin NOT INSTALLED");
      
      if (OsDetectionUtils.isDesktop()) {
        TimbriUtils.readFromLocalhost("http://127.0.0.1:8888/.image?name=timbro-test.jpg", new Delegate<String>() {
          public void execute(String fileContent) {
            
            fileContent = TimbriUtils.encodeImageData(fileContent, "jpeg");
            
            orderItem.setCustomerImage(fileContent);
            saveOrderItemOnDevice(orderItem, new Delegate<Order>() {
              public void execute(Order element) {
                delegate.execute(orderItem);
              }
            });
          }
        });
      }
      
    }
  }

}
