

package dashboard.parameters;

public class CompassParams implements GuageInterface {
   float guageMaxValue = 360.0f;
   float guageMinValue = 0.0f;
   
   double guageOverAngle = 630.0;
   double guageMaxAngle = 630.0;
   double guageMinAngle = 270.0;
   double guageUnderAngle = 270.0;
   
   int originX = 675;
   int originY = 325;
   int radius = 311;
   double scale = 1.0;
   
   boolean increasesClockwise = true;

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
