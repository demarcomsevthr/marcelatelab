package it.mate.gend.client.api;

import it.mate.gendtest.shared.model.TestCommand;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.api.AbstractEndpointProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class CommandsProxy extends AbstractEndpointProxy {

//private static final String API_ROOT = "https://gendtest.appspot.com/_ah/api";
  private static final String API_ROOT = "http://127.0.0.1:8080/_ah/api";
  
  private static final String API_NAME = "commandsAPI";
  
  private MyAutoBeanFactory factory = GWT.create(MyAutoBeanFactory.class);
  
  public CommandsProxy(Delegate<Void> initDelegate) {
    super(API_ROOT, API_NAME, false, initDelegate);
  }
  
  public void sendEnableCommand(String message, final Delegate<Void> delegate) {
    sendCommandImpl(message, 1, new Callback() {
      public void execute(JavaScriptObject jso) {
        delegate.execute(null);
      }
    });
  }
  
  public void sendDisableCommand(String message, final Delegate<Void> delegate) {
    sendCommandImpl(message, 2, new Callback() {
      public void execute(JavaScriptObject jso) {
        delegate.execute(null);
      }
    });
  }
  
  public void sendTestCommand(TestCommand command, final Delegate<Void> delegate) {
    String json = AutoBeanCodex.encode(factory.getTestCommand(command)).getPayload();
    sendCommandJsonImpl(json, new Callback() {
      public void execute(JavaScriptObject jso) {
        delegate.execute(null);
      }
    });
  }
  
  private native void sendCommandImpl(String message, int action, Callback pCallback) /*-{
    $wnd.gapi.client.commandsAPI.addAction({'action': action}).execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;

  private native void sendCommandJsonImpl(String json, Callback pCallback) /*-{
    $wnd.gapi.client.commandsAPI.addActionJson({'json': json}).execute(function(resp) {
      if (!resp.code) {
        pCallback.@it.mate.phgcommons.client.api.AbstractEndpointProxy.Callback::execute(Lcom/google/gwt/core/client/JavaScriptObject;)(resp);
      }
    });
  }-*/;

}
