package it.mate.therapyreminder.client.factories;

import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.therapyreminder.client.activities.mapper.MainActivityMapper;
import it.mate.therapyreminder.client.logic.AppSqlDao;
import it.mate.therapyreminder.shared.service.RemoteFacade;
import it.mate.therapyreminder.shared.service.RemoteFacadeAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.inject.Provides;
import com.googlecode.gwtphonegap.client.util.PhonegapUtil;

public class AppGinModule extends AbstractGinModule {

  private final static String NAT_PROP_FACADE_MODULE_URL = "remoteFacadeModuleUrl";
  private final static String DEFAULT_FACADE_MODULE_URL = "https://therapyremindersrv.appspot.com/main/";
  private final static String NAT_PROP_FACADE_RELATIVE_PATH = "remoteFacadeRelativePath";
  private final static String DEFAULT_FACADE_RELATIVE_PATH = ".remoteFacade";
  
  private static AppSqlDao appSqlDao;
  
  @Override
  protected void configure() {
    
  }
  
  @Provides
  MainActivityMapper getMainActivityMapper() {
    return new MainActivityMapper(AppClientFactory.IMPL);
  }
  
  @Provides
  public RemoteFacadeAsync getRemoteFacade() {
    PhonegapUtils.log("preparing remote facade...");
    RemoteFacadeAsync facade = GWT.create(RemoteFacade.class);
    ServiceDefTarget service = (ServiceDefTarget)facade;
    if (OsDetectionUtils.isDesktop()) {
      // do nothing
    } else {
      PhonegapUtil.prepareService(service, 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_MODULE_URL, DEFAULT_FACADE_MODULE_URL), 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_RELATIVE_PATH, DEFAULT_FACADE_RELATIVE_PATH));
    }
    PhonegapUtils.log("remote facade set on " + service.getServiceEntryPoint());
    return facade;
  }
  
  @Provides
  public AppSqlDao getAppSqlDao() {
    if (appSqlDao == null) {
      appSqlDao = new AppSqlDao();
    }
    return appSqlDao;
  }
  
}
