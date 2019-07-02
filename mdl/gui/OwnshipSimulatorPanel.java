/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OwnshipSimulatorPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;
import mdl.simulator.*;
import mdl.simulator.ships.*;
import mdl.data.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-17
 */
public class OwnshipSimulatorPanel extends JPanel implements OwnshipUpdateListener {

	private static final long serialVersionUID = 6964078896118078232L;

	OwnshipController ownshipController;
   
   DecimalFormat twoDigitForm = new DecimalFormat("00");
   DecimalFormat threeDigitForm = new DecimalFormat("000");
   DecimalFormat minuteForm = new DecimalFormat("00.0000");
   DecimalFormat headingForm = new DecimalFormat("000.0");
   DecimalFormat speedForm = new DecimalFormat("#0.0");
   Calendar calendarObj = new GregorianCalendar();
      
   boolean autopilotEngaged = false;
   
   double rudderHelm       = 0.0;
   double rudderActual     = 0.0;
   
   double headingAutopilot = 0.0;
   double headingActual    = 0.0;
   double headingOverGround    = 0.0;
   
   double speedAutopilot   = 0.0;
   double speedThrottle    = 0.0;
   double speedActual      = 0.0;
   double speedOverGround = 0.0;
   
   double set = 0.0;
   double drift = 0.0;
   
   char latNS;
   char lonEW;
   
   JLabel timeLabel;
   JLabel dateLabel;
   JLabel latitudeLabel;
   JLabel longitudeLabel;
   JLabel headingLabel;
   JLabel speedLabel;
   JLabel cogLabel;
   JLabel sogLabel;
   JLabel setLabel;
   JLabel driftLabel;
   JLabel rotLabel;
   CompassPanel compassPanel;
   JProgressBar speedActualBarFwd;
   JProgressBar speedActualBarAft;
   
   JSlider speedThrottleSlider; 
   
   JProgressBar rudderActualBarStbd;
   JProgressBar rudderActualBarPort;
   
   JSlider rudderHelmSlider;
   
   /**
    * Creates and displays the ownship simulator panel.
    *
    * @param controller The ownship controller for this GUI panel.
    */
   public OwnshipSimulatorPanel (OwnshipController controller) {
      if (controller != null) {
         ownshipController = controller;
         ownshipController.setOwnshipSimulatorPanel (this);
      }
      
      setBorder(BorderFactory.createTitledBorder("Ownship"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
            
      setLayout (new GridBagLayout());
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      
      add(new JLabel("Hdg:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 0;
      headingLabel = new JLabel("###.#T");
      headingLabel.setToolTipText ("Simulated heading, in degrees True.");
      add(headingLabel, constraints);
 
      constraints.gridx = 0;
      constraints.gridy = 1;
      add(new JLabel("Spd:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 1;
      speedLabel = new JLabel("##.#KTS");
      speedLabel.setToolTipText ("Simulated speed, in knots.");
      add(speedLabel, constraints);

      constraints.gridx = 2;
      constraints.gridy = 0;
      add(new JLabel("Lat:"), constraints); 

      constraints.gridx = 3;
      constraints.gridy = 0;
      latitudeLabel = new JLabel("##-##.####_");
      latitudeLabel.setToolTipText ("Simulated latitude, in degrees and minutes.");
      add(latitudeLabel, constraints);

      constraints.gridx = 2;
      constraints.gridy = 1;
      add(new JLabel("Lon:"), constraints); 

      constraints.gridx = 3;
      constraints.gridy = 1;
      longitudeLabel = new JLabel("###-##.####_");
      longitudeLabel.setToolTipText ("Simulated longitude, in degrees and minutes.");
      add(longitudeLabel, constraints);

      constraints.gridx = 4;
      constraints.gridy = 0;
      add(new JLabel("Time:"), constraints); 

      constraints.gridx = 5;
      constraints.gridy = 0;
      timeLabel = new JLabel("##:##:##");
      timeLabel.setToolTipText ("Simulator time, in UTC.");
      add(timeLabel, constraints);

      constraints.gridx = 4;
      constraints.gridy = 1;
      dateLabel = new JLabel("####-##-##");
      dateLabel.setToolTipText ("Simulator date, in UTC.");
      add(new JLabel("Date:"), constraints); 

      constraints.gridx = 5;
      constraints.gridy = 1;
      add(dateLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      constraints.gridwidth = 4;
      constraints.anchor = GridBagConstraints.CENTER;
      compassPanel = new CompassPanel();
      compassPanel.setToolTipText ("Simulated compass heading card.");
      add(compassPanel, constraints);
      
      Color panelColor = (Color) UIManager.get ("Panel.background");
      
      constraints.gridx = 4;
      constraints.gridy = 2;
      constraints.gridwidth = 1;
      constraints.anchor = GridBagConstraints.EAST;
      Box speedBarBox = new Box(BoxLayout.Y_AXIS);
      speedActualBarFwd = new JProgressBar(JProgressBar.VERTICAL, 0, 154);
      speedActualBarFwd.setToolTipText ("Forward speed indicator.");
      speedActualBarFwd.setValue (5);
      speedActualBarFwd.setPreferredSize (new Dimension(20, 154));
      speedActualBarFwd.setMaximumSize (new Dimension(20, 154));
      speedActualBarFwd.setForeground(new Color(0, 0, 192));
      speedActualBarFwd.setBackground(panelColor);
      
      speedActualBarAft = new JProgressBar(JProgressBar.VERTICAL, -26, 0);
      speedActualBarAft.setToolTipText ("Aft speed indicator.");
      speedActualBarAft.setValue (-2);
      speedActualBarAft.setPreferredSize (new Dimension(20, 26));
      speedActualBarAft.setMaximumSize (new Dimension(20, 26));
      speedActualBarAft.setForeground(panelColor);
      speedActualBarAft.setBackground(Color.WHITE);
      speedBarBox.add(speedActualBarFwd);
      speedBarBox.add(speedActualBarAft);
      add(speedBarBox, constraints);
      
      constraints.gridx = 5;
      constraints.gridy = 2;
      constraints.anchor = GridBagConstraints.WEST;
      constraints.gridwidth = GridBagConstraints.REMAINDER;
      speedThrottleSlider = new JSlider (SwingConstants.VERTICAL, 
         -WPB110.SPEED_ASTERN_MAX, WPB110.SPEED_AHEAD_MAX, 0);
      speedThrottleSlider.setToolTipText ("Simulated manual throttle control.");
      speedThrottleSlider.setMinorTickSpacing (1);
      speedThrottleSlider.setMajorTickSpacing (5);
      speedThrottleSlider.setPaintTicks (true);
      speedThrottleSlider.setPaintLabels (true);
      speedThrottleSlider.setPreferredSize (new Dimension(60, 180));
      speedThrottleSlider.setMaximumSize (new Dimension(60, 180));
      add(speedThrottleSlider, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 3;
      constraints.gridwidth = 4;
      constraints.anchor = GridBagConstraints.CENTER;
      Box rudderActualBox = new Box(BoxLayout.X_AXIS);
      rudderActualBox.add(new JLabel("L "));
      
      rudderActualBarStbd = new JProgressBar(JProgressBar.HORIZONTAL, 0, WPB110.RUDDER_ANGLE_MAX);
      rudderActualBarStbd.setToolTipText ("Starboard rudder indicator.");
      rudderActualBarStbd.setValue (5);
      rudderActualBarStbd.setPreferredSize (new Dimension(90, 20));
      rudderActualBarStbd.setMaximumSize (new Dimension(90, 20));
      rudderActualBarStbd.setForeground(new Color(0, 192, 0));
      rudderActualBarStbd.setBackground(panelColor);
      
      rudderActualBarPort = new JProgressBar(JProgressBar.HORIZONTAL, -WPB110.RUDDER_ANGLE_MAX, 0);
      rudderActualBarPort.setToolTipText ("Port rudder indicator.");
      rudderActualBarPort.setValue (-2);
      rudderActualBarPort.setPreferredSize (new Dimension(90, 20));
      rudderActualBarPort.setMaximumSize (new Dimension(90, 20));
      rudderActualBarPort.setForeground(panelColor);
      rudderActualBarPort.setBackground(new Color(192, 0, 0));
      rudderActualBox.add(rudderActualBarPort);
      rudderActualBox.add(rudderActualBarStbd);
      rudderActualBox.add(new JLabel(" R"));
      rudderActualBox.add(Box.createGlue ());
      add(rudderActualBox, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 4;
      constraints.gridwidth = 4;
      Box rudderHelmBox = new Box(BoxLayout.X_AXIS);
      
      rudderHelmSlider = new JSlider (SwingConstants.HORIZONTAL, 
         -WPB110.RUDDER_ANGLE_MAX, WPB110.RUDDER_ANGLE_MAX, 0);
      rudderHelmSlider.setToolTipText ("Simulate manual helm control.");
      rudderHelmSlider.setSnapToTicks (false);
      rudderHelmSlider.setMinorTickSpacing (2);
      rudderHelmSlider.setMajorTickSpacing (10);
      rudderHelmSlider.setPaintTicks (true);
      rudderHelmSlider.setPaintLabels (true);
      rudderHelmSlider.setPreferredSize (new Dimension(200, 60));
      rudderHelmSlider.setMaximumSize (new Dimension(200, 60));
      rudderHelmBox.add(rudderHelmSlider);
      rudderHelmBox.add(Box.createGlue ());
      add(rudderHelmBox, constraints);
      
      speedThrottleSlider.addChangeListener (new SpeedThrottleAction());
      rudderHelmSlider.addChangeListener (new RudderHelmAction());
      

      constraints.gridx = 0;
      constraints.gridy = 5;
      constraints.gridwidth = 1;
      constraints.anchor = GridBagConstraints.WEST;
      constraints.insets = new Insets (2, 3, 2, 3);
      
      add(new JLabel("Set:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 5;
      setLabel = new JLabel("###.#T");
      setLabel.setToolTipText ("Simulated set, in degrees True.");
      add(setLabel, constraints);
 
      constraints.gridx = 0;
      constraints.gridy = 6;
      add(new JLabel("Drift:"), constraints); 

      constraints.gridx = 1;
      constraints.gridy = 6;
      driftLabel = new JLabel("##.#KTS");
      driftLabel.setToolTipText ("Simulated drift, in knots.");
      add(driftLabel, constraints);
      

      constraints.gridx = 2;
      constraints.gridy = 5;
      constraints.insets = new Insets (2, 3, 2, 3);
      
      add(new JLabel("COG:"), constraints); 

      constraints.gridx = 3;
      constraints.gridy = 5;
      cogLabel = new JLabel("###.#T");
      cogLabel.setToolTipText ("Simulated course over ground, in degrees True.");
      add(cogLabel, constraints);
 
      constraints.gridx = 2;
      constraints.gridy = 6;
      add(new JLabel("SOG:"), constraints); 

      constraints.gridx = 3;
      constraints.gridy = 6;
      sogLabel = new JLabel("##.#KTS");
      sogLabel.setToolTipText ("Simulated speed over ground, in knots.");
      add(sogLabel, constraints);
      
      constraints.gridx = 4;
      constraints.gridy = 5;
      add(new JLabel("ROT:"), constraints); 

      constraints.gridx = 5;
      constraints.gridy = 5;
      rotLabel = new JLabel("##.#DPM");
      rotLabel.setToolTipText ("Simulated rate of turn, in degrees per minute.");
      add(rotLabel, constraints);
   }
   
   /**
    * Sets the ownship controller to the GUI.
    *
    * @param obj An ownship controller object reference.
    */
   public void setOwnshipController (OwnshipController obj) {
      if (obj != null) {
         ownshipController = obj;
      } 
   }
   
   /**
    * This method receives an ownship update from the set ownship controller.
    * @param ownshipUpdate The updated data from the ownship simulator.
    */
   public void updateOwnship (OwnshipUpdate ownshipUpdate) {
      
      calendarObj.setTime (ownshipUpdate.timeDate);
      timeLabel.setText (
         twoDigitForm.format (calendarObj.get(Calendar.HOUR_OF_DAY)) + ":" +
         twoDigitForm.format (calendarObj.get(Calendar.MINUTE)) + ":" +
         twoDigitForm.format (calendarObj.get(Calendar.SECOND)));
      
      dateLabel.setText ("" + calendarObj.get(Calendar.YEAR) + "-" +
         twoDigitForm.format ((calendarObj.get(Calendar.MONTH) + 1)) + "-" +
         twoDigitForm.format (calendarObj.get(Calendar.DAY_OF_MONTH)));
      
      if (ownshipUpdate.latitude > 0.0) {
         latNS = 'N';
      }
      else {
         latNS = 'S';
      }
      if (ownshipUpdate.longitude > 0.0) {
         lonEW = 'E';
      }
      else {
         lonEW = 'W';
      }
      latitudeLabel.setText ("" +
         twoDigitForm.format ((int)Math.abs(ownshipUpdate.latitude)) + "-" +
         minuteForm.format (Math.abs(ownshipUpdate.latitude % 1.0) * 60.0) + latNS);
      
      longitudeLabel.setText ("" + 
         threeDigitForm.format ((int)Math.abs(ownshipUpdate.longitude)) + "-" +
         minuteForm.format (Math.abs(ownshipUpdate.longitude % 1.0) * 60.0) + lonEW);
      
      headingLabel.setText (headingForm.format(ownshipUpdate.headingActual) + "T");
      speedLabel.setText (speedForm.format(ownshipUpdate.speedActual) + "KTS");
 
      // Update actual rudder position progress bars...
      if (ownshipUpdate.rudderActual > 0.0) {
         rudderActualBarStbd.setValue ((int)Math.round (ownshipUpdate.rudderActual));
         rudderActualBarPort.setValue (0);
      }
      else { // Rudder is left of center
         rudderActualBarStbd.setValue (0);
         rudderActualBarPort.setValue ((int)Math.round (ownshipUpdate.rudderActual));
      }    
      
      // Update actual speed progress bars...
      if (ownshipUpdate.speedActual > 0.0) {
         speedActualBarFwd.setValue ((int)Math.round 
            (ownshipUpdate.speedActual * (154/WPB110.SPEED_AHEAD_MAX)));
         speedActualBarAft.setValue (0);
      }
      else { // Rudder is left of center
         speedActualBarFwd.setValue (0);
         speedActualBarAft.setValue ((int)Math.round 
            (ownshipUpdate.speedActual * (26/WPB110.SPEED_ASTERN_MAX)));
      }
      
      setLabel.setText (headingForm.format(ownshipUpdate.set) + "T");
      driftLabel.setText (speedForm.format(ownshipUpdate.drift) + "KTS");
      
      cogLabel.setText (headingForm.format(ownshipUpdate.headingOverGround) + "T");
      sogLabel.setText (speedForm.format(ownshipUpdate.speedOverGround) + "KTS");
      
      rotLabel.setText (speedForm.format(ownshipUpdate.headingVelocity) + "DPM");
      
      compassPanel.setHeading(ownshipUpdate.headingActual);
   }
   
   private class RudderHelmAction implements ChangeListener {
      
      public RudderHelmAction () {
      }

      public void stateChanged (ChangeEvent event) {
         JSlider source = (JSlider) event.getSource ();
         if (ownshipController != null) {
            ownshipController.setRudderHelm ((double) source.getValue());
         }
      }    
   }
   
   private class SpeedThrottleAction implements ChangeListener {
      
      public SpeedThrottleAction () {
      }

      public void stateChanged (ChangeEvent event) {
         JSlider source = (JSlider) event.getSource ();
         if (ownshipController != null) {          
            ownshipController.setSpeedThrottle ((double) source.getValue());
         }
      }    
   }
   
   /** 
    * This method is called by other GUI objects just prior to setting new
    * helm values.
    *
    * @return Rudder angle in degrees.
    */
   public double getRudderHelm () {
      return (double) rudderHelmSlider.getValue();
   }
   
   /** 
    * This method is called by other GUI objects just prior to setting new
    * throttle values.
    *
    * @return Throttle position in knots.
    */
   public double getSpeedThrottle () {
      return (double) speedThrottleSlider.getValue();
   }
}
/* 
 * Version history
 * 1.3.0.0 - Added set and drift, COG and SOG, and rate of turn labels.
 */
