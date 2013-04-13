package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PortalPagePlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String LIST = "list";

  public static String LIST_EXPLORER = "listTree";

  public static String EDIT = "edit";

  public static String VIEW = "view";

  public static String VIEW_SUMMARY = "viewSummary";

  public static String LIST_MENU = "listMenu";

  public static String VIEW_BY_CODE = "viewByCode";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "PortalPagePlace [token=" + token + ", model=" + model + ", previousPlace=" + previousPlace + "]";
  }

  public PortalPagePlace() {
    this.token = LIST;
  }
  
  public PortalPagePlace(String token) {
    this.token = token;
  }
  
  public PortalPagePlace(String token, Object model) {
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

  public static class Tokenizer implements PlaceTokenizer<PortalPagePlace> {

    public String getToken(PortalPagePlace place) {
      return place.getToken();
    }

    public PortalPagePlace getPlace(String token) {
      return null;
    }

  }
  
  private String historyName;
  
  public String getHistoryName() {
    return historyName;
  }

  public PortalPagePlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    return historyAppend;
  }

  public PortalPagePlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
