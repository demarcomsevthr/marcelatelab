package it.mate.gend.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.gwtcommons.client.utils.GwtUtils;
import it.mate.phgcommons.client.utils.PhonegapUtils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;


/*******************************************************************************
 * 
 * SEE API CONSOLE
 * 
 * FOR gendtest >> https://code.google.com/apis/console/#project:929530856992
 * 
 * FOR gendtest3 >> https://code.google.com/apis/console/#project:770939113776
 * 
 * 
 */


public class GreetingsProxy {
  
  private static final String API_ROOT = "https://gendtest.appspot.com/_ah/api";
  
  private static final String GOOGLE_CLIENT_API = "https://apis.google.com/js/client.js";

//private static final String CLIENT_ID = "770939113776.apps.googleusercontent.com";
  
//private static final String CLIENT_ID = "929530856992-8sdg8clc0dk3is3hvn7aaoajvcgt07vr.apps.googleusercontent.com";

  private static final String CLIENT_ID = "929530856992-tgsgml6l0au4b4q12r296o0bdk6o7e4f.apps.googleusercontent.com";

  private static final String CLIENT_SECRET = "uzYaikAhZp5JiwCl-Q25Ujgt";

  private static final String SCOPES = "https://www.googleapis.com/auth/userinfo.email";
  
  
  private Delegate<Void> initDelegate;
  
  private Delegate<Void> signedInDelegate;
  
  private Delegate<Void> signedOutDelegate;
  
  private boolean initialized = false;
  
  private boolean signedIn = false;
  
  public GreetingsProxy(Delegate<Void> initDelegate) {
    this.initDelegate = initDelegate;
    initClientApi();
  }

  public boolean isInitialized() {
    return initialized;
  }
  
  public void setSignedInDelegate(Delegate<Void> signedInDelegate) {
    this.signedInDelegate = signedInDelegate;
  }
  
  public void setSignedOutDelegate(Delegate<Void> signedOutDelegate) {
    this.signedOutDelegate = signedOutDelegate;
  }
  
  public void list(final Delegate<List<Greetings>> delegate) {
    listImpl(new Callback() {
      public void execute(JavaScriptObject jso) {
        List<Greetings> results = new ArrayList<GreetingsProxy.Greetings>();
        ResponseCollection coll = jso.cast();
        JsArray<JavaScriptObject> items = coll.getItems();
        for (int it = 0; it < items.length(); it++) {
          Greetings greetings = items.get(it).cast();
          results.add(greetings);
        }
        delegate.execute(results);
      }
    });
  }
  
  private native void listImpl(Callback pCallback) /*-{
    $wnd.gapi.client.greetings.list().execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;
  
  public void addGreeting(String message, final Delegate<Void> delegate) {
    addGreetingImpl(message, new Callback() {
      public void execute(JavaScriptObject jso) {
        delegate.execute(null);
      }
    });
  }
  
  private native void addGreetingImpl(String message, Callback pCallback) /*-{
    $wnd.gapi.client.greetings.add({'message': message}).execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;

  protected void initClientApi() {
    createGlobalInitEndpointImpl(this);
    String source = GOOGLE_CLIENT_API+"?onload=_proxyInitEndpointCallback";
    ScriptElement scriptElem = Document.get().createScriptElement();
    scriptElem.setSrc(source);
    scriptElem.setType("text/javascript");
    Element head = Document.get().getElementsByTagName("head").getItem(0);
    head.appendChild(scriptElem);
  }
  
  private native void createGlobalInitEndpointImpl(GreetingsProxy proxy) /*-{
    $wnd._proxyInitEndpointCallback = $entry(function() {
      proxy.@it.mate.gend.client.api.GreetingsProxy::initEndpointApi()();
    });
  }-*/;

  protected void initEndpointApi() {
    GwtUtils.log("calling initEndpointImpl method with " + API_ROOT);
    initEndpointApiImpl(API_ROOT, new Callback() {
      public void execute(JavaScriptObject jso) {
        initialized = true;
        initDelegate.execute(null);
        signIn(true);
      }
    });
  }
  
  private native void initEndpointApiImpl(String apiRoot, Callback pCallback) /*-{
    var apisToLoad;
    var callback = function() {
      if (--apisToLoad == 0) {
        pCallback.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)();
      }
    }
    apisToLoad = 2; // must match number of calls to gapi.client.load()
    $wnd.gapi.client.load('greetings', 'v1', callback, apiRoot);
    $wnd.gapi.client.load('oauth2', 'v2', callback);
  }-*/;

  private void signIn(boolean immediate) {
    GwtUtils.log("calling signInImpl");
    
    final Delegate<Token> tokenDelegate = new Delegate<Token>() {
      public void execute(Token token) {
        PhonegapUtils.log("setting gapi auth token");
        setTokenImpl(token);
        PhonegapUtils.log("calling user authed");
        userAuthedImpl(new Callback() {
          public void execute(JavaScriptObject jso) {
            signedIn = true;
            if (signedInDelegate != null) {
              signedInDelegate.execute(null);
            }
          }
        });
      }
    };
    
    //TODO : recuperare token da localStorage
    // se trovato e ancora valido >> chiamo direttamente il tokenDelegate
    
    signInImpl(immediate, CLIENT_ID, CLIENT_SECRET, SCOPES, new Callback() {
      public void execute(JavaScriptObject jso) {
        Token token = jso.cast();
        PhonegapUtils.log("authorization success with token '" + token + "'");
        
        //TODO : salvare token in localStorage
        
        tokenDelegate.execute(token);

      }
    }, new Callback() {
      public void execute(JavaScriptObject jso) {
        PhonegapUtils.log("authorization failure");
      }
    });
  }
  
  private native void signInImpl (Boolean mode, String clientId, String clientSecret, String scopes, Callback success, Callback failure) /*-{
    
    //Build the OAuth consent page URL
    var authUrl = 'https://accounts.google.com/o/oauth2/auth?' + $wnd.$.param({
      client_id: clientId,
      redirect_uri: 'http://localhost',
      response_type: 'code',
      scope: scopes
    });
    
    //Open the OAuth consent page in the InAppBrowser
    var authWindow = $wnd.open(authUrl, '_blank', 'location=no,toolbar=no');

    $wnd.$(authWindow).on('loadstart', function(e) {
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
            code = @it.mate.gend.client.api.GreetingsProxy::purgeBlanks(Ljava/lang/String;)(code[1]);
            @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("received auth code '" + code + "'");
            //Exchange the authorization code for an access token
            $wnd.$.post('https://accounts.google.com/o/oauth2/token', {
                code: code,
                client_id: clientId,
                client_secret: clientSecret,
                redirect_uri: 'http://localhost',
                grant_type: 'authorization_code'
            }).done(function(data) {
                success.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(data);
                // (Ljava/lang/String;)(data.access_token)
            }).fail(function(response) {
                failure.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(response.responseJSON);
            });
        } else if (error) {
          failure.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(error[1]);
        }
        
    });
    
  }-*/;
  
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

  protected static String purgeBlanks(String code) {
    int pos;
    if ((pos = code.indexOf(' ')) > -1) {
      code = code.substring(pos);
    }
    PhonegapUtils.log("purged code = '" + code + "'");
    return code;
  }
  
  private native void setTokenImpl (Token token) /*-{
    $wnd.gapi.auth.setToken(token);
  }-*/;

  private native void signInImpl_WITH_GAPI_AUTH_ (Boolean mode, String cliendId, String scopes, Callback pCallback) /*-{
    $wnd.gapi.auth.authorize({
        client_id: cliendId,
        scope: scopes,
        immediate: mode},
      function() {
        pCallback.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)();
      });
  }-*/;

  private native void userAuthedImpl(Callback pCallback) /*-{
    var request = $wnd.gapi.client.oauth2.userinfo.get().execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.gend.client.api.GreetingsProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)();
      } else {
        @it.mate.phgcommons.client.utils.PhonegapUtils::log(Ljava/lang/String;)("received userinfo error code '" + resp.code + "'");
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
    signedIn = false;
    if (signedOutDelegate != null) {
      signedOutDelegate.execute(null);
    }
  }
  
  private static interface Callback {
    public void execute(JavaScriptObject jso);
  }
  
  public static class ResponseCollection extends JavaScriptObject {
    protected ResponseCollection() { }
    public final JsArray<JavaScriptObject> getItems() {
      return GwtUtils.getPropertyJsoImpl(this, "items").cast();
    }
  }
  
  public static class Greetings extends JavaScriptObject {
    protected Greetings() { }
    public final String getMessage() {
      return (String)GwtUtils.getPropertyImpl(this, "message");
    }
  }
  
}
