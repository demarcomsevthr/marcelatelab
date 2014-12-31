package it.mate.onscommons.client.mvp;


import it.mate.gwtcommons.client.factories.CommonGinModule;

import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
//import com.google.gwt.event.shared.EventBus;

public class OnsGinModule extends CommonGinModule {

  protected void configure() {
    
//  bind(PlaceController.class).to(InjectedPlaceController.class).in(Singleton.class);
    bind(PlaceController.class).to(OnsPlaceController.class).in(Singleton.class);
    
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    
  }

  
}
