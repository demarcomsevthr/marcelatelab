package it.mate.ckd;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;

import android.content.res.Resources;
import android.os.Bundle;


public class MainActivity extends DroidGap {

  @Override
  public void onCreate(Bundle savedInstanceState) {

    Resources res = getResources();
    super.onCreate(savedInstanceState);

    // FREE VERSION:
//  super.loadUrl(res.getString(it.mate.ckd.R.string.startup_url));
    
    // PRO VERSION:
    super.loadUrl(res.getString(it.mate.ckd.pro.R.string.startup_url));

  }

  @Override
  public void init() {
    super.init(new CordovaWebView(this), new GWTCordovaWebViewClient(this), new CordovaChromeClient(this));
    System.out.println("init");
  }

}
