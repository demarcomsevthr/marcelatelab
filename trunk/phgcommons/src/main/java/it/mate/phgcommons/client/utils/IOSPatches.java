package it.mate.phgcommons.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;

public class IOSPatches {
  
  // 20/03/2014
  // iOS webview focus issue (http://stackoverflow.com/questions/19110144/ios7-issues-with-webview-focus-when-using-keyboard-html)
  public static void applyViewPortPatch(ViewPort viewPort) {
    if (OsDetectionUtils.isIOs()) {
      viewPort.setHeightToDeviceHeight();
    }
  }
  
  // 20/03/2014
  // vedi sopra
  public static void applyStatusBarStylePatch() {
    if (OsDetectionUtils.isIOs()) {
      MetaElement statusBarStyleElement = Document.get().createMetaElement();
      statusBarStyleElement.setName("apple-mobile-web-app-status-bar-style");
      statusBarStyleElement.setContent("black-translucent");
      Element head = Document.get().getElementsByTagName("head").getItem(0);
      head.appendChild(statusBarStyleElement);
    }
  }

}
