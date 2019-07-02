/*
 * Position.java
 *
 * Created on June 16, 2005, 6:32 PM
 */

package dashboard.parameters;

/**
 *
 * @author  Stewarts
 */
public class Position {
   
   public long time = 0;
   public double latitude = 0.0;
   public double longitude = 0.0;
   
   public Position (long t, double lat, double lon) {
      time = t;
      latitude = lat;
      longitude = lon;
   }
   
   public Position (double lat, double lon) {
      latitude = lat;
      longitude = lon;
   }
   
}
