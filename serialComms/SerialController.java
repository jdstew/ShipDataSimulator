/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialController.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms;

import javax.comm.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import mdl.data.DataFactory;
import serialComms.sentences.SentenceManager;
import serialComms.serialInterface.SerialControllerUIListener;
import serialComms.serialInterface.SerialInfo;
import serialComms.serialInterface.SerialPortInfo;
import dashboard.DashboardController;
/**
 * An object of this class manages the serial port operations for the 
 * application, including opening and closing serial ports.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SerialController {
  
   DataFactory dataFactory;
   DashboardController dashboardController;
   
   String thisController;
   HashMap<String, SerialPortInfo> serialPorts;
   
   private Vector<SerialControllerUIListener> serialControllerUIListeners = new Vector<SerialControllerUIListener>();

   /**
    * Creates a new instance of SerialChannelController
    *
    * @param factory The data factory used to provide data to the sentence manager.
    * @param dbgMgr The debug manager to report processing errors.
    */
   public SerialController (DataFactory factory, DashboardController dbCntrl) {   
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
      
      thisController = "mdl_" + System.currentTimeMillis ();
      serialPorts = new HashMap<String, SerialPortInfo>(SerialInfo.MAX_SERIAL_PORTS);
      this.pollSerialPorts ();
   } 
   
   /**
    * Add a serial controller listener object, which is generally a GUI object.
    *
    * @param listener The serial controller listener.
    */   
   public synchronized void addSerialControllerListener (SerialControllerUIListener listener) {
      serialControllerUIListeners.addElement(listener);
   }
   
   /**
    * Remove a serial controller listener object, which is generally a GUI object.
    *
    * @param listener The serial controller listener.
    */   
   public synchronized void removeSerialControllerListener (SerialControllerUIListener listener) {
      serialControllerUIListeners.remove(listener);
   }
   
   /* Poll serial ports to build and refresh the list of available serial ports. */
   void pollSerialPorts () {
      Enumeration<?> commPortIdentifiers = CommPortIdentifier.getPortIdentifiers();
      CommPortIdentifier commPortIdentifier;
      String portName;
      int portType;
      
      System.out.println("SerialController polled CommPortIdentifier.");
      
      while (commPortIdentifiers.hasMoreElements ()) {
         commPortIdentifier = (CommPortIdentifier) commPortIdentifiers.nextElement();
         portType = commPortIdentifier.getPortType();
         if (portType == CommPortIdentifier.PORT_SERIAL) {
            portName = commPortIdentifier.getName();
            
            if (!serialPorts.containsKey (portName)) {
               serialPorts.put (portName,
                  new SerialPortInfo (commPortIdentifier, thisController));
            }
         } 
      }
   }
   
   /**
    * Open a serial port.
    *
    * @param newPortData Serial port parameter information.
    * @return The status of whether the port was opened or not.
    */   
   public String openSerialPort (SerialPortInfo newPortData) {
      SerialPortInfo serialPortInfo;
      CommPortIdentifier commPortIdentifier;
      SerialPort serialPort;
      String returnStatus;
      
      if (serialPorts.containsKey (newPortData.getName ())) {
         serialPortInfo = (SerialPortInfo) serialPorts.get (newPortData.getName ());
         if (serialPortInfo.isAvailable()) {
            try {
               commPortIdentifier = serialPortInfo.getCommPortIdentifier();
               serialPort = (SerialPort) commPortIdentifier.open(thisController, SerialInfo.TIMEOUT_DEFAULT);
            }
            catch (PortInUseException error) {
               serialPort = null;
               System.out.println ("SerialController.openSerialPort() failed, PortInUseException thrown.");
               return "open serial port failed - selected serial port is already in use.";
            }
            
               serialPortInfo.setSerialPort (serialPort);
               
               returnStatus = serialPortInfo.setSerialPortParameters (newPortData); 
               
               serialPortInfo.setSentenceManager(
                  new SentenceManager(serialPort, newPortData, dataFactory, 
                     dashboardController.getDashboardChannel ( newPortData.getName () )));
               System.out.println ("Sentence process created.");
               
               for (int i = 0; i < serialControllerUIListeners.size(); i++) {
                 SerialControllerUIListener listener = (SerialControllerUIListener) serialControllerUIListeners.elementAt (i);
                 listener.createSerialPortView(serialPortInfo);
               }
               
               if (returnStatus == null) {
                  return "serial port opened.";
               }
               else {
                  return returnStatus;
               }
         }
         else {
            System.out.println ("SerialController.openSerialPort() failed, isAvailable returned false.");
            return "open serial port failed - selected serial port is not available.";
         }
      }
      else { 
         System.out.println ("Serial port not found in serialPorts within SerialController.");
         return "open serial port failed - unable to find the selected serial port.";
      }
   }
   
   /**
    * Close all serial ports.
    */
   public void closeAllSerialPorts () {
      String [] portList = this.getSerialPortNames ();
      
      for (int i = 0; i < portList.length; i++) {
         this.closeSerialPort (portList[i]);
      }
      System.out.println ("SerialController.closeAllSerialPorts() attempted.");      
   }
   
   /**
    * Close a serial port by port name reference.
    *
    * @param portName Name of the serial port to close (e.g. 'TTYA').
    */   
   public void closeSerialPort (String portName) {
      SerialPortInfo serialPortInfo;
      /* CommPortIdentifier commPortIdentifier; */
      SentenceManager sentenceManager; 
      
      if (serialPorts.containsKey (portName)) {
         serialPortInfo = (SerialPortInfo) serialPorts.get (portName);
         if (thisController.equalsIgnoreCase (serialPortInfo.getControllerName())) {
            System.out.println ("serialPorts in serialController owns [" + portName +"]");
            sentenceManager = serialPortInfo.getSentenceManager();
            if (sentenceManager != null) {
               sentenceManager.closeSerialPort ();
               sentenceManager = null;
            }
            System.out.println ("[" + portName +"] serial sentence process destroyed.");
         }
      }
   }
  
   /**
    * Get an array of serial port names.  Note: the returned list contains all 
    * serial ports, whether they are 'owned' by a Java app or not.
    *
    * @return Serial port names (e.g. 'TTYA' or 'COM1').
    */   
   public String[] getSerialPortNames () {
      Vector<SerialPortInfo> serialPortList = new Vector<SerialPortInfo>(serialPorts.values ());
      String [] serialPortNames = new String[serialPortList.size ()];
      SerialPortInfo listItem;
      
      for (int i = 0; i < serialPortList.size (); i++) {
         listItem = (SerialPortInfo) serialPortList.elementAt (i);
         serialPortNames[i] = listItem.getName ();
      }
      return serialPortNames;
   }
}
