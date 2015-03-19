package it.mate.copymob.client.factories;

import it.mate.copymob.client.activities.mapper.MainActivityMapper;
import it.mate.copymob.client.logic.MainDao;
import it.mate.copymob.client.view.AccountEditView;
import it.mate.copymob.client.view.HomeView;
import it.mate.copymob.client.view.MenuView;
import it.mate.copymob.client.view.MessageListView;
import it.mate.copymob.client.view.OrderItemComposeView;
import it.mate.copymob.client.view.OrderItemEditView;
import it.mate.copymob.client.view.SettingsView;
import it.mate.copymob.client.view.TimbriListView;
import it.mate.copymob.client.view.TimbroDetailView;
import it.mate.copymob.shared.service.RemoteFacadeAsync;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public RemoteFacadeAsync getRemoteFacade();
  
  public MainDao getMainDao();
  
  public MenuView getMenuView();
  
  public HomeView getHomeView();
  
  public SettingsView getSettingsView();
  
  public TimbriListView getTimbriListView();
  
  public TimbroDetailView getTimbroDetailView();
  
  public OrderItemEditView getOrderItemEditView();
  
  public OrderItemComposeView getOrderItemComposeView();
  
  public MessageListView getMessageListView();
  
  public AccountEditView getAccountEditView();
  
}
