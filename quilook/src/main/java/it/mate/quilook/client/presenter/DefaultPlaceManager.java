package it.mate.quilook.client.presenter;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class DefaultPlaceManager extends PlaceManagerImpl {

  @Inject
  public DefaultPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter) {
    super(eventBus, tokenFormatter);
  }

  @Override
  public void revealDefaultPlace() {
    revealPlace(new PlaceRequest("main"), false);
  }

}
