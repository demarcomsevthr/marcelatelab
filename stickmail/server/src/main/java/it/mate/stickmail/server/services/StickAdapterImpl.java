package it.mate.stickmail.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.stickmail.server.model.StickMailDs;
import it.mate.stickmail.shared.model.StickMail;
import it.mate.stickmail.shared.model.impl.StickMailTx;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StickAdapterImpl {
  
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
  
  public StickMail create(StickMail entity) {
    StickMailDs ds = CloneUtils.clone(entity, StickMailDs.class);
    ds = dao.create(ds);
    return CloneUtils.clone (ds, StickMailTx.class);
  }

}
