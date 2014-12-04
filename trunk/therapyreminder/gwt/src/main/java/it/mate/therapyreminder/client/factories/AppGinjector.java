package it.mate.therapyreminder.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.therapyreminder.client.activities.mapper.MainActivityMapper;
import it.mate.therapyreminder.client.logic.MainDao;
import it.mate.therapyreminder.client.view.AboutView;
import it.mate.therapyreminder.client.view.AccountEditView;
import it.mate.therapyreminder.client.view.ContactEditView;
import it.mate.therapyreminder.client.view.ContactListView;
import it.mate.therapyreminder.client.view.ContactMenuView;
import it.mate.therapyreminder.client.view.DosageEditView;
import it.mate.therapyreminder.client.view.HomeView;
import it.mate.therapyreminder.client.view.PatientEditView;
import it.mate.therapyreminder.client.view.PatientListView;
import it.mate.therapyreminder.client.view.ReminderEditView;
import it.mate.therapyreminder.client.view.ReminderListView;
import it.mate.therapyreminder.client.view.SettingsView;
import it.mate.therapyreminder.client.view.TestView;
import it.mate.therapyreminder.client.view.TherapyEditView;
import it.mate.therapyreminder.client.view.TherapyListView;
import it.mate.therapyreminder.shared.service.RemoteFacadeAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, AppGinModule.class})
public interface AppGinjector extends CommonGinjector {
  
  public MainActivityMapper getMainActivityMapper();
  
  public RemoteFacadeAsync getRemoteFacade();
  
  public MainDao getPrescrizioniDao();
  
  public HomeView getHomeView();
  
  public SettingsView getSettingsView();
  
  public TherapyListView getTherapyListView();
  
  public TherapyEditView getTherapyEditView();
  
  public DosageEditView getDosageEditView();
  
  public ReminderListView getReminderListView();
  
  public ReminderEditView getReminderEditView();
  
  public AccountEditView getAccountEditView();
  
  public AboutView getAboutView();
  
  public ContactMenuView getContactMenuView();
  
  public TestView getTestView();
  
  public ContactListView getContactListView();
  
  public ContactEditView getContactEditView();
  
  public PatientListView getPatientListView();
  
  public PatientEditView getPatientEditView();
  
}
