package it.mate.quilook.client.presenter;

import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.quilook.client.presenter.MainPagePresenter.MyProxy;
import it.mate.quilook.client.presenter.MainPagePresenter.MyView;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class MainPagePresenter extends Presenter<MyView, MyProxy>{
  
  public interface MyView extends View {

  }
  
  @ProxyCodeSplit
  @NameToken ("main")
  public interface MyProxy extends ProxyPlace<MainPagePresenter> {}

  @Inject
  public MainPagePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
    super(eventBus, view, proxy);
  }
  
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
  
  public void goToSecondPlace() {
    PlaceRequest place = new PlaceRequest("second");
    GwtUtils.log("go!");
  }
  

}
