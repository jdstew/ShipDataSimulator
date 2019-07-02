/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OwnshipController.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.testSimulator;

import java.util.*;
/**
 * This class implements a controller object between the MDL GUI and the 
 * simulator and autopilot objects.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-17
 */
public class OwnshipController implements OwnshipSimulationListener {
   static final int USER_CONTROL = 1;
   static final int AUTOPILOT_CONTROL = -1;
     
   private Vector<OwnshipUpdateListener> ownshipUpdateListeners = new Vector<OwnshipUpdateListener>();
   
   int controlStatus = USER_CONTROL;
   
   OwnshipSimulator ownshipSimulator;
   Autopilot autopilot;
   
   /**
    * Creates an ownship controller, ownship simulator, and autopilot objects
    *
    * @param shipObj The simulated ship type.
    * @param dbgMgr The debug manager to make reports to.
    */
   public OwnshipController (AbstractShip shipObj) { 

      
      if (shipObj != null) {
         ownshipSimulator = new OwnshipSimulator(this, shipObj);
         autopilot = new Autopilot(this, shipObj);
      }
      else {
         AbstractShip defaultShip = new WPB110 ();
         ownshipSimulator = new OwnshipSimulator(this, defaultShip);
         autopilot = new Autopilot(this, defaultShip);
      }

      this.addOwnshipUpdateListener (autopilot);
   }
   
   /**
    * Starts the ownship simulator.
    */
   public void start() {
      ownshipSimulator.start ();
   }
   
   /**
    * Pauses the ownship simulator, which halts the simulator ship movement but
    * not time.
    */
   public void pause () {
      ownshipSimulator.pause ();
   }
   
   /**
    * Stops the ownship simulation, including simulation time.
    */
   public void stop () {
      ownshipSimulator.stop ();
   }
   




   /**
    * Sets the simulator's 'ordered' rudder, or helm.  Calling this method
    * causes the autopilot control to disengage.
    *
    * @param rudder the 'ordered' rudder.
    */
   public void setRudderHelm (double rudder) {
      ownshipSimulator.setRudderHelm (rudder);
      if (controlStatus == AUTOPILOT_CONTROL) {
//         ownshipSimulator.setSpeedThrottle(ownshipSimulatorPanel.getSpeedThrottle());
         controlStatus = USER_CONTROL;
      }
//      autopilotControlPanel.engageAutopilot(false);
      autopilot.engageAutopilot(false);
   }
   
   /**
    * Sets the simulator's 'ordered' speed, or throttle.  Calling this method
    * causes the autopilot control to disengage.
    *
    * @param throttle the 'ordered' speed
    */
   public void setSpeedThrottle (double throttle) {
      ownshipSimulator.setSpeedThrottle(throttle);
      if (controlStatus == AUTOPILOT_CONTROL) {
//         ownshipSimulator.setRudderHelm(ownshipSimulatorPanel.getRudderHelm());
         controlStatus = USER_CONTROL;
      }
//      autopilotControlPanel.engageAutopilot(false);
      autopilot.engageAutopilot(false);
   }
   
   /**
    * Turns the autopilot on or off.
    *
    * @param engaged 'true' turns the autopilot 'on'
    */
   public void engageAutopilot (boolean engaged) {
      autopilot.engageAutopilot(engaged);
      if (engaged) {
         controlStatus = AUTOPILOT_CONTROL;
      }
      else {
         controlStatus = USER_CONTROL;
//         ownshipSimulator.setRudderHelm(ownshipSimulatorPanel.getRudderHelm());
//         ownshipSimulator.setSpeedThrottle(ownshipSimulatorPanel.getSpeedThrottle());
      }
   }
   
   /** 
    * Sets the autopilot's mode (eg manual, pattern, route).
    *
    * @param value autopilot mode
    */
   public void setAutopilotMode (int value) {
      autopilot.setAutopilotMode(value);
   }

   /** 
    * Sets the autopilot's orderd course.
    *
    * @param value in degrees true (000-359)
    */
   public void setManualCourse (int value) {
      autopilot.setManualCourse(value);
   }
   
   /** 
    * Sets the autopilot's orderd speed, or throttle.
    *
    * @param value in knots.
    */
   public void setManualSpeed (int value) {
      autopilot.setManualSpeed(value);
   } 
   
   /** 
    * Sets the autopilot's pattern mode type (eg racetrack, sector, etc.)
    *
    * @param value pattern mode
    */
   public void setPatternType (int value) {
      autopilot.setPatternType(value);
   }
   
   /** 
    * Sets the autopilot's pattern mode initial course value.
    *
    * @param value in degrees true (000-359).
    */
   public void setPatternInitialCourse (int value) {
      autopilot.setPatternInitialCourse(value);
   }
   
   /** 
    * Sets the autopilot's pattern mode speed value.
    *
    * @param value speed in knots.
    */
   public void setPatternInitialSpeed (int value) {
      autopilot.setPatternInitialSpeed(value);
   }
   
   /** 
    * Sets the autopilot's leg length time for the pattern mode.
    *
    * @param value time in minutes for the initial leg length time.
    */
   public void setPatternLegTime (int value) {
      autopilot.setPatternLegTime(value);
   }
   
   /** 
    * Sets the autopilot's initial leg turn direction for the pattern mode.
    * 
    * @param value valid autopilot initil let turn direction value (left/right).
    */
   public void setPatternTurnDirection (int value) {
      autopilot.setPatternTurnDirection(value);
   }
   
   /** 
    * Sets the autopilot's orderd helm, or rudder angle.
    *
    * @param rudder positive(+) values are right, negative(-) values are left
    */
   public void setAutopilotRudder (double rudder) {
      ownshipSimulator.setRudderHelm (rudder);
      controlStatus = AUTOPILOT_CONTROL;
   }
   
   /** 
    * Sets the autopilot's ordered speed, or throttle.
    *
    * @param throttle value in knots.
    */
   public void setAutopilotSpeed (double throttle) {
      ownshipSimulator.setSpeedThrottle(throttle);
      controlStatus = AUTOPILOT_CONTROL;
   }
   
   /**
    * Add an ownship update listener object. 
    *
    * @param listener The listener to add.
    */   
   public synchronized void addOwnshipUpdateListener (OwnshipUpdateListener listener) {
      ownshipUpdateListeners.addElement(listener);
   }
   
   /**
    * Remove an ownship update listener object. 
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeOwnshipUpdateListener (OwnshipUpdateListener listener) {
      ownshipUpdateListeners.remove(listener);
   }
   
   /** 
    * Passes periodic ownship updates to the autopilot and GUI.  The update
    * is only passed to GUI objects if an ApplicationFrame has been provided.
    *
    * @param ownshipUpdate contains all of the current simulator ship values.
    */
   public void updateOwnship (OwnshipUpdate ownshipUpdate) {
      for (int i = 0; i < ownshipUpdateListeners.size(); i++) {
        OwnshipUpdateListener listener = (OwnshipUpdateListener) ownshipUpdateListeners.elementAt (i);
        listener.updateOwnship(ownshipUpdate);
      } 

//      if (ownshipPerformancePanel != null) {
//         ownshipSimulatorPanel.updateOwnship(ownshipUpdate);
//      } 

   }
   
   /** 
    * Gets a ownship update for use with autopilot and GUI controls.
    *
    * @return an ownship update with all of the current simulator values.
    */
   public OwnshipUpdate getOwnshipUpdate () {
      return ownshipSimulator.getOwnshipUpdate ();
   }
   
   /**
    *
    * Sets the ownship simulator's heading and speed.
    *
    * @param newHdgSpd The heading and speed to instantaneously set the simulator to.
    */
   public void setHeadingSpeed (HdgSpdData newHdgSpd) {
      ownshipSimulator.setHeadingSpeed (newHdgSpd);
   }
   
   /**
    *
    * Sets the ownship simulator's set and drift.
    *
    * @param newSetDrift The set and drift to instantaneously set the simulator to.
    */
   public void setSetDrift (SetDriftData newSetDrift) {
      ownshipSimulator.setSetDrift (newSetDrift);
   }
   
   /**
    *
    * Sets the ownship simulator's position
    *
    * @param newPosition The position to set the simulation at.
    */
   public void setPosition (PositionData newPosition) {
      ownshipSimulator.setPosition (newPosition);
   }
   
   /**
    *
    * Sets the ownship simulator's time and date.
    *
    * @param newTimeDate The time and date to set at the simulator.
    */
   public void setTimeDate (Date newTimeDate) {
      ownshipSimulator.setTimeDate (newTimeDate);
   } 
}
/*
 * Version history
 * 1.3.0.0 - Added set and drift functionality.
 */
