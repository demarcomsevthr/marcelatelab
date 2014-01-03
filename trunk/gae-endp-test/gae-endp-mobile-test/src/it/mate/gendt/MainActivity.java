package it.mate.gendt;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;

import android.content.res.Resources;
import android.os.Bundle;


public class MainActivity extends DroidGap {
  
  Resources res;

  @Override
  public void onCreate(Bundle savedInstanceState) {

    res = getResources();
    super.onCreate(savedInstanceState);
    super.loadUrl(res.getString(R.string.startup_url));
    
    this.appView.addJavascriptInterface(new JsConfigWrapper(), "jsConfigWrapper");
    
  }

  class JsConfigWrapper {
    public String getTestFacadeModuleUrl() {
      return res.getString(R.string.test_facade_module_url);
    }
    public String getTestFacadeRelativeUrl() {
      return res.getString(R.string.test_facade_relative_url);
    }
  }

  @Override
  public void init() {
    super.init(new CordovaWebView(this), new GWTCordovaWebViewClient(this), new CordovaChromeClient(this));
    System.out.println("init");
  }

}

