package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.places.HasToken;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.Navigator;
import it.mate.onscommons.client.onsen.dom.NavigatorEvent;
import it.mate.onscommons.client.onsen.dom.Page;
import it.mate.onscommons.client.ui.OnsLayoutView;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.onscommons.client.ui.OnsSlidingMenu;
import it.mate.phgcommons.client.utils.PhgUtils;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.dom.client.Element;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

public abstract class OnsActivityManagerWithSlidingNavigator extends OnsActivityManagerBase {
  
  private static final boolean CLOSE_MENU_AFTER_PUSH = Boolean.parseBoolean( PhgUtils.getLocalStorageItemForDebug("debug.OnsActivityManagerWithSlidingNavigator.closeMenuAfterPush", "false") );
  
  private boolean allowPagePoping = false;
  
  private boolean navigatorInitialized = false;
  
  private HasToken lastProcessedPlace;
  
  private Navigator navigator;
  
  private OnsSlidingMenu slidingMenu;
  
  public abstract void onNavigatorInitialized(Navigator navigator);
  
  public OnsActivityManagerWithSlidingNavigator(ActivityMapper mapper, EventBus eventBus, final OnsLayoutView layoutView) {
    super(mapper, eventBus);
    RootPanel.get().add(layoutView);
    OnsenUi.compileElement(layoutView.asWidget().getElement());
    setNavigator(layoutView.getOnsNavigator());
    setSlidingMenu(layoutView.getOnsSlidingMenu());
  }
  
  @Override
  public void onPlaceChange(PlaceChangeEvent event) {
    Place newPlace = event.getNewPlace();
    
    HasToken newTokenizedPlace = (HasToken)newPlace;
    if (lastProcessedPlace != null && lastProcessedPlace.getToken().equals(newTokenizedPlace.getToken())) {
      PhgUtils.log("ON PLACE CHANGE: PLACE JUST PROCESSED (SKIP) newPlace = " + newPlace);
      slidingMenu.getController().closeMenu();
      return;
    } else {
      lastProcessedPlace = newTokenizedPlace;
    }
    
    PhgUtils.log("ON PLACE CHANGE: newPlace = " + newPlace);
    boolean preventPush = setActivePanelFromTemplate(newPlace);
    PhgUtils.log("STARTING ACTIVITY FOR " + newPlace);
    super.onPlaceChange(event);
    if (!preventPush) {
      Integer insertIndex = null;
      if (event instanceof OnsPlaceChangeEvent) {
        OnsPlaceChangeEvent onsEvent = (OnsPlaceChangeEvent)event;
        insertIndex = onsEvent.getInsertIndex();
      }
      pushPage(newPlace, insertIndex);
    } else {
      Element pageElement = navigator.getCurrentPage().getPageElement();
      OnsenUi.compileElement(pageElement);
    }
    
  }
  
  @Override
  protected Page getCurrentPage() {
    return navigator.getCurrentPage();
  }
  
  private void pushPage(Place newPlace, Integer insertIndex) {
    compileActivePanel();
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    putPlace(newPlace);
    
    boolean pagePushed = false;
    Page currentPage = navigator.getCurrentPage();
    
    Delegate<Void> onPushTransitionEndDelegate = null;
    if (CLOSE_MENU_AFTER_PUSH) {
      onPushTransitionEndDelegate = new Delegate<Void>() {
        public void execute(Void element) {
          PhgUtils.log("onPushTransitionEndDelegate: closing menu");
          slidingMenu.getController().closeMenu();
        }
      };
    } else {
      slidingMenu.getController().closeMenu();
    }
    
    if (currentPage != null) {
      String currentPageName = currentPage.getName();
      if (!newToken.equals(currentPageName)) {
        if (insertIndex != null) {
          navigator.log("BEFORE INSERT PAGE");
          navigator.insertPage(insertIndex, newToken);
          GwtUtils.deferredExecution(new Delegate<Void>() {
            public void execute(Void element) {
              allowPagePoping = true;
              navigator.popPage();
            }
          });
        } else {
          navigator.pushPage(newToken, onPushTransitionEndDelegate);
        }
        pagePushed = true;
      }
    } else {
      navigator.pushPage(newToken, onPushTransitionEndDelegate);
      pagePushed = true;
    }
    
    if (!pagePushed) {
      navigator.resetToPage(newToken);
    }
    
  }
  
  protected void setAfterPagePushHandler() {
    if (!navigatorInitialized) {
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          setAfterPagePushHandler();
        }
      });
    } else {
      navigator.onAfterPagePush(new Delegate<NavigatorEvent>() {
        public void execute(NavigatorEvent event) {
          Page enteringPage = event.getEnterPage();
          if (enteringPage != null) {
            String enteringPageName = enteringPage.getName();
            PhgUtils.log("AFTER PUSH PAGE " + enteringPageName);
            navigator.log("NAVIGATOR PAGE");
          }
        }
      });
    }
  }
  
  protected void setBeforePagePopHandler() {
    if (!navigatorInitialized) {
      GwtUtils.deferredExecution(new Delegate<Void>() {
        public void execute(Void element) {
          setBeforePagePopHandler();
        }
      });
    } else {
      navigator.onBeforePagePop(new Delegate<NavigatorEvent>() {
        public void execute(NavigatorEvent event) {
          if (allowPagePoping) {
            allowPagePoping = false;
            PhgUtils.log("CONTINUE POPING");
            navigator.log("BEFORE POPING");
            return;
          }
          int index = navigator.getCurrentPage().getIndex() - 1;
          Page prevPage = navigator.getPages().get(index);
          String prevPageName = prevPage.getName();
          PhgUtils.log("PREV PAGE NAME = " + prevPageName);
          PhgUtils.log("DESTROYING PAGE " + prevPage);
          prevPage.destroy();
          PhgUtils.log("CANCELING POP EVENT");
          event.cancel();
          navigator.log("AFTER DESTROY PAGE");
          Place prevPlace = getPlace(prevPageName);
          PhgUtils.log("GOING TO PLACE " + prevPlace);
          lastProcessedPlace = null;
          eventBus.fireEvent(new OnsPlaceChangeEvent(prevPlace, index));
        }
      });
    }
  }
  
  private void setNavigator(final OnsNavigator onsNavigator) {
    OnsenUi.onAvailableElement(onsNavigator, new Delegate<Element>() {
      public void execute(Element navigatorElement) {
        OnsenUi.compileElement(navigatorElement);
        GwtUtils.deferredExecution(new Delegate<Void>() {
          public void execute(Void element) {
            // NB: se si usa controller singleton va in loop
//          Navigator navigator = onsNavigator.getControllerSingleton();
            Navigator navigator = onsNavigator.getController();
            if (navigator == null) {
              setNavigator(onsNavigator);
            } else {
              OnsActivityManagerWithSlidingNavigator.this.navigator = navigator;
              OnsenUi.setNavigator(navigator);
              navigatorInitialized = true;
              onNavigatorInitialized(navigator);
            }
          }
        });
      }
    });
  }
  
  private void setSlidingMenu(OnsSlidingMenu slidingMenu) {
    this.slidingMenu = slidingMenu;
  }
  
}
