/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: NetworkPortInfoPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import networkComms.messages.*;
import networkComms.networkInterface.*;
/**
 * This panel provides a container for displaying open network ports.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class NetworkChannelPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	NetworkControllerPanel networkControllerPanel;
   UDPServer server;
   
   JFrame applicationFrame;
   
   JButton closeNetworkChannel;
   
   JTabbedPane tabbedPane;
   
   /**
    * Initializes this panel.
    *
    * @param controller The network port controller for the appplication.
    * @param portInfo The network port information to display the status.
    */
   public NetworkChannelPanel (JFrame parentFrame, NetworkControllerPanel controller, UDPServer udpServer) {
      if (parentFrame != null) {
         applicationFrame = parentFrame;
      }
      else {
         applicationFrame = new JFrame();
      }
      
      if (controller != null) {
         networkControllerPanel = controller;
      }
      
      if (udpServer != null) {
         server = udpServer;
      }

      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));
        
      String statusText = "Local socket: " + server.getLocalIA () + 
         ":" + server.getLocalPort () + ", remote socket: " +
         server.getRemoteIA () + ":" + server.getRemotePort ();
      JLabel portStatus = new JLabel (statusText);
      portStatus.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(portStatus);
      
      closeNetworkChannel = new JButton ("Close This Server");
      closeNetworkChannel.setToolTipText ("Close this network communications port.");
      closeNetworkChannel.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               closeNetworkPort();
            }
         });
      closeNetworkChannel.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(closeNetworkChannel);

      tabbedPane = new JTabbedPane();
      
      MessageManager msgMngr = server.getMessageManager ();
      tabbedPane.addTab ("Transmit List", new JScrollPane (new TransmitListPanel(applicationFrame, msgMngr)));
      tabbedPane.addTab ("Transmit Data", new TransmitDataPanel(msgMngr));
      tabbedPane.addTab ("Receive List", new JScrollPane (new ReceiveListPanel(msgMngr)));
      tabbedPane.addTab ("Received Data", new ReceiveDataPanel(msgMngr));
      tabbedPane.addTab ("Receive Errors", new ReceiveProcessingPanel(msgMngr));
      tabbedPane.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(tabbedPane);
   }

   /**
    * Close this network port.
    */
   public void closeNetworkPort () {
      networkControllerPanel.destroyServerView (this);
      server.closeUDPServer ();
   }
}
