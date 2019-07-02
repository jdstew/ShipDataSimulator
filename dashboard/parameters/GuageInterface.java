/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ParametersInterface.java
 * Created: 2005-01-01, 12:01:01
 */
package dashboard.parameters;

/**
 * This interface defines .
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public interface GuageInterface {   
   public float getGuageMaxValue ();
   public float getGuageMinValue ();
   public double getGuageMaxAngle ();
   public double getGuageMinAngle ();
   public double getGuageOverAngle ();
   public double getGuageUnderAngle ();
   public int getOriginX ();
   public int getOriginY ();
   public int getInitialRadius ();
   public double getInitialScale ();
   public boolean isIncreaseClockwise();
}
