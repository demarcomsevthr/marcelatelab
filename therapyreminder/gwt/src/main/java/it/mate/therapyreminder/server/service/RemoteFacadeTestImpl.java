package it.mate.therapyreminder.server.service;

import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.service.RemoteFacade;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings({"serial"})
public class RemoteFacadeTestImpl extends RemoteServiceServlet implements RemoteFacade {

  private static Logger logger = Logger.getLogger(RemoteFacadeTestImpl.class);
  
  private RemoteFacade remoteFacade = null;
  
  private final boolean LOCALTEST = true;
  
  private final String REMOTE_SERVICE_RELATIVE_PATH = ".remoteFacade";
  
  private String moduleBaseUrl;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    moduleBaseUrl = LOCALTEST ? "http://127.0.0.1:8080/main/" : "https://therapyremindersrv.appspot.com/main/"; 
    remoteFacade = (RemoteFacade)SyncProxy.newProxyInstance(RemoteFacade.class, moduleBaseUrl, REMOTE_SERVICE_RELATIVE_PATH);
    logger.debug("initialized " + this);
    logger.debug("moduleBaseUrl = " + moduleBaseUrl);
  }
  
  @Override
  public Date getServerTime() {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.getServerTime();
  }
  
  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion);
  }

  @Override
  public Account createAccount(Account entity) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.createAccount(entity);
  }
  
  @Override
  public Account updateAccount(Account entity) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.updateAccount(entity);
  }

  @Override
  public Boolean checkConnection() {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.checkConnection();
  }

  @Override
  public List<Somministrazione> saveSomministrazioni(List<Somministrazione> somministrazioni, Account account, String devInfoId) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.saveSomministrazioni(somministrazioni, account, devInfoId);
  }
  
}
