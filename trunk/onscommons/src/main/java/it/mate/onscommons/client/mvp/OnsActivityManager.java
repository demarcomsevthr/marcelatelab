package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.onscommons.client.onsen.OnsenReadyHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.OnsUtils;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class OnsActivityManager extends ActivityManager {
  
  OnsNavigationDisplay onsDisplay;
  
  EventBus eventBus;

  private static OnsTemplate activeTemplate;

  public OnsActivityManager(OnsActivityMapper mapper, EventBus eventBus) {
    super(mapper, eventBus);
    this.eventBus = eventBus;
  }
  
  public void setOnsDisplay(OnsNavigationDisplay onsDisplay, HasToken initialPlace) {
    this.onsDisplay = onsDisplay;
    setActiveTemplate(initialPlace);
    super.setDisplay(new SimplePanel());
  }
  
  private void setActiveTemplate(HasToken place) {
    OnsTemplate template = onsDisplay.getTemplateByPlace(place);
    template.clear();
    activeTemplate = template;
  }
  
  public static OnsTemplate getActiveTemplate() {
    return activeTemplate;
  }
  
  @Override
  public void onPlaceChange(PlaceChangeEvent event) {

    final Place newPlace = event.getNewPlace();
    
    OnsUtils.log("onPlaceChange: newPlace = " + newPlace);
    
    if (newPlace instanceof HasToken) {
      HasToken hasToken = (HasToken)newPlace;
      setActiveTemplate(hasToken);
    }
    
    super.onPlaceChange(event);
    
    OnsenUi.initializeOnsen(new OnsenReadyHandler() {
      public void onReady() {
        OnsUtils.log("ONSEN READY");
      }
    });
    
    OnsUtils.log("activeTemplate " + activeTemplate.getId() + " attached = " + isReallyAttached(activeTemplate.getId()));
    OnsUtils.log("activeTemplate " + activeTemplate);
    
    if (!isReallyAttached(activeTemplate.getId())) {
      try {
        RootPanel.get().remove(activeTemplate);
      } catch (Exception ex) { }
      RootPanel.get().add(activeTemplate);
    }

    if (isReallyAttached(activeTemplate.getId())) {
      Element templateElem = activeTemplate.getElement();
      OnsUtils.log("compiling element " + templateElem);
      if (templateElem != null) {
        OnsenUi.compile(templateElem);
      }
    }
    
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    OnsenUi.pushPage(newToken);
    
  }
  
  protected static native boolean isReallyAttached(String elemId) /*-{
    return $doc.getElementById(elemId) != null;
  }-*/;
  
  
  
}
