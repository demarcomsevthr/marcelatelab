package it.mate.therapyreminder.server.service;

import it.mate.therapyreminder.shared.model.RemoteUser;
import it.mate.therapyreminder.shared.service.StickFacade;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

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
    /*
    moduleBaseUrl = LOCALTEST ? "http://127.0.0.1:8080/main/" : "https://therapyremindersrv.appspot.com/main/"; 
    remoteFacade = (StickFacade)SyncProxy.newProxyInstance(StickFacade.class, moduleBaseUrl, REMOTE_SERVICE_RELATIVE_PATH);
    logger.debug("initialized " + this);
    logger.debug("moduleBaseUrl = " + moduleBaseUrl);
    */
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
  
}
