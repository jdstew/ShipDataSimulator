/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OwnshipSimulator.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.simulator;

import java.util.*;
import mdl.data.*;
import mdl.debug.*;
import mdl.simulator.ships.*;
/**
 * This class implements a ship simulator object for the MDL application.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-17
 */
public class OwnshipSimulator extends TimerTask {
   static final int SIMULATION_PLAYING = 0;
   static final int SIMULATION_PAUSED = 2;
   static final int SIMULATION_STOPPED = 4;
   static final long DEFAULT_TIME_INCREMENT = 100; // in milliseconds
   static final double QTR_PI = 0.78539816339744830961566084581988;
   static final double     PI = 3.1415926535897932384626433832795;
   static final double TWO_PI = 6.283185307179586476925286766559;
   static final double DEG_TO_RAD = 0.017453292519943295769236907684886;
   static final double SPEED_TO_RAD = 8.0802280184922665598319017059658e-8;
   static final double RUDDER_MAX = 90.0;
   static final double SPEED_MAX = 50.0;
   static final double SPEED_MIN = -10.0;
   static final double DRIFT_MAX = 6.0;
   
   DebugManager debugManager;
   
   AbstractShip simulatedShip;
   
   Timer simulationTimer = new Timer();
   long simulationStep = 0;
   int simulationStatus = SIMULATION_STOPPED;
   
   long simulationStopTime = 0;
   long simulationTimeOffset = 0;
   long simulationTimeIncrement = DEFAULT_TIME_INCREMENT;
   
   double rudderMaximum; // in degrees
   double speedMaximumAhead; // in knots
   double speedMaximumAstern; // in knots

   // negative (-) rudder is left, positive (+) rudder is right
   double rudderHelm = 0.0; // Range: {-rudderMaximum, rudderMaximum}
   double rudderActual = 0.0; // Range: {-rudderMaximum, rudderMaximum}
   double rudderAcceleration; // degrees/second
   
   // Note: heading can not equal '360'
   double maxHeadingVelocity = 0.0;
   double timeToMaxHdgVel = 0.0;
   double headingVelocity = 0.0; // Range: TBD
   double headingAcceleration = 0.0; // Range: TBD
   double headingActual = 0.0; // Range: {0.0, 360.0]
   double headingOverGround = 0.0; // Range: {0.0, 360.0]
   NavigationVector headingSpeedVector = new NavigationVector();
   
   // negative (-) speed is astern, positive (+) speed is ahead
   double speedThrottle = 0.0; // Range: {-(speedMaximum * 0.2), speedMaximum}
   double speedAcceleration = 0.0; // Range: TBD
   double speedActual = 0.0; // Range: {-(speedMaximum * 0.2), speedMaximum}
   double speedOverGround = 0.0; // Range: {-(speedMaximum * 0.2), speedMaximum}
   
   double headingRadians = 0.0;
   double distanceRadians = 0.0;
   double oldLatInRad = 0.0;
   double newLatInRad = 0.0;
   double deltaPhi = 0.0;
   double qValue = 0.0;
   double deltaLon = 0.0;
   double latitude = 0.001; // negative (-) is South, positive (+) is North
   double longitude = -179.999; // negative (-) is West, positive (+) is East
   
   double set = 0.0; // Range: {0.0, 360.0] 
   double drift = 0.0; // Range: {0.0, DRIFT_MAX}
   NavigationVector setDriftVector = new NavigationVector();
   
   OwnshipController ownshipController;
   OwnshipUpdate ownshipUpdate;
   
   /**
    * Creates an OwnshipSimulator object.
    *
    * @param obj an ownship controller
    */
   public OwnshipSimulator (OwnshipController obj, AbstractShip shipObj, DebugManager dbgMgr) {
      if (dbgMgr != null) {
         debugManager = dbgMgr;
      }
      else {
         debugManager = new DebugManager();
         debugManager.setOutputToConsole ();
         debugManager.report (DebugListener.CODE_FAULT,
            "DebugManager object not passed to SerialChannelIO object. ");
      }
      
      if (obj != null) {
         ownshipController = obj;
      }
      else {
         debugManager.report (DebugListener.CODE_FAULT,
            "OwnshipController object not passed to OwnshipSimulator object. ");
      }
      
      if (shipObj != null) {
         simulatedShip = shipObj;
      }
      else {
         simulatedShip = new DefaultShip ();
         debugManager.report (DebugListener.CODE_FAULT,
            "AbstractShip object not passed to OwnshipSimulator object. ");
      }
   
      rudderMaximum = simulatedShip.getRudderAngleMaximum ();
      rudderAcceleration = simulatedShip.getRudderVelocityMaximum ();
      speedMaximumAhead = simulatedShip.getSpeedAheadMaximum ();
      speedMaximumAstern = simulatedShip.getSpeedAsternMaximum ();

      ownshipUpdate = new OwnshipUpdate();
      resetSimulation ();
   }
  
   /**
    * Resets the ownship simulator.
    */
   public void resetSimulation () {
      resetSimulation(DEFAULT_TIME_INCREMENT);
   }
   
   /** 
    * Resets the ownship simulator.
    *
    * @param timeIncrement is the simulation time step in milliseconds.
    */
   public void resetSimulation (long timeIncrement) {
      simulationStatus = SIMULATION_PAUSED;
      simulationStopTime = System.currentTimeMillis ();
      simulationTimeOffset = 0;
      simulationTimer.schedule (this, 0, DEFAULT_TIME_INCREMENT);
   }
    
   /**
    * Required by Timer abstract class, called only by the Timer object.
    */
   public void run () {
      if (simulationStatus != SIMULATION_STOPPED){
         if (simulationStatus == SIMULATION_PLAYING) {
            calcOwnshipUpdate ();
         }
         updateOwnship();
         if (ownshipController != null) {
            ownshipController.updateOwnship(ownshipUpdate);
         }
      }
   }
   
   /**
    * Starts the simulation process.
    */
   public void start() {
      if (simulationStatus == SIMULATION_STOPPED) {
         simulationTimeOffset += System.currentTimeMillis () - simulationStopTime;
      }
      simulationStatus = SIMULATION_PLAYING;
   }
   
   /**
    * Pauses the simulation of ownship movement; however, time continues to increment.
    */
   public void pause () {
      if (simulationStatus == SIMULATION_STOPPED) {
         simulationTimeOffset += System.currentTimeMillis () - simulationStopTime;
      }
      simulationStatus = SIMULATION_PAUSED;
   }
   
   /**
    * Stops both ownship simulated movement and time.
    */
   public void stop () {
      simulationStatus = SIMULATION_STOPPED;
      simulationStopTime = System.currentTimeMillis ();
   }
   
   /**
    * Sets the ownship controller
    *
    * @param obj an OwnshipController object
    */
   public void setOwnshipController (OwnshipController obj) {
      ownshipController = obj;
   }
   
   /**
    * Sets the simulated ordered rudder, or helm.
    *
    * @param rudder angle in degrees, positive(+) values are to the right,
    *  negative(-) values are to the left, values greater than 90 degrees are
    *  ignored.
    */
   public void setRudderHelm (double rudder) {
      if (rudder > RUDDER_MAX) {
         rudderHelm = RUDDER_MAX;
      }
      else if (rudder < -RUDDER_MAX) {
         rudderHelm = -RUDDER_MAX;
      }
      else {
         rudderHelm = rudder;
      }
   }
   
   /** 
    * Sets the simulated orderd speed, or throttle.
    *
    * @param throttle speed in knots, positive(+) values are forward, negative(-)
    *  values are astern, values greater than 50 knots forward and 10 knots astern
    *  are ignored.
    */
   public void setSpeedThrottle (double throttle) {
      if (throttle > SPEED_MAX) {
         speedThrottle = SPEED_MAX;
      }
      else if (throttle < SPEED_MIN) {
         speedThrottle = SPEED_MIN;
      }
      else {
         speedThrottle = throttle;
      }
   }
   
   /**
    * Returns the current ownship data to an external object.
    *
    * @return an OwnshipUpdate object
    */
   public OwnshipUpdate getOwnshipUpdate () {
      return (OwnshipUpdate) ownshipUpdate.clone();
   }
   
   /**
    * Sets the simulated ship's heading and speed.
    *
    * @param newHdgSpd contains the new data for the simulator.  Out of range
    *  heading and speed data are set to possible limits.
    */
   public void setHeadingSpeed (HdgSpdData newHdgSpd) {
      if (newHdgSpd.headingActual >= 360.0) {
         headingActual = 0.0;
      }
      else if (newHdgSpd.headingActual < 0.0) {
         headingActual = 0.0;
      }
      else {
         headingActual = newHdgSpd.headingActual;
      }
      
      if (newHdgSpd.speedActual > SPEED_MAX) {
         speedActual = SPEED_MAX;
      }
      else if (newHdgSpd.speedActual < SPEED_MIN) {
         speedActual = SPEED_MIN;
      }
      else {
         speedThrottle = newHdgSpd.speedActual;
      }
   }
   
   /**
    * Sets the simulated ship's set and drift.
    *
    * @param newDriftData contains the new data for the simulator.  Out of range
    *  heading and speed data are set to possible limits.
    */
   public void setSetDrift (SetDriftData newDriftData) {
      if (newDriftData.set >= 360.0) {
         set = 0.0;
      }
      else if (newDriftData.set < 0.0) {
         set = 0.0;
      }
      else {
         set = newDriftData.set;
      }
      
      if (newDriftData.drift > DRIFT_MAX) {
         drift = 0.0;
      }
      else if (newDriftData.drift < 0.0) {
         drift = 0.0;
      }
      else {
         drift = newDriftData.drift;
      }
   }
   
   /**
    * Sets the simulated ship's position.
    *
    * @param newPosition contains the new data for the simulator. Invalid
    *  position data is ignored.
    */
   public void setPosition (PositionData newPosition) {
      if ((newPosition.latitude <= 90.0) && (newPosition.latitude >= -90.0)){
         latitude = newPosition.latitude;
      }
      if ((newPosition.longitude <= 180.0) && (newPosition.longitude >= -180.0)){
         longitude = newPosition.longitude;
      }
   }

   /**
    * Sets the simulation time and date.
    *
    * @param newTimeDate contains the new data for the simulator.
    */
   public void setTimeDate (Date newTimeDate) {
      simulationTimeOffset = System.currentTimeMillis () - 
         newTimeDate.getTime ();
   }
   
   // Calculates heading acceleration (degrees per second^2)
   private double calcHeadingAcceleration (double newRudder, double newSpeed) {    
      maxHeadingVelocity = 
         simulatedShip.getMaxHeadingVelocity (newSpeed, newRudder);

      double velocityDelta =  
         (maxHeadingVelocity - 
          headingVelocity);
      
      double timeToMaxVelDelta = Math.abs(
         (simulatedShip.getTimeToMaxHdgVel (newSpeed, newRudder) - 
          simulatedShip.getTimeToMaxHdgVel (speedActual, rudderActual)));
        
      if (timeToMaxVelDelta != 0.0) {
         return (velocityDelta / timeToMaxVelDelta);
      }
      else {
         return (velocityDelta / 20.0); // or slowest time to reach max heading acceleration
      }
   }
   
   // Calculates the periodic changes to the simulated ship.
   private void calcOwnshipUpdate () {
      double timeDelta = (double) simulationTimeIncrement/1000.0; // in seconds
      double newRudder;
      double newSpeed;
      
      simulationStep++;

      // Step 1. Calculate rudder position
      // Skip rudder calculation if no movement is needed...
headingAcceleration += calcHeadingAcceleration (rudderActual, speedActual);// * timeDelta;

      
      if (rudderActual != rudderHelm) {
         // Test if change is smaller than simulation time increment...
         if (Math.abs(rudderActual - rudderHelm) < 0.5f ){
             //(rudderAcceleration * timeDelta / 2.0)) {
            rudderActual = rudderHelm;
//            headingAcceleration += calcHeadingAcceleration (rudderActual, speedActual);// * timeDelta;
         }
         else { // Calculate rudder movement...
            // Determine direction of rudder movement and reposition...
            if (rudderActual < rudderHelm) {
               newRudder = rudderActual + rudderAcceleration * timeDelta;
            }
            else { // Rudder is to the right of helm
               newRudder = rudderActual - rudderAcceleration * timeDelta;
            }
//            headingAcceleration += calcHeadingAcceleration (newRudder, speedActual);// * timeDelta;
            rudderActual = newRudder;
         }
      }
      
      // Step 2. Calculate speed acceleration
      // Skip calculation if speed and trottle are matched already
      if (Math.abs(speedActual - speedThrottle) > 0.05f) {
         if (speedActual > 0.0) {
            if (speedThrottle > speedActual) {
               speedAcceleration = 
                  simulatedShip.getFwdFastCeleration (speedActual, speedThrottle);
            }
            else {
               if (speedThrottle < 0.0) {
                  speedAcceleration = 
                     -simulatedShip.getFwdFastCeleration (speedActual, speedThrottle);
               }
               else {
                  speedAcceleration = 
                     -simulatedShip.getFwdSlowCeleration (speedActual, speedThrottle);
               }
            }
         }
         else if (speedActual < 0.0) {
            if (speedThrottle < speedActual) {
               speedAcceleration = 
                  simulatedShip.getAftFastCeleration (speedActual, speedThrottle);
            }
            else {
               if (speedThrottle > 0.0) {
                  speedAcceleration = 
                     -simulatedShip.getAftFastCeleration (speedActual, speedThrottle);
               }
               else {
                  speedAcceleration = 
                     -simulatedShip.getAftSlowCeleration (speedActual, speedThrottle);
               }
            }  
         }
         else { // speedActual == 0.0
            if (speedThrottle > 0.0) {
               speedAcceleration = 
                  simulatedShip.getFwdFastCeleration(speedActual, speedThrottle);
            }
            else if (speedThrottle < 0.0) {
               speedAcceleration = 
                  simulatedShip.getAftFastCeleration(speedActual, speedThrottle); 
            }  
         }
      }
      else {
         speedAcceleration = 0.0;
      }

      // Step 3. Calculate speed velocity
      if (speedAcceleration != 0.0) {
         newSpeed = speedActual + speedAcceleration * timeDelta;
//         headingAcceleration += calcHeadingAcceleration (rudderActual, newSpeed);// * timeDelta;
         speedActual = newSpeed;
      }
      
      // Step 4. Calculate heading velocity
      // Test if change is smaller than simulation time increment...
      if (Math.abs(maxHeadingVelocity - headingVelocity) <=
          Math.abs(headingAcceleration * timeDelta)) {
         headingVelocity = maxHeadingVelocity;
         //headingAcceleration = 0.0;
         if (rudderActual == 0.0) {
            headingVelocity = 0.0;
         }
      }
      else { // Calculate heading velocity
         headingVelocity += headingAcceleration * timeDelta;
      }
      
      // Step 5. Calculate heading
      headingActual += headingVelocity * timeDelta;
      if (headingActual >= 360.0) {
         headingActual -= 360.0;
      }
      else if (headingActual < 0.0) {
         headingActual += 360.0;
      }
      
      // Step 6. Calculate heading velocity influence on speed
      // NOT IMPLEMENTED
      
      // Step 7. Calculate COG and SOG
      if (drift > 0.0) {
         setDriftVector.setDegrees (set);
         setDriftVector.setLength (drift);
         headingSpeedVector.setDegrees (headingActual);
         headingSpeedVector.setLength (speedActual);
         headingSpeedVector.add (setDriftVector);
         headingOverGround = headingSpeedVector.getDegrees ();
         speedOverGround = headingSpeedVector.getLength ();
      }
      else {
         headingOverGround = headingActual;
         speedOverGround = speedActual;
      }

      // Step 8. Calculate position
      if (speedOverGround > 0.0) {
         distanceRadians = speedOverGround * timeDelta * SPEED_TO_RAD;
         headingRadians = headingOverGround * DEG_TO_RAD;
         oldLatInRad = latitude * DEG_TO_RAD; 
         newLatInRad = oldLatInRad + distanceRadians * Math.cos(headingRadians);
   
         deltaPhi = Math.log(Math.tan (newLatInRad / 2.0 + QTR_PI) /
                        Math.tan (oldLatInRad / 2.0 + QTR_PI));
         if (Math.abs(newLatInRad - oldLatInRad) < Double.MIN_VALUE) {
            qValue = Math.cos(newLatInRad);
         }
         else {
            qValue = (oldLatInRad - newLatInRad) / deltaPhi; 
         }
         deltaLon = -distanceRadians * Math.sin(headingRadians)
                    / qValue;
         longitude = (((longitude * DEG_TO_RAD) + deltaLon + PI % TWO_PI) - PI) 
                     / DEG_TO_RAD;
         if (longitude > 180.0) {
            longitude -= 360.0;
         }
         else if (longitude < -180.0) {
            longitude += 360.0;
         }
         latitude = newLatInRad / DEG_TO_RAD;
      }
   }
   
   // Updates data within the ownshipUpdate object.
   private void updateOwnship () {     
      ownshipUpdate.timeDate = new Date (System.currentTimeMillis () 
         - simulationTimeOffset);
      ownshipUpdate.rudderActual = rudderActual;
      ownshipUpdate.headingAcceleration = headingAcceleration;
      ownshipUpdate.headingVelocity = headingVelocity * 60.0; // to deg per min
      ownshipUpdate.headingActual = headingActual;
      ownshipUpdate.headingOverGround = headingOverGround;
      ownshipUpdate.speedAcceleration = speedAcceleration;
      ownshipUpdate.speedActual = speedActual;
      ownshipUpdate.speedOverGround = speedOverGround;
      ownshipUpdate.latitude = latitude;
      ownshipUpdate.longitude = longitude;
      ownshipUpdate.set = set;
      ownshipUpdate.drift = drift;
   }
}
/*
 * Version history:
 *    1.3.0.0 - Added set and drift calculations and correct rate of turn
 *              calculations.
 */
