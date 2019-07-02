/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: PositionData.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.data;

/**
 * An object of this class is used to communicate position data between
 * objects.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class PositionData {
   /** Latitude (+90 to -90 degrees), negative (-) is South, positive (+) is North */
   public double latitude;
   
   /** Longitude (+180 to -180 degrees),  negative (-) is West, positive (+) is East */
   public double longitude;
   
   public PositionData (double lat, double lon) {
      latitude = lat;
      longitude = lon;
   }
   
   /**
    * Returns a text representation of the object's values. 
    *
    * @return Values contained by this object.
    */   
   public String toString() {
      return "Latitude = " + latitude + ", longitude = " + longitude + ".";
   }
}
