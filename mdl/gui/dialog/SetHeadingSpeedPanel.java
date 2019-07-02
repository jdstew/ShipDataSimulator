/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetHeadingSpeedPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * An instance of this class generates a pop-up GUI for changing the simulator's
 * heading and speed (instantaneously).
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetHeadingSpeedPanel extends JPanel {
   
	private static final long serialVersionUID = 1L;
	SpinnerNumberModel headingModel;
   SpinnerNumberModel speedModel;
   
   JSpinner headingSpinner;
   JSpinner speedSpinner;
   
   JButton okButton;
   JButton cancelButton;
   JDialog dialog;
   
   HdgSpdData outputData;
   
   /**
    * Creates the pop-up set heading and speed panel
    *
    * @param inputData The current ownship data values for heading and speed.
    */
   public SetHeadingSpeedPanel (HdgSpdData inputData) {

      setBorder(BorderFactory.createTitledBorder("Set ownship heading and speed"));
      
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
      add(new JLabel("Heading:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 3;
      headingModel = new SpinnerNumberModel (0, 0, 359, 1);
      headingSpinner = new JSpinner(headingModel);
      add(headingSpinner, constraints);
      add(new JLabel("T"), constraints); 
      
      constraints.gridx = 0;
      constraints.gridy = 4;
      add(new JLabel("Speed:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 4;
      speedModel = new SpinnerNumberModel (10, -10, 35, 1);
      speedSpinner = new JSpinner(speedModel);
      add(speedSpinner, constraints);
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
         headingSpinner.setValue(new Integer((int) inputData.headingActual));
         speedSpinner.setValue(new Integer((int) inputData.speedActual));
      }
   }
   
   void getHeadingSpeed () {
      outputData = new HdgSpdData( 
         Double.parseDouble (headingSpinner.getValue().toString()), 
         Double.parseDouble (speedSpinner.getValue().toString()));
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The heading and speed to set the simulator.
    */
   public HdgSpdData showDialog (JFrame parentFrame) {
      
      dialog = new JDialog (parentFrame, "Set Simulation Heading and Speed", true);
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
