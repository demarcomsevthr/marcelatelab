package it.mate.quilook.client.factories;

import it.mate.quilook.client.presenter.MainPagePresenter;
import it.mate.quilook.client.presenter.DefaultPlaceManager;
import it.mate.quilook.client.view.MainPageView;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class SiteGinModule extends AbstractPresenterModule {

  @Override
  protected void configure() {
    install(new DefaultModule(DefaultPlaceManager.class));
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class, MainPagePresenter.MyProxy.class);
  }

}
