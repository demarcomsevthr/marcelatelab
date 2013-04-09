package it.mate.econyx.client.places;

import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BlogPlace extends Place implements ReversiblePlace, HasToken {

  public static String BLOG_VIEW = "blogView";

  public static String DISCUSSION_VIEW = "discussionView";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "ProductPlace [token=" + token + ", model=" + model + "]";
  }

  public BlogPlace() {
    this.token = BLOG_VIEW;
  }
  
  public BlogPlace(String token) {
    this.token = token;
  }
  
  public BlogPlace(String token, Object model) {
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
  
  public static class Tokenizer implements PlaceTokenizer<BlogPlace> {

    @Override
    public String getToken(BlogPlace place) {
      return place.getToken();
    }

    @Override
    public BlogPlace getPlace(String token) {
      return null;
    }

  }
  
}
