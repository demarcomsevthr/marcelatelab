package it.mate.protons.client.view;

import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.event.TapHandler;
import it.mate.onscommons.client.mvp.OnsActivityManagerWithNavigator;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsCarouselItem;
import it.mate.onscommons.client.ui.OnsNavigator;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.protons.client.view.IngredientListView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class IngredientListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {

  }

  public interface ViewUiBinder extends UiBinder<Widget, IngredientListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
//@UiField OnsCarousel carousel;
  @UiField OnsNavigator ingNavigator;
  
  public IngredientListView() {
    initUI();
  }

  private void initProvidedElements() {

  }

  private void initUI() {
    initProvidedElements();
    initWidget(uiBinder.createAndBindUi(this));
    if (OnsenUi.isSlidingMenuLayoutPattern()) {
      OnsenUi.getSlidingMenu().setSwipeable(false);
    }
  }
  
  @Override
  protected void onAttach() {
    super.onAttach();
    initIngredientMvc();
  }
  
  private OnsActivityManagerWithNavigator activityManager = null;
  
  private void initIngredientMvc() {
    /*
    OnsenUi.compileElement(ingNavigator.getElement());
    OnsenUi.setNavigator(ingNavigator.getController());
    IngredientActivityMapper activityMapper = new IngredientActivityMapper(AppClientFactory.IMPL);
    activityManager = new OnsActivityManagerWithNavigator(activityMapper, AppClientFactory.IMPL.getBinderyEventBus());
    IngredientPlaceHistoryMapper historyMapper = GWT.create(IngredientPlaceHistoryMapper.class);
    OnsMvpUtils.initMvpMapped(activityMapper.getHistoryName(), AppClientFactory.IMPL, activityMapper, historyMapper, new IngredientPlace());
    */
  }
  
  @Override
  public void onDetachView() {
    if (OnsenUi.isSlidingMenuLayoutPattern()) {
      OnsenUi.getSlidingMenu().setSwipeable(true);
    }
  }
  
  @Override
  public void setModel(Object model, String tag) {
    GwtUtils.deferredExecution( new Delegate<Void>() {
      public void execute(Void element) {
        populateCarousel();
      }
    });
  }
  
  private void populateCarousel() {
    for (int it = 0; it < 20; it++) {
      final int n = it+1;
      String divId = "swipeArea" + it;
      String html = "<div id=\""+divId+"\" class=\"app-carousel-item-inner\">Item " + n + "</div>";
      OnsCarouselItem item = new OnsCarouselItem(html);
      item.addTapHandler(new TapHandler() {
        public void onTap(TapEvent event) {
          PhgUtils.log("tapped " + n);
        }
      });
//    carousel.add(item);
    }
  }
  
}
