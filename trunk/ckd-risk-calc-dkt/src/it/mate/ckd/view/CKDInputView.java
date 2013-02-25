package it.mate.ckd.view;

import it.mate.ckd.activities.MainActivity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CKDInputView extends JPanel {

  /**
   * Create the panel.
   */
  public CKDInputView() {
    setLayout(null);
    
    JLabel lblInputsHere = new JLabel("Inputs here");
    lblInputsHere.setBounds(62, 59, 102, 16);
    add(lblInputsHere);
    
    JButton btnBack = new JButton("Back");
    btnBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MainActivity.goToHomeView();
      }
    });
    btnBack.setBounds(17, 19, 90, 28);
    add(btnBack);
    
    JButton btnCalcola = new JButton("Calcola");
    btnCalcola.setBounds(108, 223, 90, 28);
    add(btnCalcola);
    

  }
}
