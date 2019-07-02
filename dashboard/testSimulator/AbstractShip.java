/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: AbstractShip.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.testSimulator;

/**
 * This object minimally defines the necessary implementation of a specific
 * simulated ship type.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public abstract class AbstractShip {
   /** Conversion factor for degrees to radians */
   static final double DEG_TO_RAD = 0.0349065850398865915384738153697;
   /** Coefficient used in ship specific modeling */
   static final double COEF = 0.044326920044603630882216228881598;

   public abstract int getRudderAngleMaximum ();
   public abstract double getRudderVelocityMaximum ();
   public abstract int getSpeedAheadMaximum ();
   public abstract int getSpeedAsternMaximum ();
   
   public abstract double getFwdSlowCeleration (double s0, double s1);
   public abstract double getFwdFastCeleration (double s0, double s1);
   public abstract double getAftSlowCeleration (double s0, double s1);
   public abstract double getAftFastCeleration (double s0, double s1);
   public abstract double getMaxHeadingVelocity (double speed, double rudder);
   public abstract double getTimeToMaxHdgVel (double speed, double rudder);
}
