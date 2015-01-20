package it.mate.postscriptum.server.service;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.service.AdapterException;
import it.mate.postscriptum.shared.service.StickFacade2;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings({"serial"})
public class StickFacadeTestImpl extends RemoteServiceServlet implements StickFacade2 {

  private static Logger logger = Logger.getLogger(StickFacadeTestImpl.class);
  
  private StickFacade2 remoteFacade = null;
  
  private final boolean LOCALTEST = true;
  
  private final String REMOTE_SERVICE_RELATIVE_PATH = ".stickFacade2";
  
  private String moduleBaseUrl;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    moduleBaseUrl = LOCALTEST ? "http://127.0.0.1:8080/main/" : "https://postscriptumsrv.appspot.com/main/"; 
    remoteFacade = (StickFacade2)SyncProxy.newProxyInstance(StickFacade2.class, moduleBaseUrl, REMOTE_SERVICE_RELATIVE_PATH);
    logger.debug("initialized " + this);
    logger.debug("moduleBaseUrl = " + moduleBaseUrl);
  }
  
  @Override
  public RemoteUser getRemoteUser() {
    return null;
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
  public void sendSmsTest(String to, String msg) {
    logger.debug("calling " + moduleBaseUrl);
    remoteFacade.sendSmsTest(to, msg);
  }

  @Override
  public RpcMap createSMS(RpcMap entity) throws AdapterException {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.createSMS(entity);
  }

  @Override
  public void checkScheduledSMSs() {
    logger.debug("calling " + moduleBaseUrl);
    remoteFacade.checkScheduledSMSs();
  }

  @Override
  public List<RpcMap> findScheduledSMSsByUser(RemoteUser user) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.findScheduledSMSsByUser(user);
  }

  @Override
  public void deleteSMS(List<RpcMap> entities) {
    logger.debug("calling " + moduleBaseUrl);
    remoteFacade.deleteSMS(entities);
  }

  @Override
  public RpcMap create(RpcMap stickMail) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.create(stickMail);
  }

  @Override
  public List<RpcMap> findMailsByUser(RemoteUser user) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.findMailsByUser(user);
  }

  @Override
  public List<RpcMap> findScheduledMailsByUser(RemoteUser user) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.findScheduledMailsByUser(user);
  }

  @Override
  public void delete(List<RpcMap> mails) {
    logger.debug("calling " + moduleBaseUrl);
    remoteFacade.delete(mails);
  }

}
