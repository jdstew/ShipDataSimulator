/*
 * DashboardJPanel.java
 *
 * Created on May 25, 2005, 9:56 PM
 */

package dashboard.gui;

import java.awt.*;
import javax.swing.*;
import dashboard.parameters.*;
/**
 *
 * @author  Stewarts
 */
public class DashboardGuagesJPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	int dashboardWidth;
   int dashboardHeight;
   double dashboardScale;
   
   DashboardGuage rateOfTurn;
   DashboardGuage speedThroughWater;
   DashboardGuage speedOverGround;
   DashboardGuage depth;
   DashboardGuage courseThroughWater;
   DashboardGuage courseOverGround;
   
   Color dashboardColor;
   
   PositionPlot positionPlot;

   long persistence;
   long samplingPeriod;
   
   String channelName;
   
   /** Creates a new instance of DashboardJPanel */
   public DashboardGuagesJPanel () {
      setOpaque (false);
      
      channelName = "[blank]";
      
      dashboardScale = 1.0;

      rateOfTurn = new DashboardGuage ( new RateOfTurnParams () );
      speedThroughWater = new DashboardGuage ( new SpeedParams () );
      speedOverGround = new DashboardGuage ( new SpeedParams () );
      depth = new DashboardGuage ( new DepthParams () );
      courseThroughWater = new DashboardGuage ( new CompassParams () );
      courseOverGround = new DashboardGuage ( new CompassParams () );
      
      positionPlot = new PositionPlot (new PlotParams());
   }
   
   /** 
    * This method overrides JComponent paintComponent method for changes
    * to the panel.
    *
    * @param graphics Object used by the rendering engine.
    */
   public void paintComponent (Graphics graphics) {
      super.paintComponent (graphics);
      
      float strokewidth = (float) (12.0 * dashboardScale);

      BasicStroke stroke = new BasicStroke(strokewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
      
      float dash[] = {18.0f, 18.0f};
      BasicStroke strokeDashed = new BasicStroke((strokewidth * 0.5f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 
         10.0f, dash, 0.0f);
      
      Graphics2D graphics2D = (Graphics2D) graphics;

      
      graphics2D.setStroke (strokeDashed);
      graphics2D.setColor (courseOverGround.getDialColor ());
      graphics2D.draw(courseOverGround.getDialLine ());
      
      graphics2D.setColor (speedOverGround.getDialColor ());
      graphics2D.draw(speedOverGround.getDialLine ());
      
      graphics2D.setStroke (stroke);
      graphics2D.setColor (rateOfTurn.getDialColor ());
      graphics2D.draw(rateOfTurn.getDialLine ());
      
      graphics2D.setColor (speedThroughWater.getDialColor ());
      graphics2D.draw(speedThroughWater.getDialLine ());
      
      graphics2D.setColor (depth.getDialColor ());
      graphics2D.draw(depth.getDialLine ());
      
      graphics2D.setColor (courseThroughWater.getDialColor ());
      graphics2D.draw(courseThroughWater.getDialLine ());
      
      positionPlot.drawPlot(graphics2D);
      
   }
   
   public void setScale (double newScale, Dimension newDimension) {
      dashboardScale = newScale;
      
      rateOfTurn.setScale (dashboardScale);
      speedThroughWater.setScale (dashboardScale);
      speedOverGround.setScale (dashboardScale);
      depth.setScale (dashboardScale);
      courseThroughWater.setScale (dashboardScale);
      courseOverGround.setScale (dashboardScale);
      positionPlot.setScale (dashboardScale);
      
      setSize (newDimension);
   }
   
   public void setChannelName (String name) {
      if (name != null) {
         channelName = name;
      }
   }
   
   public String getChannelName () {
      return channelName;
   }
   
      
   public Color getColor () {
      return dashboardColor;
   }
   
   public void setColor (Color newColor) {
      if (newColor != null) {
         rateOfTurn.setColor (newColor);
         speedThroughWater.setColor (newColor);
         speedOverGround.setColor (newColor);
         depth.setColor (newColor);
         courseThroughWater.setColor (newColor);
         courseOverGround.setColor (newColor);
         positionPlot.setColor (newColor);
         dashboardColor = newColor;
      }
   }
   
   public void setPersistence (long newPersistenceValue) {
      persistence = newPersistenceValue;
      
      rateOfTurn.setPersistence (persistence);
      speedThroughWater.setPersistence (persistence);
      speedOverGround.setPersistence (persistence);
      depth.setPersistence (persistence);
      courseThroughWater.setPersistence (persistence);
      courseOverGround.setPersistence (persistence);
      positionPlot.setPersistence (persistence);
   }
   
   public void setDataSamplingPeriod (long newPeriod) {
      samplingPeriod = newPeriod;
      positionPlot.setDataSamplingPeriod (samplingPeriod);
   }
   
   public long getDataSamplingPeriod () {
      return samplingPeriod;
   }
   
   public void setRateOfTurn (float rot) {
      rateOfTurn.setValue (rot);
   }
   
   public void setSpeedThroughWater (float stw) {
      speedThroughWater.setValue (stw);
   }
   
   public void setSpeedOverGround (float sog) {
      speedOverGround.setValue (sog);
   }
   
   public void setDepth (float dep) {
      depth.setValue (dep);
   }
   
   public void setCourseThroughWater (float ctw) {
      courseThroughWater.setValue (ctw);
   }
   
   public void setCourseOverGround (float cog) {
      courseOverGround.setValue (cog);
   }   
   
   public void setPlotPosition (double lat, double lon) {
      positionPlot.setPlotPosition (lat, lon);
   }

   public void setPlotPositionBufferCount (int newBufferCount) {
      positionPlot.setPositionBufferCount(newBufferCount);
   }
   
   public void setPlotRadius (double newRadius) {
      positionPlot.setPlotRadius (newRadius);
   }
     
   public void setPlotReferencePosition (Position refPosit) {
      positionPlot.setPlotReferencePosition (refPosit);
   }
}
