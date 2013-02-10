package it.mate.abaco.client.factories;

import it.mate.abaco.client.activities.mapper.MainActivityMapper;
import it.mate.abaco.client.view.AbacoView;
import it.mate.abaco.client.view.HomeView;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public HomeView getHomeView();
  
  public AbacoView getAbacoView();
  
//public NotificationsView getNotificationsView();
  
}
