package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenReadyHandler;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.NavigatorEvent;
import it.mate.onscommons.client.onsen.dom.Page;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.CdvUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.JavaScriptObject;
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

public class OnsActivityManagerWithNavigator extends ActivityManager {
  
  private EventBus eventBus;

  private static Panel activePanel;
  
  private static String activePanelId = "";
  
  private boolean allowPagePoping = false;
  
  private boolean pagePopingHandlerInitialized = false;
  
  private boolean pagePushedHandlerInitialized = false;
  
  private List<OnsTemplate> templates;
  
  private Map<String, Place> placesMap = new HashMap<String, Place>();

  public OnsActivityManagerWithNavigator(ActivityMapper mapper, EventBus eventBus) {
    super(mapper, eventBus);
    this.eventBus = eventBus;
    if (!OnsenUi.isInitialized()) {
      OnsenUi.initializeOnsen(new OnsenReadyHandler() {
        public void onReady() {
          CdvUtils.log("ONSEN READY");
        }
      });
    }
    setPagePushedHandler();
    setPagePopingHandler();
    setDisplay(new SimplePanel());
  }
  
  public static AcceptsOneWidget getActivePanel() {
    return (AcceptsOneWidget)activePanel;
  }
  
  private OnsTemplate getTemplateByPlace(HasToken place) {
    if (templates == null) {
      templates = new ArrayList<OnsTemplate>();
    }
    String token = place.getToken();
    for (OnsTemplate template : templates) {
      if (template.getToken().equals(token)) {
        return template;
      }
    }
    OnsTemplate template = new OnsTemplate(token);
    templates.add(template);
    return template;
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
    Place newPlace = event.getNewPlace();
    CdvUtils.log("ON PLACE CHANGE: newPlace = " + newPlace);
    boolean preventPush = setActivePanelFromTemplate(newPlace);
    CdvUtils.log("STARTING ACTIVITY FOR " + newPlace);
    super.onPlaceChange(event);
    if (!preventPush) {
      Integer insertIndex = null;
      if (event instanceof OnsPlaceChangeEvent) {
        OnsPlaceChangeEvent onsEvent = (OnsPlaceChangeEvent)event;
        insertIndex = onsEvent.getInsertIndex();
      }
      pushPage(newPlace, insertIndex);
    } else {
      Element pageElement = OnsenUi.getNavigator().getCurrentPage().getPageElement();
      OnsenUi.compileElement(pageElement);
    }
  }
  
  private boolean setActivePanelFromTemplate(Place newPlace) {
    boolean preventPush = false;
    HasToken hasToken = (HasToken)newPlace;
    String newToken = hasToken.getToken();
    String currentPageName = OnsenUi.getNavigator().getCurrentPage().getName();
    if (newToken.equals(currentPageName)) {
      setActivePanelFromCurrentPage(newPlace);
      preventPush = true;
    } else {
      OnsTemplate template = getTemplateByPlace(hasToken);
      template.clear();
      setActivePanel(template, newToken);
    }
    return preventPush;
  }
  
  private void setActivePanelFromCurrentPage(Place newPlace) {
    HasToken hasToken = (HasToken)newPlace;
    String newToken = hasToken.getToken();
    Element innerElem = OnsenUi.getNavigator().getCurrentPage().getInnerPageElement();
    ElementWrapperPanel wrapper = new ElementWrapperPanel(innerElem);
    String currentPageName = OnsenUi.getNavigator().getCurrentPage().getName();
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
  
  private void pushPage(Place newPlace, Integer insertIndex) {
    compileActivePanel();
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    if (newToken != null) {
      placesMap.put(newToken, newPlace);
      boolean pagePushed = false;
      JavaScriptObject currentPage = OnsenUi.getNavigator().getCurrentPage();
      if (currentPage != null) {
        String currentPageName = GwtUtils.getJsPropertyString(currentPage, "name");
        if (!newToken.equals(currentPageName)) {
          if (insertIndex != null) {
            OnsenUi.getNavigator().log("BEFORE INSERT PAGE");
            OnsenUi.getNavigator().insertPage(insertIndex, newToken);
            GwtUtils.deferredExecution(new Delegate<Void>() {
              public void execute(Void element) {
                allowPagePoping = true;
                OnsenUi.getNavigator().popPage();
              }
            });
          } else {
            OnsenUi.getNavigator().pushPage(newToken);
          }
          pagePushed = true;
        }
      } else {
        OnsenUi.getNavigator().pushPage(newToken);
        pagePushed = true;
      }
      if (!pagePushed) {
        OnsenUi.getNavigator().resetToPage(newToken);
      }
    }
  }
  
  private void compileActivePanel() {
    if (!CdvUtils.isReallyAttached(activePanelId)) {
      try {
        RootPanel.get().remove(activePanel);
      } catch (Exception ex) { }
      CdvUtils.log("ADDING PANEL TO DOCUMENT - " + activePanel.getElement());
      RootPanel.get().add(activePanel);
    }
    if (CdvUtils.isReallyAttached(activePanelId)) {
      Element templateElem = activePanel.getElement();
      if (templateElem != null) {
        OnsenUi.compileElement(templateElem);
      }
    }
  }
  
  private void setPagePushedHandler() {
    if (!pagePushedHandlerInitialized) {
      pagePushedHandlerInitialized = true;
      
      OnsenUi.getNavigator().onPagePushed(new Delegate<NavigatorEvent>() {
        public void execute(NavigatorEvent event) {
          Page enteringPage = event.getEnterPage();
          if (enteringPage != null) {
            String enteringPageName = enteringPage.getName();
            CdvUtils.log("AFTER PUSH PAGE " + enteringPageName);
            OnsenUi.getNavigator().log("NAVIGATOR PAGE");
          }
        }
      });
      
    }
  }
  
  private void setPagePopingHandler() {
    if (!pagePopingHandlerInitialized) {
      pagePopingHandlerInitialized = true;
      
      OnsenUi.getNavigator().onPagePoping(new Delegate<NavigatorEvent>() {
        public void execute(NavigatorEvent event) {
          if (allowPagePoping) {
            allowPagePoping = false;
            CdvUtils.log("CONTINUE POPING");
            OnsenUi.getNavigator().log("BEFORE POPING");
            return;
          }
          
          int index = OnsenUi.getNavigator().getCurrentPage().getIndex() - 1;
          Page prevPage = OnsenUi.getNavigator().getPages().get(index);
          String prevPageName = prevPage.getName();
          CdvUtils.log("PREV PAGE NAME = " + prevPageName);
          CdvUtils.log("DESTROYING PAGE " + prevPage);
          prevPage.destroy();

          CdvUtils.log("CANCELING POP EVENT");
          event.cancel();
          OnsenUi.getNavigator().log("AFTER DESTROY PAGE");
          Place prevPlace = placesMap.get(prevPageName);
          CdvUtils.log("GOING TO PLACE " + prevPlace);
          eventBus.fireEvent(new OnsPlaceChangeEvent(prevPlace, index));
        }
      });
      
    }
  }
  
  public static class OnsPlaceChangeEvent extends PlaceChangeEvent {
    private Integer insertIndex = null;
    public OnsPlaceChangeEvent(Place newPlace, Integer insertIndex) {
      super(newPlace);
      this.insertIndex = insertIndex;
    }
    public Integer getInsertIndex() {
      return insertIndex;
    }
  }

}
