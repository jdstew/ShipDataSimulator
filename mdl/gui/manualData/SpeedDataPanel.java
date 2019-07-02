/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SpeedDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the speed manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SpeedDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField speedGroundInKnots;
   JTextField speedGroundInKMH;
   JTextField speedGroundTranverseInKnots;
   JTextField speedGroundStatus;
   JTextField speedGroundSternInKnots;
   JTextField speedGroundSternStatus;
   JTextField speedWaterInKnots;
   JTextField speedWaterInKMH;
   JTextField speedWaterTranverseInKnots;
   JTextField speedWaterStatus;
   JTextField speedWaterSternInKnots;
   JTextField speedWaterSternStatus;
   JTextField speedMode;
   JTextField dopplerMode;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public SpeedDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Speed related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());
      
      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Ground speed (knots):"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedGroundInKnots = new JTextField(dataFactory.getSpeedGroundInKnots (), 13);
      speedGroundInKnots.setToolTipText ("Data used in VHW, VTG.");
      speedGroundInKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedGroundInKnots (speedGroundInKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedGroundInKnots, constraints);  

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Ground speed (KMH):"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedGroundInKMH = new JTextField(dataFactory.getSpeedGroundInKMH (), 13);
      speedGroundInKMH.setToolTipText ("Data used in VHW, VTG.");
      speedGroundInKMH.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedGroundInKMH (speedGroundInKMH.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedGroundInKMH, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Traverse ground speed (knots):"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedGroundTranverseInKnots = new JTextField(dataFactory.getSpeedGroundTranverseInKnots (), 13);
      speedGroundTranverseInKnots.setToolTipText ("Data used in VHW, VTG.");
      speedGroundTranverseInKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedGroundTranverseInKnots (speedGroundTranverseInKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedGroundTranverseInKnots, constraints);
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Ground speed status:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedGroundStatus = new JTextField(dataFactory.getSpeedGroundStatus (), 13);
      speedGroundStatus.setToolTipText ("Data used in VHW, VTG.");
      speedGroundStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedGroundStatus (speedGroundStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedGroundStatus, constraints); 

      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Stern ground speed (knots):"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedGroundSternInKnots = new JTextField(dataFactory.getSpeedGroundSternInKnots (), 13);
      speedGroundSternInKnots.setToolTipText ("Data used in VHW, VTG.");
      speedGroundSternInKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedGroundSternInKnots (speedGroundSternInKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedGroundSternInKnots, constraints);
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Stern ground speed status:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedGroundSternStatus = new JTextField(dataFactory.getSpeedGroundSternStatus (), 13);
      speedGroundSternStatus.setToolTipText ("Data used in VHW, VTG.");
      speedGroundSternStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedGroundSternStatus (speedGroundSternStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedGroundSternStatus, constraints);
      
      constraints.gridy = 5;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 6;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Water speed (knots):"), constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedWaterInKnots = new JTextField(dataFactory.getSpeedWaterInKnots (), 13);
      speedWaterInKnots.setToolTipText ("Data used in VHW, VTG.");
      speedWaterInKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedWaterInKnots (speedWaterInKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedWaterInKnots, constraints);  

      constraints.gridy = 7;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Water speed (KMH):"), constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedWaterInKMH = new JTextField(dataFactory.getSpeedWaterInKMH (), 13);
      speedWaterInKMH.setToolTipText ("Data used in VHW, VTG.");
      speedWaterInKMH.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedWaterInKMH (speedWaterInKMH.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedWaterInKMH, constraints);  

      constraints.gridy = 8;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Traverse water speed (knots):"), constraints);  
      
      constraints.gridy = 8;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedWaterTranverseInKnots = new JTextField(dataFactory.getSpeedWaterTranverseInKnots (), 13);
      speedWaterTranverseInKnots.setToolTipText ("Data used in VHW, VTG.");
      speedWaterTranverseInKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedWaterTranverseInKnots (speedWaterTranverseInKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedWaterTranverseInKnots, constraints);
      
      constraints.gridy = 8;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 9;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Water speed status:"), constraints);  
      
      constraints.gridy = 9;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedWaterStatus = new JTextField(dataFactory.getSpeedWaterStatus (), 13);
      speedWaterStatus.setToolTipText ("Data used in VHW, VTG.");
      speedWaterStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedWaterStatus (speedWaterStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedWaterStatus, constraints);

      constraints.gridy = 9;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 10;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Stern water speed (knots):"), constraints);  
      
      constraints.gridy = 10;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedWaterSternInKnots = new JTextField(dataFactory.getSpeedWaterSternInKnots (), 13);
      speedWaterSternInKnots.setToolTipText ("Data used in VHW, VTG.");
      speedWaterSternInKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedWaterSternInKnots (speedWaterSternInKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedWaterSternInKnots, constraints);  
      
      constraints.gridy = 10;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 11;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Stern water speed status:"), constraints);  
      
      constraints.gridy = 11;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedWaterSternStatus = new JTextField(dataFactory.getSpeedWaterSternStatus (), 13);
      speedWaterSternStatus.setToolTipText ("Data used in VHW, VTG.");
      speedWaterSternStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedWaterSternStatus (speedWaterSternStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(speedWaterSternStatus, constraints);
      
      constraints.gridy = 11;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 12;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Speed mode:"), constraints);  
      
      constraints.gridy = 12;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      speedMode = new JTextField(dataFactory.getSpeedMode (), 13);
      speedMode.setToolTipText ("Data used in VHW, VTG.");
      speedMode.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSpeedMode (speedMode.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
       gridPanel.add(speedMode, constraints);
      
      constraints.gridy = 12;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 13;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Doppler mode:"), constraints);  
      
      constraints.gridy = 13;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      dopplerMode = new JTextField(dataFactory.getDopplerMode (), 13);
      dopplerMode.setToolTipText ("Data used in VHW, VTG.");
      dopplerMode.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDopplerMode (dopplerMode.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(dopplerMode, constraints);  

      constraints.gridy = 13;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
           
      constraints.gridy = 14;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("* - used in simulated data"), constraints);
      
      gridPanel.setMaximumSize (gridPanel.getPreferredSize ());
      gridPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      add(gridPanel);
   }
}
