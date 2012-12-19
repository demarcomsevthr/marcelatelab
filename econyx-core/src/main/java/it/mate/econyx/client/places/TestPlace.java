package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TestPlace extends Place implements ReversiblePlace, HasToken {

  public static String VIEW = "view";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "PortalPagePlace [token=" + token + ", model=" + model + ", previousPlace=" + previousPlace + "]";
  }

  public TestPlace() {
    this.token = VIEW;
  }
  
  public TestPlace(String token) {
    this.token = token;
  }
  
  public TestPlace(String token, Object model) {
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

  public static class Tokenizer implements PlaceTokenizer<TestPlace> {

    public String getToken(TestPlace place) {
      return place.getToken();
    }

    public TestPlace getPlace(String token) {
      return null;
    }

  }
  
}
