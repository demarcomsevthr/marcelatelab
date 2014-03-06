package it.mate.phgcommons.client.ui.ph;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.ui.TimePickerDialog;
import it.mate.phgcommons.client.utils.DatePickerPluginUtil;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.Time;
import it.mate.phgcommons.client.utils.TouchUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class PhTimeBox extends TouchWidget implements HasValue<Time>, HasChangeHandlers {

  private InputElement element;
  
  private Time value;
  
  private List<ValueChangeHandler<Time>> valueChangeHandlers = new ArrayList<ValueChangeHandler<Time>>();
  
  private static DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm");
  
  private final static boolean USE_TIME_PICKER_PLUGIN = false;
  
  @SuppressWarnings("unused")
  public PhTimeBox() {
    element = DOM.createInputText().cast();
    setElement(element);
    addStyleName("phg-TimeBox");
    
    if (OsDetectionUtils.isDesktop() && USE_TIME_PICKER_PLUGIN) {
      addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          Time value = stringToTime(element.getValue(), new Time());
          setValue(value, true);
        }
      });
    } else {
      element.setReadOnly(true);
      addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          TouchUtils.applyQuickFixFocusPatch();
          if (USE_TIME_PICKER_PLUGIN) {
            DatePickerPluginUtil.showTimeDialog(new Delegate<Time>() {
              public void execute(Time value) {
                setValue(value, true);
              }
            });
          } else {
            TimePickerDialog dialog = new TimePickerDialog(new TimePickerDialog.Options().setOnClose(new Delegate<Time>() {
              public void execute(Time value) {
                setValue(value, true);
              }
            }));
            Time value = PhTimeBox.this.value != null ? PhTimeBox.this.value : new Time();
            dialog.setTime(value);
          }
        }
      });
    }
    
  }

  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Time> handler) {
    this.valueChangeHandlers.add(handler);
    HandlerRegistration registration = new HandlerRegistration() {
      public void removeHandler() {
        valueChangeHandlers.remove(handler);
      }
    };
    return registration;
  }
  
  public HandlerRegistration addChangeHandler(final ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public Time getValue() {
    return value;
  }

  public void setValue(Time value) {
    setValue(value, true);
  }

  public void setValue(Time value, boolean fireEvents) {
    this.value = value;
    element.setValue(timeToString(value));
    if (fireEvents) {
      TimeChangeEvent.fire(this, value);
    }
  }
  
  private String timeToString(Time time) {
    return fmt.format(time.asDate());
  }
  
  private Time stringToTime(String text, Time defTime) {
    try {
      return new Time(fmt.parse(text));
    } catch (Exception ex) {
      return defTime;
    }
  }
  
  @Override
  public void fireEvent(GwtEvent<?> event) {
    if (event instanceof TimeChangeEvent) {
      TimeChangeEvent dateChangeEvent = (TimeChangeEvent)event;
      for (ValueChangeHandler<Time> handler : valueChangeHandlers) {
        handler.onValueChange(dateChangeEvent);
      }
    } else {
      super.fireEvent(event);
    }
  }
  
  public static class TimeChangeEvent extends ValueChangeEvent<Time> {

    public static <S extends HasValueChangeHandlers<Time> & HasHandlers> void fire(S source, Time value) {
      source.fireEvent(new TimeChangeEvent(value));
    }
    
    public static <S extends HasValueChangeHandlers<Time> & HasHandlers> void fireIfNotEqualDates(S source, Time oldValue, Time newValue) {
      if (ValueChangeEvent.shouldFire(source, oldValue, newValue)) {
        source.fireEvent(new TimeChangeEvent(newValue));
      }
    }

    protected TimeChangeEvent(Time value) {
      // The date must be copied in case one handler causes it to change.
      super(new Time(value.getHours(), value.getMinutes()));
    }

    @Override
    public Time getValue() {
      Time value = super.getValue();
      return new Time(value.getHours(), value.getMinutes());
    }
    
  }
  

}
