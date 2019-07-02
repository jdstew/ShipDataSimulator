/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: NavigationVector.java
 * Created: 2005-02-10, 12:01:01
 */
package dashboard.testSimulator;

/**
 * A NavigationVector object is used to add heading and speed vectors for
 * such purposes as calculating course and speed over ground.  This vector 
 * differs from 'standard' geometry due to the fact that courses are measured
 * clockwise and set from a north-up, or X and Y axis transformation.
 *
 * @author Jeff Stewart
 * @version 1.3.0.0, 2005-02-10
 */
public class NavigationVector {
   /** Used to indicate data provided is in degrees (0-359.9). */
   public final static int IN_DEGREES = 1;
   /** Used to indicate data provided is in radians (0-2pi). */
   public final static int IN_RADIANS = 2;
   /** Used to indicate data provided is in XLon/YLat cartesian coordinates. */
   public final static int IN_COORDINATES = 3;
   
   double length;
   double angle;
   
   /**
    * No parameter constructor.
    */
   public NavigationVector () {
      length = 0.0;
      angle = 0.0;
   }
   
   /**
    * Vector type specific constructor.  The behavior of this constructor
    * depends upon the 'vectorUnits' selected, as follows:
    * <pre>
    *
    * vectorUnits     value1  value2
    * --------------  ------  ------
    * IN_DEGREES       length    angle (in degrees)
    * IN_RADIANS       length    angle (in radians)
    * IN_COORDINATES    XLon      YLat
    *
    * </pre>
    *
    * @param value1 First vector value
    * @param value2 Second vector value
    * @param vectorUnits Vector units, see table above
    */
   public NavigationVector (double value1, double value2, int vectorUnits) {
      if (vectorUnits == IN_DEGREES) {
         length = value1;
         angle = Math.toRadians (value2);
      }
      else if (vectorUnits == IN_RADIANS) {
         length = value1;
         angle = value2;
      }
      else if (vectorUnits == IN_COORDINATES) {
         length = Math.sqrt ((value1 * value1) + (value2 * value2));
         if (length != 0.0) {
               angle = Math.acos(value1/length);
               if (value2 < 0) {
                  angle = (2.0 * Math.PI) - angle;
               }
         }
         else {
            angle = 0.0;
         }
      }
      else {
         length = 0.0;
         angle = 0.0;
      }
   }
   
   /**
    * Returns the length of the vector.
    *
    * @return Length of the vector (unitless).
    */
   public double getLength () {
      return length;
   }
      
   /**
    * Sets the length of the vector.
    *
    * @param len Length of the vector (unitless).
    */
   public void setLength (double len) {
      length = len;
   }
   
   /**
    * Returns the YLat-coordinate of the vector.
    *
    * @return YLat-coordinate of the vector (unitless).
    */
   public double getYLat () {
      return length * Math.cos(angle);
   }
   
   /**
    * Returns the XLon-coordinate of the vector.
    *
    * @return XLon-coordinate of the vector (unitless).
    */
   public double getXLon () {
      return length * Math.sin(angle);
   }
      
   /**
    * Sets the XLon and YLat-coordinate of the vector.
    *
    * @param xLon X-axis, or longitude part
    * @param yLat Y-axis, or latitude part
    */
   public void setXLonYLatCoordinates (double xLon, double yLat) {
      length = Math.sqrt ((xLon * xLon) + (yLat * yLat));
      if (length != 0.0) {
         angle = Math.acos(yLat/length);
         if (xLon < 0) { 
            angle = (2.0 * Math.PI) - angle;
         }
      }
      else {
         angle = 0.0;
      }
   }
   
   /**
    * Returns the angle in radians of the vector.
    *
    * @return Angle of the vector in radians (0 to 2PI)
    */
   public double getRadians () {
      return angle;
   }
      
   /**
    * Sets the angle in radians of the vector.
    *
    * @param angleInRadians Angle of the vector in radians (0 to 2PI)
    */
   public void setRadians (double angleInRadians) {
      angle = angleInRadians;
   }
   
   /**
    * Returns the angle in degrees of the vector.
    *
    * @return Angle of the vector in degrees (0 to 360)
    */
   public double getDegrees () {
      return Math.toDegrees(angle);
   }
   
   /**
    * Sets the angle in degrees of the vector.
    *
    * @param angleInDegrees Angle of the vector in degrees (0 to 360)
    */
   public void setDegrees (double angleInDegrees) {
      angle =  Math.toRadians(angleInDegrees);
   }
   
   /**
    * Add a vector to this vector.
    *
    * @param vector Vector to be added.
    */
   public void add (NavigationVector vector) {
      this.setXLonYLatCoordinates (
         this.getXLon () + vector.getXLon (),
         this.getYLat () + vector.getYLat ());
   }
   
   /**
    * Subtract a vector to this vector.
    *
    * @param vector Vector to be subtracted.
    */
   public void subtract (NavigationVector vector) {
      this.setXLonYLatCoordinates (
         this.getXLon () - vector.getXLon (),
         this.getYLat () - vector.getYLat ());
   }

   
//   public static void main (String [] arg){
//      NavigationVector v1 = new NavigationVector();
//      v1.setDegrees (0.0);
//      v1.setLength (2.0);
//      System.out.println ("v1, d=" + v1.getDegrees () + ", l=" + v1.getLength ());
//      
//      NavigationVector v2 = new NavigationVector();
//      
//      double degree;
//      for (degree = 0.0; degree < 360.0; degree++) {
//         v2.setDegrees (degree);
//         v2.setLength (10.0);
//         v2.add (v1);
//         System.out.print("d=" + degree);
//         System.out.print(", v2 d=" + v2.getDegrees ());
//         System.out.println(", l=" + v2.getLength ());
//      }
//   }
}
