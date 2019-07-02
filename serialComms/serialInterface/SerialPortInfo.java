/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialPortInfo.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.serialInterface;

import javax.comm.*;
import serialComms.sentences.*;
/**
 * An object of this class represent the data relating to a specific serial
 * port.  It is used to manage the names and the specific controls over a serial
 * port.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SerialPortInfo {
   
   String controller;
   String portName;
   boolean portIsCurrentlyOwned;
   String portOwner;
   
   CommPortIdentifier commPortIdentifier;
   SerialPort serialPort;
   SentenceManager sentenceManager;

   int digitalInterface;
   int baudRate;
   int dataBits;
   int parity;
   int stopBits;
   int flowControl;
   
   /**
    * Instantiates an object of this class with only the port name - use only 
    * valid for program admin, not for data processing.
    *
    * @param name Name of the serial port.
    */   
   public SerialPortInfo (String name) {
      if (name != null) {
         portName = name;
      }
   }
   
   /**
    * Creates a new instance of SerialChannel for processing serial data.
    *
    * @param id The communications port identifier.
    * @param thisController The serial controller of this serial port.
    * @param dbgMgr The debug manager for reporting processing errors.
    */
   public SerialPortInfo (CommPortIdentifier id, String thisController) {
      
      SerialPort testPort;

      if (id != null) {
         commPortIdentifier = id;
      }
      else {
         System.out.println ("CommPortIdentifier missing in SerialPortStatus");
      }
      
      if (thisController != null) {
         controller = thisController;
      }
      else {
         System.out.println ("SerialController ID missing in SerialPortStatus");
      }
      
      baudRate = SerialInfo.BAUDRATE_DEFAULT;
      dataBits = SerialInfo.DATABITS_DEFAULT;
      parity = SerialInfo.PARITY_DEFAULT;
      stopBits = SerialInfo.STOPBITS_DEFAULT;
      flowControl = SerialInfo.FLOWCONTROL_DEFAULT;
      
      portName = commPortIdentifier.getName();
      
      // Test if current JVM owns this port.
      if (commPortIdentifier.isCurrentlyOwned()) {
         if (thisController.equalsIgnoreCase (commPortIdentifier.getCurrentOwner())) {
            // This Java application owns the port.
            portOwner = "This application";
            portIsCurrentlyOwned = true;
         }
         else {
            // Some other Java application owns the port.
            portOwner = thisController;
            portIsCurrentlyOwned = true;
         }
      }
      else { 
         /* Test if a non-Java application owns this port.  This is necessary
          * due to 'isCurrentlyOwned' only returning values regarding the
          * current JVM. */
         try {
            testPort = (SerialPort) commPortIdentifier.open("mdl-poll-test", SerialInfo.TIMEOUT_DEFAULT);
            testPort.close();
            portOwner = "none";
            portIsCurrentlyOwned = false;
         }
         catch (PortInUseException error) { 
            // Port in use by a non-Java application.
            portOwner = "Unknown application";
            portIsCurrentlyOwned = true;
         }
      }
   }

   /**
    * Get the communications port identifier for this serial port.
    *
    * @return The serial port id object.
    */   
   public CommPortIdentifier getCommPortIdentifier() {
      return commPortIdentifier;
   }
   
   /**
    * Get the sentence manager for this serial port.
    *
    * @return SentenceManager object.
    */   
   public SentenceManager getSentenceManager () {
      return sentenceManager;
   }
   
   /**
    * Set the sentence manager for this serial port.
    *
    * @param sentMngr SentenceManager object.
    */   
   public void setSentenceManager (SentenceManager sentMngr) {
      if (sentMngr != null) {
         sentenceManager = sentMngr;
      }
   }
   
   /**
    * Get the serial port controlling application name (if a Java app).
    *
    * @return The serial port controlling application name.
    */   
   public String getControllerName () {
      return commPortIdentifier.getCurrentOwner();
   }

   /**
    * Get the serial port name (e.g. 'TTYA' or 'COM1').
    *
    * @return Serial port name.
    */   
   public String getName () {
      return portName;
   }
   
   /**
    * Get the digital interface for the serial port.
    *
    * @return Digital interface type.
    */   
   public int getDigitalInterface () {
      return digitalInterface;
   }
   
   /**
    * Get the baud rate for the serial port.
    *
    * @return Baud rate in bits per second.
    */   
   public int getBaudRate () {
      return baudRate;
   }
   
   /**
    * Get the data bits for the serial port.
    *
    * @return Data bits, see SerialPortInfo.
    */   
   public int getDataBits () {
      return dataBits;
   }
   
   /**
    * Get the parity of the serial port.
    *
    * @return Parity, see SerialPortInfo.
    */   
   public int getParity () {
      return parity;
   }
   
   /**
    * Get the number of stop bits for the serial port.
    *
    * @return Stop bits, see SerialPortInfo.
    */   
   public int getStopBits () {
      return stopBits;
   }
   
   /**
    * Get the flow control mode of this serial port.
    *
    * @return Flow control, see SerialPortInfo.
    */   
   public int getFlowControlMode () {
      return flowControl;
   }
   
   /**
    * Check the availability of this port.
    *
    * @return True indicates the serial port is avaiable.
    */   
   public boolean isAvailable () {
      return !portIsCurrentlyOwned;
   }
   
   /**
    * Set the serial port.
    *
    * @param port The serial port object to bind to this object.
    */   
   public void setSerialPort (SerialPort port) {
      if (port != null) {
         serialPort = port;
      }
   }

   String bindSerialPort () {
      
      if (serialPort != null) {
         try {
            serialPort.setSerialPortParams (baudRate, dataBits, stopBits, parity);
         } 
         catch (UnsupportedCommOperationException firstError) {
            
            
            try {
               serialPort.setSerialPortParams (SerialInfo.BAUDRATE_DEFAULT, 
                                               SerialInfo.DATABITS_DEFAULT, 
                                               SerialInfo.STOPBITS_DEFAULT, 
                                               SerialInfo.PARITY_DEFAULT);
               baudRate = SerialInfo.BAUDRATE_DEFAULT;
               dataBits = SerialInfo.DATABITS_DEFAULT;
               parity = SerialInfo.PARITY_DEFAULT;
               stopBits = SerialInfo.STOPBITS_DEFAULT;
            } 
            catch (UnsupportedCommOperationException secondError) {
               System.out.println ("setSerialPortParams in SerialPortInfo object failed attempt to set default values. " + 
                  secondError.toString());
               return "setting port parameters failed.";
            }

            
            System.out.println ("setSerialPortParams in SerialPortInfo object failed. " + 
               firstError.toString());
            return "selected port parameters not supported - default values used.";
         }
      }
      return null;
   }
   
   /**
    * Set the serial port parameters.
    *
    * @param serialPortInfo Serial port information.
    * @return The status of whether the parameters were set.
    */   
   public String setSerialPortParameters (SerialPortInfo serialPortInfo) {
      digitalInterface = serialPortInfo.getDigitalInterface ();
      baudRate = serialPortInfo.getBaudRate (); 
      dataBits = serialPortInfo.getDataBits (); 
      parity = serialPortInfo.getParity (); 
      stopBits = serialPortInfo.getStopBits (); 
      flowControl = serialPortInfo.getFlowControlMode ();
      return bindSerialPort();
   }
   
   /**
    * Sets the serial port parameters.
    *
    * @param bd Baud rate.
    * @param db Data bits.
    * @param par Parity.
    * @param sb Stop bits.
    * @param fc Flow control.
    */   
   public void setSerialPortParameters (int ic, int bd, int db, int par, int sb, int fc) {
      digitalInterface = ic;
      baudRate = bd;
      dataBits = db;
      parity = par;
      stopBits = sb;
      flowControl = fc;
   }
}