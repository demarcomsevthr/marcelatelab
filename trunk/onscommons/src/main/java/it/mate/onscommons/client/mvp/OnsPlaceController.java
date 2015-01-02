package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.onsen.OnsenUi;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class OnsPlaceController extends PlaceController {

  @Inject
  public OnsPlaceController(EventBus eventBus) {
    super(eventBus);
  }
  
  @Override
  public void goTo(Place newPlace) {
    
    super.goTo(newPlace);

    /*
    HasToken hasToken = (HasToken)newPlace;
    final String newToken = hasToken.getToken();
    
    if (!OnsenUi.isInitialized()) {
      OnsenUi.initializeOnsen(new OnsenReadyHandler() {
        public void onReady() {
          CdvUtils.log("ONSEN READY");
        }
      });
    }
    
    if (OnsenUi.isInitialized() && !MvpUtils.PUSH_PAGE_IN_ACTIVITY_MANAGER && !newToken.equals(OnsenUi.getCurrentPage().getName())) {
      OnsTemplate template = OnsNavigationDisplay.getTemplateByPlace(hasToken);
      String activeTemplateId = template.getId();
      
      if (!CdvUtils.isReallyAttached(activeTemplateId)) {
        try {
          RootPanel.get().remove(template);
        } catch (Exception ex) { }
        RootPanel.get().add(template);
      }
      
      if (!template.isCompiled()) {
        Element templateElem = template.getElement();
        if (templateElem != null) {
          if ("".equals(templateElem.getInnerHTML())) {
            templateElem.setInnerHTML("<ons-page></ons-page>");
          }
          CdvUtils.log("compiling element " + CdvUtils.elementToString(templateElem));
          OnsenUi.compile(templateElem);
          template.setCompiled(true);
        }
      }
      
      pushPageBeforePlaceChange(newToken);
      
    } else {
      super.goTo(newPlace);
    }
    
    */
    
  }
  
  private void pushPageBeforePlaceChange(String newToken) {
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
