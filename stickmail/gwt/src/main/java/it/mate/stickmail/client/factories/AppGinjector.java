package it.mate.stickmail.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.stickmail.client.activities.mapper.MainActivityMapper;
import it.mate.stickmail.client.view.HomeView;
import it.mate.stickmail.client.view.NewMailView;
import it.mate.stickmail.shared.service.StickFacadeAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public HomeView getHomeView();
  
  public NewMailView getNewMailView();
  
  public StickFacadeAsync getStickFacade();
  
}
