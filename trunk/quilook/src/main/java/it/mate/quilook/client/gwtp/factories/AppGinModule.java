package it.mate.quilook.client.gwtp.factories;

import it.mate.econyx.shared.services.GeneralService;
import it.mate.econyx.shared.services.GeneralServiceAsync;
import it.mate.econyx.shared.services.ImageService;
import it.mate.econyx.shared.services.ImageServiceAsync;
import it.mate.econyx.shared.services.PortalPageService;
import it.mate.econyx.shared.services.PortalPageServiceAsync;
import it.mate.econyx.shared.services.PortalUserService;
import it.mate.econyx.shared.services.PortalUserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

public class AppGinModule extends AbstractGinModule {

  @Override
  protected void configure() {

  }

  private static GeneralServiceAsync generalService = null;
  
  private static PortalPageServiceAsync portalPageService = null;
  
  private static PortalUserServiceAsync portalUserService = null;
  
  private static ImageServiceAsync imageService = null;
  
  
  @Provides
  public GeneralServiceAsync getGeneralService() {
    if (generalService == null) {
      generalService = GWT.create(GeneralService.class);
    }
    return generalService;
  }

  @Provides
  public PortalPageServiceAsync getPortalPageService() {
    if (portalPageService == null) {
      portalPageService = GWT.create(PortalPageService.class);
    }
    return portalPageService;
  }

  @Provides
  public PortalUserServiceAsync getPortalUserService() {
    if (portalUserService == null) {
      portalUserService = GWT.create(PortalUserService.class);
    }
    return portalUserService;
  }
  
  @Provides
  public ImageServiceAsync getImageService() {
    if (imageService == null) {
      imageService = GWT.create(ImageService.class);
    }
    return imageService;
  }
  
}
