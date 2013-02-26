package it.mate.abaco;

import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.DroidGap;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends DroidGap {

  @Override
  public void onCreate(Bundle savedInstanceState) {

    Resources res = getResources();
    super.onCreate(savedInstanceState);
    super.loadUrl(res.getString(R.string.startup_url));

  }

  @Override
  public void init() {
    super.init(new CordovaWebView(this), new GWTCordovaWebViewClient(this), new CordovaChromeClient(this));
    System.out.println("init");
    super.appView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
  }

}
