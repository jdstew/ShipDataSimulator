/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetPositionPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides dialog box access to entering the simulated position.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetPositionPanel extends JPanel {
   
	private static final long serialVersionUID = 1L;
	SpinnerNumberModel latDegModel;
   SpinnerNumberModel latMinModel;
   SpinnerNumberModel lonDegModel;
   SpinnerNumberModel lonMinModel;
   
   JSpinner latDegSpinner;
   JSpinner latMinSpinner;
   JSpinner lonDegSpinner;
   JSpinner lonMinSpinner;
   
   JComboBox latHemisphereBox; 
   JComboBox lonHemisphereBox; 

   JButton okButton;
   JButton cancelButton;
   JDialog dialog;
   
   PositionData outputData;
   
   /**
    * Initializes an object of this class.
    *
    * @param inputData The simulated position to manipulate.
    */
   public SetPositionPanel (PositionData inputData) {

      setBorder(BorderFactory.createTitledBorder("Set ownship position"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
            
      setLayout (new GridBagLayout());
 
      constraints.gridx = 1;
      constraints.gridy = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel degreesLabel = new JLabel("Degrees");
      degreesLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(degreesLabel, constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 0;
      JLabel minutesLabel = new JLabel("Minutes");
      minutesLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(minutesLabel, constraints);   
            
      constraints.gridx = 3;
      constraints.gridy = 0;
      JLabel hemisphereLabel = new JLabel("Hemisphere");
      hemisphereLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(hemisphereLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      add(new JLabel("Latitude:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 1;
      constraints.anchor = GridBagConstraints.EAST;
      latDegModel = new SpinnerNumberModel (36, 0, 89, 1);
      latDegSpinner = new JSpinner(latDegModel);
      add(latDegSpinner, constraints); 

      constraints.gridx = 2;
      constraints.gridy = 1;
      constraints.anchor = GridBagConstraints.WEST;
      latMinModel = new SpinnerNumberModel (58, 0, 59, 1);
      latMinSpinner = new JSpinner(latMinModel);
      add(latMinSpinner, constraints); 
      
      constraints.gridx = 3;
      constraints.gridy = 1;
      latHemisphereBox = new JComboBox ();
      latHemisphereBox.addItem ("North");
      latHemisphereBox.addItem ("South");
      add(latHemisphereBox, constraints);  
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      add(new JLabel("Longitude:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 2;
      lonDegModel = new SpinnerNumberModel (76, 0, 179, 1);
      lonDegSpinner = new JSpinner(lonDegModel);
      add(lonDegSpinner, constraints); 

      constraints.gridx = 2;
      constraints.gridy = 2;
      lonMinModel = new SpinnerNumberModel (3, 0, 59, 1);
      lonMinSpinner = new JSpinner(lonMinModel);
      add(lonMinSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 2;
      lonHemisphereBox = new JComboBox ();
      lonHemisphereBox.addItem ("East");
      lonHemisphereBox.addItem ("West");
      lonHemisphereBox.setSelectedIndex (1);
      add(lonHemisphereBox, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      add(new JLabel(" "), constraints);  
      
      okButton = new JButton ("Ok");
      okButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               setOwnshipPosition();
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

      constraints.gridx = 2;
      constraints.gridy = 4;
      add(okButton, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 4;
      add(cancelButton, constraints);
      
      if (inputData != null) {
         latDegSpinner.setValue(new Integer((int) Math.abs(inputData.latitude))); 
         latMinSpinner.setValue(new Integer((int) (Math.abs(inputData.latitude % 1.0) * 60.0))); 
         lonDegSpinner.setValue(new Integer((int) Math.abs(inputData.longitude))); 
         lonMinSpinner.setValue(new Integer((int) (Math.abs(inputData.longitude % 1.0) * 60.0))); 
         if (inputData.latitude > 0) {
            latHemisphereBox.setSelectedIndex (0);
         }
         else {
            latHemisphereBox.setSelectedIndex (1);
         }
         if (inputData.longitude > 0) {
            lonHemisphereBox.setSelectedIndex (0);
         }
         else {
            lonHemisphereBox.setSelectedIndex (1);
         }
      }
   }
   
   void setOwnshipPosition () {
      outputData = new PositionData(0.0, 0.0);
      outputData.latitude = 
         Integer.parseInt (latDegSpinner.getValue().toString()) +
         Integer.parseInt (latMinSpinner.getValue().toString()) / 60.0;
      if (latHemisphereBox.getSelectedIndex () == 1) {
         outputData.latitude = -outputData.latitude;
      }
      outputData.longitude = 
         Integer.parseInt (lonDegSpinner.getValue().toString()) +
         Integer.parseInt (lonMinSpinner.getValue().toString()) / 60.0;
      if (lonHemisphereBox.getSelectedIndex () == 1) {
         outputData.longitude = -outputData.longitude;
      }
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The position to set the simulator.
    */
   public PositionData showDialog (JFrame parentFrame) {    
      dialog = new JDialog (parentFrame, "Set Ownship Position", true);
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
