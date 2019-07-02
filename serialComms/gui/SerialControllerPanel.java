/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialControllerPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.gui;

import java.awt.event.*;
import javax.swing.*;
import serialComms.*;
import serialComms.gui.dialog.*;
import serialComms.serialInterface.*;
/**
 * An instance of this class presents a GUI for the ownship simulator.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SerialControllerPanel extends JPanel implements SerialControllerUIListener {
  
	private static final long serialVersionUID = 1L;

	SerialController serialController;
   
   JButton openSerialChannel;
   JButton closeAllSerialChannels;
   
   JLabel statusLabel;
   
   JTabbedPane tabbedPane;
   
   /**
    * Creates and displays the ownship simulator panel.
    *
    * @param controller The serial port controller for this panel.
    */
   public SerialControllerPanel (SerialController controller) {
      if (controller != null) {
         serialController = controller;
      }

      setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));

      Box buttonBox = new Box(BoxLayout.X_AXIS);
      buttonBox.setAlignmentX (JComponent.LEFT_ALIGNMENT);
      openSerialChannel = new JButton ("Open a Port");
      openSerialChannel.setToolTipText ("Open a new serial communications port.");
      openSerialChannel.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               openSerialPort();
            }
         });
      buttonBox.add(openSerialChannel); 
      
      buttonBox.add(new JLabel(" "));
      
      closeAllSerialChannels = new JButton ("Close All Ports");
      closeAllSerialChannels.setToolTipText ("Close all serial ports currently open by this MDL application.");
      closeAllSerialChannels.addActionListener (new
         ActionListener () {
            public void actionPerformed (ActionEvent event) {
               closeAllSerialPorts();
            }
         });
      buttonBox.setBorder (BorderFactory.createEmptyBorder (4, 4, 4, 4));
      buttonBox.add(closeAllSerialChannels);
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
    * Open a serial port.
    */
   public void openSerialPort () {
      JFrame dialogFrame = new JFrame();
      SerialPortInfo serialPortInfo;
      
      OpenSerialPortPanel portPanel = new OpenSerialPortPanel(
         serialController.getSerialPortNames ());
      serialPortInfo = portPanel.showDialog (dialogFrame);
      if (serialPortInfo != null) {
         this.setStatus (serialController.openSerialPort (serialPortInfo));
      }
      else {
         return;
      }
   }
   
   /**
    * Create a serial port view (tabbed display).
    *
    * @param portInfo The serial port object to set a display setup.
    */
   public void createSerialPortView (SerialPortInfo portInfo) {
      String portName = portInfo.getName ();
      if (portName != null) {
         tabbedPane.addTab(portName, null, new SerialPortInfoPanel (this, portInfo),
                           (portName + " serial channel control and data."));
         this.validateTree ();
      }
   }
   
   /**
    * Remove a serial port view (tabbed display).
    *
    * @param portInfo The serial port object to remove from the display.
    */
   public void removeSerialPortView (SerialPortInfo portInfo) {
      String portName = portInfo.getName ();
      String tabTitle;
      for (int i = 0; i < tabbedPane.getTabCount (); i++) {
         tabTitle = tabbedPane.getTitleAt (i);
         if (tabTitle.compareToIgnoreCase (portName) == 0) {
            tabbedPane.remove (i);
         }
      }
      serialController.closeSerialPort (portName);
   }
   
   /**
    * Close all currently opened serial ports for this application.
    */
   public void closeAllSerialPorts () {
      serialController.closeAllSerialPorts ();
      tabbedPane.removeAll ();
   }
   
   /** 
    * Set the status of operations for the serial port controller.
    *
    * @param status The controller status.
    */
   public void setStatus (String status) {
      if (status != null) {
         statusLabel.setText (status);
      }
      else {
         statusLabel.setText ("unknown");
      }
   }
}
