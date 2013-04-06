package it.mate.econyx.client.places;

import it.mate.econyx.shared.model.DocumentFolder;
import it.mate.gwtcommons.client.mvp.ReversiblePlace;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.places.HistoryPlace;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DocumentPlace extends Place implements ReversiblePlace, HasToken, HistoryPlace {

  public static String FOLDER_VIEW = "folderView";

  public static String FOLDER_LIST = "folderList";

  public static String FOLDER_EDIT = "folderEdit";

  public static String DOCUMENT_VIEW = "documentView";

  public static String DOCUMENT_EDIT = "documentEdit";

  private String token;
  
  private Object model;
  
  private Place previousPlace;
  
  @Override
  public String toString() {
    return "DocumentPlace [token=" + token + ", model=" + model + "]";
  }

  public DocumentPlace() {
    this.token = FOLDER_VIEW;
  }
  
  public DocumentPlace(String token) {
    this.token = token;
  }
  
  public DocumentPlace(String token, Object model) {
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
  
  public static class Tokenizer implements PlaceTokenizer<DocumentPlace> {

    @Override
    public String getToken(DocumentPlace place) {
      return place.getToken();
    }

    @Override
    public DocumentPlace getPlace(String token) {
      return null;
    }

  }
  
  private String historyName;
  
  public String getHistoryName() {
    if (FOLDER_EDIT.equals(token) && model instanceof DocumentFolder) {
      DocumentFolder folder = (DocumentFolder)model;
      return folder.getName();
    }
    return historyName;
  }

  public DocumentPlace setHistoryName(String name) {
    this.historyName = name;
    return this;
  }

  private boolean historyAppend = false;
  
  public boolean isHistoryAppend() {
    if (FOLDER_EDIT.equals(token) && model instanceof DocumentFolder) {
      return true;
    }
    return historyAppend;
  }

  public DocumentPlace setHistoryAppend() {
    this.historyAppend = true;
    return this;
  }

  public HistoryPlace historyPlace() {
    return this;
  }
  
}
