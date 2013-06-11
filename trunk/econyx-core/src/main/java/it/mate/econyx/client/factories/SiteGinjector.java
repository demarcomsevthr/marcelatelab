package it.mate.econyx.client.factories;

import it.mate.gwtcommons.client.factories.CommonGinModule;

import com.google.gwt.inject.client.GinModules;

@GinModules ({CommonGinModule.class, SiteGinModule.class, AppGinModule.class})
public interface SiteGinjector extends AppGinjector {

  /* spostato in CoreGinjector
  PortalServiceAsync getPortalService();
  */ 
  
}
