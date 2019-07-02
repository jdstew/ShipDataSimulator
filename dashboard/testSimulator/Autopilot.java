/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: Autopilot.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.testSimulator;

/**
 * This class implements an autopilot for the MDL simulator.  The autopilot
 * allows for a simulated ship to steer itself to a set course and speed, which
 * may be a static value, a pattern, or a route.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class Autopilot implements OwnshipUpdateListener {
   /** Used for setting a constant, or manual, autopilot course and speed mode. */
   public static final int MANUAL_MODE = 1; 
   /** Used for setting a pattern mode for the autopilot. */
   public static final int PATTERN_MODE = 2;
   /** Used for setting a route mode for the autopilot. */
   public static final int ROUTE_MODE = 4;
   /** Used for a racetrack pattern of 180 degree turns. */
   public static final int PATTERN_RACETRACK = 0;
   /** Used for an equallateral triangle pattern of 120 degree turns. */
   public static final int PATTERN_TRIANGLE = 1;
   /** Used for a square pattern of 90 degree turns. */
   public static final int PATTERN_SQUARE = 2;
   /** Used for a sector search pattern (three triangles). */
   public static final int PATTERN_SECTOR = 3;
   /** Used for a expanding search pattern (square outward spiral). */
   public static final int PATTERN_EXPANDING_SQUARE = 4;
   /** Directs the pattern to turn left after the first leg. */
   public static final int PATTERN_DIR_LEFT = 0;
   /** Directs the pattern to turn right after the first leg. */
   public static final int PATTERN_DIR_RIGHT = 1;
     
   AbstractShip simulatedShip;
   
   double anticipation = 1.0;
   static long AUTOPILOT_TRIGGER = 10;
   static final double DEG_TO_RAD = 0.017453292519943295769236907684886;
   
   boolean autopilotEngaged = false;
   int autopilotMode = MANUAL_MODE;
   int manualCse = 0;
   int manualSpd = 10;
   int patternType = PATTERN_RACETRACK;
   int patternCse = 0;
   int patternSpd = 10;
   long patternLegTime = 180000;
   int patternTurnDirection = PATTERN_DIR_RIGHT;
   boolean sectorFirstLeg = true;
   boolean sectorOuterLeg = false;
   boolean expSqrEvenSide = false;
   int expSqrSideIncrement = 1;
   long startTime;
   long timeToTurn;
  
   double autopilotHeading = 0.0;
   double autopilotSpeed = 0.0;
   double headingDifference = 0.0;
      
   double rudderMaximum; // in degrees
//   double speedMaximumAhead; // in knots
//   double speedMaximumAstern; // in knots
//   double rudderAcceleration; // degrees/second
   
   OwnshipController ownshipController;
   
   /**
    * Creates an Autopilot object.
    *
    * @param obj the controller class for the Simulator object
    * @param shipObj the ship type currently being simulated
    * @param dbgMgr the debug manager to make reports to
    */
   public Autopilot (OwnshipController obj, AbstractShip shipObj) {
      
      if (shipObj != null) {
         simulatedShip = shipObj;
         rudderMaximum = simulatedShip.getRudderAngleMaximum ();
      }
      
      rudderMaximum = simulatedShip.getRudderAngleMaximum ();
//      rudderAcceleration = simulatedShip.getRudderVelocityMaximum ();
//      speedMaximumAhead = simulatedShip.getSpeedAheadMaximum ();
//      speedMaximumAstern = simulatedShip.getSpeedAsternMaximum ();
   }
   
   /** 
    * Turns the autopilot 'on'.
    *
    * @param engaged 'true' turns the autopilot 'on'
    */
   public void engageAutopilot (boolean engaged) {
      autopilotEngaged = engaged;
      setAutopilotHdgSpd ();
      startTime = System.currentTimeMillis ();
   }
   
   /** 
    * Sets the mode of the autopilot to either manual, pattern, or route.
    *
    * @param value the static mode value. Ignored if invalid value passed.
    */
   public void setAutopilotMode (int value) {
      if ((value == MANUAL_MODE) || 
          (value == PATTERN_MODE) || 
          (value == ROUTE_MODE)) {
         autopilotMode = value;
         setAutopilotHdgSpd ();
      }
   }
   
   /** 
    * Sets the autopilot course for manual mode. Ignored if invalid value passed.
    *
    * @param value the course (000-359) for the autopilot.
    */
   public void setManualCourse (int value) {
      if ((value >= 0) && (value < 360)) {
         manualCse = value;
         if (autopilotEngaged) {
            setAutopilotHdgSpd ();
         }
      }
   }
   
   /**
    * Sets the autopilot speed for the manual mode. Valid range is -10 to +50,
    * ignored otherwise.
    *
    * @param value the speed for the autopilot.
    */
   public void setManualSpeed (int value) {
      if ((value >= -10) && (value < 51)) {
         manualSpd = value;
         if (autopilotEngaged) {
            setAutopilotHdgSpd ();
         }
      }
   }
   
   /*
    * This internal method handles changes to autopilot modes.
    */
   private void setAutopilotHdgSpd () {
      switch (autopilotMode) {
         case MANUAL_MODE: {
            autopilotHeading = (double) manualCse;
            autopilotSpeed = (double) manualSpd;
            return;
         }
         case PATTERN_MODE: {
            autopilotHeading = (double) patternCse;
            autopilotSpeed = (double) patternSpd;
            return;
         }
         case ROUTE_MODE: {
            return;
         }
      }
   }
   
   /** 
    * Sets the pattern type of the autopilot in the pattern mode.
    *
    * @param value the static pattern type. Ignored if invalid value passed.
    */
   public void setPatternType (int value) {
      if ((patternType == PATTERN_RACETRACK) ||
          (patternType == PATTERN_TRIANGLE) ||
          (patternType == PATTERN_SQUARE) ||
          (patternType == PATTERN_SECTOR) ||
          (patternType == PATTERN_EXPANDING_SQUARE)) {
         patternType = value;
         switch (patternType) {
            case PATTERN_SECTOR: {
               sectorFirstLeg = true;
               sectorOuterLeg = false;
               return;
            }
            case PATTERN_EXPANDING_SQUARE: {
               expSqrEvenSide = false;
               expSqrSideIncrement = 1;
               return;
            }
         }
      }
   }
   
   /** 
    * Sets the initial course for pattern mode. Ignored if invalid value passed.
    *
    * @param value the course (000-359) for the autopilot.
    */
   public void setPatternInitialCourse (int value) {
      if ((value >= 0) && (value < 360)) {
         patternCse = value;
      }
   }
   
   /**
    * Sets the autopilot speed for the pattern mode. Valid range is -10 to +50,
    * ignored otherwise.
    *
    * @param value the speed for the autopilot.
    */
   public void setPatternInitialSpeed (int value) {
      if ((value >= -10) && (value < 51)) {
         patternSpd = value;
      }
   }
   
   /** 
    * Sets the autopilot leg length in time for the pattern mode. Values less
    * than '1' are ignored.
    *
    * @param value the pattern leg length time in minutes
    */
   public void setPatternLegTime (int value) {
      if (value > 0) {
         patternLegTime = (long) value * 60000;
      }
   }
   
   /**
    * Sets the autopilot turn direction after the first leg in the pattern mode.
    * Ignored if invalid value passed.
    *
    * @param value pattern turn direction after first leg.
    */
   public void setPatternTurnDirection (int value) {
      if ((value == PATTERN_DIR_LEFT) ||
          (value == PATTERN_DIR_RIGHT)) {
         patternTurnDirection = value;
      }
   }

   /*
    * Internal method to handle course changes.
    */
   private int patternCseChange () {
      switch (patternType) {
         case PATTERN_RACETRACK: {
            startTime = System.currentTimeMillis ();
            return 180;
         }
         case PATTERN_TRIANGLE: {
            startTime = System.currentTimeMillis ();
            if (patternTurnDirection == PATTERN_DIR_LEFT) {
               return -120;
            }
            else {
               return 120;
            }
         }
         case PATTERN_SQUARE: {
            startTime = System.currentTimeMillis ();
            if (patternTurnDirection == PATTERN_DIR_LEFT) {
               return -90;
            }
            else {
               return 90;
            }
         }
         case PATTERN_SECTOR: {
            if (sectorFirstLeg) {
               startTime = System.currentTimeMillis ();
               sectorFirstLeg = false;
               sectorOuterLeg = true;
            }
            else if (sectorOuterLeg) {
               startTime = System.currentTimeMillis () + patternLegTime;
               sectorOuterLeg = false;
            }
            else {
               startTime = System.currentTimeMillis ();
               sectorOuterLeg = true; 
            }
            if (patternTurnDirection == PATTERN_DIR_LEFT) {
               return -120;
            }
            else {
               return 120;
            }
         }
         case PATTERN_EXPANDING_SQUARE: {
            if (expSqrEvenSide) {
               startTime = System.currentTimeMillis () + 
                  ++expSqrSideIncrement * patternLegTime;
               expSqrEvenSide = false;
            }
            else {
               startTime = System.currentTimeMillis () + 
                  expSqrSideIncrement * patternLegTime;
               expSqrEvenSide = true;  
            }
            if (patternTurnDirection == PATTERN_DIR_LEFT) {
               return -90;
            }
            else {
               return 90;
            }     
         }
      }
      return 0;      
   }
   
   /**
    * Called periodically to update a simulator's autopilot rudder and speed.
    * This method 'steers' the ownship to the desired course and speed, and
    * changes the autopilot course based upon its mode of operation.
    *
    * @param ownship the current status of ownship. 
    */
   public void updateOwnship (OwnshipUpdate ownship) {
      if (autopilotEngaged) {
         // 1. Check for pattern and route heading changes
         if (autopilotMode == PATTERN_MODE) {
            timeToTurn = (startTime + patternLegTime) - System.currentTimeMillis ();
            if (timeToTurn <= 0) {
               autopilotHeading += patternCseChange();
               if (autopilotHeading >= 360) {
                  autopilotHeading -= 360;
               }
               else if (autopilotHeading < 0) {
                  autopilotHeading += 360;
               }
            }
         }

         // 2. Compare heading and compute ordered rudder
         headingDifference = (autopilotHeading + 360.0) 
                             - (ownship.headingActual + 360.0);
         if (Math.sin(headingDifference * DEG_TO_RAD) > 0.0) {
            if (Math.abs(headingDifference * anticipation) > rudderMaximum) {
               ownshipController.setAutopilotRudder (rudderMaximum);
            }
            else {
               ownshipController.setAutopilotRudder (anticipation * Math.abs(headingDifference));
            }
         }
         else {
            if (Math.abs(headingDifference * anticipation) > rudderMaximum) {
               ownshipController.setAutopilotRudder (-rudderMaximum);
            }
            else {
               ownshipController.setAutopilotRudder (anticipation * -Math.abs(headingDifference));
            }  
         }

         // 3. Compare speed and compute ordered throttle
         if (ownship.speedActual != autopilotSpeed) {
            ownshipController.setAutopilotSpeed (autopilotSpeed);
         }
      }
   }
}
