/*
 * DashboardGuage.java
 *
 * Created on May 26, 2005, 9:23 PM
 */

package dashboard.gui;


import java.awt.*;
import java.awt.geom.*;
import dashboard.parameters.*;
/**
 *
 * @author  Stewarts
 */
public class DashboardGuage {
   
   public static final int FADE_TO_WHITE = 1;
   public static final int FADE_TO_BLACK = -1;
   public static final float FADE_TO_DEFAULT = FADE_TO_WHITE;
   static final Color COLOR_DEFAULT = Color.GRAY;
   
   float guageMaxValue;
   float guageMinValue;
   double guageMaxAngle;
   double guageMinAngle;
   double guageOverAngle;
   double guageUnderAngle;
   double valueToAngle;
   float valueRangeOffset;
   
   int originX;
   int originY;
   int radius;
   
   double scale;
   float scaledX;
   float scaledY;
   
   boolean isIncreasingClockwise;
   
   float value;
   double valueAngle;
   long timeOfLastValue;
   
   float dialColorRed;
   float dialColorGreen;
   float dialColorBlue;
   float fadeToColor;
      
   long persistence;
   long lastValueUpdate;
      
   /** Creates a new instance of DashboardGuage */
   public DashboardGuage (GuageInterface parameters) {
      
      guageMaxValue = parameters.getGuageMaxValue ();
      guageMinValue = parameters.getGuageMinValue ();
      
      guageOverAngle = Math.toRadians (parameters.getGuageOverAngle ());
      guageMaxAngle = Math.toRadians (parameters.getGuageMaxAngle ());
      guageMinAngle = Math.toRadians (parameters.getGuageMinAngle ());
      guageUnderAngle = Math.toRadians (parameters.getGuageUnderAngle ());
      
      originX = parameters.getOriginX ();
      originY = parameters.getOriginY ();
      radius = parameters.getInitialRadius ();
      
      scale = parameters.getInitialScale ();
      timeOfLastValue = 0;
      
      isIncreasingClockwise = parameters.isIncreaseClockwise ();
           
      double angularRange;
      angularRange = guageMaxAngle - guageMinAngle;
      
      double valueRange;
      valueRange = guageMaxValue - guageMinValue;
      
      if (valueRange != 0.0) {
         valueToAngle = angularRange / valueRange;
      }
      else {
         valueToAngle = Double.POSITIVE_INFINITY;
      }

      if (guageMinValue < 0.0f) {
         valueRangeOffset = -guageMinValue;
      }
      
      setScale (scale);
      setValue (guageMinValue);
      setColor (COLOR_DEFAULT);
      fadeToColor = FADE_TO_DEFAULT;
   }
   
   public void setScale (double newScale) {
      scale = newScale;
   }
   
   public void setValue (float newValue) {
      value = newValue;
      lastValueUpdate = System.currentTimeMillis ();
      
//      System.out.println ("value==>" + value);
      valueAngle = guageMinAngle + 
         ((double) (value + valueRangeOffset)  * valueToAngle);
//      System.out.println ("valueAngleBefore==>" + valueAngle);
//      System.out.println ("guageMinAngle==>" + guageMinAngle);
//      System.out.println ("valueToAngle==>" + valueToAngle);

      if (isIncreasingClockwise) {
         if (valueAngle > guageOverAngle) {
            valueAngle = guageOverAngle;
         }
         else if (valueAngle < guageUnderAngle) {
            valueAngle = guageUnderAngle;
         }
      }
      else {
         if (valueAngle < guageOverAngle) {
            valueAngle = guageOverAngle;
         }
         else if (valueAngle > guageUnderAngle) {
            valueAngle = guageUnderAngle;
         }
      }
//      System.out.println ("valueAngleAfter==>" + valueAngle);
     
//      System.out.println ("valueAngle==>" + valueAngle);
   }
   
   public void setColor (Color newColor) {
      if (newColor != null) {
         dialColorRed =   ( (float)newColor.getRed() / 255.0f);
         dialColorGreen = ( (float)newColor.getGreen () / 255.0f);
         dialColorBlue =  ( (float)newColor.getBlue () / 255.0f);
      }
   }
   
   public Color getInitialColor () {
      return new Color (dialColorRed, dialColorGreen, dialColorBlue, 1.0f);
   }
   
   public void setPersistence (long newPersistenceValue) {
      persistence = newPersistenceValue;
   }
   
   public long getPersistence () {
      return persistence;
   }
   
   public Line2D getDialLine () {
      double x1 = originX * scale;
      double y1 = originY * scale;
      double x2 = (originX + radius * Math.cos (valueAngle)) * scale;
      double y2 = (originY + radius * Math.sin (valueAngle)) * scale;
      return new Line2D.Double ( x1, y1, x2, y2 );
   }
   
   public Color getDialColor () {
      float red;
      float green;
      float blue;      
      
      float percentOfPersistence = (float) (System.currentTimeMillis () - lastValueUpdate) / 
                                   (float) persistence;
      
      if (percentOfPersistence < 1.0f) {
         if (fadeToColor == FADE_TO_WHITE) {
            red = dialColorRed + ((1.0f - dialColorRed) * percentOfPersistence);
            green = dialColorGreen + ((1.0f - dialColorGreen) * percentOfPersistence);
            blue = dialColorBlue + ((1.0f - dialColorBlue) * percentOfPersistence);
            return new Color (red, green, blue, (1.0f - percentOfPersistence));
         }
         else {
            red = dialColorRed * percentOfPersistence;
            green = dialColorGreen * percentOfPersistence;
            blue = dialColorBlue * percentOfPersistence;
            return new Color (red, green, blue, (1.0f - percentOfPersistence));
         }
      }
      else {
         return new Color (0.0f, 0.0f, 0.0f, 0.0f);
      }
   }
}
