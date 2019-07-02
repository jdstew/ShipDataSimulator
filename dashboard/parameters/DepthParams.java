

package dashboard.parameters;

public class DepthParams implements GuageInterface {
   float guageMaxValue = 200.0f;
   float guageMinValue = 0.0f;
   
   double guageOverAngle = 86.0;//272.1;
   double guageMaxAngle = 90.0;//270.0;
   double guageMinAngle = 180.0;
   double guageUnderAngle = 182.1;
   
   int originX = 323;
   int originY = 315;
   int radius = 311;
   double scale = 1.0;
   
   boolean increasesClockwise = false;
   
   public float getGuageMaxValue () {
      return guageMaxValue;
   }
   
   public float getGuageMinValue () {
      return guageMinValue;
   }

   
   public double getGuageOverAngle () {
      return guageOverAngle;
   }
   
   public double getGuageMaxAngle () {
      return guageMaxAngle;
   }
   
   public double getGuageMinAngle () {
      return guageMinAngle;
   }
   
   public double getGuageUnderAngle () {
      return guageUnderAngle;
   }

   
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
   
   public boolean isIncreaseClockwise () {
      return increasesClockwise;
   }
   
}
