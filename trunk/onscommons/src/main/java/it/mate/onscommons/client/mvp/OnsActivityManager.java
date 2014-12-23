package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.onscommons.client.onsen.OnsenReadyHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.OnsUtils;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class OnsActivityManager extends ActivityManager {
  
  OnsNavigationDisplay onsDisplay;
  
  EventBus eventBus;

  public OnsActivityManager(OnsActivityMapper mapper, EventBus eventBus) {
    super(mapper, eventBus);
    this.eventBus = eventBus;
  }
  
  public void setOnsDisplay(OnsNavigationDisplay onsDisplay, HasToken initialPlace) {
    this.onsDisplay = onsDisplay;
    setActiveTemplate(initialPlace);
    super.setDisplay(new SimplePanel());
  }
  
  private static int counter = -1;
  
  private static OnsTemplate activeTemplate;

  private void setActiveTemplate(HasToken place) {
    counter ++;
    OnsTemplate template = onsDisplay.getPlaceTemplate(place);
    OnsUtils.log("SET ACTIVE TEMPLATE " + counter + " " + template);
    activeTemplate = template;
//  super.setDisplay(template);
    /*
    OnsenUi.compile(template.getElement());
    OnsenUi.pushPage(place.getToken());
    */
    
    final HandlerRegistration placeReg = eventBus.addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
      public void onPlaceChange(PlaceChangeEvent event) {
        OnsUtils.log("MY PLACE CHANGE EVENT " + event);
      }
    });
    
  }
  
  public static OnsTemplate getActiveTemplate() {
    return activeTemplate;
  }
  
  @Override
  public void onPlaceChange(PlaceChangeEvent event) {

    Place newPlace = event.getNewPlace();
    
    OnsUtils.log("onPlaceChange: newPlace = " + newPlace);
    
    String newToken = null;
    
    if (newPlace instanceof HasToken) {
      
      HasToken hasToken = (HasToken)newPlace;
      
      newToken =  hasToken.getToken();
      
      setActiveTemplate(hasToken);
      
    }
    
    super.onPlaceChange(event);
    
    OnsenUi.initializeOnsen(new OnsenReadyHandler() {
      public void onReady() {
        OnsUtils.log("ONSEN READY");
      }
    });
    
    OnsenUi.pushPage(newToken);
    
    /*
    OnsenUi.initializeOnsen(new OnsenReadyHandler() {
      public void onReady() {
        OnsUtils.log("ONSEN READY");
      }
    });
//  OnsenUi.compile(template.getElement());
    OnsenUi.pushPage(newToken);
    */
    
  }
  
  
}
