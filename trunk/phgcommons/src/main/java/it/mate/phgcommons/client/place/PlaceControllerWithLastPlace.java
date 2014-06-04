package it.mate.phgcommons.client.place;

import it.mate.phgcommons.client.utils.PhonegapUtils;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public class PlaceControllerWithLastPlace extends PlaceController {
  
  Place lastPlace = null;
  Place currentPlace = null;
  
  public PlaceControllerWithLastPlace(EventBus eventBus, Delegate delegate) {
    super(eventBus, delegate);
  }
  
  @Override
  public void goTo(final Place newPlace) {
    super.goTo(newPlace);
    lastPlace = currentPlace;
    currentPlace = newPlace;
    PhonegapUtils.log("currentPlace = " + currentPlace + " lastPlace = " + lastPlace);
  }
  
  public Place getLastPlace() {
    return lastPlace;
  }

}
