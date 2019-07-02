/*
 * StartDashboard.java
 *
 * Created on May 25, 2005, 9:03 PM
 */

package dashboard;

import dashboard.testSimulator.*;
/**
 *
 * @author  Stewarts
 */
public class StartDashboard {
   
   public static final String START_COMDAC = "-c";
   public static final String SET_COMDAC_IP = "-addr";
   public static final String SET_COMDAC_PORT = "-port";
   public static final String SET_RADIUS = "-r";
   public static final String SET_PERSISTENCE = "-p";
   public static final String SET_POSITION_BUFFER = "-b";
   public static final String GET_HELP = "/?";
   
   DashboardController controller;
   
   /** Creates a new instance of StartDashboard */
   public StartDashboard () {
      controller = new DashboardController (false);
   }
   
   private void processCommandLine (String[] args) {
      for (int i = 0; i < args.length; i++) {
         if (args[i].equals (SET_RADIUS)) {
            i++;
            setRadius (args[i]);
         }
         else if (args[i].equals (SET_PERSISTENCE)) {
            i++;
            setPersistence (args[i]);
         }
         else if (args[i].equals (SET_POSITION_BUFFER)) {
            i++;
            setPositionBuffer (args[i]);
         }
         else if (args[i].equals (GET_HELP)) {
            printHelpMessage();
         }
         else {
            System.out.println ("Unrecognized command line arguement (" + args[i] + ").");
         }
      }
   }
   
   private void setRadius (String stringRadius) {
      double radius;
      try {
         radius = Double.parseDouble (stringRadius);
         controller.setPlotRadius (radius);
      }
      catch (NumberFormatException exception) {
         System.out.println ("Unable to parse radius value (" + stringRadius + ").");
         System.out.println (exception.toString () + "\n");
      }
   }
   
   private void setPersistence (String stringPersistence) {
      long persistence;
      try {
         persistence = Long.parseLong (stringPersistence);
         controller.setPersistence (persistence);
      }
      catch (NumberFormatException exception) {
         System.out.println ("Unable to parse persistence value (" + stringPersistence + ").");
         System.out.println (exception.toString () + "\n");
      }
   }
   
   private void setPositionBuffer (String stringPositionBuffer) {
      int buffer;
      try {
         buffer = Integer.parseInt (stringPositionBuffer);
         controller.setPlotPositionBufferCount (buffer);
      }
      catch (NumberFormatException exception) {
         System.out.println ("Unable to parse buffer value (" + stringPositionBuffer + ").");
         System.out.println (exception.toString () + "\n");
      }
   }
   
   private void printHelpMessage() {
      System.out.println ("\n" +
         "Usage:   [-c [-addr address] [-port port]] [-r radius] [-p persistence]\n" +
         "         [-b buffer]\n\n" +
         "Options:\n" +
         "   -c              Start Dashboard and connect to COMDAC-INS\n" +
         "   -addr address   Specific IP address to connect to COMDAC-INS NM server\n" +
         "   -port port      Specific Port number to connect to COMDAC-INS NM server\n" +
         "   -r radius       Radius of position plot (in meters)\n" +
         "   -p persistence  Persistence of position plot (in seconds)\n" +
         "   -b buffer       The buffer size (or count) of the position plot\n\n");
   }
   
   /**
    * @param args the command line arguments
    */
   public static void main (String[] args) {
      
      StartDashboard dashboard = new StartDashboard ();
      dashboard.processCommandLine (args);

      dashboard.controller.setPlotRadius (5.0 * DashboardController.POSITION_RADIUS_MINIMUM);
      dashboard.controller.setPlotPositionBufferCount (25);//DashboardController.POSITION_BUFFER_MAXIMUM);
      
      DashboardChannel channel = dashboard.controller.getDashboardChannel ("simulator");
      OwnshipController ownshipController =  new OwnshipController (new WPB110 ());
      ChannelToSimulatorAdapter adapter = new ChannelToSimulatorAdapter (channel);
      ownshipController.addOwnshipUpdateListener (adapter);
      ownshipController.setRudderHelm (30.0);
      ownshipController.setSpeedThrottle (12.0);
      ownshipController.setSetDrift (new SetDriftData (120.0, 3.0));
      ownshipController.start ();
      
      //       DashboardChannel channel2 = dashboard.controller.getDashboardChannel ("simulator2");
      //       OwnshipController ownshipController2 =  new OwnshipController (new WPB110());
      //       ChannelToSimulatorAdapter adapter2 = new ChannelToSimulatorAdapter (channel2);
      //       ownshipController2.addOwnshipUpdateListener (adapter2);
      //       ownshipController2.setRudderHelm (-5.0);
      //       ownshipController2.setSpeedThrottle (23.0);
      //       ownshipController2.setSetDrift (new SetDriftData (180.0, 1.0));
      //       ownshipController2.start ();
   }
}
