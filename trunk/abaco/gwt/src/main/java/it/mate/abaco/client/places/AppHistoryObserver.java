package it.mate.abaco.client.places;

import com.google.gwt.place.shared.Place;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.mgwt.mvp.client.history.HistoryHandler;
import com.googlecode.mgwt.mvp.client.history.HistoryObserver;

public class AppHistoryObserver implements HistoryObserver {

  public void onPlaceChange(Place place, HistoryHandler handler) {

  }

  public void onHistoryChanged(Place place, HistoryHandler handler) {

  }

  public void onAppStarted(Place place, HistoryHandler historyHandler) {

  }

  public HandlerRegistration bind(EventBus eventBus, HistoryHandler historyHandler) {
    return null;
  }

}
