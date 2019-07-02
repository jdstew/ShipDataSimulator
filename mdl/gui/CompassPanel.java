/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: CompassPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;
/**
 * An instance of this class displays the gyro compass card for a
 * vessel simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class CompassPanel extends JPanel {
 
	private static final long serialVersionUID = -8417130276631917409L;
	
	static double DEG_TO_RAD = 0.017453292519943295769236907684886;
   static double RAD_TO_DEG = 57.295779513082320876798154814105;
   static double TWO_PI = 6.283185307179586476925286766559;  
   
   double rotationAngleRAD = 0.0d;
   double currentHeadingRAD = 0.0f;
   Image compassImg;
   
   int [] tickX = {90, 99, 94};
   int [] tickY = { 1,  1,  9};
   
   /**
    * Creates the compass card display.
    */
   public CompassPanel () {
      setBackground(Color.BLACK);

      URL fileLoc = ApplicationFrame.class.getResource("images/compass.gif"); 
      if (fileLoc != null) {
         try {
            compassImg = ImageIO.read(fileLoc);
         }
         catch (IOException exception) {
            exception.printStackTrace ();
         }
      } else {
         System.err.println ("Couldn't find compass icon.");
      }

      setPreferredSize (new Dimension(188, 200));
      setMinimumSize (new Dimension(188, 200));
   }   
   
   /** 
    * This method overrides JComponent paintComponent method for changes
    * to the compass card's orientation.
    *
    * @param graphics Object used by the rendering engine.
    */
   public void paintComponent (Graphics graphics) {
      super.paintComponent (graphics);
      
      Graphics2D tickGraphic = (Graphics2D) graphics;
      tickGraphic.setColor (new Color(192, 0, 0));
      tickGraphic.fillPolygon (tickX, tickY, 3);
      
      Graphics2D compassGraphic = (Graphics2D) graphics;
      compassGraphic.rotate (-currentHeadingRAD, 94, 100);
      compassGraphic.drawImage (compassImg, 4, 10, null); 
   }
   
   /**
    * This method is called by the OwnshipSimulatorPanel for changes to the
    * heading of the simulated vessel.
    *
    * @param newHeading The vessel's heading, in degrees (0-360).
    */
   public void setHeading (double newHeading) {
      rotationAngleRAD = newHeading * DEG_TO_RAD - currentHeadingRAD;
      if (rotationAngleRAD >= Math.PI) {
         rotationAngleRAD -= Math.PI;
      }
      else if (rotationAngleRAD <= -Math.PI) {
         rotationAngleRAD += Math.PI;
      }
      currentHeadingRAD = newHeading * DEG_TO_RAD;
      repaint ();
   }
}
