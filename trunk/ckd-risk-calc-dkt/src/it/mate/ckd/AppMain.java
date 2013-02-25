package it.mate.ckd;

import it.mate.ckd.activities.MainActivity;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class AppMain {

  private JFrame mainFrame;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    
    try {
      UIManager.setLookAndFeel(new NimbusLookAndFeel());
    } catch (UnsupportedLookAndFeelException e1) {
      e1.printStackTrace();
    }
    
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          AppMain window = new AppMain();
          window.mainFrame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public AppMain() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    mainFrame = new JFrame();
    mainFrame.setTitle("CKD Risk Calculator (v. 0.018)");
    mainFrame.setBounds(100, 100, 450, 300);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
    
    MainActivity.setMainFrame(mainFrame);
    
    MainActivity.goToHomeView();
    
  }

}
