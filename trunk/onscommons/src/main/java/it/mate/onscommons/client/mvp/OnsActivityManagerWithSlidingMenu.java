package it.mate.onscommons.client.mvp;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BaseActivity;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.places.HasToken;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.onsen.dom.Page;
import it.mate.onscommons.client.ui.OnsTemplate;
import it.mate.onscommons.client.utils.CdvUtils;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

public class OnsActivityManagerWithSlidingMenu extends OnsActivityManagerBase {
  
  public OnsActivityManagerWithSlidingMenu(ActivityMapper mapper, EventBus eventBus, AbstractBaseView<? extends BasePresenter> menuView) {
    super(mapper, eventBus);
    initMenuTemplate(menuView);
  }
  
  private void initMenuTemplate(AbstractBaseView<? extends BasePresenter> menuView) {
    OnsTemplate menuTemplate = new OnsTemplate("menu");
    BaseActivity activity = (BaseActivity)menuView.getPresenter();
    activity.start((AcceptsOneWidget)menuTemplate, (com.google.gwt.event.shared.EventBus)eventBus);
    RootPanel.get().add(menuTemplate);
    OnsenUi.compileElement(menuTemplate.getElement());
    OnsenUi.getSlidingMenu().setMenuPage("menu");
  }
  
  @Override
  public void onPlaceChange(PlaceChangeEvent event) {
    Place newPlace = event.getNewPlace();
    CdvUtils.log("ON PLACE CHANGE: newPlace = " + newPlace);
    setActivePanelFromTemplate(newPlace);
    super.onPlaceChange(event);
    compileActivePanel();
    HasToken hasToken = (HasToken)newPlace;
    String newToken =  hasToken.getToken();
    putPlace(newPlace);
    OnsenUi.getSlidingMenu().setMainPage(newToken);
  }
  
  @Override
  protected Page getCurrentPage() {
    return null;
  }
  
  @Override
  protected void setAfterPagePushHandler() {
    
  }

  @Override
  protected void setBeforePagePopHandler() {
    
  }

}
