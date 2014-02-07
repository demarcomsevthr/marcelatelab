package it.mate.stickmail.server.services;

import it.mate.stickmail.shared.model.RemoteUser;
import it.mate.stickmail.shared.model.StickMail;
import it.mate.stickmail.shared.service.StickFacade;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class StickFacadeImpl extends RemoteServiceServlet implements StickFacade {

  private static Logger logger = Logger.getLogger(StickFacadeImpl.class);
  
  private StickAdapter adapter = null;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdapterUtil.initContext(config.getServletContext());
    adapter = AdapterUtil.getStickAdapter();
    logger.debug("initialized " + this);
  }
  
  @Override
  public RemoteUser getRemoteUser() {
    return null;
  }

  @Override
  public Date getServerTime() {
    /*
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    return cal.getTime();
    */
    return new Date();
  }

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
  
}
