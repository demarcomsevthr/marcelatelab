package com.googlecode.gwtphonegap.client.log.shared;

import com.googlecode.gwtphonegap.client.rpc.impl.PhoneGapRemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;

public class PhoneGapLogService_Proxy extends PhoneGapRemoteServiceProxy implements com.googlecode.gwtphonegap.client.log.shared.PhoneGapLogServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "com.googlecode.gwtphonegap.client.log.shared.PhoneGapLogService";
  private static final String SERIALIZATION_POLICY ="BEE24460B7011DC681008484EA7C9AEA";
  private static final com.googlecode.gwtphonegap.client.log.shared.PhoneGapLogService_TypeSerializer SERIALIZER = new com.googlecode.gwtphonegap.client.log.shared.PhoneGapLogService_TypeSerializer();
  
  public PhoneGapLogService_Proxy() {
    super(GWT.getModuleBaseURL(),
      null, 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void logOnServer(java.lang.String clientId, java.util.List record, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    com.google.gwt.user.client.rpc.impl.RemoteServiceProxy.ServiceHelper helper = new com.google.gwt.user.client.rpc.impl.RemoteServiceProxy.ServiceHelper("PhoneGapLogService_Proxy", "logOnServer");
    try {
      SerializationStreamWriter streamWriter = helper.start(REMOTE_SERVICE_INTERFACE_NAME, 2);
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeString("java.util.List");
      streamWriter.writeString(clientId);
      streamWriter.writeObject(record);
      helper.finish(callback, ResponseReader.STRING);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  @Override
  public SerializationStreamWriter createStreamWriter() {
    ClientSerializationStreamWriter toReturn =
      (ClientSerializationStreamWriter) super.createStreamWriter();
    if (getRpcToken() != null) {
      toReturn.addFlags(ClientSerializationStreamWriter.FLAG_RPC_TOKEN_INCLUDED);
    }
    return toReturn;
  }
  @Override
  protected void checkRpcTokenType(RpcToken token) {
    if (!(token instanceof com.google.gwt.user.client.rpc.XsrfToken)) {
      throw new RpcTokenException("Invalid RpcToken type: expected 'com.google.gwt.user.client.rpc.XsrfToken' but got '" + token.getClass() + "'");
    }
  }
}
