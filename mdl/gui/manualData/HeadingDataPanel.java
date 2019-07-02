/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: HeadingDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the heading manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class HeadingDataPanel extends JPanel {
 
	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField headingTrueGround; // Course over ground, degrees True
   JTextField headingTrueWater;
   JTextField headingMagneticGround;
   JTextField headingMagneticWater; 
   JTextField headingStatus; // Mode indicator
   JTextField rateOfTurn;
   JTextField rateOfTurnStatus;
   JTextField variation; // Variation, in degrees Magnetic
   JTextField variationDirection;
   JTextField deviation; // Deviation, in degrees Magnetic
   JTextField deviationDirection;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public HeadingDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Heading related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("True ground heading:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1; 
      constraints.anchor = GridBagConstraints.WEST;
      headingTrueGround = new JTextField(dataFactory.getHeadingTrueGround (), 13);
      headingTrueGround.setToolTipText ("Data used in HDT, HDG.");
      headingTrueGround.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setHeadingTrueGround (headingTrueGround.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
       gridPanel.add(headingTrueGround, constraints);  

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("True water heading:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      headingTrueWater = new JTextField(dataFactory.getHeadingTrueWater (), 13);
      headingTrueWater.setToolTipText ("Data used in HDT, HDG.");
      headingTrueWater.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setHeadingTrueWater (headingTrueWater.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(headingTrueWater, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Magnetic ground heading:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      headingMagneticGround = new JTextField(dataFactory.getHeadingMagneticGround (), 13);
      headingMagneticGround.setToolTipText ("Data used in HDT, HDG, and VTG.");
      headingMagneticGround.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setHeadingMagneticGround (headingMagneticGround.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(headingMagneticGround, constraints);
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Magnetic water heading:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      headingMagneticWater = new JTextField(dataFactory.getHeadingMagneticWater (), 13);
      headingMagneticWater.setToolTipText ("Data used in HDT, HDG, VHW.");
      headingMagneticWater.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setHeadingMagneticWater (headingMagneticWater.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(headingMagneticWater, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Heading status:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      headingStatus = new JTextField(dataFactory.getHeadingStatus (), 13);
      headingStatus.setToolTipText ("Data used in HDT, HDG.");
      headingStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setHeadingStatus (headingStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(headingStatus, constraints);  

      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Rate of turn:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      rateOfTurn = new JTextField(dataFactory.getRateOfTurn (), 13);
      rateOfTurn.setToolTipText ("Data used in HDT, HDG.");
      rateOfTurn.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setRateOfTurn (rateOfTurn.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(rateOfTurn, constraints);  

      constraints.gridy = 6;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Rate of turn status:"), constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      rateOfTurnStatus = new JTextField(dataFactory.getRateOfTurnStatus (), 13);
      rateOfTurnStatus.setToolTipText ("Data used in HDT, HDG.");
      rateOfTurnStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setRateOfTurnStatus (rateOfTurnStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(rateOfTurnStatus, constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 7;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Variation:"), constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      variation = new JTextField(dataFactory.getVariation (), 13);
      variation.setToolTipText ("Data used in HDT, HDG.");
      variation.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setVariation (variation.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(variation, constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 8;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Variation direction:"), constraints);  
      
      constraints.gridy = 8;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      variationDirection = new JTextField(dataFactory.getVariationDirection (), 13);
      variationDirection.setToolTipText ("Data used in HDT, HDG.");
      variationDirection.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setVariationDirection (variationDirection.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(variationDirection, constraints);  
      
      constraints.gridy = 8;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 9;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Deviation:"), constraints);  
      
      constraints.gridy = 9;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      deviation = new JTextField(dataFactory.getDeviation (), 13);
      deviation.setToolTipText ("Data used in HDT, HDG.");
      deviation.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDeviation (deviation.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(deviation, constraints);  
      
      constraints.gridy = 9;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 10;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Deviation direction:"), constraints);  
      
      constraints.gridy = 10;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      deviationDirection = new JTextField(dataFactory.getDeviationDirection (), 13);
      deviationDirection.setToolTipText ("Data used in HDT, HDG.");
      deviationDirection.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDeviationDirection (deviationDirection.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(deviationDirection, constraints);  
      
      constraints.gridy = 10;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 11;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("* - used in simulated data"), constraints);
      
      gridPanel.setMaximumSize (gridPanel.getPreferredSize ());
      gridPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      add(gridPanel);
   }
}
