package it.mate.gwtcommons.client.factories;


import it.mate.gwtcommons.client.history.LoggedSimpleEventBus;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Singleton;

public class CommonGinModule extends AbstractGinModule {

  protected void configure() {
    
    bind(EventBus.class).to(LoggedSimpleEventBus.class).in(Singleton.class);
//  bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    // 10/03/2012
//  bind(PlaceController.class).to(InjectedPlaceController.class);
    bind(PlaceController.class).to(InjectedPlaceController.class).in(Singleton.class);
    
    bind(com.google.web.bindery.event.shared.EventBus.class).to(com.google.web.bindery.event.shared.SimpleEventBus.class).in(Singleton.class);
    
  }

  
}
