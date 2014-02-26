package it.mate.postscriptum.server.service;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.service.StickFacade;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings({"serial"})
public class StickFacadeTestImpl extends RemoteServiceServlet implements StickFacade {

  private static Logger logger = Logger.getLogger(StickFacadeTestImpl.class);
  
  private StickFacade remoteFacade = null;
  
  private final boolean LOCALTEST = false;
  
  private final String REMOTE_SERVICE_RELATIVE_PATH = ".stickFacade";
  
  private String moduleBaseUrl;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    moduleBaseUrl = LOCALTEST ? "http://127.0.0.1:8080/main/" : "https://postscriptumsrv.appspot.com/main/"; 
    remoteFacade = (StickFacade)SyncProxy.newProxyInstance(StickFacade.class, moduleBaseUrl, REMOTE_SERVICE_RELATIVE_PATH);
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
  public StickMail create(StickMail stickMail) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.create(stickMail);
  }
  
  @Override
  public List<StickMail> findMailsByUser(RemoteUser user) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.findMailsByUser(user);
  }

  @Override
  public List<StickMail> findScheduledMailsByUser(RemoteUser user) {
    logger.debug("calling " + moduleBaseUrl);
    return remoteFacade.findScheduledMailsByUser(user);
  }

  @Override
  public void delete(List<StickMail> mails) {
    logger.debug("calling " + moduleBaseUrl);
    remoteFacade.delete(mails);
  }
  
}
