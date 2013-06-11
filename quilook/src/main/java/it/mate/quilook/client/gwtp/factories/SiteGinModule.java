package it.mate.quilook.client.gwtp.factories;

import it.mate.quilook.client.gwtp.presenter.DefaultPlaceManager;
import it.mate.quilook.client.gwtp.presenter.MainPagePresenter;
import it.mate.quilook.client.gwtp.view.MainPageView;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class SiteGinModule extends AbstractPresenterModule {

  @Override
  protected void configure() {
    install(new DefaultModule(DefaultPlaceManager.class));
    bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class, MainPageView.class, MainPagePresenter.MyProxy.class);
  }

}
