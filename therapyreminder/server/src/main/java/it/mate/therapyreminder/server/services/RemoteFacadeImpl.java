package it.mate.therapyreminder.server.services;

import it.mate.therapyreminder.shared.model.Account;
import it.mate.therapyreminder.shared.model.Contatto;
import it.mate.therapyreminder.shared.model.Somministrazione;
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

  @Override
  public Account createAccount(Account entity) {
    return adapter.createAccount(entity);
  }
  
  @Override
  public Account updateAccount(Account entity) {
    return adapter.updateAccount(entity);
  }
  
  @Override
  public Boolean checkConnection() {
    return true;
  }

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
  
  @Override
  public void updateDatiContatto(Contatto tutor, Account account) {
    adapter.updateDatiContatto(tutor, account);
  }
  
  @Override
  public void deleteSomministrazioni(List<Somministrazione> somministrazioni) {
    if (somministrazioni != null) {
      for (Somministrazione somministrazione : somministrazioni) {
        adapter.deleteSomministrazione(somministrazione);
      }
    }
  }
  
}
