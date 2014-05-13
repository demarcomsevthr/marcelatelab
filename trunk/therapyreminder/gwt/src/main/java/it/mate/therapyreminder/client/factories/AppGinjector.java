package it.mate.therapyreminder.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.therapyreminder.client.activities.mapper.MainActivityMapper;
import it.mate.therapyreminder.client.dao.AppSqlDao;
import it.mate.therapyreminder.client.view.CalendarEventTestView;
import it.mate.therapyreminder.client.view.DosageEditView;
import it.mate.therapyreminder.client.view.HomeView;
import it.mate.therapyreminder.client.view.SettingsView;
import it.mate.therapyreminder.client.view.TherapyEditView;
import it.mate.therapyreminder.client.view.TherapyListView;
import it.mate.therapyreminder.shared.service.StickFacadeAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public StickFacadeAsync getStickFacade();
  
  public AppSqlDao getAppSqlDao();
  
  public HomeView getHomeView();
  
  public SettingsView getSettingsView();
  
  public TherapyListView getTherapyListView();
  
  public TherapyEditView getTherapyEditView();
  
  public DosageEditView getDosageEditView();
  
  public CalendarEventTestView getCalendarEventTestView();
  
}
