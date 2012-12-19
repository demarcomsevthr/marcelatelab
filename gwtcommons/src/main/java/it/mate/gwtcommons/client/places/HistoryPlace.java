package it.mate.gwtcommons.client.places;

import com.google.gwt.place.shared.Place;

public interface HistoryPlace {
  
  String getName();
  
  Place name(String name);
  
  boolean isAppend();
  
  Place setAppend();
  
}
