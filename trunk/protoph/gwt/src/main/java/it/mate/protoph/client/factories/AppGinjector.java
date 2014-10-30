package it.mate.protoph.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.protoph.client.activities.mapper.MainActivityMapper;
import it.mate.protoph.client.logic.MainDao;
import it.mate.protoph.client.view.AboutView;
import it.mate.protoph.client.view.ApplicationApplyView;
import it.mate.protoph.client.view.ApplicationEditView;
import it.mate.protoph.client.view.ApplicationListView;
import it.mate.protoph.client.view.HomeView;
import it.mate.protoph.client.view.IngredientListView;
import it.mate.protoph.client.view.SettingsView;
import it.mate.protoph.shared.service.RemoteFacadeAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public RemoteFacadeAsync getRemoteFacade();
  
  public MainDao getMainDao();
  
  public HomeView getHomeView();
  
  public SettingsView getSettingsView();
  
  public AboutView getAboutView();
  
  public ApplicationListView getApplicationListView();
  
  public ApplicationEditView getApplicationEditView();
  
  public ApplicationApplyView getApplicationApplyView();
  
  public IngredientListView getIngredientListView();
  
}
