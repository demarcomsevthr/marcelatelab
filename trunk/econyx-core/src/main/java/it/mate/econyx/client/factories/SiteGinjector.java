package it.mate.econyx.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;
import it.mate.portlets.shared.services.PortalServiceAsync;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, SiteGinModule.class, AppGinModule.class})
public interface SiteGinjector extends AppGinjector {
  
  PortalServiceAsync getPortalService(); 
  
}
