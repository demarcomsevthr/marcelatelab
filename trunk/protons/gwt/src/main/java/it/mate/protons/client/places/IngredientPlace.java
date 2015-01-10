package it.mate.protons.client.places;

import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class IngredientPlace extends Place implements HasToken {
  
  public static final String SUB1 = "sub1";
  
  public static final String SUB2 = "sub2";
  
  private String token;
  
  private Object model;
  
  public IngredientPlace() {
    this.token = SUB1;
  }
  
  public IngredientPlace(String token) {
    this(token, null);
  }
  
  @Override
  public String toString() {
    return "IngredientPlace [token=" + token + "]";
  }

  public IngredientPlace(String token, Object model) {
    this.token = token;
    this.model = model;
  }
  
  public String getToken() {
    return token;
  }
  
  public Object getModel() {
    return model;
  }
  
  
  public static class Tokenizer implements PlaceTokenizer<IngredientPlace> {

    @Override
    public String getToken(IngredientPlace place) {
      return place.getToken();
    }

    @Override
    public IngredientPlace getPlace(String token) {
      return new IngredientPlace(token);
    }

  }
  
}
