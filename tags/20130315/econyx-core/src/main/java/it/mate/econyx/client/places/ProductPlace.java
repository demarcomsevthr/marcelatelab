package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProductPlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String LIST = "list";

  public static String EDIT = "edit";

  public static String VIEW = "view";

  public static String ORDER_DETAIL = "orderDetail";

  public static String PRODUCER_LIST = "producerList";

  public static String PRODUCER_EDIT = "producerEdit";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "ProductPlace [token=" + token + ", model=" + model + "]";
  }

  public ProductPlace() {
    this.token = LIST;
  }
  
  public ProductPlace(String token) {
    this.token = token;
  }
  
  public ProductPlace(String token, Object model) {
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
  
  public static class Tokenizer implements PlaceTokenizer<ProductPlace> {

    @Override
    public String getToken(ProductPlace place) {
      return place.getToken();
    }

    @Override
    public ProductPlace getPlace(String token) {
      return null;
    }

  }
  
  private String historyName;
  
  public String getHistoryName() {
    return historyName;
  }

  public ProductPlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    return historyAppend;
  }

  public ProductPlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
