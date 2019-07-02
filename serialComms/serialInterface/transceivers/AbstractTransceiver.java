/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: AbstractTransceiver.java
 * Created: 2005-02-01, 12:01:01
 */
package serialComms.serialInterface.transceivers;

import java.io.*;
import java.util.*;
import javax.comm.*;
import serialComms.serialInterface.*;
/**
 * This class both receieves and transmits serial data, and performs 
 * common preprocessing of sentence data.
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-01
 */
public abstract class AbstractTransceiver implements SerialChannelListener, SentTransListener {
  
   ReceivedSentenceListener receivedSentenceListener;
   SerialChannelIO serialChannelIO;

   /**
    * Instantiates a data transceiver object.
    *
    * @param serialPort The serial port object.
    * @param dbgMgr The debug manager object for report errors to.
    */   
   public AbstractTransceiver (SerialPort serialPort) {
      
      if (serialPort != null) {
         serialChannelIO = new SerialChannelIO(serialPort);
         try {
            serialChannelIO.addSerialChannelListener (this);
         }
         catch (TooManyListenersException err) {
            System.out.println (err.toString ()); 
         }
      }
      else {
         System.out.println ("SerialPort object not passed to data transceiver object. ");
      }
   }
   
   /**
    * Closes the associated serial port, then finalizes this object.
    */
   public void closeSerialPort () {
      try {
         if (serialChannelIO != null) {
            serialChannelIO.closeSerialPort ();
         }
         this.finalize ();
      }
      catch (Throwable throwable) { 
         System.out.println ("Finalizing SentenceTranceiver attempted." +
            throwable.toString ());
      }
   }
   
   /**
    * Adds a received sentence listener, which should generally be a 
    * SentenceManager object.
    *
    * @param listener The listener of received sentences.
    */   
   public synchronized void addReceivedSentenceListener (ReceivedSentenceListener listener) 
         throws TooManyListenersException {
      if (receivedSentenceListener == null) {
         receivedSentenceListener = listener;
      }
      else {
         throw new TooManyListenersException ("Can not set more than one SerialChannelIO listener.");
      }
   }
   
   /**
    * Removes a received sentence listener, which should generally be a 
    * SentenceManager object.
    *
    * @param listener The listener of received sentences.
    */   
   public synchronized void removeReceivedSentenceListener (ReceivedSentenceListener listener) {
      receivedSentenceListener = null;
   }

   abstract void resetReceiver ();

   public abstract void receiveCharacter (char c);
   
   abstract void transmitToParser ();
   
   /**
    * Transmit a sentence to the serial channel.
    *
    * @param sentence The String object to be transmitted as a series of
    * characters.
    */   
   public void transmitSentence (String sentence) {
      if (serialChannelIO != null) {
         if (sentence != null) {
            try {
               serialChannelIO.transmitSentence(sentence);
            }
            catch (IOException error) {
               System.out.println ("Serial port transmit failure." +
               error.toString());
            }
         }
      }
   }
}
/* 
 * 
 */
