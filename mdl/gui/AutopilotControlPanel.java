/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: AutopilotControlPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import mdl.simulator.*;
import mdl.simulator.ships.*;
/**
 * An instance of AutopilotControlPanel creates the GUI for controlling a
 * vessel autopilot.  It is linked to the OwnshipController and ApplicationFrame
 * objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class AutopilotControlPanel extends JPanel {

	private static final long serialVersionUID = 9063884962580534364L;

	OwnshipController ownshipController;
   
   SpinnerNumberModel manualCseModel;
   SpinnerNumberModel manualSpdModel;
   SpinnerNumberModel patternCseModel;
   SpinnerNumberModel patternSpdModel;
   SpinnerNumberModel timeModel;
   
   JCheckBox autopilotEngagedBox;
   
   JSpinner manualCseSpinner;
   JSpinner manualSpdSpinner;
   
   JComboBox patternTypeBox;
   JSpinner patternCseSpinner;
   JSpinner patternSpdSpinner;
   JSpinner legTimeSpinner;
   JComboBox patternDirectionBox;
   
   JRadioButton manualRadio;
   JRadioButton patternRadio;
   JRadioButton routeRadio;
   ButtonGroup controlButtonGroup;
   
   /**
    * Creates an instance of the autopilot control GUI.
    *
    * @param controller The ownship controller for the autopilot GUI.
    */
   public AutopilotControlPanel (OwnshipController controller) {
      if (controller != null) {
         ownshipController = controller;
         ownshipController.setAutopilotControlPanel (this);
      }
      
      setBorder (BorderFactory.createTitledBorder ("Autopilot"));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      constraints.anchor = GridBagConstraints.WEST;
      
      setLayout (new GridBagLayout ());

      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.gridwidth = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      autopilotEngagedBox = new JCheckBox ("Autopilot [off]");
      autopilotEngagedBox.setToolTipText ("Autopilot on/off control and status indicator.");
      autopilotEngagedBox.addActionListener (new AutopilotEngageAction ());
      autopilotEngagedBox.setBackground (Color.red);
      add (autopilotEngagedBox, constraints);
      
      controlButtonGroup = new ButtonGroup ();
      manualRadio = new JRadioButton ("Manual", true);
      manualRadio.setToolTipText ("Set autopilot cse/spd using manual controls.");
      controlButtonGroup.add (manualRadio);
      patternRadio = new JRadioButton ("Pattern", false);
      patternRadio.setToolTipText ("Set autopilot to follow a specific time-based pattern.");
      controlButtonGroup.add (patternRadio);
      routeRadio = new JRadioButton ("Route", false);
      routeRadio.setToolTipText ("Set autopilot to follow a waypoint-based route.");
      controlButtonGroup.add (routeRadio);

      manualRadio.addActionListener (new ModeManualChange ());
      patternRadio.addActionListener (new ModePatternChange ());
      routeRadio.addActionListener (new ModeRouteChange ());
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      constraints.gridwidth = 1;
      constraints.anchor = GridBagConstraints.WEST;
      JLabel behaviorLabel = new JLabel ("Behavior");
      behaviorLabel.setBorder (BorderFactory.createMatteBorder (0, 0, 1, 0, Color.black));
      add (behaviorLabel, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 1;
      constraints.anchor = GridBagConstraints.EAST;
      JLabel criteriaLabel = new JLabel ("Criteria");
      criteriaLabel.setBorder (BorderFactory.createMatteBorder (0, 0, 1, 0, Color.black));
      add (criteriaLabel, constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 1;
      constraints.anchor = GridBagConstraints.WEST;
      JLabel valueLabel = new JLabel ("Value");
      valueLabel.setBorder (BorderFactory.createMatteBorder (0, 0, 1, 0, Color.black));
      add (valueLabel, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 1;
      JLabel unitsLabel = new JLabel ("Units");
      unitsLabel.setBorder (BorderFactory.createMatteBorder (0, 0, 1, 0, Color.black));
      add (unitsLabel, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      constraints.gridwidth = 1;
      constraints.fill = GridBagConstraints.NONE;
      add (manualRadio, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 2;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("course:"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 2;
      constraints.anchor = GridBagConstraints.WEST;
      manualCseModel = new SpinnerNumberModel (0, 0, 359, 1);
      manualCseSpinner = new JSpinner (manualCseModel);
      manualCseSpinner.setToolTipText ("Autopilot course spin slector.");
      manualCseSpinner.addChangeListener (new ManualCseAction ());
      add (manualCseSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 2;
      add (new JLabel ("degrees (T)"), constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 3;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("speed:"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 3;
      constraints.anchor = GridBagConstraints.WEST;
      manualSpdModel = new SpinnerNumberModel (10, 0, WPB110.SPEED_AHEAD_MAX, 1);
      manualSpdSpinner = new JSpinner (manualSpdModel);
      manualSpdSpinner.setToolTipText ("Autopilot speed spin selector.");
      manualSpdSpinner.addChangeListener (new ManualSpdAction ());
      add (manualSpdSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 3;
      add (new JLabel ("knots"), constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 4;
      add (new JLabel (" "), constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 5;
      add (patternRadio, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 5;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("type:"), constraints);
      
      
      constraints.gridx = 2;
      constraints.gridy = 5;
      constraints.gridwidth = 2;
      constraints.anchor = GridBagConstraints.WEST;
      patternTypeBox = new JComboBox ();
      patternTypeBox.setToolTipText ("Autopilot pattern type pull-down selector.");
      patternTypeBox.addActionListener (new AutopilotPatternTypeAction ());
      patternTypeBox.addItem ("Racetrack");             // index = 0
      patternTypeBox.addItem ("Triangle");              // index = 1
      patternTypeBox.addItem ("Square");                // index = 2
      patternTypeBox.addItem ("Sector (VS)");           // index = 3
      patternTypeBox.addItem ("Expanding square (SS)"); // index = 4
      add (patternTypeBox, constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 6;
      constraints.gridwidth = 1;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("initial course:"), constraints);
      
      
      constraints.gridx = 2;
      constraints.gridy = 6;
      constraints.anchor = GridBagConstraints.WEST;
      patternCseModel = new SpinnerNumberModel (0, 0, 359, 1);
      patternCseSpinner = new JSpinner (patternCseModel);
      patternCseSpinner.setToolTipText ("Autopilot pattern initial course spin selector.");
      patternCseSpinner.addChangeListener (new PatternCseAction ());
      add (patternCseSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 6;
      add (new JLabel ("degrees (T)"), constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 7;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("initial speed:"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 7;
      constraints.anchor = GridBagConstraints.WEST;
      patternSpdModel = new SpinnerNumberModel (10, 0, WPB110.SPEED_AHEAD_MAX, 1);
      patternSpdSpinner = new JSpinner (patternSpdModel);
      patternSpdSpinner.setToolTipText ("Autopilot pattern speed spin selector.");
      patternSpdSpinner.addChangeListener (new PatternSpdAction ());
      add (patternSpdSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 7;
      add (new JLabel ("knots"), constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 8;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("leg time:"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 8;
      constraints.anchor = GridBagConstraints.WEST;
      timeModel = new SpinnerNumberModel (3, 3, 60, 3);
      legTimeSpinner = new JSpinner (timeModel);
      legTimeSpinner.setToolTipText ("Autopilot pattern initial leg time length.");
      legTimeSpinner.addChangeListener (new PatternLegTimeAction ());
      add (legTimeSpinner, constraints);
      
      constraints.gridx = 3;
      constraints.gridy = 8;
      add (new JLabel ("minutes"), constraints);
      
      constraints.gridx = 1;
      constraints.gridy = 9;
      constraints.anchor = GridBagConstraints.EAST;
      add (new JLabel ("turn direction:"), constraints);
      
      constraints.gridx = 2;
      constraints.gridy = 9;
      constraints.anchor = GridBagConstraints.WEST;
      patternDirectionBox = new JComboBox ();
      patternDirectionBox.setToolTipText ("Autopilot pattern turn direction pull-down selector.");
      patternDirectionBox.addActionListener (new AutopilotPatternTurnAction ());
      patternDirectionBox.addItem ("Left");  // index = 0
      patternDirectionBox.addItem ("Right"); // index = 1
      patternDirectionBox.setSelectedIndex (1);
      add (patternDirectionBox, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 10;
      add (new JLabel (" "), constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 11;
      add (routeRadio, constraints);
      routeRadio.setEnabled (false);
   }
   
   /**
    * Sets the ownship controller for the autopilot.
    *
    * @param obj The OwnshipController for the autopilot.
    */
   public void setOwnshipController (OwnshipController obj) {
      ownshipController = obj;
   }
   
   private class AutopilotEngageAction implements ActionListener {
      public void actionPerformed (ActionEvent event) {
         if (autopilotEngagedBox.isSelected ()) {
            autopilotEngagedBox.setText ("Autopilot [on]");
            autopilotEngagedBox.setBackground (Color.green);
            if (ownshipController != null) {
               ownshipController.engageAutopilot (true);
               patternRadio.setEnabled (false);
               patternTypeBox.setEnabled (false);
               patternCseSpinner.setEnabled (false);
               patternSpdSpinner.setEnabled (false);
               legTimeSpinner.setEnabled (false);
               patternDirectionBox.setEnabled (false);
               routeRadio.setEnabled (false);
            }
         }
         else {
            autopilotEngagedBox.setText ("Autopilot [off]");
            autopilotEngagedBox.setBackground (Color.red);
            if (ownshipController != null) {
               ownshipController.engageAutopilot (false);
               patternRadio.setEnabled (true);
               patternTypeBox.setEnabled (true);
               patternCseSpinner.setEnabled (true);
               patternSpdSpinner.setEnabled (true);
               legTimeSpinner.setEnabled (true);
               patternDirectionBox.setEnabled (true);
               routeRadio.setEnabled (false);
            }
         }
      }
   }
   
   private class ModeManualChange implements ActionListener {
      public void actionPerformed (ActionEvent event) {
         if (ownshipController != null) {
            ownshipController.setAutopilotMode (Autopilot.MANUAL_MODE);
         }
      }
   }
   
   private class ModePatternChange implements ActionListener {
      public void actionPerformed (ActionEvent event) {
         if (ownshipController != null) {
            ownshipController.setAutopilotMode (Autopilot.PATTERN_MODE);
         }
      }
   }
   
   private class ModeRouteChange implements ActionListener {
      public void actionPerformed (ActionEvent event) {
         if (ownshipController != null) {
            ownshipController.setAutopilotMode (Autopilot.ROUTE_MODE);
         }
      }
   }
   
   private class ManualCseAction implements ChangeListener {
      public void stateChanged (ChangeEvent event) {
         JSpinner source = (JSpinner) event.getSource ();
         if (ownshipController != null) {
            ownshipController.setManualCourse (
            Integer.parseInt (source.getValue ().toString ()));
         }
      }
   }
   
   private class ManualSpdAction implements ChangeListener {
      public void stateChanged (ChangeEvent event) {
         JSpinner source = (JSpinner) event.getSource ();
         if (ownshipController != null) {
            ownshipController.setManualSpeed (
            Integer.parseInt (source.getValue ().toString ()));
         }
      }
   }
   
   private class AutopilotPatternTypeAction implements ActionListener {
      public void actionPerformed (ActionEvent event) {
         if (ownshipController != null) {
            ownshipController.setPatternType (patternTypeBox.getSelectedIndex ());
         }
      }
   }
   
   private class PatternCseAction implements ChangeListener {
      public void stateChanged (ChangeEvent event) {
         JSpinner source = (JSpinner) event.getSource ();
         if (ownshipController != null) {
            ownshipController.setPatternInitialCourse (
            Integer.parseInt (source.getValue ().toString ()));
         }
      }
   }
   
   private class PatternSpdAction implements ChangeListener {
      public void stateChanged (ChangeEvent event) {
         JSpinner source = (JSpinner) event.getSource ();
         if (ownshipController != null) {
            ownshipController.setPatternInitialSpeed (
            Integer.parseInt (source.getValue ().toString ()));
         }
      }
   }
   
   private class PatternLegTimeAction implements ChangeListener {
      public void stateChanged (ChangeEvent event) {
         JSpinner source = (JSpinner) event.getSource ();
         if (ownshipController != null) {
            ownshipController.setPatternLegTime (
            Integer.parseInt (source.getValue ().toString ()));
         }
      }
   }
   
   private class AutopilotPatternTurnAction implements ActionListener {
      public void actionPerformed (ActionEvent event) {
         if (ownshipController != null) {
            ownshipController.setPatternTurnDirection (patternDirectionBox.getSelectedIndex ());
         }
      }
   }
   
   /**
    * This method is called when an external control engages the autopilot.
    *
    * @param engaged 'True' means the autopilot is 'on'.
    */
   public void engageAutopilot (boolean engaged) {
      autopilotEngagedBox.setSelected (engaged);
      
      //duplicate code from action listener...
      if (autopilotEngagedBox.isSelected ()) {
         autopilotEngagedBox.setText ("Autopilot [on]");
         autopilotEngagedBox.setBackground (Color.green);
         if (ownshipController != null) {
            ownshipController.engageAutopilot (true);
            patternRadio.setEnabled (false);
            patternTypeBox.setEnabled (false);
            patternCseSpinner.setEnabled (false);
            patternSpdSpinner.setEnabled (false);
            legTimeSpinner.setEnabled (false);
            patternDirectionBox.setEnabled (false);
            routeRadio.setEnabled (false);
         }
      }
      else {
         autopilotEngagedBox.setText ("Autopilot [off]");
         autopilotEngagedBox.setBackground (Color.red);
         if (ownshipController != null) {
            ownshipController.engageAutopilot (false);
            patternRadio.setEnabled (true);
            patternTypeBox.setEnabled (true);
            patternCseSpinner.setEnabled (true);
            patternSpdSpinner.setEnabled (true);
            legTimeSpinner.setEnabled (true);
            patternDirectionBox.setEnabled (true);
            routeRadio.setEnabled (false);
         }
      }
   }
}
