package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.onscommons.client.onsen.OnsenReadyHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.OnsUtils;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.web.bindery.event.shared.EventBus;

public class OnsActivityManager extends ActivityManager {
  
  OnsNavigationDisplay onsDisplay;

  public OnsActivityManager(OnsActivityMapper mapper, EventBus eventBus) {
    super(mapper, eventBus);
  }
  
  public void setOnsDisplay(OnsNavigationDisplay onsDisplay, HasToken initialPlace) {
    this.onsDisplay = onsDisplay;
    setActiveTemplate(initialPlace);
  }
  
  private OnsTemplate setActiveTemplate(HasToken place) {
    OnsTemplate template = onsDisplay.getPlaceTemplate(place);
    super.setDisplay(template);
    /*
    OnsenUi.compile(template.getElement());
    OnsenUi.pushPage(place.getToken());
    */
    return template;
  }

  @Override
  public void onPlaceChange(PlaceChangeEvent event) {

    OnsTemplate template = null;
    
    Place newPlace = event.getNewPlace();
    
    OnsUtils.log("onPlaceChange: newPlace = " + newPlace);
    
    String newToken = null;
    
    if (newPlace instanceof HasToken) {
      
      HasToken hasToken = (HasToken)newPlace;
      
      newToken =  hasToken.getToken();
      
      template = setActiveTemplate(hasToken);
      
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
