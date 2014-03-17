package it.mate.postscriptum.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.postscriptum.client.activities.mapper.MainActivityMapper;
import it.mate.postscriptum.client.view.HomeView;
import it.mate.postscriptum.client.view.MailListView;
import it.mate.postscriptum.client.view.NewMailView;
import it.mate.postscriptum.client.view.NewSmsView;
import it.mate.postscriptum.shared.service.StickFacadeAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public HomeView getHomeView();
  
  public NewMailView getNewMailView();
  
  public MailListView getMailListView();
  
  public NewSmsView getNewSmsView();
  
  public StickFacadeAsync getStickFacade();
  
}
