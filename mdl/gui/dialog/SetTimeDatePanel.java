/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetTimeDatePanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
/**
 * This GUI provides access to setting the simulated time and date.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SetTimeDatePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	SpinnerNumberModel hourModel;
   SpinnerNumberModel minuteModel;
   SpinnerNumberModel secondModel;
   SpinnerNumberModel yearModel;
   SpinnerNumberModel monthModel;
   SpinnerNumberModel dayModel;
   
   JSpinner hourSpinner;
   JSpinner minuteSpinner;
   JSpinner secondSpinner;
   JSpinner yearSpinner;
   JSpinner monthSpinner;
   JSpinner daySpinner;
   
   JLabel sysTimeLabel;
                  
   JRadioButton systemTimeRadio;
   JRadioButton userTimeRadio;
   ButtonGroup timeButtonGroup;
   
   JButton okButton;
   JButton cancelButton;
   JDialog dialog;
   
   UpdateTime timeTrigger;
   Date ownshipOutputDate = new Date();
   
   /**
    * Initializes an object of this class.
    *
    * @param ownshipInputDate The current simulated time and date to manipulate.
    */
   public SetTimeDatePanel (Date ownshipInputDate) {

      setBorder(BorderFactory.createTitledBorder("Select and set ownship time and date values"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
            
      
       
      setLayout (new GridBagLayout());
          
      systemTimeRadio = new JRadioButton ("Use system value:", false);
      systemTimeRadio.setToolTipText ("Use system clock value for simulated UTC time.");
      userTimeRadio = new JRadioButton ("Use user setting:", true);
      userTimeRadio.setToolTipText ("Set the time value entered in this dialog window.");
      timeButtonGroup = new ButtonGroup(); 
      timeButtonGroup.add(systemTimeRadio);
      timeButtonGroup.add(userTimeRadio);
        
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel sourceLabel = new JLabel("Source");
      sourceLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(sourceLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 0;
      JLabel valueLabel = new JLabel("Value");
      valueLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      add(valueLabel, constraints);   
            
      constraints.gridx = 0;
      constraints.gridy = 1;
      add(systemTimeRadio, constraints); 

      constraints.gridx = 1;
      constraints.gridy = 1;
      constraints.gridwidth = 3;
      sysTimeLabel = new JLabel("0000-00-00 00:00:00");
      sysTimeLabel.setToolTipText ("Current system clock time.");
      add(sysTimeLabel, constraints); 
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      constraints.gridwidth = 1;
      add(new JLabel(" "), constraints);  
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      add(userTimeRadio, constraints); 

      constraints.gridx = 1;
      constraints.gridy = 4;
      add(new JLabel("Time:"), constraints);       

      constraints.gridx = 2;
      constraints.gridy = 3;
      add(new JLabel("Hour"), constraints);

      constraints.gridx = 3;
      constraints.gridy = 3;
      add(new JLabel("Minute"), constraints);
      
      constraints.gridx = 4;
      constraints.gridy = 3;
      add(new JLabel("Second"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 4;
      hourModel = new SpinnerNumberModel (12, 0, 23, 1);
      hourSpinner = new JSpinner(hourModel);
      add(hourSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 4;
      minuteModel = new SpinnerNumberModel (30, 0, 59, 1);
      minuteSpinner = new JSpinner(minuteModel);
      add(minuteSpinner, constraints);
      
      constraints.gridx = 4;
      constraints.gridy = 4;
      secondModel = new SpinnerNumberModel (0, 0, 59, 1);
      secondSpinner = new JSpinner(secondModel);
      add(secondSpinner, constraints);
            
      constraints.gridx = 0;
      constraints.gridy = 6;
      add(new JLabel(" "), constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 6;
      add(new JLabel("Date:"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 5;
      constraints.gridwidth = 2;
      add(new JLabel("Year"), constraints);
      
      constraints.gridx = 4;
      constraints.gridy = 5;
      constraints.gridwidth = 1;
      add(new JLabel("Month"), constraints);
      
      constraints.gridx = 5;
      constraints.gridy = 5;
      add(new JLabel("Day"), constraints);
          
      constraints.gridx = 2;
      constraints.gridy = 6;
      constraints.gridwidth = 2;
      yearModel = new SpinnerNumberModel (2004, 2000, 2010, 1);
      yearSpinner = new JSpinner(yearModel);
      add(yearSpinner, constraints);
      
      constraints.gridx = 4;
      constraints.gridy = 6;
      constraints.gridwidth = 1;
      monthModel = new SpinnerNumberModel (1, 1, 12, 1);
      monthSpinner = new JSpinner(monthModel);
      add(monthSpinner, constraints);
      
      constraints.gridx = 5;
      constraints.gridy = 6;
      dayModel = new SpinnerNumberModel (1, 1, 31, 1);
      daySpinner = new JSpinner(dayModel);
      add(daySpinner, constraints);
   
      constraints.gridx = 0;
      constraints.gridy = 7;
      add(new JLabel(" "), constraints);
      
      timeTrigger = new UpdateTime();
      
      okButton = new JButton ("Ok");
      okButton.setToolTipText ("Set simulator time and date based upon selection made.");
      okButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               getTimeDate();
               stopTimeUpdates();
               dialog.setVisible (false);
            }
         });
      
      cancelButton = new JButton ("Cancel");
      cancelButton.setToolTipText ("Cancel operation - do not change simulated time and date.");
      cancelButton.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               ownshipOutputDate = null;
               stopTimeUpdates();
               dialog.setVisible (false);
            }
         });
      
      constraints.gridx = 2;
      constraints.gridy = 8;
      add(okButton, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 8;
      add(cancelButton, constraints);
      
      if (ownshipInputDate != null) {
         Calendar calendar = new GregorianCalendar ();
         calendar.setTime (ownshipInputDate);
         
         hourSpinner.setValue(new Integer(calendar.get(Calendar.HOUR_OF_DAY)));
         minuteSpinner.setValue(new Integer(calendar.get(Calendar.MINUTE)));
         secondSpinner.setValue(new Integer(calendar.get(Calendar.SECOND)));
         yearSpinner.setValue(new Integer(calendar.get(Calendar.YEAR)));
         monthSpinner.setValue(new Integer(calendar.get(Calendar.MONTH)));
         daySpinner.setValue(new Integer(calendar.get(Calendar.DAY_OF_MONTH)));
      }
   }
   
   void getTimeDate () {
      Calendar calendar = new GregorianCalendar ();
      if (userTimeRadio.isSelected ()) {
         calendar.set (
            Integer.parseInt (yearSpinner.getValue().toString()),
            (Integer.parseInt (monthSpinner.getValue().toString()) - 1),
            Integer.parseInt (daySpinner.getValue().toString()),
            Integer.parseInt (hourSpinner.getValue().toString()),
            Integer.parseInt (minuteSpinner.getValue().toString()),
            Integer.parseInt (secondSpinner.getValue().toString()));
         ownshipOutputDate = calendar.getTime ();
      }
      else {
         calendar.setTimeInMillis (System.currentTimeMillis ());
         ownshipOutputDate = calendar.getTime ();
      }
   }
   
   void stopTimeUpdates() {
      timeTrigger.cancel ();
   }

   /**
    * Displays a dialog box of this panel.
    *
    * @param parentFrame The application frame for this application.
    * @return The time and date to set the simulator.
    */
   public Date showDialog (JFrame parentFrame) {
      
      dialog = new JDialog (parentFrame, "Set Ownship Time and Date", true);
      dialog.getContentPane().add(this);
      dialog.getRootPane().setDefaultButton (okButton);
      dialog.pack();

      Dimension screenSize =
         Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(screenSize.width/2 - (dialogSize.width/2),
                  screenSize.height/2 - (dialogSize.height/2));
 
      dialog.setVisible(true);
      return ownshipOutputDate;
   }

   private class UpdateTime extends TimerTask {

      public UpdateTime () {
         java.util.Timer thisTimer = new java.util.Timer();
         thisTimer.schedule (this, 0, 1000);
      }

      public void run () {
         sysTimeLabel.setText (DateFormat.getDateTimeInstance().format(
            new Date(System.currentTimeMillis ())));
      }    
   }
}
/*
 * Version history:
 * 1.3.0.1 - corrected parsing of set month (Calendar months are base-zero).
 */
