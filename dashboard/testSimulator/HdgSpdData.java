/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: HdgSpdData.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.testSimulator;

/**
 * An object of this class is used to communicate packaged data between
 * objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class HdgSpdData {
   
   /** Heading, in degrees True, range: {0.0, 360.0] */
   public double headingActual;
   
   /** Speed, through the water in knots, + forward, - aft */
   public double speedActual;
   
   public HdgSpdData (double heading, double speed) {
      headingActual = heading;
      speedActual = speed;
   }
}
