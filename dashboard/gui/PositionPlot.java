/*
 * DashboardGuage.java
 *
 * Created on May 26, 2005, 9:23 PM
 */

package dashboard.gui;


import java.util.*;
import java.awt.*;
import dashboard.parameters.*;
/**
 *
 * @author  Stewarts
 */
public class PositionPlot {
   
   public static final int FADE_TO_WHITE = 1;
   public static final int FADE_TO_BLACK = -1;
   public static final float FADE_TO_DEFAULT = FADE_TO_WHITE;
   static final Color COLOR_DEFAULT = Color.GRAY;
   
   static final double [] X_VALUES = { 0.0, 5.5, -5.5};
   static final double [] Y_VALUES = {-7.0, 4.0,  4.0};
      
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
   long samplingPeriod;
   
   int plotBufferCount;
   double plotRadius; // in degrees
   
   Position referencePosition;
   Vector<Position> positionBuffer;
      
   /** Creates a new instance of DashboardGuage */
   public PositionPlot (PlotParams parameters) {
           
      originX = parameters.getOriginX ();
      originY = parameters.getOriginY ();
      radius = parameters.getInitialRadius ();
      
      scale = parameters.getInitialScale ();
      timeOfLastValue = 0;
      
      setScale (scale);
      setColor (COLOR_DEFAULT);
      fadeToColor = FADE_TO_DEFAULT;
      
      positionBuffer = new Vector<Position> (128, 10);
      timeOfLastValue = 0;
   }
   
   public void setScale (double newScale) {
      scale = newScale;
      scaledX = (float)(newScale * originX);
      scaledY = (float)(newScale * originY);
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
   
   public void setDataSamplingPeriod (long newPeriod) {
      samplingPeriod = newPeriod;
   }
   
   public long getDataSamplingPeriod () {
      return samplingPeriod;
   }
   
   public long getPersistence () {
      return persistence;
   }
   
   public void setPlotPosition (double lat, double lon) {
      long currentTime = System.currentTimeMillis ();
      if ( currentTime > (timeOfLastValue + samplingPeriod)) {
         positionBuffer.add(0, new Position (currentTime, lat, lon));
         timeOfLastValue = currentTime;
      }
   }

   public void setPositionBufferCount (int newBufferCount) {
      plotBufferCount = newBufferCount;
   }
   
   public void setPlotRadius (double newRadius) {
      plotRadius = newRadius;
   }
   
   public void setPlotReferencePosition (Position refPosit) {
      if (refPosit != null) {
         referencePosition = refPosit;
      }
   }
   
   public void drawPlot (Graphics2D graphics2D) {
      if (positionBuffer.size () > plotBufferCount) {
         positionBuffer.setSize (plotBufferCount);
      }
      
      double latDist;
      double lonDist;
      double distToPosition;
      
      float strokewidth = (float) (3.0 * scale);
      BasicStroke stroke = new BasicStroke(strokewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
      graphics2D.setStroke (stroke);
      
      
      Position position;
      for (int i = 0; i < positionBuffer.size (); i++) {
         position = (Position) positionBuffer.get (i);
         
         if ( (position != null) && (referencePosition != null)) {
            
//            if (referencePosition.latitude < 0) {
//               latDist = position.latitude - referencePosition.latitude;
//            }
//            else {
               latDist = referencePosition.latitude - position.latitude;
//            }
            
//            if (referencePosition.longitude < 0) {
//               lonDist = (position.longitude - referencePosition.longitude) /
//                  Math.cos(Math.toRadians (referencePosition.latitude));
//            }
//            else {
               lonDist = (referencePosition.longitude - position.longitude) /
                  Math.cos(Math.toRadians (referencePosition.latitude));
//            }

            distToPosition = Math.sqrt ( (latDist * latDist) + (lonDist * lonDist) );

            if (distToPosition < plotRadius) {
               graphics2D.setColor (this.getPositColor (position.time));
               graphics2D.draw (this.getPositSymbol (latDist, lonDist));
            }
            
         }
      }
   }
   
   private Polygon getPositSymbol (double latDist, double lonDist) {
      double radiusToPlotScale = ((double)radius * scale) / plotRadius;
      
      double symbolX = scaledX - (lonDist * radiusToPlotScale);
      double symbolY = scaledY + (latDist * radiusToPlotScale);
      
      int [] xPoints = new int [3];
      int [] yPoints = new int [3];
      
      for (int i = 0; i < X_VALUES.length; i++) {
         xPoints[i] = (int) (symbolX + (X_VALUES[i] * scale));
         yPoints[i] = (int) (symbolY + (Y_VALUES[i] * scale));
      }
      
      return new Polygon(xPoints, yPoints, xPoints.length);
   }
 
   private Color getPositColor (long time) {
      float red;
      float green;
      float blue;      
      
      float percentOfPersistence = (float) (System.currentTimeMillis () - time) / 
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
