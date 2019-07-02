/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DepthDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the depth manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class DepthDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;
      
   JTextField depthInFeet;
   JTextField depthInMeters;
   JTextField depthInFathoms;
   JTextField depthOffset;
   JTextField depthRange;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public DepthDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Depth related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());
      
      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Depth (feet):"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      depthInFeet = new JTextField(dataFactory.getDepthInFeet (), 13);
      depthInFeet.setToolTipText ("Data used in DPT.");
      depthInFeet.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDepthInFeet (depthInFeet.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(depthInFeet, constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Depth (meters):"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      depthInMeters = new JTextField(dataFactory.getDepthInMeters (), 13);
      depthInMeters.setToolTipText ("Data used in DPT.");
      depthInMeters.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDepthInMeters (depthInMeters.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(depthInMeters, constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Depth (fathoms):"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      depthInFathoms = new JTextField(dataFactory.getDepthInFathoms (), 13);
      depthInFathoms.setToolTipText ("Data used in DPT.");
      depthInFathoms.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDepthInFathoms (depthInFathoms.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(depthInFathoms, constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Depth offset:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      depthOffset = new JTextField(dataFactory.getDepthOffset (), 13);
      depthOffset.setToolTipText ("Data used in DPT.");
      depthOffset.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDepthOffset (depthOffset.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(depthOffset, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Depth range:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      depthRange = new JTextField(dataFactory.getDepthRange (), 13);
      depthRange.setToolTipText ("Data used in DPT.");
      depthRange.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDepthRange (depthRange.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(depthRange, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
           
      constraints.gridy = 5;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("* - used in simulated data"), constraints);
      
      gridPanel.setMaximumSize (gridPanel.getPreferredSize ());
      gridPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      add(gridPanel);
   }
}
/*
 * Version history:
 *
 * 1.3.0.1 - added astericks to manual values used in simulated data.
 */

//   xxx.addKeyListener (new
//         KeyListener () {
//            public void keyPressed (KeyEvent e) { }
//
//            public void keyReleased (KeyEvent e) { 
//               dataFactory.yyy (xxx.getText ());
//            }
//
//            public void keyTyped (KeyEvent e)  { }
//         });