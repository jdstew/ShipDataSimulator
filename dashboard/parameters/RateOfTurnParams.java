

package dashboard.parameters;


public class RateOfTurnParams implements GuageInterface {
   float guageMaxValue = 100.0f;
   float guageMinValue = -100.0f;
   
   double guageOverAngle = 306.0; 
   double guageMaxAngle = 300.0;
   double guageMinAngle = 240.0;
   double guageUnderAngle = 234.0;
   
   int originX = 347;
   int originY = 183;
   int radius = 173;
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
