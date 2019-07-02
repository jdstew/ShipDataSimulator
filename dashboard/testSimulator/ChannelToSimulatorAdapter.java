/*
 * ChannelToSimulatorAdapter.java
 *
 * Created on June 16, 2005, 9:15 PM
 */

package dashboard.testSimulator;

import dashboard.*;
/**
 *
 * @author  Stewarts
 */
public class ChannelToSimulatorAdapter implements OwnshipUpdateListener {
   
   DashboardChannel dashboardChannel;
   
   /** Creates a new instance of ChannelToSimulatorAdapter */
   public ChannelToSimulatorAdapter (DashboardChannel channel) {
      if (channel != null) {
         dashboardChannel = channel;
      }
   }
   
   public void updateOwnship (OwnshipUpdate ownshipUpdate) {
      if (dashboardChannel != null) {
         dashboardChannel.setCourseThroughWater ((float)ownshipUpdate.headingActual);
         dashboardChannel.setCourseOverGround ((float)ownshipUpdate.headingOverGround);
         dashboardChannel.setSpeedThroughWater ((float)ownshipUpdate.speedActual);
         dashboardChannel.setSpeedOverGround ((float)ownshipUpdate.speedOverGround);
         dashboardChannel.setRateOfTurn ((float)ownshipUpdate.headingVelocity);
         dashboardChannel.setPlotPosition (ownshipUpdate.latitude, ownshipUpdate.longitude);
      }
   }
}
