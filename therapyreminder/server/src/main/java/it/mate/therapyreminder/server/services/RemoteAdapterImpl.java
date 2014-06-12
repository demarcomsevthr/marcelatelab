package it.mate.therapyreminder.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.therapyreminder.server.model.AccountDs;
import it.mate.therapyreminder.server.model.DevInfoDs;
import it.mate.therapyreminder.server.model.SomministrazioneDs;
import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Somministrazione;
import it.mate.therapyreminder.shared.model.impl.AccountTx;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Service
public class RemoteAdapterImpl implements RemoteAdapter {
  
  private static Logger logger = Logger.getLogger(RemoteFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    if (dao == null) {
      logger.error("error", new InstantiationException("dao is null!"));
    }
  }

  @Override
  public void refresh() {
    
  }
  
  
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp) {
    DevInfoDs ds = null;
    if (devUuid != null) {
      ds = dao.findSingle(DevInfoDs.class, "devUuid == devUuidParam" ,
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(String.class, "devUuidParam")
          }) , null, devUuid);
    }
    if (ds == null) {
      ds = new DevInfoDs();
      ds.setOs(os);
      ds.setLayout(layout);
      ds.setDevName(devName);
      ds.setPhgVersion(phgVersion);
      ds.setPlatform(platform);
      ds.setDevUuid(devUuid);
      ds.setDevVersion(devVersion);
      ds.setCreated(new Date());
      ds.setDevIp(devIp);
      LoggingUtils.debug(getClass(), "creating " + ds);
      ds = dao.create(ds);
    }
    if (ds != null) {
      return ds.getId();
    } else {
      return null;
    }
  }
  
  public Account createAccount(Account entity) {
    AccountDs ds = null;
    // controllo se per errore arrivano due create dallo stesso device
    if (entity.getDevInfoId() != null) {
      Key devInfoKey = KeyFactory.stringToKey(entity.getDevInfoId());
      ds = dao.findSingle(AccountDs.class, "devInfoId == devInfoIdParam", 
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(Key.class, "devInfoIdParam")
          }), 
        null, devInfoKey );
      if (ds != null) {
        ds.setName(entity.getName());
        ds.setEmail(entity.getEmail());
        ds = dao.update(ds);
      }
    }
    if (ds == null) {
      ds = CloneUtils.clone(entity, AccountDs.class);
      ds = dao.create(ds);
    }
    return CloneUtils.clone (ds, AccountTx.class);
  }
  
  public Account updateAccount(Account entity) {
    AccountDs ds = CloneUtils.clone(entity, AccountDs.class);
    ds = dao.update(ds);
    return CloneUtils.clone (ds, AccountTx.class);
  }
  
  public Somministrazione saveSomministrazione(Somministrazione txSomministrazione, Account txAccount, String devInfoId) {
    SomministrazioneDs dsSomministrazione = CloneUtils.clone(txSomministrazione, SomministrazioneDs.class);
    AccountDs dsAccount = CloneUtils.clone(txAccount, AccountDs.class);
    dsSomministrazione.setAccountId(dsAccount.getKey());
    dsSomministrazione.setDevInfoId(devInfoId);
    logger.debug("received devInfoId = " + devInfoId + " " + dsSomministrazione + " " + dsAccount);
    
    SomministrazioneDs exSomministrazione = null;
    if (dsSomministrazione.getKey() != null) {
      exSomministrazione = dao.findById(SomministrazioneDs.class, dsSomministrazione.getKey());
    }
    if (exSomministrazione != null) {
      exSomministrazione.setLocalId(dsSomministrazione.getLocalId());
      exSomministrazione.setData(dsSomministrazione.getData());
      exSomministrazione.setQuantita(dsSomministrazione.getQuantita());
      exSomministrazione.setOrario(dsSomministrazione.getOrario());
      exSomministrazione.setStato(dsSomministrazione.getStato());
      exSomministrazione.setDevInfoId(dsSomministrazione.getDevInfoId());
      exSomministrazione.setAccountId(dsSomministrazione.getAccountId());
      exSomministrazione.setPrescrizione(txSomministrazione.getPrescrizione());
      dsSomministrazione = dao.update(exSomministrazione);
      LoggingUtils.debug(getClass(), "updated " + dsSomministrazione);
    } else {
      dsSomministrazione = dao.create(dsSomministrazione);
      LoggingUtils.debug(getClass(), "created " + dsSomministrazione);
    }
    
    txSomministrazione = CloneUtils.clone(dsSomministrazione, SomministrazioneTx.class);
    return txSomministrazione;
  }
  
}
