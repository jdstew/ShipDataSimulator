

package dashboard.parameters;

public class SpeedParams implements GuageInterface {
   float guageMaxValue = 30.0f;
   float guageMinValue = 0.0f;
   
   double guageOverAngle = 363.6;
   double guageMaxAngle = 360.0;
   double guageMinAngle = 180.0;
   double guageUnderAngle = 176.4;
   
   int originX = 176;
   int originY = 281;
   int radius = 162;
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
