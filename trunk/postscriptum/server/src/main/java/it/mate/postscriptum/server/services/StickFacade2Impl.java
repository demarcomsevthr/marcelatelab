package it.mate.postscriptum.server.services;

import it.mate.gwtcommons.shared.rpc.RpcMap;
import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickMail2;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;
import it.mate.postscriptum.shared.model.impl.StickMailTx2;
import it.mate.postscriptum.shared.model.impl.StickSmsTx2;
import it.mate.postscriptum.shared.service.AdapterException;
import it.mate.postscriptum.shared.service.StickFacade2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class StickFacade2Impl extends RemoteServiceServlet implements StickFacade2 {

  private static Logger logger = Logger.getLogger(StickFacade2Impl.class);
  
  private StickAdapter adapter = null;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    AdapterUtil.initContext(config.getServletContext());
    adapter = AdapterUtil.getStickAdapter();
    logger.debug("initialized " + this);
  }
  
  @Override
  protected void onAfterRequestDeserialized(RPCRequest rpcRequest) {
    logger.debug("calling method " + rpcRequest.getMethod().getName());
    super.onAfterRequestDeserialized(rpcRequest);
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
  public RpcMap create(RpcMap rpc) {
    StickMail2 entity = StickMailTx2.fromRpcMap2(rpc);
    entity.setState(StickMail.STATE_SCHEDULED);
    entity = adapter.createV2(entity);
    return StickMailTx2.toRpcMap(entity);
  }

  @Override
  public List<RpcMap> findMailsByUser(RemoteUser user) {
    List<RpcMap> results = new ArrayList<RpcMap>();
    List<StickMail2> mails = adapter.findMailsByUserV2(user);
    if (mails != null) {
      for (StickMail2 mail : mails) {
        results.add(StickMailTx2.toRpcMap(mail));
      }
    }
    return results;
  }

  @Override
  public List<RpcMap> findScheduledMailsByUser(RemoteUser user) {
    List<RpcMap> results = new ArrayList<RpcMap>();
    List<StickMail2> mails = adapter.findScheduledMailsByUserV2(user);
    if (mails != null) {
      for (StickMail2 mail : mails) {
        results.add(StickMailTx2.toRpcMap(mail));
      }
    }
    return results;
  }

  @Override
  public void delete(List<RpcMap> rpcs) {
    List<StickMail> mails = new ArrayList<StickMail>();
    for (RpcMap rpc : rpcs) {
      mails.add(StickMailTx2.fromRpcMap2(rpc));
    }
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
  public RpcMap createSMS(RpcMap rpc) throws AdapterException {
    StickSms2 sms2 = StickSmsTx2.fromRpcMap2(rpc);
    sms2.setState(StickSms.STATE_SCHEDULED);
    sms2 = adapter.createSMSV2(sms2);
    return StickSmsTx2.toRpcMap(sms2);
  }

  @Override
  public void checkScheduledSMSs() {
    adapter.checkScheduledSMSs();
  }

  @Override
  public List<RpcMap> findScheduledSMSsByUser(RemoteUser user) {
    List<RpcMap> results = new ArrayList<RpcMap>();
    List<StickSms2> smss = adapter.findScheduledSMSsByUserV2(user);
    if (smss != null) {
      for (StickSms2 sms : smss) {
        results.add(StickSmsTx2.toRpcMap(sms));
      }
    }
    return results;
  }

  @Override
  public void deleteSMS(List<RpcMap> rpcs) {
    List<StickSms> smss = new ArrayList<StickSms>();
    for (RpcMap rpc : rpcs) {
      smss.add(StickSmsTx2.fromRpcMap2(rpc));
    }
    adapter.deleteSMS(smss);
  }

}
