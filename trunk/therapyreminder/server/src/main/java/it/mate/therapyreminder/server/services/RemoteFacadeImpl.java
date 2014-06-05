package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.shared.model.RemoteUser;
import it.mate.therapyreminder.shared.service.RemoteFacade;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RemoteFacadeImpl extends RemoteServiceServlet implements RemoteFacade {

  private static Logger logger = Logger.getLogger(RemoteFacadeImpl.class);
  
  private RemoteAdapter adapter = null;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdapterUtil.initContext(config.getServletContext());
    adapter = AdapterUtil.getRemoteAdapter();
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
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
    return adapter.sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion);
  }
  
  

  /*
  @Override
  public StickMail create(StickMail stickMail) {
    logger.debug("received " + stickMail);
    stickMail.setState(StickMail.STATE_SCHEDULED);
    return adapter.create(stickMail);
  }

  @Override
  public List<StickMail> findMailsByUser(RemoteUser user) {
    return adapter.findMailsByUser(user);
  }

  @Override
  public List<StickMail> findScheduledMailsByUser(RemoteUser user) {
    return adapter.findScheduledMailsByUser(user);
  }
  
  public void delete(List<StickMail> mails) {
    adapter.delete(mails);
  }
  */
  
}
