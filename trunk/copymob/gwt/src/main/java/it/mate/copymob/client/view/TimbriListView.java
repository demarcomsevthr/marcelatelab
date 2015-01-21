package it.mate.copymob.client.view;

import it.mate.copymob.client.view.TimbriListView.Presenter;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.onscommons.client.event.TapEvent;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsCarousel;
import it.mate.onscommons.client.ui.OnsCarouselItem;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class TimbriListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {
    public void goToTimbroDetailView(Timbro timbro);
    public void orderTimbro(final Timbro timbro);
  }

  public interface ViewUiBinder extends UiBinder<Widget, TimbriListView> { }

  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiField Panel wrapperPanel;
  @UiField OnsCarousel carousel;
  
  public TimbriListView() {
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
  public void onDetachView() {
    super.onDetachView();
    if (OnsenUi.isSlidingMenuLayoutPattern()) {
      OnsenUi.getSlidingMenu().setSwipeable(true);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    List<Timbro> timbri = (List<Timbro>)model;
    populateCarousel(timbri);
  }
  
  private void populateCarousel(List<Timbro> timbri) {
    carousel.getElement().getStyle().setOpacity(0);
    for (Timbro timbro : timbri) {
      String html = "<div id='timbro"+timbro.getId()+"' class='app-carousel-item-inner'><p class='app-carousel-item-name'>Timbro " + timbro.getId() +"</p>";
      html += "<img src='"+ timbro.getImageData() +"'/>";
      html += "</div>";
      OnsCarouselItem item = new OnsCarouselItem(html);
      item.setModel(timbro);
      carousel.add(item);
    }
    showImpl(carousel.getElement());
  }
  
  protected static native void showImpl(Element elem) /*-{
    $wnd.animit(elem)
      .queue({
        css: {
          opacity: 0
        },
        duration: 0
      })
      .queue({
        css: {
          opacity: 1
        },
        duration: 5
      })
      .play();
  }-*/;

  @UiHandler("btnBuy")
  public void onBtnBuy(TapEvent event) {
    carousel.getActiveItem(new Delegate<OnsCarouselItem>() {
      public void execute(OnsCarouselItem item) {
        Timbro timbro = (Timbro)item.getModel();
//      getPresenter().goToTimbroDetailView(timbro);
        getPresenter().orderTimbro(timbro);
      }
    });
  }
  
}
