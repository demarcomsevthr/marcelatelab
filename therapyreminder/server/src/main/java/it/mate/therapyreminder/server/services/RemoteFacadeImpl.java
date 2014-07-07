package it.mate.therapyreminder.server.services;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.therapyreminder.shared.model.impl.AccountTx;
import it.mate.therapyreminder.shared.model.impl.ContattoTx;
import it.mate.therapyreminder.shared.model.impl.SomministrazioneTx;
import it.mate.therapyreminder.shared.service.RemoteFacade;

import java.util.Date;
import java.util.List;

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
  public Date getServerTime() {
    return new Date();
  }
  
  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
    String devIp = getThreadLocalRequest().getRemoteAddr();
    return adapter.sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, devIp);
  }

  //TODO
  public RpcMap createAccount(RpcMap entity) {
    AccountTx tx = new AccountTx().fromRpcMap(entity);
    tx = (AccountTx)adapter.createAccount(tx);
    return tx.toRpcMap();
  }
  
  /*
  @Override
  public Account createAccount(Account entity) {
    return adapter.createAccount(entity);
  }
  */
  
  //TODO
  public RpcMap updateAccount(RpcMap entity) {
    AccountTx tx = new AccountTx().fromRpcMap(entity);
    tx = (AccountTx)adapter.updateAccount(tx);
    return tx.toRpcMap();
  }
  
  /*
  @Override
  public Account updateAccount(Account entity) {
    return adapter.updateAccount(entity);
  }
  */
  
  @Override
  public Boolean checkConnection() {
    return true;
  }

  private RpcMap saveSomministrazione(RpcMap somministrazione, RpcMap account, String devInfoId) {
    SomministrazioneTx txSomministrazione = new SomministrazioneTx().fromRpcMap(somministrazione);
    AccountTx txAccount = new AccountTx().fromRpcMap(account);
    txSomministrazione = (SomministrazioneTx)adapter.saveSomministrazione(txSomministrazione, txAccount, devInfoId);
    return txSomministrazione.toRpcMap();
  }
  
  //TODO
  public List<RpcMap> saveSomministrazioni(List<RpcMap> somministrazioni, RpcMap account, String devInfoId) {
    if (somministrazioni == null)
      return null;
    for (int it = 0; it < somministrazioni.size(); it++) {
      RpcMap somministrazione = somministrazioni.get(it);
      somministrazione = saveSomministrazione(somministrazione, account, devInfoId);
      somministrazioni.set(it, somministrazione);
    }
    return somministrazioni;
  }
  
  /*
  @Override
  public List<Somministrazione> saveSomministrazioni(List<Somministrazione> somministrazioni, Account account, String devInfoId) {
    if (somministrazioni == null)
      return null;
    for (int it = 0; it < somministrazioni.size(); it++) {
      Somministrazione somministrazione = somministrazioni.get(it);
      somministrazione = adapter.saveSomministrazione(somministrazione, account, devInfoId);
      somministrazioni.set(it, somministrazione);
    }
    return somministrazioni;
  }
  */
  
  //TODO
  public void updateDatiContatto(RpcMap tutor, RpcMap account) {
    ContattoTx txTutor = new ContattoTx().fromRpcMap(tutor);
    AccountTx txAccount = new AccountTx().fromRpcMap(account);
    adapter.updateDatiContatto(txTutor, txAccount);
  }
  
  /*
  @Override
  public void updateDatiContatto(Contatto tutor, Account account) {
    adapter.updateDatiContatto(tutor, account);
  }
  */
  
  //TODO
  public void deleteSomministrazioni(List<RpcMap> somministrazioni) {
    if (somministrazioni != null) {
      for (RpcMap somministrazione : somministrazioni) {
        SomministrazioneTx txSomministrazione = new SomministrazioneTx().fromRpcMap(somministrazione);
        adapter.deleteSomministrazione(txSomministrazione);
      }
    }
  }
  
  /*
  @Override
  public void deleteSomministrazioni(List<Somministrazione> somministrazioni) {
    if (somministrazioni != null) {
      for (Somministrazione somministrazione : somministrazioni) {
        adapter.deleteSomministrazione(somministrazione);
      }
    }
  }
  */
  
}
