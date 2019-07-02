/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetAndDriftDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the set and drift manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetAndDriftDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField setTrue; 
   JTextField setMagnetic; 
   JTextField driftKnots;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public SetAndDriftDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Set and drift related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("True set:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      setTrue = new JTextField(dataFactory.getSetTrue (), 13);
      setTrue.setToolTipText ("Data used in VDR.");
      setTrue.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSetTrue (setTrue.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(setTrue, constraints);  

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Magnetic set:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      setMagnetic = new JTextField(dataFactory.getSetMagnetic (), 13);
      setMagnetic.setToolTipText ("Data used in VDR.");
      setMagnetic.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setSetMagnetic (setMagnetic.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(setMagnetic, constraints);
      
      constraints.gridy = 1;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Drift, in knots:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      driftKnots = new JTextField(dataFactory.getDriftKnots (), 13);
      driftKnots.setToolTipText ("Data used in VDR.");
      driftKnots.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDriftKnots (driftKnots.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(driftKnots, constraints);  
           
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