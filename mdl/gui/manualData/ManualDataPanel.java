/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ManualDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for access to all of the manual data (tabs).
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ManualDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JButton resetDataButton;
   JTabbedPane manualDataPane;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public ManualDataPanel (DataFactory data) {
      dataFactory = data;
      
      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      
      Box controlBox = Box.createHorizontalBox ();
      
      resetDataButton = new JButton ("Reset Manual Data");
      resetDataButton.setToolTipText ("Reset all manual data to default settings.");
      resetDataButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               dataFactory.resetDefaults ();
               remove (manualDataPane);
               manualDataPane = getResetManualData ();
               
               add (manualDataPane);
               validate ();
            }
         });
      controlBox.add(resetDataButton);
      controlBox.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(controlBox);
      
      manualDataPane = getResetManualData ();
      add(manualDataPane);
   }
   
   private JTabbedPane getResetManualData () {
      JTabbedPane tabbedPane = new JTabbedPane();
      tabbedPane.setAlignmentX (Component.LEFT_ALIGNMENT);
      tabbedPane.addTab ("Time & Date", null, new TimeAndDateDataPanel (dataFactory),
         "Time and date related data.");
      tabbedPane.addTab ("Position", null, new PositionDataPanel (dataFactory),
         "Position related data.");
      tabbedPane.addTab ("GPS/GLONASS", null, new GPSDataPanel (dataFactory),
         "GPS specific related data.");
      tabbedPane.addTab ("LORAN-C", null, new LORANDataPanel (dataFactory),
         "LORAN-C related data.");
      tabbedPane.addTab ("Heading", null, new HeadingDataPanel (dataFactory),
         "Heading related data (including magnetic information).");
      tabbedPane.addTab ("Speed", null, new SpeedDataPanel (dataFactory),
         "Velocity related data.");
      tabbedPane.addTab ("Set & Drift", null, new SetAndDriftDataPanel (dataFactory),
         "Set and drift related data.");
      tabbedPane.addTab ("Depth", null, new DepthDataPanel (dataFactory),
         "Depth related data.");
      tabbedPane.addTab ("Datum", null, new DatumDataPanel (dataFactory),
         "Datum related data.");
      tabbedPane.addTab ("Envirnoment", null, new EnvironmentDataPanel (dataFactory),
         "Environment related data (e.g. wind).");
      tabbedPane.addTab ("Misc", null, new MiscDataPanel (dataFactory),
         "Manually entered and miscellaneous data (including OSS).");
      return tabbedPane;
   }
}
