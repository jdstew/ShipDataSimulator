/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: GPSDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the GPS manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class GPSDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField GPS_qualityID; // GPS quality indicator  
   JTextField GPS_noOfSatellites; // Number of satellites used, (w/o leading zeros)
   JTextField GPS_HDOP; // Horizontal Dilution of Precision 
   JTextField GPS_altitude; // Altitude, meters (may be negative)
   JTextField GPS_geoidalSep; // Geoidal separation, meters 
   JTextField GPS_ageOfDGPS; // Age of DGPS data, seconds (null if not used) 
   JTextField GPSD_GPSStation; // DGPS reference station ID, (w/o leading zeros) 
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public GPSDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("GPS/GLONAS related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Quality id:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPS_qualityID = new JTextField(dataFactory.getGPSQualityId (), 13);
      GPS_qualityID.setToolTipText ("Data used in GGA, GNS.");
      GPS_qualityID.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSQualityId (GPS_qualityID.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPS_qualityID, constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Number of satellites:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPS_noOfSatellites = new JTextField(dataFactory.getGPSNoOfSatellites (), 13);
      GPS_noOfSatellites.setToolTipText ("Data used in GGA, GNS.");
      GPS_noOfSatellites.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSNoOfSatellites (GPS_noOfSatellites.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPS_noOfSatellites, constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("HDOP:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPS_HDOP = new JTextField(dataFactory.getGPSHDOP (), 13);
      GPS_HDOP.setToolTipText ("Data used in GGA, GNS.");
      GPS_HDOP.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSHDOP (GPS_HDOP.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPS_HDOP, constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Antenna altitude:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPS_altitude = new JTextField(dataFactory.getGPSAltitude (), 13);
      GPS_altitude.setToolTipText ("Data used in GGA, GNS.");
      GPS_altitude.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSAltitude (GPS_altitude.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPS_altitude, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Geoidal separation:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPS_geoidalSep = new JTextField(dataFactory.getGPSGeoidalSeparation (), 13);
      GPS_geoidalSep.setToolTipText ("Data used in GGA, GNS.");
      GPS_geoidalSep.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSGeoidalSeparation (GPS_geoidalSep.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPS_geoidalSep, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Age of differential corrections:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPS_ageOfDGPS = new JTextField(dataFactory.getGPSAgeOfDGPS (), 13);
      GPS_ageOfDGPS.setToolTipText ("Data used in GGA, GNS.");
      GPS_ageOfDGPS.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSAgeOfDGPS (GPS_ageOfDGPS.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPS_ageOfDGPS, constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 6;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Differential station:"), constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      GPSD_GPSStation = new JTextField(dataFactory.getGPSDifferentialStation (), 13);
      GPSD_GPSStation.setToolTipText ("Data used in GGA, GNS.");
      GPSD_GPSStation.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setGPSDifferentialStation (GPSD_GPSStation.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(GPSD_GPSStation, constraints);  

      constraints.gridy = 6;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
           
      constraints.gridy = 7;
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
