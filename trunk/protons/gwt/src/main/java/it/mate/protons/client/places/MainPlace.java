package it.mate.protons.client.places;

import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends Place implements HasToken {
  
  public static final String HOME = "home";
  
  public static final String SETTINGS = "settings";
  
  public static final String ABOUT = "about";
  
  public static final String TEST = "test";
  
  public static String[] getTokenList() {
    return new String[] {HOME, SETTINGS, ABOUT, TEST};
  }
  
  private String token;
  
  private Object model;
  
  public MainPlace() {
    this.token = HOME;
  }
  
  public MainPlace(String token) {
    this(token, null);
  }
  
  @Override
  public String toString() {
    return "MainPlace [token=" + token + "]";
  }

  public MainPlace(String token, Object model) {
    this.token = token;
    this.model = model;
  }
  
  public String getToken() {
    return token;
  }
  
  public Object getModel() {
    return model;
  }
  
  
  public static class Tokenizer implements PlaceTokenizer<MainPlace> {

    @Override
    public String getToken(MainPlace place) {
      return place.getToken();
    }

    @Override
    public MainPlace getPlace(String token) {
      return new MainPlace(token);
    }

  }
  
}