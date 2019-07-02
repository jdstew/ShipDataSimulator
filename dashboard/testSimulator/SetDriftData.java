/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SetDriftData.java
 * Created: 2005-02-15, 12:01:01
 */
package dashboard.testSimulator;

/**
 * An object of this class is used to communicate packaged data between
 * objects.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-15
 */
public class SetDriftData {
   
   /** Set, in degrees True, range: {0.0, 360.0] */
   public double set;
   
   /** Drift, in knots */
   public double drift;
   
   public SetDriftData (double setValue, double driftValue) {
      set = setValue;
      drift = driftValue;
   }
}
