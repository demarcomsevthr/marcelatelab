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

@SuppressWarnings("serial")
public class StickFacadeTestImpl extends RemoteServiceServlet implements StickFacade {

  private static Logger logger = Logger.getLogger(StickFacadeTestImpl.class);
  
  private StickFacade proxyFacade = null;
  
  private final String MODULE_BASE_URL = "http://127.0.0.1:8080/main/";
  
  private final String REMOTE_SERVICE_RELATIVE_PATH = ".stickFacade";
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    proxyFacade = (StickFacade)SyncProxy.newProxyInstance(StickFacade.class,  MODULE_BASE_URL, REMOTE_SERVICE_RELATIVE_PATH);
    logger.debug("initialized " + this);
  }
  
  @Override
  public RemoteUser getRemoteUser() {
    return null;
  }

  @Override
  public Date getServerTime() {
    return new Date();
  }

  @Override
  public void createStickMail(StickMail stickMail) {
    logger.debug("received " + stickMail);
    proxyFacade.createStickMail(stickMail);
  }
  
}
