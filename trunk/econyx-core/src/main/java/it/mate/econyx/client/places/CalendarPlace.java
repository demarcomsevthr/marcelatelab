package it.mate.econyx.client.places;

import it.mate.econyx.shared.model.CalEvent;
import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CalendarPlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String CAL_DATE_VIEW = "calDateView";

  public static String EVENT_VIEW = "eventView";

  public static String EVENT_LIST = "eventList";

  public static String EVENT_EDIT = "eventEdit";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "CalendarPlace [token=" + token + ", model=" + model + "]";
  }

  public CalendarPlace() {
    this.token = EVENT_VIEW;
  }
  
  public CalendarPlace(String token) {
    this.token = token;
  }
  
  public CalendarPlace(String token, Object model) {
    this.token = token;
    this.model = model;
  }
  
  public String getToken() {
    return token;
  }
  
  public Object getModel() {
    return model;
  }
  
  public void setModel(Object model) {
    this.model = model;
  }
  
  public Place getPreviousPlace() {
    return previousPlace;
  }

  public void setPreviousPlace(Place previousPlace) {
    this.previousPlace = previousPlace;
  }
  
  public static class Tokenizer implements PlaceTokenizer<CalendarPlace> {

    @Override
    public String getToken(CalendarPlace place) {
      return place.getToken();
    }

    @Override
    public CalendarPlace getPlace(String token) {
      return null;
    }

  }
  
  private String historyName;
  
  public String getHistoryName() {
    if (EVENT_EDIT.equals(token) && model instanceof CalEvent) {
      CalEvent event = (CalEvent)model;
      return event.getName();
    }
    return historyName;
  }

  public CalendarPlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    if (EVENT_EDIT.equals(token) && model instanceof CalEvent) {
      return true;
    }
    return historyAppend;
  }

  public CalendarPlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
