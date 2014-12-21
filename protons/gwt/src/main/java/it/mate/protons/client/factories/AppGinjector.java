package it.mate.protons.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.protons.client.activities.mapper.MainActivityMapper;
import it.mate.protons.client.logic.MainDao;
import it.mate.protons.client.view.AboutView;
import it.mate.protons.client.view.ApplicationApplyView;
import it.mate.protons.client.view.ApplicationEditView;
import it.mate.protons.client.view.ApplicationListView;
import it.mate.protons.client.view.HomeView;
import it.mate.protons.client.view.IngredientListView;
import it.mate.protons.client.view.SettingsView;
import it.mate.protons.client.view.TestView;
import it.mate.protons.shared.service.RemoteFacadeAsync;

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
  
  public TestView getTestView();
  
}
