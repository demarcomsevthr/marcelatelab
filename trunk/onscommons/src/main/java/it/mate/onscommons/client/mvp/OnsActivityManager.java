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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class OnsActivityManager extends ActivityManager {
  
  OnsNavigationDisplay onsDisplay;
  
  EventBus eventBus;

  private static Panel activePanel;
  
  private static String activePanelId = "";
  
  public OnsActivityManager(OnsActivityMapper mapper, EventBus eventBus) {
    super(mapper, eventBus);
    this.eventBus = eventBus;
  }
  
  public abstract Place getPlaceFromTepmplateId(String id);
  
  public void setOnsDisplay(OnsNavigationDisplay onsDisplay, HasToken initialPlace) {
    this.onsDisplay = onsDisplay;
    super.setDisplay(new SimplePanel());
  }
  
  public static AcceptsOneWidget getActivePanel() {
    return (AcceptsOneWidget)activePanel;
  }
  
  private static void setActivePanel(Panel panel, String id) {
    activePanel = panel;
    activePanelId = id;
  }
  
  public static String getActivePanelId() {
    return activePanelId;
  }
  
  @Override
  public void onPlaceChange(PlaceChangeEvent event) {

    final Place newPlace = event.getNewPlace();
    
    CdvUtils.log("ON PLACE CHANGE: newPlace = " + newPlace);
    
    boolean preventPush = false;
    
    if (MvpUtils.PUSH_PAGE_IN_ACTIVITY_MANAGER) {
      preventPush = setActivePanelFromTemplate(newPlace);
    } else {
      setActivePanelFromCurrentPage(newPlace);
      preventPush = true;
    }
    
    CdvUtils.log("STARTING ACTIVITY FOR " + newPlace);
    super.onPlaceChange(event);
    
    if (MvpUtils.PUSH_PAGE_IN_ACTIVITY_MANAGER && !preventPush) {
      
      boolean insertPageFirst = false;
      if (event instanceof OnsPlaceChangeEvent) {
        OnsPlaceChangeEvent onsEvent = (OnsPlaceChangeEvent)event;
        insertPageFirst = onsEvent.isInsertPageFirst();
      }
      
      pushPageAfterPlaceChange(newPlace, insertPageFirst);
    } else {
      Element onsPageElement = OnsenUi.getCurrentPageElement();
      OnsenUi.compile(onsPageElement);
    }
    
    setPagePopingHandler();
    
  }
  
  private boolean setActivePanelFromTemplate(Place newPlace) {
    boolean preventPush = false;
    HasToken hasToken = (HasToken)newPlace;
    String newToken = hasToken.getToken();
    String currentPageName = OnsenUi.getCurrentPageName();
    if (newToken.equals(currentPageName)) {
      setActivePanelFromCurrentPage(newPlace);
      preventPush = true;
    } else {
      OnsTemplate template = OnsNavigationDisplay.getTemplateByPlace(hasToken);
      template.clear();
      setActivePanel(template, newToken);
    }
    return preventPush;
  }
  
  private void setActivePanelFromCurrentPage(Place newPlace) {
    HasToken hasToken = (HasToken)newPlace;
    String newToken = hasToken.getToken();
    Element pageElement = OnsenUi.getCurrentPageElement();
    Element innerElem = OnsenUi.getInnerPageElement(pageElement);
    ElementWrapperPanel wrapper = new ElementWrapperPanel(innerElem);
    String currentPageName = OnsenUi.getCurrentPageName();
    CdvUtils.log("WRAPPING CURRENT PAGE " + currentPageName);
    setActivePanel(wrapper, newToken);
  }
  
  public static class ElementWrapperPanel extends ComplexPanel implements AcceptsOneWidget {
    protected ElementWrapperPanel(Element elem) {
      setElement(elem);
    }
    @Override
    public void setWidget(IsWidget w) {
      add(w);
    }
    @Override
    public void add(Widget child) {
      add(child, getElement());
    }
  }
  
  private boolean pageChangedHandlerInitialized = false;

  private void pushPageAfterPlaceChange(Place newPlace, boolean insertPageFirst) {
    
    if (!OnsenUi.isInitialized()) {
      OnsenUi.initializeOnsen(new OnsenReadyHandler() {
        public void onReady() {
          CdvUtils.log("ONSEN READY");
        }
      });
    }

    if (!pageChangedHandlerInitialized) {
      pageChangedHandlerInitialized = true;
      OnsenUi.onPageChanged(new Delegate<JavaScriptObject>() {
        public void execute(JavaScriptObject event) {
          JavaScriptObject enteringPage = GwtUtils.getJsPropertyJso(event, "enterPage");
          if (enteringPage != null) {
            
            OnsenUi.destroyPage("");
            
            String enteringPageName = GwtUtils.getJsPropertyString(enteringPage, "name");
            CdvUtils.log("AFTER PUSH PAGE NAME = " + enteringPageName);
            
            OnsenUi.logNavigator("NAVIGATOR PAGE");
            
            if (!enteringPageName.equals(activePanelId)) {
              
              Place newPlace = getPlaceFromTepmplateId(enteringPageName);
              
              CdvUtils.log("FIRING NEW PLACE CHANGE EVENT WITH " + newPlace);
              
//            eventBus.fireEvent(new PlaceChangeEvent(newPlace));
              
            }
            
          }
        }
      });
    }
    
    compileActivePanel();
    
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    if (newToken != null) {
      boolean pagePushed = false;
      JavaScriptObject currentPage = OnsenUi.getCurrentPage();
      if (currentPage != null) {
        String currentPageName = GwtUtils.getJsPropertyString(currentPage, "name");
        if (!newToken.equals(currentPageName)) {
          if (insertPageFirst) {
            
            OnsenUi.logNavigator("BEFORE INSERT PAGE");
            
            JsArray<JavaScriptObject> pages = OnsenUi.getPages();
            int index = pages.length() - 1;
            OnsenUi.insertPage(index, newToken);
//          OnsenUi.insertPage(-1, newToken);
            
            GwtUtils.deferredExecution(new Delegate<Void>() {
              public void execute(Void element) {
                allowPagePoping = true;
                OnsenUi.popPage();
              }
            });

            CdvUtils.log("CURRENT PAGE NAME IS " + OnsenUi.getCurrentPageName());
            
          } else {
            OnsenUi.pushPage(newToken);
          }
          pagePushed = true;
        }
      } else {
        OnsenUi.pushPage(newToken);
        pagePushed = true;
      }
      if (!pagePushed) {
        OnsenUi.resetToPage(newToken);
      }
    }
    
  }
  
  private void compileActivePanel() {
    if (!CdvUtils.isReallyAttached(activePanelId)) {
      try {
        RootPanel.get().remove(activePanel);
      } catch (Exception ex) { }
      Element templateElem = activePanel.getElement();
      CdvUtils.log("ADDING PANEL TO DOCUMENT - " + templateElem);
      RootPanel.get().add(activePanel);
    }
    if (CdvUtils.isReallyAttached(activePanelId)) {
      Element templateElem = activePanel.getElement();
      if (templateElem != null) {
        OnsenUi.compile(templateElem);
      }
    }
  }
  
  private boolean allowPagePoping = false;
  
  private boolean pagePopingHandlerInitialized = false;
  
  private void setPagePopingHandler() {
    if (!pagePopingHandlerInitialized) {
      pagePopingHandlerInitialized = true;
      OnsenUi.onPagePoping(new Delegate<JavaScriptObject>() {
        public void execute(JavaScriptObject event) {
          
//        OnsenUi.logNavigator("BEFORE PAGE POP");
          
          if (allowPagePoping) {
            allowPagePoping = false;
            CdvUtils.log("CONTINUE POPING");
            return;
          }
          
          JsArray<JavaScriptObject> pages = OnsenUi.getPages();
          
          String prevPageName = "home";
          if (pages.length() > 1) {
            int lastPageIt = pages.length() - 2;
            JavaScriptObject page = pages.get(lastPageIt);
            prevPageName = GwtUtils.getJsPropertyString(page, "name");
          }
          CdvUtils.log("PREV PAGE NAME = " + prevPageName);
          
          OnsenUi.destroyPage("");
          OnsenUi.destroyPage(prevPageName);
          
          CdvUtils.log("CANCELING POP EVENT");
          OnsenUi.cancelEvent(event);
          
          Place prevPlace = getPlaceFromTepmplateId(prevPageName);
          CdvUtils.log("GOING TO PLACE " + prevPlace);
          eventBus.fireEvent(new OnsPlaceChangeEvent(prevPlace, true));
          
        }
      });
    }
  }
  
  public static class OnsPlaceChangeEvent extends PlaceChangeEvent {
    
    private boolean insertPageFirst = false;

    public OnsPlaceChangeEvent(Place newPlace, boolean insertPageFirst) {
      super(newPlace);
      this.insertPageFirst = insertPageFirst;
    }
    
    public boolean isInsertPageFirst() {
      return insertPageFirst;
    }
    
  }
  

}
