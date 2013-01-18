package it.mate.gpg.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NotificationsPlace extends Place {
  
  public static final String DEFAULT = "default";
  
  private String token;

  public NotificationsPlace() {
    this.token = DEFAULT;
  }
  
  public NotificationsPlace(String token) {
    this.token = token;
  }
  
  public String getToken() {
    return token;
  }
  
  public static class Tokenizer implements PlaceTokenizer<NotificationsPlace> {

    @Override
    public String getToken(NotificationsPlace place) {
      return place.getToken();
    }

    @Override
    public NotificationsPlace getPlace(String token) {
      return new NotificationsPlace(token);
    }

  }
  
}
