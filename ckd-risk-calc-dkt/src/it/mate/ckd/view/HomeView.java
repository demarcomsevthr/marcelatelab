package it.mate.ckd.view;

import it.mate.ckd.activities.MainActivity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HomeView extends JPanel {

  /**
   * Create the panel.
   */
  public HomeView() {
    setLayout(null);
    
    JLabel lblVersionHere = new JLabel("Version here");
    lblVersionHere.setBounds(153, 235, 94, 14);
    add(lblVersionHere);
    
    JButton btnNewButton = new JButton("Inserisci parametri");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        MainActivity.goToInputView();
      }
    });
    btnNewButton.setBounds(116, 153, 178, 40);
    add(btnNewButton);

  }
}
