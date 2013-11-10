package it.mate.phgcommons.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;

public class AbstractEndpointProxy {
  
  protected static final String GOOGLE_CLIENT_API = "https://apis.google.com/js/client.js";
  
  private String apiRoot;
  
  private String apiName;
  
  private boolean initialized = false;
  
  private Delegate<Void> initDelegate;
  
  private boolean useAuthentication = false;
  
  protected AbstractEndpointProxy(String apiRoot, String apiName, boolean useAuthentication, Delegate<Void> initDelegate) {
    super();
    this.apiRoot = apiRoot;
    this.apiName = apiName;
    this.initDelegate = initDelegate;
    this.useAuthentication = useAuthentication;
  }

  protected void initClientApi() {
    createGlobalInitEndpointImpl(this);
    String source = GOOGLE_CLIENT_API+"?onload=_proxyInitEndpointCallback";
    ScriptElement scriptElem = Document.get().createScriptElement();
    scriptElem.setSrc(source);
    scriptElem.setType("text/javascript");
    Element head = Document.get().getElementsByTagName("head").getItem(0);
    head.appendChild(scriptElem);
  }
  
  private native void createGlobalInitEndpointImpl(AbstractEndpointProxy proxy) /*-{
    $wnd._proxyInitEndpointCallback = $entry(function() {
      proxy.@it.mate.phgcommons.client.api.AbstractEndpointProxy::initEndpointApi()();
    });
  }-*/;

  protected void initEndpointApi() {
    GwtUtils.log("calling initEndpointImpl method with " + apiRoot);
    initEndpointApiImpl(apiRoot, apiName, useAuthentication, new Callback() {
      public void execute(JavaScriptObject jso) {
        initialized = true;
        initDelegate.execute(null);
//      signIn(true);
      }
    });
  }
  
  private native void initEndpointApiImpl(String apiRoot, String apiName, boolean useAuthentication, Callback pCallback) /*-{
    var apisToLoad;
    var callback = function() {
      if (--apisToLoad == 0) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)();
      }
    }
    if (useAuthentication) {
      apisToLoad = 2; // must match number of calls to gapi.client.load()
    } else {
      apisToLoad = 1; // must match number of calls to gapi.client.load()
    }
    $wnd.gapi.client.load(apiName, 'v1', callback, apiRoot);
    if (useAuthentication) {
      $wnd.gapi.client.load('oauth2', 'v2', callback);
    }
  }-*/;
  
  protected static interface Callback {
    public void execute(JavaScriptObject jso);
  }
  

}
