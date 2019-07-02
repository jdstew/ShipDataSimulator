/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: EnvironmentDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the environment manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class EnvironmentDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;
   
   JTextField windAngle;
   JTextField windReference;
   JTextField windSpeed;
   JTextField windUnits;
   JTextField windStatus;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public EnvironmentDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Environment related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Wind angle:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      windAngle = new JTextField(dataFactory.getWindAngle (), 6);
      windAngle.setToolTipText ("Data used in MWV.");
      windAngle.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setWindAngle (windAngle.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(windAngle, constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Wind reference:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      windReference = new JTextField(dataFactory.getWindReference (), 4);
      windReference.setToolTipText ("Data used in MWV.");
      windReference.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setWindReference (windReference.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(windReference, constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Wind speed:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      windSpeed = new JTextField(dataFactory.getWindSpeed (), 6);
      windSpeed.setToolTipText ("Data used in MWV.");
      windSpeed.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setWindSpeed (windSpeed.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(windSpeed, constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Wind speed units:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      windUnits = new JTextField(dataFactory.getWindUnits (), 4);
      windUnits.setToolTipText ("Data used in MWV.");
      windUnits.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setWindUnits (windUnits.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(windUnits, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Wind status:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      windStatus = new JTextField(dataFactory.getWindStatus (), 4);
      windStatus.setToolTipText ("Data used in MWV.");
      windStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setWindStatus (windStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(windStatus, constraints);  
      
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