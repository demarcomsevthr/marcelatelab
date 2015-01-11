package it.mate.protons.client.activities;

import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.onscommons.client.event.OnsPlaceChangeEvent;
import it.mate.onscommons.client.mvp.OnsAbstractActivity;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.phgcommons.client.place.PlaceControllerWithHistory;
import it.mate.protons.client.factories.AppClientFactory;
import it.mate.protons.client.places.IngredientPlace;
import it.mate.protons.client.places.MainPlace;
import it.mate.protons.client.view.IngredientSub1View;
import it.mate.protons.client.view.IngredientSub2View;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

@SuppressWarnings("rawtypes")
public class IngredientActivity extends OnsAbstractActivity implements 
    IngredientSub1View.Presenter, IngredientSub2View.Presenter
  {
  
  private IngredientPlace place;
  
  private BaseView view;
  
  public IngredientActivity(BaseClientFactory clientFactory, IngredientPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {

    if (place.getToken().equals(IngredientPlace.SUB1)) {
      this.view = AppClientFactory.IMPL.getGinjector().getIngredientSub1View();
    }
    
    view.setPresenter(this);
    panel.setWidget(view.asWidget());
    
    retrieveModel();
    
  }
  
  private void retrieveModel() {
    
  }
  
  @Override
  public BaseView getView() {
    return view;
  }

  @Override
  public void goToPrevious() {
    if (OnsenUi.isNavigatorLayoutPattern()) {
      OnsenUi.getNavigator().popPage();
    } else {
      if (AppClientFactory.IMPL.getPlaceController() instanceof PlaceControllerWithHistory) {
        PlaceControllerWithHistory placeController = (PlaceControllerWithHistory)AppClientFactory.IMPL.getPlaceController();
//      placeController.goBack(new MainPlace());
        Place prevPlace = placeController.getPreviousPlace();
        if (prevPlace == null) {
          prevPlace = new MainPlace();
        }
//      placeController.goToWithEvent(new OnsPlaceChangeEvent(prevPlace).setAnimation(OnsenUi.ANIMATION_REVERSE_SLIDE));
        placeController.goToWithEvent(new OnsPlaceChangeEvent(prevPlace).setAnimation(OnsenUi.ANIMATION_PUSH));
        return;
      }
      AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace());
    }
  }

  public void goToIngredientSub1View() {
    AppClientFactory.IMPL.getPlaceController().goTo(new IngredientPlace(IngredientPlace.SUB1));
  }

  public void goToIngredientSub2View() {
    AppClientFactory.IMPL.getPlaceController().goTo(new IngredientPlace(IngredientPlace.SUB2));
  }


}
