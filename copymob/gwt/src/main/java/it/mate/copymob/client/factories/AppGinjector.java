package it.mate.copymob.client.factories;

import it.mate.copymob.client.activities.mapper.MainActivityMapper;
import it.mate.copymob.client.view.AccountEditView;
import it.mate.copymob.client.view.CartConfView;
import it.mate.copymob.client.view.CartListView;
import it.mate.copymob.client.view.CategorieListView;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.MenuView;
import it.mate.copymob.client.view.MessageListView;
import it.mate.copymob.client.view.OrderEditView;
import it.mate.copymob.client.view.OrderItemComposeView;
import it.mate.copymob.client.view.OrderItemEditView;
import it.mate.copymob.client.view.OrderItemImageView;
import it.mate.copymob.client.view.OrderListView;
import it.mate.copymob.client.view.SettingsView;
import it.mate.copymob.client.view.TimbriListView;
import it.mate.copymob.client.view.TimbroDetailView;
import it.mate.copymob.shared.service.RemoteFacadeAsync;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.phgcommons.client.utils.Dao;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public RemoteFacadeAsync getRemoteFacade();
  
  public Dao getMainDao();
  
  public MenuView getMenuView();
  
  public HomeView getHomeView();
  
  public SettingsView getSettingsView();
  
  public CategorieListView getCategorieListView();
  
  public TimbriListView getTimbriListView();
  
  public TimbroDetailView getTimbroDetailView();
  
  public OrderItemEditView getOrderItemEditView();
  
  public OrderItemComposeView getOrderItemComposeView();
  
  public OrderItemImageView getOrderItemImageView();
  
  public MessageListView getMessageListView();
  
  public AccountEditView getAccountEditView();
  
  public CartListView getCartListView();
  
  public OrderListView getOrderListView();
  
  public OrderEditView getOrderEditView();
  
  public CartConfView getCartConfView();
  
}
