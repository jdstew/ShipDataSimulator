/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: WPB110.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.simulator.ships;

/**
 * This class represents a specific ship's turning and tactical characteristics
 * through calculations used by a OwnshipSimultor object.
 *
 * This class currently implements the behavior of a 110' WPB Island Class 
 * Patrol Boat.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class WPB110 extends AbstractShip{
   
   /** Maximum rudder angle in degrees (for VesselSpecs) */
   public static final int RUDDER_ANGLE_MAX = 35;
   /** Rudder angle change rate in degrees per second  (for VesselSpecs) */
   public static final double RUDDER_VELOCTIY_MAX = 6.0;
   /** Maximum forward speed in knots  (for VesselSpecs) */
   public static final int SPEED_AHEAD_MAX = 30;  // knots
   /** Maximum astern speed in knots (for VesselSpecs) */
   public static final int SPEED_ASTERN_MAX = 8;
   
   /**
    * Get the maximum rudder angle for the ship's rudder.
    *
    * @return Angle, in degrees.
    */   
   public int getRudderAngleMaximum () {
      return RUDDER_ANGLE_MAX;
   }
   
   /**
    * Get the maximum, instantaneous velocity of the ship's rudder.
    *
    * @return Velocity, in degrees per second.
    */   
   public double getRudderVelocityMaximum () {
      return RUDDER_VELOCTIY_MAX;
   }
   
   /**
    * Get the maximum ahead speed, in knots, for the ship.
    *
    * @return Velocity.
    */   
   public int getSpeedAheadMaximum () {
      return SPEED_AHEAD_MAX;
   }
   
   /**
    * Get the maximum astern speed, in knots, for the ship.
    *
    * @return Velocity.
    */   
   public int getSpeedAsternMaximum () {
      return SPEED_ASTERN_MAX;
   }
   
   /** 
    * Used in slow deceleration (coasting in a forward direction).
    *
    * @param s0 is speed (in Knots) at t0 (time zero)
    * @param s1 is speed (in Knots) at t1 (time one)
    * @return acceleration (in Knots/second^2)
    */
   public double getFwdSlowCeleration (double s0, double s1) {
      // t = 0.0062s^3 - 0.3933s^2 + 8.5397s - 0.119
      double t0 = 0.0062*s0*s0*s0 - 0.3933*s0*s0 + 8.5397*s0 - 0.119;
      double t1 = 0.0062*s1*s1*s1 - 0.3933*s1*s1 + 8.5397*s1 - 0.119;
      return (s1-s0)/(t1-t0);
   }
   
   /**
    * Used in forward acceleration, when ordered speed is greater than current.
    * Used in fast forward deceleration.
    *
    * @param s0 is speed (in Knots) at t0 (time zero)
    * @param s1 is speed (in Knots) at t1 (time one)
    * @return acceleration (in Knots/second^2)
    */
   public double getFwdFastCeleration (double s0, double s1) {
      // t = 0.9003s + 0.1981
      double t0 = 0.9003*s0 + 0.1981;
      double t1 = 0.9003*s1 + 0.1981;
      return (s1-s0)/(t1-t0);
   }
   
   /**
    * Used in slow deceleration (coasting in a aft direction).
    *
    * @param s0 is speed (in Knots) at t0 (time zero)
    * @param s1 is speed (in Knots) at t1 (time one)
    * @return acceleration (in Knots/second^2)
    */
   public double getAftSlowCeleration (double s0, double s1) {
      // t = -0.1071s^2 - 5.1971s - 1.0171
      double t0 = -0.1071*s0*s0 - 5.1971*s0 - 1.0171;
      double t1 = -0.1071*s1*s1 - 5.1971*s1 - 1.0171;
      return (s1-s0)/(t1-t0);
   }
   
   /**
    * Used in aft acceleration, when ordered speed is greater than current.
    * Used in fast aft deceleration.
    *
    * @param s0 is speed (in Knots) at t0 (time zero)
    * @param s1 is speed (in Knots) at t1 (time one)
    * @return acceleration (in Knots/second^2)
    */
   public double getAftFastCeleration (double s0, double s1) {
      // t = 0.1107s^2 - 0.5043s + 0.0457
      double t0 = 0.1107*s0*s0 - 0.5043*s0 + 0.0457;
      double t1 = 0.1107*s1*s1 - 0.5043*s1 + 0.0457;
      if (s1 > 0.0) {
         return s0/t0;
      }
      else {
         return (s1-s0)/(t1-t0);
      }
   }
   
   /**
    * Used in heading acceleration, using rudder angle and speed.
    * Used in fast aft deceleration.
    *
    * @param speed is ship's speed (in knots)
    * @param rudder is actual rudder angle (in degrees)
    * @return headingAcceleration (in deg/seconds^2)
    */
   public double getMaxHeadingVelocity (double speed, double rudder) {
      double sign = 0.0;

      // composite of sine wave and bell curve (near 20 degrees rudder)
      if (rudder != 0.0) {
         sign = rudder / Math.abs(rudder);
      }
    
      return sign * (double)((speed / 10.0) * 
                    Math.sin (DEG_TO_RAD * Math.abs(rudder)) + 
                    (2.8 * speed) * COEF * 
                    Math.exp (-0.5 * 
                    Math.pow (
                    ((Math.abs(rudder) - 
                    (Math.abs((speed - 30.0) / 2.0) + 20.0)) / 9.0), 2.0)));
   }

   /**
    * Used in heading acceleration, using rudder angle and speed.
    * Used in fast aft deceleration.
    *
    * @param speed is ship's speed (in knots)
    * @param rudder is actual rudder angle (in degrees)
    * @return getTimeToMaxHdgVel (in seconds)
    */
   public double getTimeToMaxHdgVel (double speed, double rudder) {
      if (speed != 0.0) {
         return Math.abs(
                (0.4091 * rudder * rudder - 23.187 * rudder + 412.0 + 3.0 * speed) /
                (speed * 0.56111111111111));
      }
      else {
         // Equal time to coast to a stop at half speed
         return Math.abs(-0.1071 * 25.0 - 5.1971 * 5.0 - 1.0171); 
      }
   }
}
