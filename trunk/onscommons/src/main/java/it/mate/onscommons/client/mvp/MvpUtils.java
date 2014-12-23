package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.onscommons.client.utils.OnsUtils;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public class MvpUtils {

  
  public static void initOnsMvp (BaseClientFactory<? extends CommonGinjector> clientFactory, 
      OnsNavigationDisplay display, OnsActivityMapper activityMapper, Place defaultPlace) {
    
    OnsUtils.log("Initializing MVP with in ONS history manager (defaultPlace="+defaultPlace.toString()+")");
    
    CommonGinjector ginjector = clientFactory.getGinjector();
    
//  EventBus eventBus = ginjector.getEventBus();
    EventBus eventBus = ginjector.getBinderyEventBus();
    
    OnsActivityManager activityManager = new OnsActivityManager(activityMapper, eventBus);
    
    activityManager.setOnsDisplay(display, (HasToken)defaultPlace);
    
    PlaceHistoryMapper historyMapper = clientFactory.getPlaceHistoryMapper();
    
    PlaceController placeController = ginjector.getPlaceController();
    
    PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
    
    historyHandler.register(placeController, eventBus, defaultPlace);
    
    historyHandler.handleCurrentHistory();
    
  }
  
}
