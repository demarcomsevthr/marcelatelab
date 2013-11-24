package it.mate.phgcommons.client.ui.ph;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.DatePickerPluginUtil;

import java.util.Date;

import com.googlecode.mgwt.dom.client.event.tap.TapEvent;

public class PhCalendarBox extends PhDateBox {

  public PhCalendarBox() {
    super();
  }
  
  @Override
  protected void onPluginTapEvent(TapEvent event) {
    DatePickerPluginUtil.showCalendarView(getValue(), new Delegate<Date>() {
      public void execute(Date value) {
        setValue(value, true);
      }
    });
  }

}
