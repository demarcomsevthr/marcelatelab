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
    super.loadUrl(res.getString(R.string.startup_url));

  }

  @Override
  public void init() {
    super.init(new CordovaWebView(this), new GWTCordovaWebViewClient(this), new CordovaChromeClient(this));
    System.out.println("init");
  }

}


/*
public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

}
*/