package it.mate.quilook.client.gwtp.factories;

import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.portlets.shared.services.PortalServiceAsync;
import it.mate.quilook.client.gwtp.presenter.MainPagePresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules ({SiteGinModule.class})
public interface SiteGinjector extends Ginjector {
  
  PortalServiceAsync getPortalService();
  
  GeneralServiceAsync getGeneralService();
  
  PlaceManager getPlaceManager();
  
  EventBus getEventBus();

  AsyncProvider<MainPagePresenter> getMainPagePresenter();
  
}
