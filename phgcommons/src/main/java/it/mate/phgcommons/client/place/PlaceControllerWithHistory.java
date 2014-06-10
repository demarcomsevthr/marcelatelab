package it.mate.phgcommons.client.place;

import it.mate.phgcommons.client.utils.PhonegapUtils;

import java.util.LinkedList;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public class PlaceControllerWithHistory extends PlaceController {
  
  LinkedList<Place> history = new LinkedList<Place>();
  
//Place lastVisitedPlace = null;
  
  public PlaceControllerWithHistory(EventBus eventBus, Delegate delegate) {
    super(eventBus, delegate);
  }
  
  @Override
  public void goTo(final Place newPlace) {
    super.goTo(newPlace);
//  historyPlace = lastVisitedPlace;
    if (history.size() > 0) {
      
      for (int it = 0; it < history.size(); it++) {
        Place placeInHistory = history.get(it);
        if (placeInHistory.equals(newPlace)) {
          history = truncate(it);
        }
      }
      
    }
    history.add(newPlace);
    
//  String logMsg = "lastVisitedPlace = " + lastVisitedPlace;
    String logMsg = " history = {";
    for (int it = 0; it < history.size(); it++) {
      Place placeInHistory = history.get(it);
      if (it > 0)
        logMsg+= ", ";
      logMsg += placeInHistory;
    }
    logMsg += "}";
    PhonegapUtils.log(logMsg);
    
  }
  
  private LinkedList<Place> truncate(int index) {
    LinkedList<Place> newHistory = new LinkedList<Place>();
    for (int it = 0; it < index; it++) {
      newHistory.add(history.get(it));
    }
    return newHistory;
  }
  
  public void goBack() {
    if (history.size() > 1) {
      Place place = history.get(history.size() - 2);
      goTo(place);
    }
  }
  
  /*
  public Place getPreviousPlace() {
    return lastPlace;
  }
  */

}
