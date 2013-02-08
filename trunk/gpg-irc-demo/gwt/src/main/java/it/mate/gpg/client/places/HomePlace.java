package it.mate.gpg.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomePlace extends Place {
  
  public static final String DEFAULT = "default";
  
  private String token;

  public HomePlace() {
    this.token = DEFAULT;
  }
  
  public HomePlace(String token) {
    this.token = token;
  }
  
  public String getToken() {
    return token;
  }
  
  public static class Tokenizer implements PlaceTokenizer<HomePlace> {

    @Override
    public String getToken(HomePlace place) {
      return place.getToken();
    }

    @Override
    public HomePlace getPlace(String token) {
      return new HomePlace(token);
    }

  }
  
}
