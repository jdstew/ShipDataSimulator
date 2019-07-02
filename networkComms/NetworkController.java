/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: NetworkController.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms;

import java.util.*;
import java.net.*;
import mdl.data.*;
import dashboard.*;
import networkComms.gui.*;
import networkComms.networkInterface.*;
import networkComms.messages.*;
/**
 * An object of this class manages the network port operations for the 
 * application, including opening and closing network ports.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class NetworkController {
  
   DataFactory dataFactory;
   DashboardController dashboardController;
   
//   HashMap servers;
   
   private Vector<NetworkControllerUIListener> networkControllerUIListeners = new Vector<NetworkControllerUIListener>();

   /**
    * Creates a new instance of NetworkChannelController
    *
    * @param factory The data factory used to provide data to the sentence manager.
    * @param dbgMgr The debug manager to report processing errors.
    */
   public NetworkController (DataFactory factory, DashboardController dbCntrl) {   
      if (factory != null) {
         dataFactory = factory;
      }
      else {
         dataFactory = new DataFactory();
         System.out.println ("DataFactory object not passed to SentenceManager object. ");
      }
      
      if (dbCntrl != null) {
         dashboardController = dbCntrl;
      }
   } 
   
   /**
    * Add a network controller listener object, which is generally a GUI object.
    *
    * @param listener The network controller listener.
    */   
   public synchronized void addNetworkControllerUIListener (NetworkControllerUIListener listener) {
      networkControllerUIListeners.addElement(listener);
   }
   
   /**
    * Remove a network controller listener object, which is generally a GUI object.
    *
    * @param listener The network controller listener.
    */   
   public synchronized void removeNetworkControllerUIListener (NetworkControllerUIListener listener) {
      networkControllerUIListeners.remove(listener);
   }
   
   /* Poll network ports to build and refresh the list of available network ports. */
   void pollUDPServers () {

   }
   
   /**
    * Open a network port.
    *
    * @param newPortData Network port parameter information.
    * @return The status of whether the port was opened or not.
    */   
   public UDPServer createUDPServer (String serverName,
      InetAddress localIP,
      int localPort,
      InetAddress remoteIP,
      int remotePort) {
         
      NetworkOperationStatus status;
      UDPServer newServer = new UDPServer();
      /* String serverReference; */
      
      if (serverName != null) {
         newServer.setName (serverName);
      }
      else {
         newServer.setName ("Unknown");
      }
      
      status = newServer.setLocalSocket (localIP, localPort);
      if (!status.wasSuccessful ()){
         notifyListeners (status.getMessage ());
         return null;
      }
      
      status = newServer.setRemoteSocket (remoteIP, remotePort);
      if (!status.wasSuccessful ()){
         notifyListeners (status.getMessage ());
         return null;
      }
      
      newServer.setMessageManager (new MessageManager (dataFactory, 
         dashboardController.getDashboardChannel ( newServer.getName () ) ));
      newServer.setTransmitting (true);
      newServer.setReceiving (true);
      
      return newServer;
   }
   
   private void notifyListeners (String message) {
      for (int i = 0; i < networkControllerUIListeners.size(); i++) {
        NetworkControllerUIListener listener = (NetworkControllerUIListener) networkControllerUIListeners.elementAt (i);
        listener.updateStatus (message);
     } 
   }

//   /**
//    * Close a network port by port name reference.
//    *
//    * @param portName Name of the network port to close (e.g. 'TTYA').
//    */   
//   public void closeUDPServer () {
//
//   }
//   
//   /**
//    * Close all network ports.
//    */
//   public void closeAllUDPServers () {
//     
//   }  
}
