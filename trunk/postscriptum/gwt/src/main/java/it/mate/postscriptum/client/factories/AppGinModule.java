package it.mate.postscriptum.client.factories;

import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.postscriptum.client.activities.mapper.MainActivityMapper;
import it.mate.postscriptum.shared.service.StickFacade2;
import it.mate.postscriptum.shared.service.StickFacade2Async;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.inject.Provides;
import com.googlecode.gwtphonegap.client.util.PhonegapUtil;

public class AppGinModule extends AbstractGinModule {

  private final static String NAT_PROP_FACADE_MODULE_URL = "stickFacadeModuleUrl";
  
  // TO DEBUG
//private final static String DEFAULT_FACADE_MODULE_URL = "http://6.postscriptumsrv.appspot.com/main/";
  private final static String DEFAULT_FACADE_MODULE_URL = "https://postscriptumsrv.appspot.com/main/";
  
  private final static String NAT_PROP_FACADE_RELATIVE_PATH = "stickFacadeRelativePath";
  private final static String DEFAULT_FACADE1_RELATIVE_PATH = ".stickFacade";
  private final static String DEFAULT_FACADE2_RELATIVE_PATH = ".stickFacade2";
  
  @Override
  protected void configure() {
    
  }
  
  @Provides
  MainActivityMapper getMainActivityMapper() {
    return new MainActivityMapper(AppClientFactory.IMPL);
  }
  
  @Provides
  public StickFacade2Async getStickFacade2() {
    PhgUtils.log("preparing stick facade2...");
    StickFacade2Async facade = GWT.create(StickFacade2.class);
    ServiceDefTarget service = (ServiceDefTarget)facade;
    if (OsDetectionUtils.isDesktop()) {
      // do nothing
    } else {
      PhonegapUtil.prepareService(service, 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_MODULE_URL, DEFAULT_FACADE_MODULE_URL), 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_RELATIVE_PATH, DEFAULT_FACADE2_RELATIVE_PATH));
    }
    PhgUtils.log("set stick facade 2 on " + service.getServiceEntryPoint());
    return facade;
  }
  
  /*
  @Provides
  public StickFacadeAsync getStickFacade() {
    PhgUtils.log("preparing stick facade...");
    StickFacadeAsync facade = GWT.create(StickFacade.class);
    ServiceDefTarget service = (ServiceDefTarget)facade;
    if (OsDetectionUtils.isDesktop()) {
      // do nothing
    } else {
      PhonegapUtil.prepareService(service, 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_MODULE_URL, DEFAULT_FACADE_MODULE_URL), 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_RELATIVE_PATH, DEFAULT_FACADE1_RELATIVE_PATH));
    }
    PhgUtils.log("set on " + service.getServiceEntryPoint());
    return facade;
  }
  */
  
}
