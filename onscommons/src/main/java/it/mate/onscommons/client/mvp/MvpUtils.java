package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.factories.CommonGinjector;
import it.mate.gwtcommons.client.places.HasToken;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.web.bindery.event.shared.EventBus;

public class MvpUtils {
  
  /**
   * con TRUE le transizioni hanno una resa visiva migliore, ma dopo il push l'activity perde il reference alla view
   * con FALSE l'activity mantiene il corretto reference alla view anche dopo il push, ma le transizioni hanno una resa visiva peggiore
   */
  public static boolean PUSH_PAGE_IN_ACTIVITY_MANAGER = true;
  
  public static void initOnsMvp (BaseClientFactory<? extends CommonGinjector> clientFactory, 
      OnsNavigationDisplay display, OnsActivityMapper activityMapper, OnsActivityManager activityManager, Place defaultPlace) {
    
    CommonGinjector ginjector = clientFactory.getGinjector();
    
    EventBus eventBus = ginjector.getBinderyEventBus();
    
    activityManager.setOnsDisplay(display, (HasToken)defaultPlace);
    
    PlaceHistoryMapper historyMapper = clientFactory.getPlaceHistoryMapper();
    
    PlaceController placeController = ginjector.getPlaceController();
    
    PlaceHistoryHandler historyHandler = new OnsPlaceHistoryHandler(historyMapper, defaultPlace);
    
    historyHandler.register(placeController, eventBus, defaultPlace);
    
    historyHandler.handleCurrentHistory();
    
  }
  
}
