package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GeneralPlace extends Place implements ReversiblePlace, HasToken {

  public static String EDIT = "edit";

  private String token;
  
  private Object model;
  
  private Place previousPlace;

  public GeneralPlace() {
    this.token = EDIT;
  }
  
  public GeneralPlace(String token) {
    this.token = token;
  }
  
  public GeneralPlace(String token, Object model) {
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

  public static class Tokenizer implements PlaceTokenizer<GeneralPlace> {

    @Override
    public String getToken(GeneralPlace place) {
      return place.getToken();
    }

    @Override
    public GeneralPlace getPlace(String token) {
      return null;
    }

  }
  
}
