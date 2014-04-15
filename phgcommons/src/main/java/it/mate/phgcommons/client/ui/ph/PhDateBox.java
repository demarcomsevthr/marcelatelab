package it.mate.phgcommons.client.ui.ph;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.ui.CalendarDialog;
import it.mate.phgcommons.client.utils.TouchUtils;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidget;

public class PhDateBox extends TouchWidget implements HasValue<Date>, HasChangeHandlers, HasModel {

  private InputElement element;
  
  private Date value = new Date();
  
  private List<ValueChangeHandler<Date>> valueChangeHandlers = new ArrayList<ValueChangeHandler<Date>>();
  
//private final static boolean USE_DATE_PICKER_PLUGIN = false;
  
  private Object model;
  
  public PhDateBox() {
    element = DOM.createInputText().cast();
    setElement(element);
    addStyleName("phg-DateBox");
    
    element.setReadOnly(true);
    addTapHandler(new TapHandler() {
      public void onTap(TapEvent event) {
        TouchUtils.applyKeyboardPatch();
        onDateBoxTapEvent(event);
      }
    });
    
  }
  
  protected void onDateBoxTapEvent(TapEvent event) {
    CalendarDialog calendar = new CalendarDialog(this.value);
    calendar.addSelectedDateChangeHandler(new CalendarDialog.SelectedDateChangeHandler() {
      public void onSelectedDateChange(Date date) {
        setValue(date, true);
      }
    });
    calendar.setGlassEnabled(true);
    calendar.show();
  }

  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Date> handler) {
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

  public Date getValue() {
    return value;
  }

  public void setValue(Date value) {
    setValue(value, true);
  }

  public void setValue(Date value, boolean fireEvents) {
    this.value = value;
    element.setValue(value != null ? GwtUtils.dateToString(value, "dd/MM/yyyy") : null);
    if (fireEvents) {
      DateChangeEvent.fire(this, value);
    }
  }
  
  @Override
  public void fireEvent(GwtEvent<?> event) {
    if (event instanceof DateChangeEvent) {
      DateChangeEvent dateChangeEvent = (DateChangeEvent)event;
      for (ValueChangeHandler<Date> handler : valueChangeHandlers) {
        handler.onValueChange(dateChangeEvent);
      }
    } else {
      super.fireEvent(event);
    }
  }
  
  public static class DateChangeEvent extends ValueChangeEvent<Date> {

    public static <S extends HasValueChangeHandlers<Date> & HasHandlers> void fire(S source, Date value) {
      source.fireEvent(new DateChangeEvent(value));
    }
    
    public static <S extends HasValueChangeHandlers<Date> & HasHandlers> void fireIfNotEqualDates(S source, Date oldValue, Date newValue) {
      if (ValueChangeEvent.shouldFire(source, oldValue, newValue)) {
        source.fireEvent(new DateChangeEvent(newValue));
      }
    }

    protected DateChangeEvent(Date value) {
      // The date must be copied in case one handler causes it to change.
      super(CalendarUtil.copyDate(value));
    }

    @Override
    public Date getValue() {
      return CalendarUtil.copyDate(super.getValue());
    }
    
  }
  
  @Override
  public HasModel setModel(Object model) {
    this.model = model;
    return this;
  }

  @Override
  public Object getModel() {
    return model;
  }


}
