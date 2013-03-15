package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CustomerPlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String LIST = "list";

  public static String EDIT = "edit";

  public static String VIEW = "view";

  public static String REGISTRATION = "registration";

  public static String PROFILE_VIEW = "profileView";
  
  public static String UPDATE_CUSTOMER_INFORMATIONS = "updCustInfo";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  public CustomerPlace() {
    this.token = VIEW;
  }
  
  public CustomerPlace(String token) {
    this.token = token;
  }
  
  public CustomerPlace(String token, Object model) {
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

  public static class Tokenizer implements PlaceTokenizer<CustomerPlace> {

    public String getToken(CustomerPlace place) {
      return place.getToken();
    }

    public CustomerPlace getPlace(String token) {
      return null;
    }

  }

  private String historyName;
  
  public String getHistoryName() {
    return historyName;
  }

  public CustomerPlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    return historyAppend;
  }

  public CustomerPlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
