/*
 * DashboardController.java
 *
 * Created on May 25, 2005, 9:07 PM
 */

package dashboard;

import java.awt.*;
import java.util.*;
import dashboard.gui.*;
import dashboard.parameters.*;
/**
 *
 * @author  Stewarts
 */
public class DashboardController extends TimerTask {
   public static final String VERSION = "0.0.1";
   
   public final static long DATA_SAMPLING_PERIOD_MAXIMUM = 30000;
   public final static long DATA_SAMPLING_PERIOD_MINIMUM = 10;
   public final static long DATA_SAMPLING_PERIOD_DEFAULT = 10 * DATA_SAMPLING_PERIOD_MINIMUM;
   
   public final static long PERSISTENCE_MAXIMUM = Long.MAX_VALUE;
   public final static long PERSISTENCE_MINIMUM = 1000; // in milliseconds
   public final static long PERSISTENCE_DEFAULT = 10 * PERSISTENCE_MINIMUM;
   
   public final static int POSITION_BUFFER_MAXIMUM = 256;
   public final static int POSITION_BUFFER_MINIMUM = 1;
   public final static int POSITION_BUFFER_DEFAULT = 10 * POSITION_BUFFER_MINIMUM;
   
   public final static double POSITION_RADIUS_MAXIMUM = 0.016666666667; // in degrees
   public final static double POSITION_RADIUS_MINIMUM = 0.000082508251;
   public final static double POSITION_RADIUS_DEFAULT = 10 * POSITION_RADIUS_MINIMUM;
   
   public final static boolean RUN_AS_STANDALONE_APPLICATION = true;
   
   DashboardJFrame frame;
   
   Vector<DashboardChannel> dashboardChannels;
   long samplingPeriod;
   long persistence;
   int plotPositionBufferCount;
   double plotRadius;
   
   ChannelColors channelColors;
   
   final static long DEFAULT_TIME_INCREMENT = 55;
   Timer updateTimer;
   boolean paused;
   
   DashboardChannel referenceChannel;
   
   /** Creates a new instance of DashboardController */
   public DashboardController (boolean isStandAloneApplication) {
      dashboardChannels = new Vector<DashboardChannel> ();
      persistence = PERSISTENCE_DEFAULT;
      samplingPeriod = DATA_SAMPLING_PERIOD_DEFAULT;
      plotPositionBufferCount = POSITION_BUFFER_DEFAULT;
      plotRadius = POSITION_RADIUS_DEFAULT;
      
      frame = new DashboardJFrame (this, isStandAloneApplication);
      
      channelColors = new ChannelColors ();
      
      updateTimer = new Timer ();
      updateTimer.schedule (this, 0, DEFAULT_TIME_INCREMENT);
      paused = false;
   }
   
   
   public void run () {
      frame.updateDisplay ();
   }
   
   public DashboardChannel getDashboardChannel (String name) {
      DashboardChannel newChannel = new DashboardChannel (this);
      newChannel.setColor ((Color) channelColors.nextElement ());
      newChannel.setPersistence (persistence);
      newChannel.setDataSamplingPeriod (samplingPeriod);
      newChannel.setPlotPositionBufferCount (plotPositionBufferCount);
      newChannel.setPlotRadius (plotRadius);
      newChannel.setChannelName (name);      
      
      dashboardChannels.add (newChannel);
      frame.addChannel (newChannel);
      
      if (referenceChannel == null) {
         referenceChannel = newChannel;
         referenceChannel.setPlotReferenceChannel (true);
      }
      
      return newChannel;
   }
   
   public long setPersistence (long newPersistenceValue) {
      
      if (newPersistenceValue > PERSISTENCE_MAXIMUM) {
         persistence = PERSISTENCE_MAXIMUM;
      }
      else if (newPersistenceValue < PERSISTENCE_MINIMUM) {
         persistence = PERSISTENCE_MINIMUM;
      }
      else {
         persistence = newPersistenceValue;
      }
      
      for (int i = 0; i < dashboardChannels.size (); i++) {
         DashboardChannel dashboardChannel = (DashboardChannel) dashboardChannels.elementAt (i);
         dashboardChannel.setPersistence (persistence);
      }
      
      return persistence;
   }
   
   public long getPersistence () {
      return persistence;
   }
   
   public long setDataSamplingPeriod (long newPeriod) {
      if (newPeriod > DATA_SAMPLING_PERIOD_MAXIMUM) {
         samplingPeriod = DATA_SAMPLING_PERIOD_MAXIMUM;
      }
      else if (newPeriod < DATA_SAMPLING_PERIOD_MINIMUM) {
         samplingPeriod = DATA_SAMPLING_PERIOD_MINIMUM;
      }
      else {
         samplingPeriod = newPeriod;
      }
      
      for (int i = 0; i < dashboardChannels.size (); i++) {
         DashboardChannel dashboardChannel = (DashboardChannel) dashboardChannels.elementAt (i);
         dashboardChannel.setDataSamplingPeriod (samplingPeriod);
      }
      
      return samplingPeriod;
   }
   
   public long getDataSamplingPeriod () {
      return samplingPeriod;
   }
   
   public int setPlotPositionBufferCount (int newBufferCount) {
      
      if (newBufferCount > POSITION_BUFFER_MAXIMUM) {
         plotPositionBufferCount = POSITION_BUFFER_MAXIMUM;
      }
      else if (newBufferCount < POSITION_BUFFER_MINIMUM) {
         plotPositionBufferCount = POSITION_BUFFER_MINIMUM;
      }
      else {
         plotPositionBufferCount = newBufferCount;
      }
      
      for (int i = 0; i < dashboardChannels.size (); i++) {
         DashboardChannel dashboardChannel = (DashboardChannel) dashboardChannels.elementAt (i);
         dashboardChannel.setPlotPositionBufferCount (plotPositionBufferCount);
      }
      
      return plotPositionBufferCount;
   }
   
   public int getPlotPositionBufferCount () {
      return plotPositionBufferCount;
   }
   
   public void setPlotReferenceChannel (DashboardChannel refChl) {
      if (refChl != null) {
         referenceChannel.setPlotReferenceChannel (false);
         refChl.setPlotReferenceChannel (true);
         referenceChannel = refChl;
      }
   }
   
   public double setPlotRadius (double newRadius) {
      
      if (newRadius > POSITION_RADIUS_MAXIMUM) {
         plotRadius = POSITION_RADIUS_MAXIMUM;
      }
      else if (newRadius < POSITION_RADIUS_MINIMUM) {
         plotRadius = POSITION_RADIUS_MINIMUM;
      }
      else {
         plotRadius = newRadius;
      }
      
      for (int i = 0; i < dashboardChannels.size (); i++) {
         DashboardChannel dashboardChannel = (DashboardChannel) dashboardChannels.elementAt (i);
         dashboardChannel.setPlotRadius (plotRadius);
      }
      
      return plotRadius;
   }
   
   public double getPlotRadius () {
      return plotRadius;
   }
   
   public void setPlotReferencePosition (Position refPosit) {
      if (refPosit != null) {
         for (int i = 0; i < dashboardChannels.size (); i++) {
            DashboardChannel dashboardChannel = (DashboardChannel) dashboardChannels.elementAt (i);
            dashboardChannel.setPlotReferencePosition (refPosit);
         }
      }
   }
   
   public void showDashboardJFrame () {
      frame.showDashboardJFrame ();
   }
   
   public void hideDashboardJFrame () {
      frame.hideDashboardJFrame ();
   }
}
