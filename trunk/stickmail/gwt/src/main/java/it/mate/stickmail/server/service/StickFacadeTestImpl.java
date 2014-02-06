package it.mate.stickmail.server.service;

import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;
import it.mate.stickmail.shared.service.StickFacade;

import java.util.Date;

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
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    String moduleBaseUrl = LOCALTEST ? "http://127.0.0.1:8080/main/" : "https://stickmailsrv.appspot.com/main/"; 
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
    return remoteFacade.getServerTime();
  }

  @Override
  public StickMail create(StickMail stickMail) {
    return remoteFacade.create(stickMail);
  }
  
}
