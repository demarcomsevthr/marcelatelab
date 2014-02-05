package it.mate.stickmail.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.api.AbstractEndpointProxy;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.OsDetectionUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;
import it.mate.stickmail.client.factories.AppClientFactory;
import it.mate.stickmail.shared.model.RemoteUser;

import com.google.gwt.core.client.JavaScriptObject;

public class StickMailEPProxy extends AbstractEndpointProxy {

  private static final boolean USE_AUTHENTICATION = true;

  private static final boolean LOCALTEST = false;
  
  private static final String API_ROOT =
      OsDetectionUtils.isDesktop() ? 
          ( LOCALTEST ? "http://127.0.0.1:8080/_ah/api" : "https://stickmailsrv.appspot.com/_ah/api" ) :
          ( AppClientFactory.IMPL.getNativeProperty("endpointApiRoot", "https://stickmailsrv.appspot.com/_ah/api") ) ;
  
  private static final String API_NAME = "stickmailEP";
  
  private static final String CLIENT_ID_DESKTOP = "834127897640.apps.googleusercontent.com";

  private static final String CLIENT_ID_MOBILE = "834127897640-pqkmabsf4d4ichgs0cvuqq0fnhf36rjh.apps.googleusercontent.com";

  private static final String CLIENT_SECRET_MOBILE = "eb2zvLaTy1KnwNoCkYJ8Thvt";
  
  public StickMailEPProxy(Delegate<Void> initDelegate, Delegate<Boolean> authDelegate) {
    super(API_ROOT, API_NAME, USE_AUTHENTICATION, initDelegate, authDelegate);
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
  protected void onInit() {
    super.onInit();
    getBuildNumber(null);
  }

  public void getBuildNumber(final Delegate<String> delegate) {
    PhonegapUtils.log("calling getBuildNumberImpl");
    getBuildNumberImpl(API_NAME, null, new Callback() {
      public void execute(JavaScriptObject resp) {
        PhonegapUtils.log("received buildNumber response " + JSONUtils.stringify(resp));
        if (delegate != null)
          delegate.execute("endpoint test success");
      }
    });
  }
  
  private native void getBuildNumberImpl(String apiName, String payload, Callback pCallback) /*-{
    $wnd.gapi.client[apiName].getBuildNumber().execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      } else {
        var msg = "StickMailEPProxy::getBuildNumberImpl - FAILURE - resp = ";
        msg += @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)(msg);
      }
    });
  }-*/;

  public void getRemoteUser(final Delegate<RemoteUser> delegate) {
    PhonegapUtils.log("calling getRemoteUserImpl");
    getRemoteUserImpl(API_NAME, "null", new Callback() {
      public void execute(JavaScriptObject resp) {
        RemoteUserJS remoteUserJs = resp.cast();
        PhonegapUtils.log("received remoteUser " + remoteUserJs.toStringCustom());
        if (delegate != null)
          delegate.execute(remoteUserJs.asRemoteUser());
      }
    });
  }
  
  private native void getRemoteUserImpl(String apiName, String token, Callback pCallback) /*-{
    $wnd.gapi.client[apiName].getRemoteUser({'token': token}).execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      } else {
        var msg = "StickMailEPProxy::getRemoteUserImpl - FAILURE - resp = ";
        msg += @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)(msg);
      }
    });
  }-*/;

}
