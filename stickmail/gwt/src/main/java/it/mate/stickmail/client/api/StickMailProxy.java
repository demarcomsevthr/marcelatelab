package it.mate.stickmail.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.api.AbstractEndpointProxy;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.stickmail.shared.model.StickMail;

import com.google.gwt.core.client.JavaScriptObject;

public class StickMailProxy extends AbstractEndpointProxy {

  private static final boolean USE_AUTHENTICATION = false;

  private static final boolean LOCALTEST = false;
  
//private static final String API_ROOT = LOCALTEST ? "http://127.0.0.1:8080/_ah/api" : "https://stickmail.appspot.com/_ah/api";
//private static final String API_ROOT = LOCALTEST ? "http://127.0.0.1:8080/_ah/api" : "https://gendtest3.appspot.com/_ah/api";
  private static final String API_ROOT = LOCALTEST ? "http://127.0.0.1:8080/_ah/api" : "https://stickmailsrv.appspot.com/_ah/api";
  
  private static final String API_NAME = "stickmailEP";
  
//private static final String API_KEY = "AIzaSyALgTL8qDZWta1KVrzlXFcLVaGvG7_-SzI";
  private static final String API_KEY = null;
  
  private static final String CLIENT_ID_DESKTOP = "935164737394-jjb7lgqjhl31o9mmdssmkdg17mtkleki.apps.googleusercontent.com";

  private static final String CLIENT_ID_MOBILE = "935164737394.apps.googleusercontent.com";

  private static final String CLIENT_SECRET_MOBILE = "EQ28ttEbjtHHlwMEHT0kL5RA";
  
  public StickMailProxy(Delegate<Void> initDelegate) {
    super(API_ROOT, API_NAME, USE_AUTHENTICATION, initDelegate);
  }
  
  @Override
  protected String getDesktopClientId() {
    return CLIENT_ID_DESKTOP;
  }

  @Override
  protected String getMobileClientId() {
    return CLIENT_ID_MOBILE;
  }

  @Override
  protected String getMobileClientSecret() {
    return CLIENT_SECRET_MOBILE;
  }
  
  @Override
  protected String getApiKey() {
    return API_KEY;
  }
  
  @Override
  protected void onInit() {
    super.onInit();
    PhonegapUtils.log("StickMailProxy::onInit");
    testService(new Delegate<String>() {
      public void execute(String result) {
        PhonegapUtils.log("testService reply " + result);
      }
    });
  }

  public void getServerTimeMillis(final Delegate<Long> delegate) {
    
  }
  
  public void testService(final Delegate<String> delegate) {
    PhonegapUtils.log("StickMailProxy::testService");
    String payload = "REQUEST SERVICE TEST";
    testServiceImpl(payload, new Callback() {
      public void execute(JavaScriptObject resp) {
        PhonegapUtils.log("received endpoint response " + JSONUtils.stringify(resp));
        delegate.execute("endpoint test success");
      }
    });
  }
  
  private native void testServiceImpl(String payload, Callback pCallback) /*-{
    @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("StickMailProxy::testServiceImpl");
    $wnd.gapi.client.stickmailEP.buildNumber().execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;

  public void postNewMail(StickMail mail, final Delegate<Integer> delegate) {
//  String payload = AutoBeanCodex.encode(factory.getStickMail(mail)).getPayload();
    String payload = null;
    postNewMailImpl(payload, new Callback() {
      public void execute(JavaScriptObject jso) {
        delegate.execute(1);
      }
    });
  }
  
  private native void postNewMailImpl(String payload, Callback pCallback) /*-{
    $wnd.gapi.client.stickMailAPI.postNewMail({'action': action}).execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;

}
