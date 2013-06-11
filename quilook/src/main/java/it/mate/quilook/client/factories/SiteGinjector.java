package it.mate.quilook.client.factories;

import it.mate.econyx.client.factories.AppGinjector;
import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.portlets.shared.services.PortalServiceAsync;
import it.mate.quilook.shared.services.QuServiceAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, SiteGinModule.class, AppGinModule.class})
public interface SiteGinjector extends AppGinjector {
  
  PortalServiceAsync getPortalService();
  
  QuServiceAsync getQuService();
  
}
