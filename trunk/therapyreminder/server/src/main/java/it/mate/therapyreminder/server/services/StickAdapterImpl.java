package it.mate.therapyreminder.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.therapyreminder.server.model.StickMailDs;
import it.mate.therapyreminder.shared.model.RemoteUser;
import it.mate.therapyreminder.shared.model.StickMail;
import it.mate.therapyreminder.shared.model.impl.StickMailTx;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StickAdapterImpl implements StickAdapter {
  
  private static Logger logger = Logger.getLogger(StickFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    if (dao == null) {
      logger.error("error", new InstantiationException("dao is null!"));
    }
  }

  /** 
   * 
   * TO DO: check max number scheduled email per account
   * 
   * "In the free version you can have up to 10 scheduled emails per account"
   * 
   */
  
  @Override
  public StickMail create(StickMail entity) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    ds = dao.create(ds);
    return CloneUtils.clone (ds, StickMailTx.class);
  }

  @Override
  public void checkScheduledMails() {
    Date NOW = new Date();
    LoggingUtils.debug(getClass(), "NOW IS " + NOW);
    List<StickMail> mails = findAllMails();
    for (StickMail mail : mails) {
      if (StickMail.Utils.isScheduled(mail)) {
        LoggingUtils.debug(getClass(), "found mail scheduled on " + mail.getScheduled());
        if (mail.getScheduled().before(NOW)) {
          LoggingUtils.debug(getClass(), "SENDING MAIL " + mail);
          try {
            AdapterUtil.getMailAdapter().sendStickMail(mail);
            mail.setState(StickMail.STATE_NOTIFIED);
            update(mail);
          } catch (MessagingException ex) {
            LoggingUtils.error(getClass(), "error", ex);
          }
        }
      }
    }
  }
  
  public List<StickMail> findAllMails() {
    List<StickMailDs> mails = dao.findAll(StickMailDs.class);
    return CloneUtils.clone(mails, StickMailTx.class, StickMail.class);
  }

  public StickMail update(StickMail entity) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    ds = dao.update(ds);
    return CloneUtils.clone (ds, StickMailTx.class);
  }
  
  @Override
  public List<StickMail> findMailsByUser(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam", String.class.getName() + " userIdParam", null, user.getUserId() );
    return CloneUtils.clone(results, StickMailTx.class, StickMail.class);
  }

  @Override
  public List<StickMail> findScheduledMailsByUser(RemoteUser user) {
    List<StickMailDs> results = dao.findList(StickMailDs.class, "userId == userIdParam && state == stateParam", 
        Dao.Utils.buildParameters(new ParameterDefinition[] {
            new ParameterDefinition(String.class, "userIdParam"),
            new ParameterDefinition(String.class, "stateParam")
        }), 
        null, user.getUserId(), StickMail.STATE_SCHEDULED );
    return CloneUtils.clone(results, StickMailTx.class, StickMail.class);
  }
  
  public void delete(List<StickMail> entities) {
    if (entities == null)
      return;
    for (StickMail entity : entities) {
      StickMailDs mail = dao.findById(StickMailDs.class, entity.getId());
      dao.delete(mail);
    }
  }
  
}
