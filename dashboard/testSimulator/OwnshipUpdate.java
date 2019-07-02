/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: OwnshipUpdate.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.testSimulator;

import java.io.*;
import java.util.*;
/**
 * An object of this class represents ownship related data specific to the
 * simulated ship or received by the input devices.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class OwnshipUpdate implements Cloneable, Serializable {
   
	private static final long serialVersionUID = -7579106832609146135L;

	/** Ownship's current time and date, in UTC */
   public Date  timeDate;
   
   /** Latitude (+90 to -90 degrees), negative (-) is South, positive (+) is North */
   public double latitude;
   /** Longitude (+180 to -180 degrees),  negative (-) is West, positive (+) is East */
   public double longitude;
   
   /** Depth, in meters from transducer. */
   public float depth;
   
   /** Depth range: {-rudderMaximum, rudderMaximum} */
   public double rudderActual;
   
   /** Heading acceleration, in degrees per second^2 */
   public double headingAcceleration;
   /** Heading velocity, in degrees per second - also called Rate of Turn (ROT) */
   public double headingVelocity;
   /** Heading, in degrees True, range: {0.0, 360.0] */
   public double headingActual;
   /** Heading, in degrees True over ground, range: {0.0, 360.0] */
   public double headingOverGround;
   
   /** Speed acceleration, in knots */
   public double speedAcceleration;
   /** Speed, through the water in knots, + forward, - aft */
   public double speedActual;
   /** Speed, over ground in knots, + forward, - aft */
   public double speedOverGround;
   
   /** Set, in degrees true */
   public double set;
   /** Drift, in knots */
   public double drift;
   
   /**
    * Used to duplicate the current state of NavigationUpdate.
    *
    * @return Reference to new NavigationUpdate object.
    */
   public Object clone () {
      try {
         return super.clone();
      }
      catch (CloneNotSupportedException e) {
         return null;
      }
   }
}
