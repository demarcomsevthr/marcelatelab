package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.NavigatorEvent;
import it.mate.onscommons.client.onsen.dom.Page;
import it.mate.onscommons.client.utils.CdvUtils;

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
        OnsenUi.onPagePushed(new Delegate<NavigatorEvent>() {
          public void execute(NavigatorEvent event) {
            Page enteringPage = event.getEnterPage();
            if (enteringPage != null) {
              String enteringPageName = enteringPage.getName();
              if (!enteringPageName.equals(OnsActivityManager.getActivePanelId())) {
                CdvUtils.log("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
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
