package it.mate.therapyreminder.client.view;

import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.phgcommons.client.ui.ph.PhTimeBox;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.view.BaseMgwtView;
import it.mate.therapyreminder.client.view.CalendarEventTestView.Presenter;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndEvent;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class CalendarEventTestView extends BaseMgwtView <Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, CalendarEventTestView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField MTextBox titleBox;
  @UiField PhTimeBox orarioInizioBox;
  
  public CalendarEventTestView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    getHeaderBackButton().setVisible(false);
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void setModel(Object model, String tag) {
    
  }

  @UiHandler ("testBtn")
  public void testBtn(TouchEndEvent event) {
    
    Date startDate = new Date();
    Time startTime = new Time(orarioInizioBox.getValue()); 
    startTime.setInDate(startDate);
    
    Date endDate = new Date();
    Time endTime = new Time(orarioInizioBox.getValue());
    endTime.incMinutes(5).setInDate(endDate);
    
    /*
    CalendarPlugin.Event calEvent = new CalendarPlugin.Event();
    calEvent.setTitle(titleBox.getValue() + " at " + startTime.asString());
    if (MGWT.getOsDetection().isIOs()) {
      calEvent.setNotes("Tap here: therapy://open");
    } else {
      calEvent.setNotes("Keep the pill!");
    }
    calEvent.setStartDate(startDate);
    calEvent.setEndDate(endDate);
    
    CalendarPlugin.createEvent(calEvent);
    */
    
  }
  
}
