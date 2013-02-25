package it.mate.gpg.client.places;

import com.google.gwt.place.shared.Place;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.mvp.client.history.HistoryHandler;
import com.googlecode.mgwt.mvp.client.history.HistoryObserver;

public class AppHistoryObserver implements HistoryObserver {

  @Override
  public void onPlaceChange(Place place, HistoryHandler handler) {
    
  }

  @Override
  public void onHistoryChanged(Place place, HistoryHandler handler) {
    
  }

  @Override
  public void onAppStarted(Place place, HistoryHandler historyHandler) {
    
  }

  @Override
  public HandlerRegistration bind(EventBus eventBus, HistoryHandler historyHandler) {
    return null;
  }

  
  
}
