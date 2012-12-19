package it.mate.econyx.client.activities;

import it.mate.econyx.client.factories.AppClientFactory;
import it.mate.econyx.client.places.TestPlace;
import it.mate.econyx.client.view.TestView;
import it.mate.gwtcommons.client.mvp.BaseActivity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class TestActivity extends BaseActivity implements 
      TestView.Presenter {

  private TestPlace place;
  
  public TestActivity(TestPlace place, AppClientFactory clientFactory) {
    super(clientFactory);
    this.place = place;
  }
  
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    if (place.getToken().equals(TestPlace.VIEW)) {
      initView(AppClientFactory.IMPL.getGinjector().getTestView(), panel);
      retrieveModel();
    }
  }
  
  private void retrieveModel() {
    getView().setModel(null);
  }
  
  
}
