/*
 * PlotParams.java
 *
 * Created on June 18, 2005, 9:55 PM
 */

package dashboard.parameters;

/**
 *
 * @author  Stewarts
 */
public class PlotParams {
   
   int originX = 675;
   int originY = 325;
   int radius = 315;
   double scale = 1.0;
   
   public int getOriginX () {
      return originX;
   }
   
   public int getOriginY () {
      return originY;
   }
   
   public int getInitialRadius () {
      return radius;
   }
   
   public double getInitialScale () {
      return scale;
   }

}
