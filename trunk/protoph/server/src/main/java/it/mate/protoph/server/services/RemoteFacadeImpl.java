package it.mate.protoph.server.services;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.protoph.shared.model.impl.AccountTx;
import it.mate.protoph.shared.service.RemoteFacade;

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
  public Date getServerTime() {
    return new Date();
  }
  
  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
    String devIp = getThreadLocalRequest().getRemoteAddr();
    return adapter.sendDevInfo(os, layout, devName, phgVersion, platform, devUuid, devVersion, devIp);
  }

  public RpcMap createAccount(RpcMap entity) {
    AccountTx tx = new AccountTx().fromRpcMap(entity);
    tx = (AccountTx)adapter.createAccount(tx);
    return tx.toRpcMap();
  }
  
  public RpcMap updateAccount(RpcMap entity) {
    AccountTx tx = new AccountTx().fromRpcMap(entity);
    tx = (AccountTx)adapter.updateAccount(tx);
    return tx.toRpcMap();
  }
  
  @Override
  public Boolean checkConnection() {
    return true;
  }

}
