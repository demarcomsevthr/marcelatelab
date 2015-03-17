package it.mate.copymob.server.service;

import it.mate.copymob.shared.service.RemoteFacade;
import it.mate.copymob.shared.service.RemoteFacadeException;
import it.mate.gwtcommons.shared.rpc.RpcMap;

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
  
  private final boolean LOCALTEST = false;
  
  private final String REMOTE_SERVICE_RELATIVE_PATH = ".remoteFacade";
  
  private String moduleBaseUrl;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    moduleBaseUrl = LOCALTEST ? "http://127.0.0.1:8080/main/" : "https://copymobsrv.appspot.com/main/"; 
    remoteFacade = (RemoteFacade)SyncProxy.newProxyInstance(RemoteFacade.class, moduleBaseUrl, REMOTE_SERVICE_RELATIVE_PATH);
    logger.debug("initialized " + this);
    logger.debug("moduleBaseUrl = " + moduleBaseUrl);
    
//  logger.debug("ATTENZIONE: RemoteFacadeTest DISABLED ");
    
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
  public RpcMap createAccount(RpcMap entity) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.createAccount(entity);
  }
  
  @Override
  public RpcMap updateAccount(RpcMap entity) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.updateAccount(entity);
  }

  @Override
  public Boolean checkConnection() {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.checkConnection();
  }

  @Override
  public List<RpcMap> getTimbri() throws RemoteFacadeException {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.getTimbri();
  }

}
