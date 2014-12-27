package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenReadyHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.CdvUtils;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public abstract class OnsActivityManager extends ActivityManager {
  
  OnsNavigationDisplay onsDisplay;
  
  EventBus eventBus;

  private static OnsTemplate activeTemplate;
  
  private boolean pageChangingHandlerInitialized = false;

  private boolean pageChangedHandlerInitialized = false;

  public OnsActivityManager(OnsActivityMapper mapper, EventBus eventBus) {
    super(mapper, eventBus);
    this.eventBus = eventBus;
  }
  
  public abstract Place getPlaceFromTepmplateId(String id);
  
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
    
    CdvUtils.log("onPlaceChange: newPlace = " + newPlace);
    
    if (newPlace instanceof HasToken) {
      HasToken hasToken = (HasToken)newPlace;
      setActiveTemplate(hasToken);
    }
    
    super.onPlaceChange(event);
    
    OnsenUi.initializeOnsen(new OnsenReadyHandler() {
      public void onReady() {
        CdvUtils.log("ONSEN READY");
      }
    });

    /*
    if (!pageChangingHandlerInitialized) {
      pageChangingHandlerInitialized = true;
      OnsenUi.onPageChanging(new Delegate<JavaScriptObject>() {
        public void execute(JavaScriptObject event) {
          JavaScriptObject currentPage = GwtUtils.getJsPropertyJso(event, "currentPage");
          String changingPageName = GwtUtils.getJsPropertyString(currentPage, "name");
          CdvUtils.log("CHANGING PAGE NAME = " + changingPageName);
        }
      });
    }
    */
    
    //TODO: controllare se l'enter page e' diversa dal tempate attivo
    //      ==> gestire il cambio place (anche se non ce l'ho il place in mano!!!)
    
    if (!pageChangedHandlerInitialized) {
      pageChangedHandlerInitialized = true;
      OnsenUi.onPageChanged(new Delegate<JavaScriptObject>() {
        public void execute(JavaScriptObject event) {
          JavaScriptObject enteringPage = GwtUtils.getJsPropertyJso(event, "enterPage");
          if (enteringPage != null) {
            String enteringPageName = GwtUtils.getJsPropertyString(enteringPage, "name");
            CdvUtils.log("ENTER PAGE NAME = " + enteringPageName);
            
            if (!enteringPageName.equals(activeTemplate.getId())) {
              
              Place newPlace = getPlaceFromTepmplateId(enteringPageName);
              
              CdvUtils.log("FIRING NEW PLACE CHANGE EVENT WITH " + newPlace);
              
              eventBus.fireEvent(new PlaceChangeEvent(newPlace));
              
            }
            
          }
        }
      });
    }
    
    CdvUtils.log("activeTemplate " + activeTemplate.getId() + " attached = " + isReallyAttached(activeTemplate.getId()));
    CdvUtils.log("activeTemplate " + activeTemplate);
    
    if (!isReallyAttached(activeTemplate.getId())) {
      try {
        RootPanel.get().remove(activeTemplate);
      } catch (Exception ex) { }
      RootPanel.get().add(activeTemplate);
    }

    if (isReallyAttached(activeTemplate.getId())) {
      Element templateElem = activeTemplate.getElement();
      CdvUtils.log("compiling element " + templateElem);
      if (templateElem != null) {
        OnsenUi.compile(templateElem);
      }
    }
    
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    if (newToken != null) {
      boolean pagePushed = false;
      JavaScriptObject currentPage = OnsenUi.getCurrentPage();
      if (currentPage != null) {
        String currentPageName = GwtUtils.getJsPropertyString(currentPage, "name");
        if (!newToken.equals(currentPageName)) {
          OnsenUi.pushPage(newToken);
          pagePushed = true;
        }
      }
      if (!pagePushed) {
        OnsenUi.resetToPage(newToken);
      }
    }
    
  }
  
  protected static native boolean isReallyAttached(String elemId) /*-{
    return $doc.getElementById(elemId) != null;
  }-*/;
  
  
  
}
