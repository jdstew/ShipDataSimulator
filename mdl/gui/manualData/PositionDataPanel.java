/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: PositionDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the position manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class PositionDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField latitude;
   JTextField latitudeHemisphere;
   JTextField longitude;
   JTextField longitudeHemisphere;
   JTextField positionStatusID; 
   JTextField positionModeID;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public PositionDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Position related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Latitude:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      latitude = new JTextField(dataFactory.getLatitude (), 13);
      latitude.setToolTipText ("Data used in GGA, GLL.");
      latitude.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLatitude (latitude.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(latitude, constraints);  

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Latitude hemisphere:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      latitudeHemisphere = new JTextField(dataFactory.getLatitudeHemisphere (), 13);
      latitudeHemisphere.setToolTipText ("Data used in GGA, GLL.");
      latitudeHemisphere.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLatitudeHemisphere (latitudeHemisphere.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(latitudeHemisphere, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Longitude:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      longitude = new JTextField(dataFactory.getLongitude (), 13);
      longitude.setToolTipText ("Data used in GGA, GLL.");
      longitude.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLongitude (longitude.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(longitude, constraints);  

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Longitude hemisphere:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      longitudeHemisphere = new JTextField(dataFactory.getLongitudeHemisphere (), 13);
      longitudeHemisphere.setToolTipText ("Data used in GGA, GLL.");
      longitudeHemisphere.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLongitudeHemisphere (longitudeHemisphere.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(longitudeHemisphere, constraints);  

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Position status:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      positionStatusID = new JTextField(dataFactory.getPositionStatusID (), 13);
      positionStatusID.setToolTipText ("Data used in GLL.");
      positionStatusID.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setPositionStatusID (positionStatusID.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(positionStatusID, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Position mode:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      positionModeID = new JTextField(dataFactory.getPositionModeID (), 13);
      positionModeID.setToolTipText ("Data used in GLL.");
      positionModeID.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setPositionModeID (positionModeID.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(positionModeID, constraints);  

      constraints.gridy = 5;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
           
      constraints.gridy = 6;
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