package it.mate.quilook.client.factories;

import it.mate.econyx.client.view.PortalPageExplorerView;
import it.mate.econyx.client.view.PortalPageView;
import it.mate.econyx.client.view.PortalUserView;
import it.mate.econyx.client.view.site.PortalPageExplorerViewImpl;
import it.mate.econyx.client.view.site.PortalPageViewImpl;
import it.mate.econyx.client.view.site.PortalUserViewImpl;

import com.google.gwt.inject.client.AbstractGinModule;

public class SiteGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    
    bind(PortalPageView.class).to(PortalPageViewImpl.class);
    bind(PortalPageExplorerView.class).to(PortalPageExplorerViewImpl.class);
    
    bind(PortalUserView.class).to(PortalUserViewImpl.class);
    
  }

}
