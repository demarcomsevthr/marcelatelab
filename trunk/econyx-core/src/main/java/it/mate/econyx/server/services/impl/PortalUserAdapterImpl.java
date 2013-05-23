package it.mate.econyx.server.services.impl;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.CodecUtils;
import it.mate.commons.server.utils.KeyUtils;
import it.mate.econyx.server.model.impl.PortalUserDs;
import it.mate.econyx.server.services.CustomAdapter;
import it.mate.econyx.server.services.PortalUserAdapter;
import it.mate.econyx.shared.model.PortalUser;
import it.mate.econyx.shared.model.impl.PortalUserTx;
import it.mate.gwtcommons.shared.services.ServiceException;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortalUserAdapterImpl implements PortalUserAdapter {

  private static Logger logger = Logger.getLogger(PortalUserAdapterImpl.class);
  
  @Autowired private Dao dao;
  
  @Autowired private CustomAdapter customAdapter;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
  }
  
  @Override
  public PortalUser findById(String id) {
    return CloneUtils.clone(dao.findById(PortalUserDs.class, id), PortalUserTx.class);
  }
  
  @Override
  public List<PortalUser> findAll() {
    List<PortalUserDs> users = dao.findAll(PortalUserDs.class);
    for (PortalUserDs user : users) {
      user.setBillingAccount(customAdapter.getSaldoByPortalUser(user.getId()));
    }
    return CloneUtils.clone(users, PortalUserTx.class, PortalUser.class);
  }

  public Double getSaldoByPortalUserId(String portalUserId) {
    return customAdapter.getSaldoByPortalUser(portalUserId);
  }
  
  @Override
  public PortalUser create(PortalUser user) {
    return create(user, false);
  }
  
  @Override
  public PortalUser create(PortalUser user, boolean fromMarshaller) {
    PortalUserDs ds = CloneUtils.clone (encryptUserPassword(user), PortalUserDs.class);
    // 27/11/2012 - perchè non usavo la IdGeneratorStrategy ????
//  ds.setKey(KeyFactory.createKey(ds.getClass().getSimpleName(), System.currentTimeMillis() ));
//  ds.setKey(KeyFactory.createKey(ds.getClass().getSimpleName(), KeyUtils.getRandomUUID()));
    ds = dao.create(ds);
    if (!fromMarshaller) {
      ds = doUpdateUserNotActive(ds);
    }
    return CloneUtils.clone(ds, PortalUserTx.class);
  }
  
  public PortalUser updateUserNotActive (PortalUser user) {
    PortalUserDs ds = CloneUtils.clone (user, PortalUserDs.class);
    ds = doUpdateUserNotActive(ds);
    return CloneUtils.clone(ds, PortalUserTx.class);
  }
  
  public PortalUser activateUserByToken (String activationToken) {
    PortalUserDs ds = dao.findSingle(PortalUserDs.class, String.format("activationToken == '%s'", activationToken), null, null);
    ds.setActive(true);
    ds.setActivationToken(null);
    ds = dao.update(ds);
    return CloneUtils.clone(ds, PortalUserTx.class);
  }
  
  public PortalUser activateUserById (String id) {
    PortalUserDs ds = dao.findById(PortalUserDs.class, id);
    ds.setActive(true);
    ds.setActivationToken(null);
    ds = dao.update(ds);
    return CloneUtils.clone(ds, PortalUserTx.class);
  }
  
  private PortalUserDs doUpdateUserNotActive (PortalUserDs ds) {
    ds.setActive(false);
    ds.setActivationToken(KeyUtils.getRandomUUID());
    ds = dao.update(ds);
    return ds;
  }

  @Override
  public PortalUser update(PortalUser entity) {
    return CloneUtils.clone (dao.update(CloneUtils.clone(entity, PortalUserDs.class)), PortalUserTx.class);
  }
  
  @Override
  public void delete(PortalUser entity) {
    dao.delete(CloneUtils.clone (entity, PortalUserDs.class));
  }
  
  @Override
  public PortalUser login(PortalUser portalUser) {
    PortalUserDs portalUserDs = dao.findSingle(PortalUserDs.class, String.format("emailAddress == '%s'", portalUser.getEmailAddress()), null, null);
    if (portalUserDs == null) {
      throw new ServiceException("Utente inesistente");
    }
    if (!portalUserDs.getPassword().equals(encryptUserPassword(portalUser).getPassword())) {
      throw new ServiceException("Password errata");
    }
    if (!portalUserDs.isActive()) {
      throw new ServiceException("Utente non attivo. Cerca nella posta la mail di attivazione.");
    }
    return CloneUtils.clone(portalUserDs, PortalUserTx.class);
  }
  
  @Override
  public PortalUser updatePassword(PortalUser portalUser, String passwordAttuale, String nuovaPassword, String confermaPassword) {
    PortalUserDs portalUserDs = dao.findSingle(PortalUserDs.class, String.format("emailAddress == '%s'", portalUser.getEmailAddress()), null, null);
    if (portalUserDs == null) {
      throw new ServiceException("Utente inesistente");
    }
    String encryptedPwd = null;
    try {
      encryptedPwd = CodecUtils.encryptMD5(passwordAttuale);
    } catch (Exception ex) {
      throw new ServiceException(ex);
    }
    if (!encryptedPwd.equals(portalUserDs.getPassword())) {
      throw new ServiceException("Password attuale errata");
    }
    if (!nuovaPassword.equals(confermaPassword)) {
      throw new ServiceException("La password di conferma deve essere uguale alla nuova password");
    }
    portalUserDs.setPassword(nuovaPassword);
    portalUserDs.setPasswordEncrypted(false);
    portalUserDs = (PortalUserDs)encryptUserPassword(portalUserDs);
    return update(portalUserDs);
  }
  
  @Override
  public PortalUser findByEmail(String email) {
    PortalUserDs portalUserDs = dao.findSingle(PortalUserDs.class, String.format("emailAddress == '%s'", email), null, null);
    return CloneUtils.clone(portalUserDs, PortalUserTx.class);
  }
  
  private PortalUser encryptUserPassword(PortalUser user) {
    if (user != null && !user.getPasswordEncrypted()) {
      try {
        user.setPassword(CodecUtils.encryptMD5(user.getPassword()));
        user.setPasswordEncrypted(true);
      } catch (Exception ex) {
        throw new ServiceException(ex);
      }
    }
    return user;
  }
  
}
