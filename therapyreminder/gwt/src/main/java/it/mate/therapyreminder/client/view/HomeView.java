package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.ui.TimePickerDialog;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.constants.AppProperties;
import it.mate.therapyreminder.client.view.HomeView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;

public class HomeView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToTherapyListView();
    public void goToSettingsView();
    public void goToReminderListView();
    public void goToContactMenu();
  }

  public interface ViewUiBinder extends UiBinder<Widget, HomeView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField Label homeLbl;
  
  public HomeView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    homeLbl.setText("Version " + AppProperties.IMPL.versionNumber()+ " by " + AppProperties.IMPL.devName());
  }
  
  @Override
  public void setPresenter(Presenter presenter) {
    super.setPresenter(presenter);
    
    ////////////////////////////////////////////////////////
    //   DEBUGGING
    
    Time.set12HFormat(true);
    new TimePickerDialog();
    
    
    ////////////////////////////////////////////////////////
    
    
  }

  @Override
  public void setModel(Object model, String tag) {
    
  }
  
  @UiHandler ("therapiesBtn")
  public void onTherapiesBtn (TouchEndEvent event) {
    getPresenter().goToTherapyListView();
  }
  
  @UiHandler ("remindersBtn")
  public void onRemindersBtn (TouchEndEvent event) {
    getPresenter().goToReminderListView();
  }
  
  @UiHandler ("doctorsBtn")
  public void onDoctorsBtn (TouchEndEvent event) {
    getPresenter().goToContactMenu();
  }
  
  @UiHandler ("preferencesBtn")
  public void onPreferencesBtn (TouchEndEvent event) {
    getPresenter().goToSettingsView();
  }
  
}
