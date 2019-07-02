/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: NetworkControllerPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import java.awt.event.*;
import javax.swing.*;
import networkComms.*;
import networkComms.gui.dialog.*;
import networkComms.networkInterface.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class NetworkControllerPanel extends JPanel implements NetworkControllerUIListener {

	private static final long serialVersionUID = 1L;

	NetworkController networkController;
   
   JFrame applicationFrame;
   
   JButton openNetworkChannel;
//   JButton closeAllNetworkChannels;
   
   JLabel statusLabel;
   
   JTabbedPane tabbedPane;
   
   /**
    * Creates and displays the ownship simulator panel.
    *
    * @param controller The network port controller for this panel.
    */
   public NetworkControllerPanel (JFrame parentFrame, NetworkController controller) {
      if (parentFrame != null) {
         applicationFrame = parentFrame;
      }
      else {
         applicationFrame = new JFrame();
      }
      
      if (controller != null) {
         networkController = controller;
      }

      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));

      Box buttonBox = new Box(BoxLayout.X_AXIS);
      buttonBox.setAlignmentX (JComponent.LEFT_ALIGNMENT);
      openNetworkChannel = new JButton ("Create Server");
      openNetworkChannel.setToolTipText ("Create a new UDP server.");
      openNetworkChannel.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               createNewServer();
            }
         });
      buttonBox.add(openNetworkChannel); 
      
//      buttonBox.add(new JLabel(" "));
      
//      closeAllNetworkChannels = new JButton ("Close All Connections");
//      closeAllNetworkChannels.setToolTipText ("Close all UDP server connections currently open by this application.");
//      closeAllNetworkChannels.addActionListener (new
//         ActionListener () {
//            public void actionPerformed (ActionEvent event) {
//               closeAllNetworkPorts();
//            }
//         });
//      buttonBox.setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));
//      buttonBox.add(closeAllNetworkChannels);
      add(buttonBox);
      
      
      tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
      tabbedPane.setAlignmentX (JComponent.LEFT_ALIGNMENT);
      add(tabbedPane);
      
      Box statusBox = new Box(BoxLayout.X_AXIS);
      statusBox.setAlignmentX (JComponent.LEFT_ALIGNMENT);
      statusBox.add(new JLabel("Status: "));
      statusLabel = new JLabel ("Ready");
      statusBox.add(statusLabel);
      add(statusBox);
   }
 
   /**
    * Open a network port.
    */
   public void createNewServer () { // applicationFrame
      UDPServerParameters params = new UDPServerParameters ();
      
      LocalSocketJPanel localSocketPanel = new LocalSocketJPanel (params);
      params = localSocketPanel.showDialog (applicationFrame);
      if (params == null) {
         return;
      }
      
      RemoteSocketJPanel remoteSocketPanel = new RemoteSocketJPanel (params);
      params = remoteSocketPanel.showDialog (applicationFrame);
      if (params == null) {
         return;
      }
      
      ServerName getServerNamePanel = new ServerName (params);
      params = getServerNamePanel.showDialog (applicationFrame);
      if (params == null) {
         return;
      }
      
      UDPServer newServer = networkController.createUDPServer (
         params.getConnectionName (),
         params.getLocalInternetAddress (),
         params.getLocalPort (),
         params.getRemoteInternetAddress (),
         params.getRemotePort ());
      
      if (newServer != null) {
         createSeverView (newServer);
      }
      
//      JFrame dialogFrame = new JFrame();
//      NetworkPortInfo networkPortInfo;
//      
//      OpenNetworkPortPanel portPanel = new OpenNetworkPortPanel(
//         networkController.getNetworkPortNames ());
//      networkPortInfo = portPanel.showDialog (dialogFrame);
//      if (networkPortInfo != null) {
//         this.setStatus (networkController.openNetworkPort (networkPortInfo));
//      }
//      else {
//         return;
//      }
   }
   
   /**
    * Create a network port view (tabbed display).
    *
    * @param portInfo The network port object to set a display setup.
    */
   public void createSeverView (UDPServer newServer) {
      if (newServer != null) {
         tabbedPane.addTab(
            newServer.getName (), 
            null, 
            new NetworkChannelPanel (applicationFrame, this, newServer),
            (newServer.getName () + " UDP server control and data."));
         this.validateTree ();
      }
   }
   
   public void destroyServerView (NetworkChannelPanel panel) {
      tabbedPane.remove (panel);
   }
   
//   /**
//    * Remove a network port view (tabbed display).
//    *
//    * @param portInfo The network port object to remove from the display.
//    */
//   public void removeNetworkPortView (NetworkPortInfo portInfo) {
//      String portName = portInfo.getName ();
//      int count = tabbedPane.getTabCount ();
//      String tabTitle;
//      for (int i = 0; i < tabbedPane.getTabCount (); i++) {
//         tabTitle = tabbedPane.getTitleAt (i);
//         if (tabTitle.compareToIgnoreCase (portName) == 0) {
//            tabbedPane.remove (i);
//         }
//      }
//      networkController.closeNetworkPort (portName);
//   }
   
//   /**
//    * Close all currently opened network ports for this application.
//    */
//   public void closeAllNetworkPorts () {
//      networkController.closeAllNetworkPorts ();
//      tabbedPane.removeAll ();
//   }
   
//   /** 
//    * Set the status of operations for the network port controller.
//    *
//    * @param status The controller status.
//    */
//   public void setStatus (String status) {
//      if (status != null) {
//         statusLabel.setText (status);
//      }
//      else {
//         statusLabel.setText ("unknown");
//      }
//   }
   
   public void updateStatus (String status) {
      if (status != null) {
         statusLabel.setText (status);
      }
      else {
         statusLabel.setText ("");
      }
   }
   
}
