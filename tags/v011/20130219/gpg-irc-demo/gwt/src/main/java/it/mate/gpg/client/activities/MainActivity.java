package it.mate.gpg.client.activities;

import it.mate.gpg.client.factories.AppClientFactory;
import it.mate.gpg.client.model.CKD;
import it.mate.gpg.client.places.MainPlace;
import it.mate.gpg.client.view.CKDInputView;
import it.mate.gpg.client.view.CKDOutputView;
import it.mate.gpg.client.view.HomeView;
import it.mate.gwtcommons.client.factories.BaseClientFactory;
import it.mate.gwtcommons.client.mvp.BaseView;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

@SuppressWarnings("rawtypes")
public class MainActivity extends MGWTAbstractActivity implements 
  HomeView.Presenter, CKDInputView.Presenter, CKDOutputView.Presenter {
  
  private MainPlace place;
  
  private final static String CKD_ATTR = "ckd";
  
  public MainActivity(BaseClientFactory clientFactory, MainPlace place) {
    this.place = place;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(MainPlace.HOME)) {
      HomeView view = AppClientFactory.IMPL.getGinjector().getHomeView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
    }
    if (place.getToken().equals(MainPlace.CKD_INPUT)) {
      CKDInputView view = AppClientFactory.IMPL.getGinjector().getCKDInputView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      CKD ckd = (CKD)GwtUtils.getClientAttribute(CKD_ATTR);
      if (ckd != null) {
        view.setModel(ckd, "ckd");
      }
    }
    if (place.getToken().equals(MainPlace.CKD_OUTPUT)) {
      CKDOutputView view = AppClientFactory.IMPL.getGinjector().getCKDOutputView();
      view.setPresenter(this);
      panel.setWidget(view.asWidget());
      view.setModel(GwtUtils.getClientAttribute(CKD_ATTR), "ckd");
    }
  }

  @Override
  public BaseView getView() {
    return null;
  }

  @Override
  public void goToPrevious() {
    
  }
  
  @Override
  public void goToHome() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.HOME));
  }

  @Override
  public void goToCkdInput() {
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_INPUT));
  }

  @Override
  public void goToCkdOutput(CKD ckd) {
    GwtUtils.setClientAttribute(CKD_ATTR, ckd);
    AppClientFactory.IMPL.getPlaceController().goTo(new MainPlace(MainPlace.CKD_OUTPUT));
  }

}
