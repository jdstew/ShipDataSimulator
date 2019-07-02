/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TimeAndDateDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the time and date manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class TimeAndDateDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField timeUTC;
   JTextField dateYear;
   JTextField dateMonth;
   JTextField dateDay;
   JTextField zoneHours; 
   JTextField zoneMinutes;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public TimeAndDateDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Time and date related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Time, in UTC:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      timeUTC = new JTextField(dataFactory.getTimeUTC (), 13);
      timeUTC.setToolTipText ("Data used in DTM, GLL, GGA, ZDA.");
      timeUTC.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setTimeUTC (timeUTC.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(timeUTC, constraints);  

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Year:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      dateYear = new JTextField(dataFactory.getDateYear (), 13);
      dateYear.setToolTipText ("Data used in DTM, GLL, GGA, ZDA.");
      dateYear.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDateYear (dateYear.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(dateYear, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Month:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      dateMonth = new JTextField(dataFactory.getDateMonth (), 13);
      dateMonth.setToolTipText ("Data used in DTM, GLL, GGA, ZDA.");
      dateMonth.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDateMonth (dateMonth.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(dateMonth, constraints);  

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Day:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      dateDay = new JTextField(dataFactory.getDateDay (), 13);
      dateDay.setToolTipText ("Data used in DTM, GLL, GGA, ZDA.");
      dateDay.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setDateDay (dateDay.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(dateDay, constraints);  

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Zone hours:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      zoneHours = new JTextField(dataFactory.getZoneHours (), 13);
      zoneHours.setToolTipText ("Data used in ZDA.");
      zoneHours.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setZoneHours (zoneHours.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(zoneHours, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Zone minutes:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      zoneMinutes = new JTextField(dataFactory.getZoneMinutes (), 13);
      zoneMinutes.setToolTipText ("Data used in ZDA.");
      zoneMinutes.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setZoneMinutes (zoneMinutes.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(zoneMinutes, constraints);  
      
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