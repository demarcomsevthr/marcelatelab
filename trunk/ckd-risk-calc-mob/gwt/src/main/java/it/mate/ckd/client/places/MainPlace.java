package it.mate.ckd.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends Place {
  
  public static final String HOME = "home";
  
  public static final String CKD_INPUT = "ckdInput";
  
  public static final String CKD_OUTPUT = "ckdOutput";
  
  public static final String CKD_REFERRAL_DECISION = "ckdReferralDecision";
  
  public static final String CKD_PROTOCOL_STEP = "ckdProtocolStep";
  
//public static final String CKD_OUTPUT_HELP = "ckdOutputHelp";
  
  private String token;

  public MainPlace() {
    this.token = HOME;
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
