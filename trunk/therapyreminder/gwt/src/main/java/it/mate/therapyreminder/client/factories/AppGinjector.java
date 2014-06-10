package it.mate.therapyreminder.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.therapyreminder.client.activities.mapper.MainActivityMapper;
import it.mate.therapyreminder.client.logic.AppSqlDao;
import it.mate.therapyreminder.client.view.AccountEditView;
import it.mate.therapyreminder.client.view.CalendarEventTestView;
import it.mate.therapyreminder.client.view.ContactsView;
import it.mate.therapyreminder.client.view.DosageEditView;
import it.mate.therapyreminder.client.view.HomeView;
import it.mate.therapyreminder.client.view.LegalNotesView;
import it.mate.therapyreminder.client.view.ReminderEditView;
import it.mate.therapyreminder.client.view.ReminderListView;
import it.mate.therapyreminder.client.view.SettingsView;
import it.mate.therapyreminder.client.view.TherapyEditView;
import it.mate.therapyreminder.client.view.TherapyListView;
import it.mate.therapyreminder.shared.service.RemoteFacadeAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public RemoteFacadeAsync getRemoteFacade();
  
  public AppSqlDao getDao();
  
  public HomeView getHomeView();
  
  public SettingsView getSettingsView();
  
  public TherapyListView getTherapyListView();
  
  public TherapyEditView getTherapyEditView();
  
  public DosageEditView getDosageEditView();
  
  public ReminderListView getReminderListView();
  
  public ReminderEditView getReminderEditView();
  
  public AccountEditView getAccountEditView();
  
  public LegalNotesView getLegalNotesView();
  
  public ContactsView getContactsView();
  
  public CalendarEventTestView getCalendarEventTestView();
  
}
