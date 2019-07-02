/*
 * DashboardChannel.java
 *
 * Created on May 26, 2005, 9:04 PM
 */

package dashboard;

import java.awt.*;
import dashboard.gui.*;
import dashboard.parameters.*;
/**
 *
 * @author  Stewarts
 */
public class DashboardChannel {
   DashboardController dashboardController;
   DashboardGuagesJPanel panel;
   
   String channelName;
   
   long persistence;
   long samplingPeriod;
   int positionBufferCount;
   double positionRadius;
   
   double currentScale;
   
   boolean isPlotReferenceChannel;
   Position plotReferencePosition;
   
   /** Creates a new instance of DashboardChannel */
   public DashboardChannel (DashboardController controller) {
      if (controller != null) {
         dashboardController = controller;
      }
      
      plotReferencePosition = new Position (0.0, 0.0);
      panel = new DashboardGuagesJPanel ();
   }
   
   public DashboardGuagesJPanel getDashboardGuagesJPanel () {
      return panel;
   }
   
   public void setChannelName (String name) {
      panel.setChannelName (name);
   }
   
   public String getChannelName () {
      return panel.getChannelName ();
   }
   
   public void setColor (Color newColor) {
      if (newColor != null) {
         panel.setColor (newColor);
      }
   }
   
   public void setPersistence (long newPersistenceValue) {
      persistence = newPersistenceValue;
      panel.setPersistence (persistence);
   }
   
   public long getPersistence () {
      return persistence;
   }
   
   public void setDataSamplingPeriod (long newPeriod) {
      samplingPeriod = newPeriod;
      panel.setDataSamplingPeriod (samplingPeriod);
   }
   
   public long getDataSamplingPeriod () {
      return samplingPeriod;
   }
   
   public void setPositionBufferCount (int newBufferCount) {
      positionBufferCount = newBufferCount;
   }
   
   public int getPositionBufferCount () {
      return positionBufferCount;
   }
   
   public double getPositionRadius () {
      return positionRadius;
   }
   
   
   public void setRateOfTurn (float rot) {
      panel.setRateOfTurn (rot);
   }
   
   public void setSpeedThroughWater (float stw) {
      panel.setSpeedThroughWater (stw);
   }
   
   public void setSpeedOverGround (float sog) {
      panel.setSpeedOverGround (sog);
   }
   
   public void setDepth (float dep) {
      panel.setDepth (dep);
   }
   
   public void setCourseThroughWater (float ctw) {
      panel.setCourseThroughWater (ctw);
   }
   
   public void setCourseOverGround (float cog) {
      panel.setCourseOverGround (cog);
   }
   
   public boolean isPlotReferenceChannel () {
      return isPlotReferenceChannel;
   }
   
   public void setPlotPosition (double lat, double lon) {
      panel.setPlotPosition (lat, lon);
      if (isPlotReferenceChannel) {
         plotReferencePosition.latitude = lat;
         plotReferencePosition.longitude = lon;
         dashboardController.setPlotReferencePosition (plotReferencePosition);
      }
   }
   
   public void setPlotPositionBufferCount (int newBufferCount) {
      panel.setPlotPositionBufferCount (newBufferCount);
   }
   
   public void setPlotReferenceChannel (boolean isRefChl) {
      isPlotReferenceChannel = isRefChl;
   }
   
   public void setPlotRadius (double newRadius) {
      panel.setPlotRadius (newRadius);
   }
   
   public void setPlotReferencePosition (Position refPosit) {
      panel.setPlotReferencePosition (refPosit);
   }
}

