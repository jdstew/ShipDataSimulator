/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialPortInfoPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import serialComms.sentences.*;
import serialComms.serialInterface.*;
/**
 * This panel provides a container for displaying open serial ports.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SerialPortInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	SerialControllerPanel serialControllerPanel;
   SerialPortInfo serialPortInfo;
   
   JButton closeSerialChannel;
   
   JTabbedPane tabbedPane;
   
   /**
    * Initializes this panel.
    *
    * @param controller The serial port controller for the appplication.
    * @param portInfo The serial port information to display the status.
    */
   public SerialPortInfoPanel (SerialControllerPanel controller,
                               SerialPortInfo portInfo) {
      
      if (controller != null) {
         serialControllerPanel = controller;
      }
      
      if (portInfo != null) {
         serialPortInfo = portInfo;
      }

      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
      setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));
         
      String statusText = new String ("Port: " + portInfo.getName () + 
         ", baud: " + SerialInfo.getBaudRate (portInfo.getBaudRate ()) + 
         " bps, data bits: " + SerialInfo.getDataBits (portInfo.getDataBits ()) +
         ", parity: " + SerialInfo.getParity (portInfo.getParity ()) +
         ", stop bits: " + SerialInfo.getStopBits(portInfo.getStopBits ()) +
         ", flow control: " + SerialInfo.getFlowControl (portInfo.getFlowControlMode ()));
      JLabel portStatus = new JLabel (statusText);
      portStatus.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(portStatus);
      
      closeSerialChannel = new JButton ("Close This Port");
      closeSerialChannel.setToolTipText ("Close this serial communications port.");
      closeSerialChannel.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               closeSerialPort();
            }
         });
      closeSerialChannel.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(closeSerialChannel);

      tabbedPane = new JTabbedPane();
      
      SentenceManager sentenceManager = serialPortInfo.getSentenceManager ();
      tabbedPane.addTab ("Transmit List", new JScrollPane (new TransmitListPanel(sentenceManager)));
      tabbedPane.addTab ("Transmit Data", new TransmitDataPanel(sentenceManager));
      tabbedPane.addTab ("Receive List", new JScrollPane (new ReceiveListPanel(sentenceManager)));
      tabbedPane.addTab ("Received Data", new ReceiveDataPanel(sentenceManager));
      tabbedPane.addTab ("Receive Errors", new ReceiveErrorPanel(sentenceManager));
      tabbedPane.setAlignmentX (Component.LEFT_ALIGNMENT);
      add(tabbedPane);
   }

   /**
    * Close this serial port.
    */
   public void closeSerialPort () {
      serialControllerPanel.removeSerialPortView (serialPortInfo);
   }
}
