package it.mate.stickmail.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.api.AbstractEndpointProxy;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.stickmail.shared.model.StickMail;

import com.google.gwt.core.client.JavaScriptObject;

public class StickMailEPProxy extends AbstractEndpointProxy {

  private static final boolean USE_AUTHENTICATION = true;

  private static final boolean LOCALTEST = false;
  
  private static final String API_ROOT = LOCALTEST ? "http://127.0.0.1:8080/_ah/api" : "https://stickmailsrv.appspot.com/_ah/api";
  
  private static final String API_NAME = "stickmailEP";
  
  private static final String API_KEY = null;
  
  private static final String CLIENT_ID_DESKTOP = "834127897640.apps.googleusercontent.com";

  private static final String CLIENT_ID_MOBILE = "834127897640-pqkmabsf4d4ichgs0cvuqq0fnhf36rjh.apps.googleusercontent.com";

  private static final String CLIENT_SECRET_MOBILE = "eb2zvLaTy1KnwNoCkYJ8Thvt";
  
  public StickMailEPProxy(Delegate<Void> initDelegate) {
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

  public void testService(final Delegate<String> delegate) {
    PhonegapUtils.log("StickMailProxy::testService");
    String payload = "REQUEST SERVICE TEST";
    testServiceImpl(API_NAME, payload, new Callback() {
      public void execute(JavaScriptObject resp) {
        PhonegapUtils.log("received endpoint response " + JSONUtils.stringify(resp));
        delegate.execute("endpoint test success");
      }
    });
  }
  
  private native void testServiceImpl(String apiName, String payload, Callback pCallback) /*-{
    @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("StickMailProxy::testServiceImpl");
    $wnd.gapi.client[apiName].buildNumber().execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;

  public void authUser(final Delegate<String> delegate) {
    PhonegapUtils.log("StickMailProxy::addUser");
    authUserImpl(API_NAME, "null", new Callback() {
      public void execute(JavaScriptObject resp) {
        PhonegapUtils.log("received endpoint response " + JSONUtils.stringify(resp));
      }
    });
  }
  
  private native void authUserImpl(String apiName, String token, Callback pCallback) /*-{
    @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("StickMailProxy::authUserImpl");
    $wnd.gapi.client[apiName].authUser({'token': token}).execute(function(resp) {
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
