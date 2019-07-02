/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SerialChannelIO.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.serialInterface;

import java.io.*;
import java.util.*;
import javax.comm.*;
/**
 * Objects of this class act as the interface between the javax.commm
 * serial port object and the sentence transceiver object.
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class SerialChannelIO implements SerialPortEventListener {

   SerialPort serialPort;
   InputStream inputStream;
   OutputStream outputStream;
   
   boolean transmitEnabled;
   boolean transmitReset;

   private SerialChannelListener serialChannelListener;
   
   /**
    * Creates a new instance of SerialChannel
    *
    * @param port The 'connected' serial port.
    * @param dbgMgr The debug manager for reporting processing errors to.
    */
   public SerialChannelIO (SerialPort port) {      
      if (port != null) {
         serialPort = port;
         
         try {
            inputStream = serialPort.getInputStream();

            try {
               serialPort.addEventListener(this);
            } 
            catch (TooManyListenersException error) {
               System.out.println ("Serial channel listener already set in SerialChannelIO. " +
                  error.toString());
            }

            serialPort.notifyOnDataAvailable(true); 
         } 
         catch (IOException error) {
            System.out.println ("SerialChannelIO unable to set input stream. " + 
               error.toString());
         }

         try {
            outputStream = serialPort.getOutputStream();
         } 
         catch (IOException error) {
            System.out.println ("SerialChannelIO unable to set output stream. " +
               error.toString());
            outputStream = System.out;
         }
      }
      else {
         System.out.println ("SerialPort object not passed to SerialChannelIO object. ");
      }
      
      transmitEnabled = true;
      transmitReset = false;
   }
   
   /**
    * Closes the underlying serial port, then finalizes this object.
    */
   public void closeSerialPort () {
      try {
         try {
            transmitEnabled = false;
            serialPort.close();
         }
         catch (Throwable throwable){ 
            System.out.println ("Closing serial port attempted in SerialChannelIO. " +
               throwable.toString ());
         }
         this.finalize ();
      }
      catch (Throwable throwable){ 
         System.out.println ("Finalizing SerialChannelIO attempted. " +
            throwable.toString ());
      }
   }
   
   /**
    * Adds a serial channel listener object, which is generally a 
    * SentenceTranceiver object.
    *
    * @param listener The serial channel listener object.
    */   
   public synchronized void addSerialChannelListener (SerialChannelListener listener) 
         throws TooManyListenersException {
      if (serialChannelListener == null) {
         serialChannelListener = listener;
      }
      else {
         throw new TooManyListenersException ("Can not set more than one SerialChannelIO listener.");
      }
   }
   
   /**
    * Removes a serial channel listener object, which is generally a 
    * SentenceTranceiver object.
    *
    * @param listener The serial channel listener object.
    */   
   public synchronized void removeSerialChannelListener (SerialChannelListener listener) {
      serialChannelListener = null;
   }

   /**
    * Processes the serial event triggered by the serial port object.
    *
    * @param event A serial port event.
    */   
   public void serialEvent(SerialPortEvent event) {
      if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
         try {
            if (serialChannelListener != null) {
               while (inputStream.available() > 0) {
                  serialChannelListener.receiveCharacter ((char) inputStream.read());
               }
            }
         } 
         catch (IOException error) { 
            System.out.println ("Unable to read character in SerialChannelIO. " +
               error.toString());
         }
      }
   }

   
   /**
    * Transmits a character to the serial port.
    *
    * @param sentence Character to transmit.
    */   
   public void transmitSentence (String sentence) throws IOException {
// loopback test code...
//   for (int i = 0; i < sentence.length (); i++) {
//      serialChannelListener.receiveCharacter (sentence.charAt (i));
//   }
// ...end test code
      
      try {
         if (transmitEnabled) {
            // convert sentence (String) to byte array
            byte byteArray [] = new byte[sentence.length ()];
            for (int i = 0; i < sentence.length (); i++) {
               byteArray[i] = (byte) sentence.charAt (i);
            }
            outputStream.write(byteArray);
         }
      } 
      catch (IOException error) {
         System.out.println ("Unable to write character in SerialChannelIO. " +
            error.toString());
         
         // Attempt to re-link output stream once if it faults.
         try {
            outputStream = serialPort.getOutputStream();
         } 
         catch (IOException resetError) {
            if (!transmitReset) {
               System.out.println ("SerialChannelIO unable reset output stream - " +
                  "output divereted to console." +
                  error.toString());
               outputStream = System.out;
            }
            else { // Force SentenceTransceiver to deal with exception.
               throw resetError;
            }
         }
      }
   }
}
/*
 * Revision history:
 *
 * 1.0.0.1  Changed transmitSentenct() to receive a String value and throw
 *    an IOException if unable to reset the OutputStream() upon error.
 *
 * 1.0.0.2  Created 'transmitEnabled' variable to ensure:
 *    (a) an output stream was open prior to transmitting, and
 *    (b) no transmittions occured after the port was closed.
 */
