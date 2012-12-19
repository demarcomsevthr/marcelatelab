package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.MailAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.server.util.AdaptersUtil;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.services.GeneralService;
import it.mate.econyx.shared.services.PortalUserService;
import it.mate.gwtcommons.server.utils.HttpUtils;
import it.mate.gwtcommons.server.utils.LoggingUtils;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PortalUserServiceImpl extends RemoteServiceServlet implements PortalUserService {

  private static Logger logger = Logger.getLogger(PortalUserServiceImpl.class);
  
  private PortalUserAdapter adapter;
  
  private MailAdapter mailAdapter;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdaptersUtil.initContext(config.getServletContext());
    this.adapter = AdaptersUtil.getPortalUserAdapter();
    this.mailAdapter = AdaptersUtil.getMailAdapter();
    logger.debug("initialized " + this);
  }
  
  @Override
  public PortalUser update(PortalUser entity) {
    if (entity.getId() == null) {
      return adapter.create(entity);
    } else {
      return adapter.update(entity);
    }
  }

  @Override
  public PortalUser create(PortalUser entity) {
    return adapter.create(entity);
  }
  
  @Override
  public PortalUser updateUserNotActive (PortalUser user) {
    return adapter.updateUserNotActive(user);
  }

  @Override
  public PortalUser findById(String id) {
    return adapter.findById(id);
  }
  
  @Override
  public List<PortalUser> findAll() {
    return adapter.findAll();
  }

  @Override
  public PortalUser login(PortalUser portalUser, boolean keepConnection) {
    PortalUser loggedUser = adapter.login(portalUser);
    if (keepConnection) {
      String cookieText = loggedUser.getEmailAddress()+"|"+loggedUser.getPassword();
      Cookie cookie = new Cookie(GeneralService.PORTAL_USER_COOKIE, cookieText);
      cookie.setMaxAge(604800); //7 gg in secondi
      getThreadLocalResponse().addCookie(cookie);
    } else {
      Cookie cookie = new Cookie(GeneralService.PORTAL_USER_COOKIE, "");
      cookie.setMaxAge(0);
      getThreadLocalResponse().addCookie(cookie);
    }
    
    LoggingUtils.getJavaLogging(this.getClass()).info(String.format("LOGGED %s", loggedUser));
    
    return loggedUser;
  }
  
  public void logout(PortalUser portalUser) {
    Cookie cookie = new Cookie(GeneralService.PORTAL_USER_COOKIE, "");
    cookie.setMaxAge(0);
    getThreadLocalResponse().addCookie(cookie);
  }
  
  public void sendActivationMail (PortalUser user) {
    try {
      HttpServletRequest request = getThreadLocalRequest();
      String activationUrl = HttpUtils.getContextUrl(request) + "/userActivation?tid=" + user.getActivationToken();
      logger.debug("activation url = " + activationUrl);
      mailAdapter.sendActivationMail(user, activationUrl);
    } catch (MessagingException ex) {
      throw new ServiceException(ex);
    }
  }
  
  public String getGoogleLoginURL(String redirectURL) {
    UserService userService = UserServiceFactory.getUserService();
    return userService.createLoginURL(redirectURL);
  }
  
  public String getGoogleLogoutURL(String redirectURL) {
    UserService userService = UserServiceFactory.getUserService();
    return userService.createLogoutURL(redirectURL);
  }
  
}
