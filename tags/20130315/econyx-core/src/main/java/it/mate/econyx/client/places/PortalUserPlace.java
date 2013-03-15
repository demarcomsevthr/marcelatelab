package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PortalUserPlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String LIST = "list";

  public static String EDIT = "edit";

  public static String VIEW = "view";

  private String token;
  
  private Object model;
  
  private Place previousPlace;

  public PortalUserPlace() {
    this.token = VIEW;
  }
  
  public PortalUserPlace(String token) {
    this.token = token;
  }
  
  public PortalUserPlace(String token, Object model) {
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

  public static class Tokenizer implements PlaceTokenizer<PortalUserPlace> {

    public String getToken(PortalUserPlace place) {
      return place.getToken();
    }

    public PortalUserPlace getPlace(String token) {
      return null;
    }

  }
  
  private String historyName;
  
  public String getHistoryName() {
    return historyName;
  }

  public PortalUserPlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    return historyAppend;
  }

  public PortalUserPlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
