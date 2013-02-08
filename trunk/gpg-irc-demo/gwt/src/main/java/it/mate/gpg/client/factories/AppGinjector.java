package it.mate.gpg.client.factories;

import it.mate.gpg.client.activities.mapper.MainActivityMapper;
import it.mate.gpg.client.view.HomeView;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public HomeView getHomeView();
  
//public NotificationsView getNotificationsView();
  
}
