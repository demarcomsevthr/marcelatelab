package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class OrderPlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String LIST = "list";

  public static String EDIT = "edit";

  public static String VIEW = "view";

  public static String EDIT_ITEM = "editItem";

  private String token;
  
  private Object model;
  
  private Place previousPlace;

  public OrderPlace() {
    this.token = LIST;
  }
  
  public OrderPlace(String token) {
    this.token = token;
  }
  
  public OrderPlace(String token, Object model) {
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

  private String name;
  
  public String getName() {
    return name;
  }

  public OrderPlace name(String name) {
    this.name = name;
    return this;
  }

  public static class Tokenizer implements PlaceTokenizer<OrderPlace> {

    @Override
    public String getToken(OrderPlace place) {
      return place.getToken();
    }

    @Override
    public OrderPlace getPlace(String token) {
      return null;
    }

  }
  
  private boolean append;

  public boolean isAppend() {
    return append;
  }

  public OrderPlace setAppend() {
    this.append = true;
    return this;
  }
  
}
