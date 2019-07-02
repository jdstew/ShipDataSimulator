/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetSetDriftPanel.java
 * Created: 2005-02-15, 12:01:01
 */
package mdl.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * An instance of this class generates a pop-up GUI for changing the simulator's
 * set and drft (instantaneously).
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-15
 */
public class SetSetDriftPanel extends JPanel {
   
	private static final long serialVersionUID = 1L;
	SpinnerNumberModel setModel;
   SpinnerNumberModel driftModel;
   
   JSpinner setSpinner;
   JSpinner driftSpinner;
   
   JButton okButton;
   JButton cancelButton;
   JDialog dialog;
   
   SetDriftData outputData;
   
   /**
    * Creates the pop-up set set and drift panel
    *
    * @param inputData The current ownship data values for set and drift.
    */
   public SetSetDriftPanel (SetDriftData inputData) {

      setBorder(BorderFactory.createTitledBorder("Set ownship set and drift"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
            
      setLayout (new GridBagLayout());
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.anchor = GridBagConstraints.WEST;
      constraints.insets = new Insets (2, 3, 2, 3);
      constraints.gridwidth = GridBagConstraints.REMAINDER;
      add(new JLabel("Note: changes are instantaneous."), constraints);  
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.gridwidth = 1;
      add(new JLabel(" "), constraints);  
      
      constraints.gridx = 1;
      constraints.gridy = 2;
      JLabel valueLabel = new JLabel("Value");
      valueLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(valueLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      add(new JLabel("Set:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 3;
      setModel = new SpinnerNumberModel (0, 0, 359, 1);
      setSpinner = new JSpinner(setModel);
      add(setSpinner, constraints);
      add(new JLabel("T"), constraints); 
      
      constraints.gridx = 0;
      constraints.gridy = 4;
      add(new JLabel("Drift:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 4;
      driftModel = new SpinnerNumberModel (0.0, 0.0, 6.0, 0.1);
      driftSpinner = new JSpinner(driftModel);
      driftSpinner.setPreferredSize (setSpinner.getPreferredSize ());
      add(driftSpinner, constraints);
      add(new JLabel("KTS"), constraints); 
      
      constraints.gridx = 0;
      constraints.gridy = 5;
      add(new JLabel(" "), constraints);  
      
      okButton = new JButton ("Ok");
      okButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               getHeadingSpeed ();
               dialog.setVisible (false);
            }
         });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               outputData = null;
               dialog.setVisible (false);
            }
         });
      
      constraints.gridx = 1;
      constraints.gridy = 6;
      add(okButton, constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 6;
      add(cancelButton, constraints);
      
      if (inputData != null) {
         setSpinner.setValue(new Double(inputData.set));
         driftSpinner.setValue(new Double(inputData.drift));
      }
   }
   
   void getHeadingSpeed () {
      outputData = new SetDriftData( 
         Double.parseDouble (setSpinner.getValue().toString()), 
         Double.parseDouble (driftSpinner.getValue().toString()));
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The heading and speed to set the simulator.
    */
   public SetDriftData showDialog (JFrame parentFrame) {
      
      dialog = new JDialog (parentFrame, "Set Simulation Set and Drift", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (okButton);
      dialog.pack();

      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
 
      dialog.setVisible(true);
      return outputData;
   }
}
