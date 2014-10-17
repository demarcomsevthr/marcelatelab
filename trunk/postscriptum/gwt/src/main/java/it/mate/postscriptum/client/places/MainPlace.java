package it.mate.postscriptum.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends Place {
  
  public static final String HOME = "home";
  
  public static final String NEW_MAIL = "newMail";
  
  public static final String MAIL_LIST = "mailList";
  
  public static final String NEW_SMS = "newSms";
  
  public static final String SMS_LIST = "smsList";
  
  public static final String EDIT_SMS = "editSms";
  
  private String token;
  
  private Object model;

  public MainPlace() {
    this.token = HOME;
  }
  
  public MainPlace(String token) {
    this.token = token;
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
