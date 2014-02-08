package it.mate.phgcommons.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.JSONUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.Window;

public abstract class AbstractEndpointProxy {
  
  protected static final String GOOGLE_CLIENT_API = "https://apis.google.com/js/client.js";
  
  private static final String AUTH_SCOPES = "https://www.googleapis.com/auth/userinfo.email";
//private static final String AUTH_SCOPES = "https://www.googleapis.com/auth/userinfo.profile";
  
  private String apiRoot;
  
  private String apiName;
  
  private boolean initialized = false;
  
  private boolean signedIn = false;
  
  private Delegate<Void> initDelegate;
  
  private Delegate<Boolean> authDelegate;
  
  private boolean useAuthentication = false;
  
  protected AbstractEndpointProxy(String apiRoot, String apiName, boolean useAuthentication, Delegate<Void> initDelegate) {
    this(apiRoot, apiName, useAuthentication, initDelegate, null);
  }
  
  protected AbstractEndpointProxy(String apiRoot, String apiName, boolean useAuthentication, Delegate<Void> initDelegate, Delegate<Boolean> authDelegate) {
    super();
    this.apiRoot = apiRoot;
    this.apiName = apiName;
    this.initDelegate = initDelegate;
    this.useAuthentication = useAuthentication;
    this.authDelegate = authDelegate;
    PhonegapUtils.log("initializing endpoint proxy " + apiName + "...");
    JSONUtils.ensureStringify();
    initClientApi();
  }
  
  protected abstract String getDesktopClientId();
  
  protected abstract String getMobileClientId();
  
  protected abstract String getMobileClientSecret();
  
  protected String getApiKey() {
    return null;
  }
  
  public boolean isInitialized() {
    return initialized;
  }

  public boolean isSignedIn() {
    return signedIn;
  }
  
  public void setAuthDelegate(Delegate<Boolean> authDelegate) {
    this.authDelegate = authDelegate;
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
    GwtUtils.log("calling initEndpointImpl");
    GwtUtils.log("apiRoot = " + apiRoot);
    GwtUtils.log("apiName = " + apiName);
    GwtUtils.log("apiKey = " + getApiKey());
    initEndpointApiImpl(apiRoot, apiName, getApiKey(), useAuthentication, new Callback() {
      public void execute(JavaScriptObject proxyRef) {
        if (proxyRef == null) {
          PhonegapUtils.log("initEndpointApi.callback: ALERT: proxyRef is null!");
        }
        initialized = true;
        onInit();
        if (initDelegate != null)
          initDelegate.execute(null);
        if (useAuthentication) {
          signIn(true);
        }
      }
    });
  }
  
  private native void initEndpointApiImpl(String apiRoot, String apiName, String apiKey, boolean useAuthentication, Callback pCallback) /*-{
    var apisToLoad;
    var callback = function() {
      if (--apisToLoad == 0) {
        $wnd.glbDebugHook($wnd.gapi.client[apiName]);
        var msg = "initEndpointApiImpl: gapi.client = ";
        msg += @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)($wnd.gapi.client);
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)(msg);
        msg = "initEndpointApiImpl: gapi.client["+apiName+"] = ";
        msg += @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)($wnd.gapi.client[apiName]);
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)(msg);
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)($wnd.gapi.client[apiName]);
      }
    }
    if (useAuthentication) {
      apisToLoad = 2; // must match number of calls to gapi.client.load()
    } else {
      apisToLoad = 1; // must match number of calls to gapi.client.load()
    }
    if (apiKey != null) {
      $wnd.gapi.client.setApiKey(apiKey);
    }
    $wnd.gapi.client.load(apiName, 'v1', callback, apiRoot);
    if (useAuthentication) {
      $wnd.gapi.client.load('oauth2', 'v2', callback);
    }
  }-*/;
  
  protected void onInit() {
    
  }
  
  protected static interface Callback {
    public void execute(JavaScriptObject jso);
  }

  protected static class Token extends JavaScriptObject {
    protected Token() { }
    protected final String getAccessToken() {
      return (String)GwtUtils.getPropertyImpl(this, "access_token");
    }
    protected final String getError() {
      return (String)GwtUtils.getPropertyImpl(this, "error");
    }
    protected final String getExpiresIn() {
      return (String)GwtUtils.getPropertyImpl(this, "expires_in");
    }
    protected final String getState() {
      return (String)GwtUtils.getPropertyImpl(this, "state");
    }
    protected final String toMyString() {
      return "Token [getAccessToken()=" + getAccessToken() + ", getError()=" + getError() + ", getExpiresIn()=" + getExpiresIn() + ", getState()=" + getState()
          + "]";
    }
  }
  
  private native void setTokenImpl (Token token) /*-{
    $wnd.gapi.auth.setToken(token);
  }-*/;

  protected static String purgeBlanks(String code) {
    int pos;
    if ((pos = code.indexOf(' ')) > -1) {
      code = code.substring(pos);
    }
    PhonegapUtils.log("purged code = '" + code + "'");
    return code;
  }
  
  private void signIn(boolean immediate) {
    
    PhonegapUtils.log("called signIn " + immediate);
    
    Callback failure = new Callback() {
      public void execute(JavaScriptObject jso) {
        PhonegapUtils.log("authorization failure");
      }
    };
    
    if (Window.Navigator.getUserAgent().toLowerCase().contains("windows nt")) {
      
      PhonegapUtils.log("calling signInDesktopImpl");
      PhonegapUtils.log("clientId " + getDesktopClientId());
      PhonegapUtils.log("scopes " + AUTH_SCOPES);
      PhonegapUtils.log("immediate " + immediate);
      signInDesktopImpl(immediate, getDesktopClientId(), null, AUTH_SCOPES, new Callback() {
        public void execute(JavaScriptObject jso) {
          userAuthedImpl(new Callback() {
            public void execute(JavaScriptObject jso) {
              signedIn = true;
              if (authDelegate != null) {
                authDelegate.execute(signedIn);
              }
            }
          });
        }
      }, failure);
      
    } else {
      
      PhonegapUtils.log("calling signInMobileImpl");
      PhonegapUtils.log("clientId " + getMobileClientId());
      PhonegapUtils.log("scopes " + AUTH_SCOPES);
      PhonegapUtils.log("immediate " + immediate);
      
      final Delegate<Token> gotTokenDelegate = new Delegate<Token>() {
        public void execute(Token token) {
          PhonegapUtils.log("setting gapi auth token");
          setTokenImpl(token);
          PhonegapUtils.log("calling user authed");
          userAuthedImpl(new Callback() {
            public void execute(JavaScriptObject jso) {
              signedIn = true;
              if (authDelegate != null) {
                authDelegate.execute(signedIn);
              }
            }
          });
        }
      };
      
      //TODO : recuperare token da localStorage
      // se trovato e ancora valido >> chiamo direttamente il tokenDelegate
      
      signInMobileImpl(immediate, getMobileClientId(), getMobileClientSecret(), AUTH_SCOPES, new Callback() {
        public void execute(JavaScriptObject jso) {
          Token token = jso.cast();
          PhonegapUtils.log("authorization success with token '" + token + "'");
          
          //TODO : salvare token in localStorage
          
          gotTokenDelegate.execute(token);

        }
      }, failure);
      
    }
    
    
  }
  
  private native void signInDesktopImpl (Boolean mode, String clientId, String clientSecret, String scopes, Callback success, Callback failure) /*-{
    $wnd.gapi.auth.authorize({
        client_id: clientId,
        scope: scopes,
        immediate: mode},
      function() {
        success.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)();
      });
  }-*/;

  /**
   *  PER QUESTA SOLUZIONE HO SEGUITO I DUE ARTICOLI:
   *  

        http://phonegap-tips.com/articles/google-api-oauth-with-phonegaps-inappbrowser.html
        
        http://phonegap-tips.com/articles/oauth-with-phonegaps-inappbrowser-expiration-and-revocation.html
  
   */
  
  private native void signInMobileImpl (Boolean mode, String clientId, String clientSecret, String scopes, Callback success, Callback failure) /*-{
    
    //Build the OAuth consent page URL
    var authUrl = 'https://accounts.google.com/o/oauth2/auth?' + $wnd.$.param({
      client_id: clientId,
      redirect_uri: 'http://localhost',
      response_type: 'code',
      scope: scopes
    });
    
    //Open the OAuth consent page in the InAppBrowser
    var authWindow = $wnd.open(authUrl, '_blank', 'location=no,toolbar=no');
    
    var loadstartHandler = function(e) {
        $wnd.glbDebugHook();
        var url = e.originalEvent.url;
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)('received loadstart event with ' + url);
        
        var code = /\?code=(.+)$/.exec(url);
        var error = /\?error=(.+)$/.exec(url);
        
        if (code || error) {
            //Always close the browser when match is found
            authWindow.close();
        }
  
        if (code) {
            code = @it.mate.phgcommons.client.api.AbstractEndpointProxy::purgeBlanks(Ljava/lang/String;)(code[1]);
            @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("received auth code '" + code + "'");
            //Exchange the authorization code for an access token
            $wnd.$.post('https://accounts.google.com/o/oauth2/token', {
                code: code,
                client_id: clientId,
                client_secret: clientSecret,
                redirect_uri: 'http://localhost',
                grant_type: 'authorization_code'
            }).done(function(data) {
                success.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(data);
                // (Ljava/lang/String;)(data.access_token)
            }).fail(function(response) {
                failure.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(response.responseJSON);
            });
        } else if (error) {
          failure.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(error[1]);
        }
        
    };
  
    @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)('setting loadstart handler');
    
    $wnd.$(authWindow).on('loadstart', loadstartHandler);
//  $wnd.$(authWindow).on('pagestart', loadstartHandler);
    
  }-*/;
  
  private native void userAuthedImpl(Callback pCallback) /*-{
    var request = $wnd.gapi.client.oauth2.userinfo.get().execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)();
      } else {
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("received userinfo error code '" + resp.code + "'");
        var msg = @it.mate.phgcommons.client.utils.JSONUtils::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("full resp is '" + msg + "'");
      }
    });
  }-*/;

  public void auth() {
    if (signedIn) {
      signOut();
    } else {
      signIn(false);
    }
  }
  
  private void signOut() {
    PhonegapUtils.log("signOut");
    signedIn = false;
    if (authDelegate != null) {
      authDelegate.execute(signedIn);
    }
  }
  
}
