/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DatumDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the datum manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class DatumDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField datumLocalCode; 
   JTextField datumLocalSubdivisionCode; 
   JTextField latitudeOffset;  
   JTextField latitudeOffsetDirection; 
   JTextField longitudeOffset; 
   JTextField longitudeOffsetDirection; 
   JTextField altitudeOffset; 
   JTextField datumReferenceCode;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public DatumDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Datum related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Local datum code:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      datumLocalCode = new JTextField(dataFactory.getDatumLocalCode (), 13);
      datumLocalCode.setToolTipText ("Data used in DTM.");
      datumLocalCode.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDatumLocalCode (datumLocalCode.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(datumLocalCode, constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Local datum subdivision code:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      datumLocalSubdivisionCode = new JTextField(dataFactory.getDatumLocalSubdivisionCode (), 13);
      datumLocalSubdivisionCode.setToolTipText ("Data used in DTM.");
      datumLocalSubdivisionCode.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDatumLocalSubdivisionCode (datumLocalSubdivisionCode.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(datumLocalSubdivisionCode, constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Latitude offset:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      latitudeOffset = new JTextField(dataFactory.getLatitudeOffset (), 13);
      latitudeOffset.setToolTipText ("Data used in DTM.");
      latitudeOffset.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLatitudeOffset (latitudeOffset.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(latitudeOffset, constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Latitude offset direction:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      latitudeOffsetDirection = new JTextField(dataFactory.getLatitudeOffsetDirection (), 13);
      latitudeOffsetDirection.setToolTipText ("Data used in DTM.");
      latitudeOffsetDirection.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLatitudeOffsetDirection (latitudeOffsetDirection.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(latitudeOffsetDirection, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Longitude offset:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      longitudeOffset = new JTextField(dataFactory.getLongitudeOffset (), 13);
      longitudeOffset.setToolTipText ("Data used in DTM.");
      longitudeOffset.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLongitudeOffset (longitudeOffset.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(longitudeOffset, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Longitude offset direction:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      longitudeOffsetDirection = new JTextField(dataFactory.getLongitudeOffsetDirection (), 13);
      longitudeOffsetDirection.setToolTipText ("Data used in DTM.");
      longitudeOffsetDirection.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLongitudeOffsetDirection (longitudeOffsetDirection.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(longitudeOffsetDirection, constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 6;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Altitude offset:"), constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      altitudeOffset = new JTextField(dataFactory.getAltitudeOffset (), 13);
      altitudeOffset.setToolTipText ("Data used in DTM.");
      altitudeOffset.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setAltitudeOffset (altitudeOffset.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(altitudeOffset, constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 7;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Reference datum code:"), constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      datumReferenceCode = new JTextField(dataFactory.getDatumReferenceCode (), 13);
      datumReferenceCode.setToolTipText ("Data used in DTM.");
      datumReferenceCode.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDatumReferenceCode (datumReferenceCode.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(datumReferenceCode, constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
           
      constraints.gridy = 8;
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
