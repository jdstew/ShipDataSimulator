/*
 * DashboardJPanel.java
 *
 * Created on May 25, 2005, 9:56 PM
 */

package dashboard.gui;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;
/**
 *
 * @author  Stewarts
 */
public class DashboardBaseJPanel extends JPanel{

	private static final long serialVersionUID = -1246107412140397919L;
Image dashboard;
   int dashboardWidth;
   int dashboardHeight;
   double dashboardScale;
   
   /** Creates a new instance of DashboardJPanel */
   public DashboardBaseJPanel () {
      setOpaque (false);
      
      URL imgURL = DashboardJFrame.class.getResource ("images/dashboard.gif");

      if (imgURL != null) {
         try {
            dashboard = ImageIO.read(imgURL);
            dashboardWidth = dashboard.getWidth (null);
            dashboardHeight = dashboard.getHeight (null);
         }
         catch (IOException exception) {
            exception.printStackTrace ();
         }
      }
//System.out.println ("dashboardWidth=" + dashboardWidth + 
//                    "dashboardHeight=" + dashboardHeight);
      dashboardScale = 1.0;
   }
   
   public double getNewScale (Dimension newDimension) {

      double widthScale = (double)newDimension.width / (double)dashboardWidth;
      double heightScale = (double)newDimension.height / (double)dashboardHeight;
      
      if (widthScale < heightScale) {
         dashboardScale = widthScale;
      }
      else {
         dashboardScale = heightScale;
      }
      
      setSize(newDimension);
      repaint ();
      
      return dashboardScale;
   }
   
   /** 
    * This method overrides JComponent paintComponent method for changes
    * to the panel.
    *
    * @param graphics Object used by the rendering engine.
    */
   public void paintComponent (Graphics graphics) {
      super.paintComponent (graphics);
      
      Image scaledDashboard = dashboard.getScaledInstance (
         (int) ((double) dashboardWidth * dashboardScale),
         (int) ((double) dashboardHeight * dashboardScale),
	 Image.SCALE_DEFAULT);
      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.drawImage (scaledDashboard, 0, 0, null); 
   }
}
