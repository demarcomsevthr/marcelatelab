package it.mate.postscriptum.client.factories;

import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.postscriptum.client.activities.mapper.MainActivityMapper;
import it.mate.postscriptum.shared.service.StickFacade;
import it.mate.postscriptum.shared.service.StickFacadeAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.inject.Provides;
import com.googlecode.gwtphonegap.client.util.PhonegapUtil;

public class AppGinModule extends AbstractGinModule {

  private final static String NAT_PROP_FACADE_MODULE_URL = "stickFacadeModuleUrl";
  private final static String DEFAULT_FACADE_MODULE_URL = "https://postscriptumsrv.appspot.com/main/";
  private final static String NAT_PROP_FACADE_RELATIVE_PATH = "stickFacadeRelativePath";
  private final static String DEFAULT_FACADE_RELATIVE_PATH = ".stickFacade";
  
  @Override
  protected void configure() {
    
  }
  
  @Provides
  MainActivityMapper getMainActivityMapper() {
    return new MainActivityMapper(AppClientFactory.IMPL);
  }
  
  @Provides
  public StickFacadeAsync getStickFacade() {
    PhonegapUtils.log("preparing stick facade...");
    StickFacadeAsync facade = GWT.create(StickFacade.class);
    ServiceDefTarget service = (ServiceDefTarget)facade;
    if (OsDetectionUtils.isDesktop()) {
      // do nothing
    } else {
      PhonegapUtil.prepareService(service, 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_MODULE_URL, DEFAULT_FACADE_MODULE_URL), 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_RELATIVE_PATH, DEFAULT_FACADE_RELATIVE_PATH));
    }
    PhonegapUtils.log("set on " + service.getServiceEntryPoint());
    return facade;
  }
  
}
