package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class OnsPlaceHistoryHandler extends PlaceHistoryHandler {
  
  private static String defaultPlaceClassName;

  public OnsPlaceHistoryHandler(PlaceHistoryMapper mapper, Place defaultPlace) {
    super(mapper, new OnsHistorian());
    int ip = defaultPlace.getClass().getName().lastIndexOf(".");
    defaultPlaceClassName = defaultPlace.getClass().getName().substring(ip + 1);
  }
  
  
  public static class OnsHistorian implements Historian, HasValueChangeHandlers<String> {

    private boolean pageChangedHandlerInitialized = false;
    
    private HandlerManager handlers = new HandlerManager(null);
    
    private String token = "";
    
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> handler) {
      
      if (OnsenUi.isInitialized()) {
        addPageChangeHandler();
      } else {
        OnsenUi.addInitializationHandler(new Delegate<Void>() {
          public void execute(Void element) {
            addPageChangeHandler();
          }
        });
      }
      
      return handlers.addHandler(ValueChangeEvent.getType(), handler);
    }
    
    public void fireEvent(GwtEvent<?> event) {
      handlers.fireEvent(event);
    }

    private void addPageChangeHandler() {
      if (!pageChangedHandlerInitialized) {
        pageChangedHandlerInitialized = true;
        OnsenUi.onPageChanged(new Delegate<JavaScriptObject>() {
          public void execute(JavaScriptObject event) {
            JavaScriptObject enteringPage = GwtUtils.getJsPropertyJso(event, "enterPage");
            if (enteringPage != null) {
              
              //TODO
              JavaScriptObject enteringPageElement = GwtUtils.getJsPropertyJso(enteringPage, "element");
              Element enteringPageContext = GwtUtils.getJsPropertyJso(enteringPageElement, "context").cast();
//            CdvUtils.log("ENTERING PAGE PARENT " + enteringPageContext.getParentElement().getNodeName());
              
              String enteringPageName = GwtUtils.getJsPropertyString(enteringPage, "name");
//            CdvUtils.log("ENTERING PAGE NAME = " + enteringPageName);
              
              if (!enteringPageName.equals(OnsActivityManager.getActivePanelId())) {
                
                newItem(defaultPlaceClassName+ ":"+enteringPageName, true);
                
              }
              
            }
          }
        });
      }
    }
    

    public String getToken() {
      return token;
    }

    public void newItem(String token, boolean issueEvent) {
      this.token = token;
      if (issueEvent) {
        ValueChangeEvent.fire(this, token);
      }
    }
    
  }
  
  

}
