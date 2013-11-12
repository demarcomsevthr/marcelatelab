package it.mate.stickmail.client.api;

import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.api.AbstractEndpointProxy;
import it.mate.stickmail.shared.model.MyAutoBeanFactory;
import it.mate.stickmail.shared.model.StickMail;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class StickMailProxy extends AbstractEndpointProxy {

//private static final String API_ROOT = "https://stickmail.appspot.com/_ah/api";
  
  private static final String API_ROOT = "http://127.0.0.1:8080/_ah/api";
  
  private static final String API_NAME = "stickMailAPI";
  
  private MyAutoBeanFactory factory = GWT.create(MyAutoBeanFactory.class);
  
  public StickMailProxy(Delegate<Void> initDelegate) {
    super(API_ROOT, API_NAME, false, initDelegate);
  }
  
  public void getServerTimeMillis(final Delegate<Long> delegate) {
    
  }
  
  public void postNewMail(StickMail mail, final Delegate<Integer> delegate) {
    String payload = AutoBeanCodex.encode(factory.getStickMail(mail)).getPayload();
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
