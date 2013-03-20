package it.mate.econyx.client.places;

import it.mate.econyx.shared.model.ArticleFolder;
import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ArticlePlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String FOLDER_VIEW = "folderView";

  public static String FOLDER_LIST = "folderList";

  public static String FOLDER_EDIT = "folderEdit";

  public static String ARTICLE_VIEW = "articleView";

  public static String ARTICLE_EDIT = "articleEdit";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "ProductPlace [token=" + token + ", model=" + model + "]";
  }

  public ArticlePlace() {
    this.token = FOLDER_VIEW;
  }
  
  public ArticlePlace(String token) {
    this.token = token;
  }
  
  public ArticlePlace(String token, Object model) {
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
  
  public static class Tokenizer implements PlaceTokenizer<ArticlePlace> {

    @Override
    public String getToken(ArticlePlace place) {
      return place.getToken();
    }

    @Override
    public ArticlePlace getPlace(String token) {
      return null;
    }

  }
  
  private String historyName;
  
  public String getHistoryName() {
    if (FOLDER_EDIT.equals(token) && model instanceof ArticleFolder) {
      ArticleFolder folder = (ArticleFolder)model;
      return folder.getName();
    }
    return historyName;
  }

  public ArticlePlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    if (FOLDER_EDIT.equals(token) && model instanceof ArticleFolder) {
      return true;
    }
    return historyAppend;
  }

  public ArticlePlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
