package it.mate.gpg.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class ZIndexPatch {
  
  public static void apply() {
    new Timer() {
      public void run() {
        Document.get().getBody().getStyle().setZIndex(1);
        RootPanel.get().setVisible(true);
      }
    }.scheduleRepeating(500);
  }

}
