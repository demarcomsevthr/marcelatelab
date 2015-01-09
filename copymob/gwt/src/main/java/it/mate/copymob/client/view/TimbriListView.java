package it.mate.copymob.client.view;

import it.mate.copymob.client.view.TimbriListView.Presenter;
import it.mate.copymob.shared.model.Timbro;
import it.mate.gwtcommons.client.mvp.AbstractBaseView;
import it.mate.gwtcommons.client.mvp.BasePresenter;
import it.mate.onscommons.client.onsen.OnsenUi;
import it.mate.onscommons.client.ui.OnsCarousel;
import it.mate.onscommons.client.ui.OnsCarouselItem;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class TimbriListView extends AbstractBaseView<Presenter> {

  public interface Presenter extends BasePresenter {

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
  @SuppressWarnings("unchecked")
  public void setModel(Object model, String tag) {
    List<Timbro> timbri = (List<Timbro>)model;
    populateCarousel(timbri);
  }
  
  private void populateCarousel(List<Timbro> timbri) {
    

    for (Timbro timbro : timbri) {
      String html = "<div id=\"timbro"+timbro.getId()+"\" class=\"app-carousel-item-inner\">Timbro " + timbro.getId();
      html += "<img src='data:image/jpeg;base64,"+ timbro.getImage() +"'/>";
      html += "</div>";
      OnsCarouselItem item = new OnsCarouselItem(html);
      carousel.add(item);
      
    }

    /*
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
      carousel.add(item);
    }
    */
    
  }
  
}
