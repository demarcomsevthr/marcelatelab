package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.Page;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.web.bindery.event.shared.EventBus;

public class OnsActivityManagerWithSlidingMenu extends OnsActivityManagerBase {
  
  ActivityMapper mapper;  
  
  public OnsActivityManagerWithSlidingMenu(ActivityMapper mapper, EventBus eventBus, Place menuPlace) {
    super(mapper, eventBus);
    this.mapper = mapper;
    eventBus.fireEvent(new OnsPlaceChangeEvent(menuPlace, true));
  }
  
  @Override
  public void onPlaceChange(final PlaceChangeEvent event) {
    Place newPlace = event.getNewPlace();
    PhgUtils.log("ON PLACE CHANGE: newPlace = " + newPlace);
    
    Activity act = mapper.getActivity(newPlace);
    if (act == null) {
      PhgUtils.log("ACTIVITY NULL >> SKIP");
      
      //TODO: CON QUESTO VA IN LOOP
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          if (OnsActivityManagerWithNavigator.instance != null) {
            OnsActivityManagerWithNavigator.instance.onPlaceChange(event);
          }
        }
      });
      
      return;
    }
    
    setActivePanelFromTemplate(newPlace);
    super.onPlaceChange(event);
    compileActivePanel();
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    putPlace(newPlace);
    if (event instanceof OnsPlaceChangeEvent && ((OnsPlaceChangeEvent)event).isMenuPlace()) {
      OnsenUi.getSlidingMenu().setMenuPage(newToken);
    } else {
      String animation = null;
      if (event instanceof OnsPlaceChangeEvent) {
        animation = ((OnsPlaceChangeEvent)event).getAnimation();
      }
      OnsenUi.getSlidingMenu().setMainPage(newToken, animation);
    }
  }
  
  @Override
  protected Page getCurrentPage() {
    return null;
  }
  
  @Override
  protected void setAfterPagePushHandler() {
    
  }

  @Override
  protected void setBeforePagePopHandler() {
    
  }

}
