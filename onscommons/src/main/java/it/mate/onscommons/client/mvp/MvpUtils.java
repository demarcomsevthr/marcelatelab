package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.factories.CommonGinjector;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public class MvpUtils {
  
  public static void initOnsMvp (BaseClientFactory<? extends CommonGinjector> clientFactory, ActivityMapper activityMapper, Place defaultPlace) {
    
    CommonGinjector ginjector = clientFactory.getGinjector();
    
    EventBus eventBus = ginjector.getBinderyEventBus();
    
    new OnsActivityManager(activityMapper, eventBus);
    
    PlaceHistoryMapper historyMapper = clientFactory.getPlaceHistoryMapper();
    
    PlaceController placeController = ginjector.getPlaceController();
    
    PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
    
    historyHandler.register(placeController, eventBus, defaultPlace);
    
    historyHandler.handleCurrentHistory();
    
  }
  
}
