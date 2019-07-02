/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialInfo.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.serialInterface;

import javax.comm.*;
import serialComms.serialInterface.transceivers.*;
/**
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-01-01
 */
public class SerialInfo {
   /** Estimated maximum number of serial ports available */
   public static final int MAX_SERIAL_PORTS = 16;
   
   /** NMEA 0180 and NMEA 0182 autopilot interface */
   public static final int NMEA_0182 = 182;
   
   /** NMEA 0183 (IEC 61162-1) digital interface */
   public static final int NMEA_0183 = 183;
   
   /** NMEA 0183-HS (IEC 61162-2) digital interface */
   public static final int NMEA_0183_HS = 18300;
   
   /** RayNav750 Color plotter and remote interface */
   public static final int RAYNAV750 = 750;
   
   /** Default digital interface */
   public static final int INTERFACE_DEFAULT = NMEA_0183;
   
   /** An array of names and values of supported digital interfaces. */
   public static final Object [][] DIGITAL_INTERFACES = {
      {"NMEA 0183 (IEC 61162-1) digital interface", new Integer(NMEA_0183)},
      {"NMEA 0183-HS (IEC 61162-2) digital interface", new Integer(NMEA_0183_HS)},
      {"NMEA 0180 and NMEA 0182 autopilot interface", new Integer(NMEA_0182)},
      {"RayNav750 color plotter and remote interface", new Integer(RAYNAV750)}
   };
   
   /**
    * Get the logical name for the symbolic digital interface value.
    *
    * @param digitalInterface The value of the baud rate.
    * @return The name for the digital interface.
    */   
   public static String getDigitalInterface (int digitalInterface) {
      Object [][] digitalInterfaces = SerialInfo.DIGITAL_INTERFACES;
      
      int indexFound = -1;
      for (int i = 0; i < digitalInterfaces.length; i++) {
         Integer thisValue = (Integer) digitalInterfaces[i][1];
         if (thisValue.intValue () == digitalInterface) {
            indexFound = i;
            break;
         }
      }
      if (indexFound > -1) {
         return (String) digitalInterfaces [indexFound][0];
      }
      else {
         return Integer.toString (digitalInterface);
      }
   }
   
   /**
    * Get a sentence tranceiver based upon a digital interface type.
    *
    * @param digitalInterface From SerialInfo
    * @return A concrete form of AbstractSentence type.
    */
   public static AbstractTransceiver getSentenceTransceiver (int digitalInterface,
      SerialPort serialPort) {
      switch (digitalInterface) {
         case NMEA_0183: {
            return new NMEA0183Transceiver(serialPort);
         }
         case NMEA_0183_HS: {
            return new NMEA0183Transceiver(serialPort);
         }
         case NMEA_0182: {
            return new NMEA0182Transceiver(serialPort);
         }
         case RAYNAV750: {
            return new RayNav750Transceiver(serialPort);
         }
         default: {
            return new NMEA0183Transceiver(serialPort);
         }
      }
   }
   
   /** Default baud rate is 4,800bps. */
   public static final int BAUDRATE_DEFAULT = 4800;
   
   /** Default number of data bits is 8. */
   public static final int DATABITS_DEFAULT = SerialPort.DATABITS_8;
   
   /** Default parity is none. */
   public static final int PARITY_DEFAULT = SerialPort.PARITY_NONE;
   
   /** Default stopbits is 1. */
   public static final int STOPBITS_DEFAULT = SerialPort.STOPBITS_1;
   
   /** Default timeout is 2 seconds. */
   public static final int TIMEOUT_DEFAULT = 2000;
   
   /** Default flow control is none. */
   public static final int FLOWCONTROL_DEFAULT = SerialPort.FLOWCONTROL_NONE;
   
   /** An array of names and values of commonly supported buad rates. */
   public static final Object [][] BUADRATE = {
      {"110", new Integer(110)},
      {"300", new Integer(300)},
      {"1200", new Integer(1200)},
      {"2400", new Integer(2400)},
      {"4800", new Integer(4800)},
      {"9600", new Integer(9600)},
      {"19.2k", new Integer(19200)},
      {"38.4k", new Integer(38400)},
      {"57.6k", new Integer(57600)},
      {"76.8k", new Integer(76800)},
      {"115.2k", new Integer(115200)}
   };
   
   /**
    * Get the logical name for the symbolic baud rate value.
    *
    * @param baudRate The value of the baud rate.
    * @return The name for the baud rate.
    */   
   public static String getBaudRate (int baudRate) {
      Object [][] baudRates = SerialInfo.BUADRATE;
      
      int indexFound = -1;
      for (int i = 0; i < baudRates.length; i++) {
         Integer thisValue = (Integer) baudRates[i][1];
         if (thisValue.intValue () == baudRate) {
            indexFound = i;
            break;
         }
      }
      if (indexFound > -1) {
         return (String) baudRates [indexFound][0];
      }
      else {
         return Integer.toString (baudRate);
      }
   }
   
   /** The name and symbolic value for common data bit configurations. */
   public static final Object[][] DATABITS  = {
      {"5", new Integer(SerialPort.DATABITS_5)},
      {"6", new Integer(SerialPort.DATABITS_6)},
      {"7", new Integer(SerialPort.DATABITS_7)},
      {"8", new Integer(SerialPort.DATABITS_8)}
   };
   
   /**
    * Get the logical name for the symbolic data bits value.
    *
    * @param dataBitsValue The data bits value from this class.
    * @return The name for the data bits value.
    */   
   public static String getDataBits (int dataBitsValue) {
      Object [][] dataBitsList = SerialInfo.DATABITS;
      
      int indexFound = -1;
      for (int i = 0; i < dataBitsList.length; i++) {
         Integer thisValue = (Integer) dataBitsList[i][1];
         if (thisValue.intValue () == dataBitsValue) {
            indexFound = i;
            break;
         }
      }
      if (indexFound > -1) {
         return (String) dataBitsList [indexFound][0];
      }
      else {
         return Integer.toString (dataBitsValue);
      }
   }
   
   /** The name and symbolic value for commonly used parities. */
   public static final Object [][] PARITY = {
      {"none", new Integer(SerialPort.PARITY_NONE)},
      {"odd", new Integer(SerialPort.PARITY_ODD)},
      {"even", new Integer(SerialPort.PARITY_EVEN)},
      {"mark", new Integer(SerialPort.PARITY_MARK)},
      {"space", new Integer(SerialPort.PARITY_SPACE)}
   };
   
   /**
    * Get the logical name for the symbolic parity value.
    *
    * @param parity Parity value from this class.
    * @return The name of the parity.
    */   
   public static String getParity (int parity) {
      Object [][] parityList = SerialInfo.PARITY;
      
      int indexFound = -1;
      for (int i = 0; i < parityList.length; i++) {
         Integer thisValue = (Integer) parityList[i][1];
         if (thisValue.intValue () == parity) {
            indexFound = i;
            break;
         }
      }
      if (indexFound > -1) {
         return (String) parityList [indexFound][0];
      }
      else {
         return "Unknown";
      }
   }
   
   /** The name and symbolic values of commonly used stop bits. */
   public static final Object [][] STOPBITS = {
      {"1", new Integer(SerialPort.STOPBITS_1)},
      {"1.5", new Integer(SerialPort.STOPBITS_1_5)},
      {"2", new Integer(SerialPort.STOPBITS_2)}
   };
   
   /**
    * Get the logical name for the symbolic stop bits value.
    *
    * @param stopBitsValue Stop bits value from this class.
    * @return The name of the stop bits.
    */   
   public static String getStopBits (int stopBitsValue) {
      Object [][] stopBitsList = SerialInfo.STOPBITS;
      
      int indexFound = -1;
      for (int i = 0; i < stopBitsList.length; i++) {
         Integer thisValue = (Integer) stopBitsList[i][1];
         if (thisValue.intValue () == stopBitsValue) {
            indexFound = i;
            break;
         }
      }
      if (indexFound > -1) {
         return (String) stopBitsList [indexFound][0];
      }
      else {
         return Integer.toString (stopBitsValue);
      }
   }
   
   /** The name and symbolic value for common control flow values. */
   public static final Object [][] FLOWCONTROL = {
      {"none", new Integer(SerialPort.FLOWCONTROL_NONE)}
   };
   
   /**
    * Get the logical name for the symbolic flow control value.
    *
    * @param flowCntrlValue Flow control value from this class.
    * @return The name for the flow control.
    */   
   public static String getFlowControl (int flowCntrlValue) {
      Object [][] flowCntrlList = SerialInfo.FLOWCONTROL;
      
      int indexFound = -1;
      for (int i = 0; i < flowCntrlList.length; i++) {
         Integer thisValue = (Integer) flowCntrlList[i][1];
         if (thisValue.intValue () == flowCntrlValue) {
            indexFound = i;
            break;
         }
      }
      if (indexFound > -1) {
         return (String) flowCntrlList [indexFound][0];
      }
      else {
         return "Unknown";
      }
   }
}
/*
 * Version history:
 *
 * 1.1.0.0 - Added static variables for the digital interface types, created
 * array object of interfaces, and a getter method for creating a contrete
 * form of AbstractTranceiver.
 */
