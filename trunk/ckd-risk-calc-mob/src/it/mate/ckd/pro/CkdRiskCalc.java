package it.mate.ckd.pro;

import org.apache.cordova.CordovaActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

public class CkdRiskCalc extends CordovaActivity {

  @Override
  @SuppressLint("NewApi")
  public void onCreate(Bundle savedInstanceState) {
    
    super.onCreate(savedInstanceState);
    // Set by <content src="index.html" /> in config.xml
    loadUrl(launchUrl);
    
    /* SE SERVE IL REMOTE DEBUGGING NELL'EMULATORE OCCORRE SCOMMENTARE QUI: */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(true);
    }

  }
  
}
