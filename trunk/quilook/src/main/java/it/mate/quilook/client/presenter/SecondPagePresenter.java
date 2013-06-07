package it.mate.quilook.client.presenter;

import it.mate.quilook.client.presenter.SecondPagePresenter.MyProxy;
import it.mate.quilook.client.presenter.SecondPagePresenter.MyView;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SecondPagePresenter extends Presenter<MyView, MyProxy>{
  
  public interface MyView extends View {}
  
  @ProxyCodeSplit
  @NameToken ("second")
  public interface MyProxy extends ProxyPlace<SecondPagePresenter> {}

  @Inject
  public SecondPagePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
    super(eventBus, view, proxy);
  }
  
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }
  

}
