package it.mate.therapyreminder.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends Place {
  
  public static final String HOME = "home";
  
  public static final String THERAPY_LIST = "listTherapy";
  
  public static final String THERAPY_EDIT = "editTherapy";
  
  public static final String TEST = "test";
  
  private String token;

  public MainPlace() {
    this.token = HOME;
//  this.token = TEST;
  }
  
  public MainPlace(String token) {
    this.token = token;
  }
  
  public String getToken() {
    return token;
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
