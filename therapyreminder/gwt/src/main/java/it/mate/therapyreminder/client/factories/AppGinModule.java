package it.mate.therapyreminder.client.factories;

import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhgUtils;
import it.mate.therapyreminder.client.activities.mapper.MainActivityMapper;
import it.mate.therapyreminder.client.logic.MainDao;
import it.mate.therapyreminder.shared.service.RemoteFacade;
import it.mate.therapyreminder.shared.service.RemoteFacadeAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.inject.Provides;
import com.googlecode.gwtphonegap.client.util.PhonegapUtil;

public class AppGinModule extends AbstractGinModule {

  // [13/10/2015]
  // AGGIORNAMENTO SERVER CON I NUOVI RPC POLICY FILE >> https://therapyremindersrv2015.appspot.com/
  
  private final static String NAT_PROP_FACADE_MODULE_URL = "remoteFacadeModuleUrl";
  //private final static String DEFAULT_FACADE_MODULE_URL = "https://therapyremindersrv.appspot.com/main/";
  private final static String DEFAULT_FACADE_MODULE_URL = "https://therapyremindersrv2015.appspot.com/main/";
  
  private final static String NAT_PROP_FACADE_RELATIVE_PATH = "remoteFacadeRelativePath";
  private final static String DEFAULT_FACADE_RELATIVE_PATH = ".remoteFacade";
  
  // [04/08/2015]
  // HO DOVUTO CREARE UNA DOPPIA VERISONE SUL SERVER PER ANDROID 
  // PER POTER FARE L'AGGIORNAMENTO DI CDV
  // NOTA: NON FUNZIONA SU SSL
  //private final static String ANDROID_FACADE_MODULE_URL = "http://2.therapyremindersrv.appspot.com/main/";
  private final static String ANDROID_FACADE_MODULE_URL = DEFAULT_FACADE_MODULE_URL;
  
  private static MainDao appSqlDao;
  
  @Override
  protected void configure() {
    
  }
  
  @Provides
  MainActivityMapper getMainActivityMapper() {
    return new MainActivityMapper(AppClientFactory.IMPL);
  }
  
  @Provides
  public RemoteFacadeAsync getRemoteFacade() {
    PhgUtils.log("preparing remote facade...");
    RemoteFacadeAsync facade = GWT.create(RemoteFacade.class);
    ServiceDefTarget service = (ServiceDefTarget)facade;
    if (OsDetectionUtils.isDesktop()) {
      // do nothing
    } else if (OsDetectionUtils.isAndroid()) {
      PhonegapUtil.prepareService(service, 
          ANDROID_FACADE_MODULE_URL, 
          DEFAULT_FACADE_RELATIVE_PATH);
    } else {
      PhonegapUtil.prepareService(service, 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_MODULE_URL, DEFAULT_FACADE_MODULE_URL), 
          AppClientFactory.IMPL.getNativeProperty(NAT_PROP_FACADE_RELATIVE_PATH, DEFAULT_FACADE_RELATIVE_PATH));
    }
    PhgUtils.log("remote facade set on " + service.getServiceEntryPoint());
    return facade;
  }
  
  @Provides
  public MainDao getAppSqlDao() {
    if (appSqlDao == null) {
      appSqlDao = new MainDao();
    }
    return appSqlDao;
  }
  
}
