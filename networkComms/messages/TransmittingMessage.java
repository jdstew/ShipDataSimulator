/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: TransmittingSentence.java
 * Created: 2005-01-01, 12:01:01
 */
package networkComms.messages;

import java.util.*;
import java.text.*;
import mdl.data.*;
import networkComms.networkInterface.*;
/**
 * An object of this class represents an actively transmitting sentence, which
 * is managed by a SentenceManager class.
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-01
 */
public class TransmittingMessage extends TimerTask {
   
   int messageID;
   int dataSource;
   long transmitPeriod; // in milliseconds;
   AbstractMessage thisMessage;
   
   TransmittedMessageListener transmittedMessageListener;
   Timer transmitTimer = new Timer();
   
   DecimalFormat freqForm = new DecimalFormat("0.00");
   
   public TransmittingMessage () {
      
   }
   
   /**
    * Creates a new instance of TransmittingSentence.
    *
    * @param formatter The three character formatter for the transmitting sentence (e.g. GGA).
    * @param talkerID The talker ID of this transmitting sentence.
    * @param source The static variable from DataFactory class that indicates the data source.
    * @param version The NMEA version of the transmitting sentence.
    * @param period The period of the transmission (in milliseconds).
    * @param dataFactory The DataFactory object suppling data to transmit.
    */
   public TransmittingMessage (int msgID, int source, long period, DataFactory dataFactory) {
      
      thisMessage = MessageTypes.getMessageObject (msgID);
      
      if (thisMessage != null) {
         messageID = msgID;
      }

      if (dataFactory != null) {
         thisMessage.setDataFactory (dataFactory);
      }
      else {
         System.out.println ("DataFactory not provided for AbstractSentence.");
      }
      
      if ((source == DataFactory.SIMULATOR_DATA_SOURCE) ||
          (source == DataFactory.MANUAL_DATA_SOURCE) ||
          (source == DataFactory.RANDOM_DATA_SOURCE)) {
          dataSource= source;
      }
      else {
         dataSource = DataFactory.RANDOM_DATA_SOURCE;
      }
      
      System.out.println("period="+period);
      if ((period > MessageManager.MIN_TRANSMIT_PERIOD) ||
          (period < MessageManager.MAX_TRANSMIT_PERIOD)) {
          transmitPeriod = MessageManager.DEFAULT_TRANSMIT_PERIOD;
      }
      else {
         transmitPeriod = period;
      }
      
      transmitTimer.schedule (this, 0, transmitPeriod);
   }
   
   /**
    * Adds a listencer to receive transmitting sentences.
    *
    * @param listener The listener to add to the transmitting receiving list.
    */
   public synchronized void addTransmittedMessageListener (TransmittedMessageListener listener)       
         throws TooManyListenersException {
      if (transmittedMessageListener == null) {
         transmittedMessageListener = listener;
      }
      else {
         throw new TooManyListenersException ("Can not set more than one TransmittingMessage listener.");
      }
   }
   
   /**
    * Removes a listener for transmitting sentences.
    *
    * @param listener The listener to be removed.
    */
   public synchronized void removeTransmittedMessageListener (TransmittedMessageListener listener) {
      transmittedMessageListener = null;
   }
   
   /* This method is called by the run() method below for transmitting a sentence. */
   private void transmitMessage (byte [] message) {
      if (transmittedMessageListener != null) {
        transmittedMessageListener.transmitMessage (message);
      }
   }
   
   /**
    * This method is called by the Timer object for this object.
    */
   public void run () {
      if (dataSource == DataFactory.SIMULATOR_DATA_SOURCE) {
         this.transmitMessage (thisMessage.getSimulatorMessage ());
      }
      else if (dataSource == DataFactory.MANUAL_DATA_SOURCE) {
         this.transmitMessage (thisMessage.getManualMessage ());
      }
      else { // dataSource == Random
         this.transmitMessage (thisMessage.getRandomMessage ());
      }
   }
   
   /** Stop the sentence from triggering to transmit. */
   public void stop () {
      this.cancel ();
      try {
         this.finalize();
      }
      catch (Throwable thrown) { /* do nothing */ }
   }
   
   /**
    * The the estimated bandwidth of the transmitting sentence.
    *
    * @return Bandwidth, bits per second.
    */   
   public int getBandwidthBPS() {
      return (int)((1000.0/transmitPeriod) * thisMessage.getEstimatedLength () * 8);
   }
   
   /**
    * Get the formatter name of the transmitting sentence.
    *
    * @return Text representation of the formatter.
    */   
   public String getMessageName () {
      return MessageTypes.getMessageName (messageID);
   }
   
   /**
    * Get the source of the transmitting sentence.
    *
    * @return Manual, Random, Simulator, or Unknown.
    */   
   public String getSource() {
      if (dataSource == DataFactory.MANUAL_DATA_SOURCE) {
         return "Manual";
      }
      else if (dataSource == DataFactory.RANDOM_DATA_SOURCE) {
         return "Random";
      }
      else if (dataSource == DataFactory.SIMULATOR_DATA_SOURCE) {
         return "Simulator";
      }
      else {
         return "Unknown";
      }
   }
   
   /**
    * Get the frequency of the transmitting sentence.
    *
    * @return Text representation of the frequency.
    */   
   public String getFrequency() {
      return freqForm.format (1000.0/transmitPeriod) + 
             "Hz (every " + freqForm.format (transmitPeriod/1000.0) + " seconds)";
   }
}
/*
 * Revision history:
 *
 * 1.0.0.1  Removed the 'listener' object from the object initialization method.
 */