package it.mate.postscriptum.server.services;

import it.mate.commons.server.utils.LoggingUtils;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.impl.TestTx;
import it.mate.postscriptum.shared.service.AdapterException;
import it.mate.postscriptum.shared.service.StickFacade;

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
    return new Date();
  }

  @Override
  public StickMail create(StickMail stickMail) {
    return createV101(stickMail, null);
  }
  
  @Override
  public StickMail createV101(StickMail stickMail, String devInfoId) {
    stickMail.setState(StickMail.STATE_SCHEDULED);
    return adapter.create(stickMail, devInfoId);
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

  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
    return adapter.sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion);
  }
  
  @Override
  public void sendSmsTest(String to, String msg) {
    adapter.sendSmsTest(to, msg);
  }

  @Override
  public StickSms createSMS(StickSms stickSMS) throws AdapterException {
    stickSMS.setState(StickSms.STATE_SCHEDULED);
    return adapter.createSMS(stickSMS);
  }

  @Override
  public void checkScheduledSMSs() {
    adapter.checkScheduledSMSs();
  }

  @Override
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user) {
    return adapter.findScheduledSMSsByUser(user);
  }

  @Override
  public void deleteSMS(List<StickSms> entities) {
    adapter.deleteSMS(entities);
  }

  ////////////////////////////////////////////////////////////////
  
  @Override
  public void doTest(TestTx test) {
    LoggingUtils.debug(getClass(), "Received " + test);
  }
  
}
