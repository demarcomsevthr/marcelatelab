package it.mate.ckd.activities;

import it.mate.ckd.view.CKDInputView;
import it.mate.ckd.view.HomeView;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainActivity {
  
  private static JFrame mainFrame;

  public static JFrame getMainFrame() {
    return mainFrame;
  }

  public static void setMainFrame(JFrame mainFrame) {
    MainActivity.mainFrame = mainFrame;
  }
  
  public static void goToHomeView() {
    goToView(new HomeView());
  }

  public static void goToInputView() {
    goToView(new CKDInputView());
  }

  private static void goToView(JPanel view) {
    mainFrame.getContentPane().removeAll();
    mainFrame.getContentPane().add(view);
    mainFrame.getContentPane().doLayout();
  }

}
