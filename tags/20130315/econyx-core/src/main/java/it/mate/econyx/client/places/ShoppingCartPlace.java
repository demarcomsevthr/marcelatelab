package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ShoppingCartPlace extends Place implements ReversiblePlace, HasToken {

  public static String SUMMARY = "summary";

  public static String DETAILED_VIEW = "detailedView";

  private String token;
  
  private Object model;
  
  private Place previousPlace;

  public ShoppingCartPlace() {
    this.token = SUMMARY;
  }
  
  public ShoppingCartPlace(String token) {
    this.token = token;
  }
  
  public ShoppingCartPlace(String token, Object model) {
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

  public static class Tokenizer implements PlaceTokenizer<ShoppingCartPlace> {

    @Override
    public String getToken(ShoppingCartPlace place) {
      return place.getToken();
    }

    @Override
    public ShoppingCartPlace getPlace(String token) {
      return null;
    }

  }
  
}
