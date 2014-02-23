package it.mate.phgcommons.client.ui.ph;

import it.mate.phgcommons.client.ui.CalendarDialog;

import java.util.Date;

import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class PhCalendarBox extends PhDateBox {

  public PhCalendarBox() {
    super();
  }
  
  @Override
  protected void onPluginTapEvent(TapEvent event) {
    CalendarDialog calendar = new CalendarDialog();
    calendar.addSelectedDateChangeHandler(new CalendarDialog.SelectedDateChangeHandler() {
      public void onSelectedDateChange(Date date) {
        setValue(date, true);
      }
    });
    calendar.setGlassEnabled(true);
    calendar.show();
  }

}
