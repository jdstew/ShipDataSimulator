/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceManager.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences;

import java.util.*;
import javax.comm.*;
import serialComms.*;
import serialComms.gui.*;
import serialComms.serialInterface.*;
import serialComms.serialInterface.transceivers.*;
import mdl.data.*;
import dashboard.*;
/**
 * An object of this class manages all received and transmitted sentences
 * on a unique serial port.
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-01
 */
public class SentenceManager extends TimerTask implements ReceivedSentenceListener, SentTransListener {
   
   /** The default received sentence list update rate, in milliseconds. */
   public static final long UPDATE_RECEIVED_DATA_PERIOD = 2700;
  /*  private static final boolean DEBUG_MODE = true; */
   
   Timer receivedDataUpdateTimer = new Timer();
   
   private Vector<ReceivedSentenceListener> receivedSentenceListeners;
   private Vector<SentTransListener> sentTransListeners;
   private Vector<Object> transmitListListeners = new Vector<Object>();
   private Vector<Object> receiveListListeners = new Vector<Object>();
   
   HashMap<Integer, ReceivingSentence> receivedSentencesMap;
   HashMap<Integer, TransmittingSentence> transmittingSentencesMap;
   
   AbstractTransceiver sentenceTransceiver;
   
   DataFactory dataFactory;
   DashboardChannel dashboardChannel;
   
   int bandwidthBPS;
   
   /**
    * Initialize a SentenceManager object.
    *
    * @param serialPort The serial port to communicate on.
    * @param factory The data factory to obtain data from.
    * @param dbgMgr The debug manager to make reports to.
    */   
   public SentenceManager (
         SerialPort serialPort,
         SerialPortInfo serialPortInfo,
         DataFactory factory,
         DashboardChannel dbChnl) {
      
      receivedSentenceListeners = new Vector<ReceivedSentenceListener>();
      sentTransListeners = new Vector<SentTransListener>();
      
      if (serialPort != null) {
         sentenceTransceiver = SerialInfo.getSentenceTransceiver (
            serialPortInfo.getDigitalInterface (), serialPort);
         
         try {
            sentenceTransceiver.addReceivedSentenceListener (this);
         }
         catch (TooManyListenersException err) {
            System.out.println (err.toString ()); 
         }
         
         this.addSentTransListener (sentenceTransceiver);
         bandwidthBPS = serialPort.getBaudRate();
      }
      else {
         bandwidthBPS = 4800;
         System.out.println ("SerialPort object not passed to SentenceManager object. ");
         bandwidthBPS = 4800;
      }

      receivedSentencesMap = new HashMap<Integer, ReceivingSentence>();
      transmittingSentencesMap = new HashMap<Integer, TransmittingSentence>();
      
      this.resetReceivedSentences ();
      this.resetTransmittingSentences ();
      
      if (factory != null) {
         dataFactory = factory;
      }
      else {
         dataFactory = new DataFactory();
         System.out.println ("DataFactory object not passed to SentenceManager object. ");
      }
      
      if (dbChnl != null) {
         dashboardChannel = dbChnl;
      }
      
      receivedDataUpdateTimer.schedule (this, 0, UPDATE_RECEIVED_DATA_PERIOD);
   }
   
   /**
    * Reset (clear) the list of received sentences.
    */
   public void resetReceivedSentences() {
      receivedSentencesMap.clear ();
   }
      
   /** 
    * Reset (clear) the list of tranmitting sentences. 
    */
   public void resetTransmittingSentences() {
      transmittingSentencesMap.clear ();
   }
   
   /**
    * Add a received sentnece listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addReceivedSentenceListener (ReceivedSentenceListener listener) {
      receivedSentenceListeners.addElement(listener);
   }
   
   /**
    * Remove a received sentnece listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeReceivedSentenceListener (ReceivedSentenceListener listener) {
      receivedSentenceListeners.remove(listener);
   }
   
   /**
    * Add a transmitted sentnece listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addSentTransListener (SentTransListener listener) {
      sentTransListeners.addElement(listener);
   }
   
   /**
    * Remove a transmitted sentnece listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeSentTransListener (SentTransListener listener) {
      sentTransListeners.remove(listener);
   }
   
   /**
    * Add a transmit list listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addTransmitListListener (Object listener) {
      transmitListListeners.addElement(listener);
   }
   
   /**
    * Remove a transmit list listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeTransmitListListener (Object listener) {
      transmitListListeners.remove(listener);
   }
   
   /**
    * Add a receive list listener.
    *
    * @param listener The listener to add.
    */   
   public synchronized void addReceiveListListener (Object listener) {
      receiveListListeners.addElement(listener);
   }
   
   /**
    * Remove a receive list listener.
    *
    * @param listener The listener to remove.
    */   
   public synchronized void removeReceiveListListener (Object listener) {
      receiveListListeners.remove(listener);
   }
   
   /**
    * Close the serial port that this SentenceManager is bound to.
    */
   public void closeSerialPort () {
      try {
         this.resetReceivedSentences ();
         this.stopAllTransmitSentences ();
         this.resetTransmittingSentences ();
         if (sentenceTransceiver != null) {
            sentenceTransceiver.closeSerialPort ();
         }
         this.cancel ();
         this.finalize ();
      }
      catch (Throwable throwable){ 
         System.out.println ("Finalizing SentenceManager attempted, but failed. " +
            throwable.toString ());
      }
   }
   
   /**
    * Start the refresh cycle for updating received sentence statistics.
    */
   public void run () {
      // Update receive data listeners
      for (int i = 0; i < receiveListListeners.size(); i++) {
         ReceiveListListener listener = (ReceiveListListener) receiveListListeners.elementAt (i);
         listener.updateList (new Vector<ReceivingSentence>(receivedSentencesMap.values ()));
      }
   }
  
   /**
    * Receive a sentence from a SentenceTransceiver.
    *
    * @param receivedSentence The received sentence object.
    */   
   public void receiveSentence (ReceivedSentence receivedSentence) {
      ReceivingSentence receivingSentence;
      Integer keyValue = new Integer(receivedSentence.formatter);
      if (receivedSentencesMap.containsKey (keyValue)) {
         receivingSentence = (ReceivingSentence) receivedSentencesMap.get (keyValue);
         receivingSentence.receiveSentence (receivedSentence);
      }
      else {
         receivingSentence = new ReceivingSentence (receivedSentence.formatter, dashboardChannel);
         receivingSentence.receiveSentence (receivedSentence);
         receivedSentencesMap.put (keyValue, receivingSentence);
      }
      
      for (int i = 0; i < receivedSentenceListeners.size (); i++) {
         ReceivedSentenceListener listener =
            (ReceivedSentenceListener) receivedSentenceListeners.elementAt (i);
         listener.receiveSentence (receivedSentence);
      }
      
      receivedSentence.setInUse (false);
   }
   
   /**
    * Add a sentence to transmit, referenced by sentence formatter. Note that
    * adding a sentence that is currently transmitting will replace, not
    * duplicate the transmissions.
    *
    * @param formatter The sentence formatter, see SentenceTypes
    * @param talkerID The sentence talker identifier, see SentenceTypes
    * @param source The source of the data to transmit, see DataFactory
    * @param version The version of the sentence to transmit, see Sentence Types
    * @param period The period, in milliseconds, of the transmittions.
    */   
   public void addTransmitSentence(int formatter, 
                                   int talkerID,
                                   int source,
                                   int version,
                                   long period) {
                                      
      int frm = formatter;
      int tlkID = talkerID;
      int ver = version;
      int src;
      long prd;
      int bandwidthDemand;
      TransmittingSentence sentence;
      TransmittingSentence removedSentence;
      String addMessage;
      
      if ((source != DataFactory.MANUAL_DATA_SOURCE) ||
          (source != DataFactory.RANDOM_DATA_SOURCE) ||
          (source != DataFactory.SIMULATOR_DATA_SOURCE)) {
             src = source;
      }
      else {
         src = DataFactory.RANDOM_DATA_SOURCE;
      }
      
      if ((period > DataFactory.TRANSMIT_FREQ_PT05HZ) ||
          (period < DataFactory.TRANSMIT_FREQ_20HZ)) {
          prd = DataFactory.TRANSMIT_FREQ_1HZ;
      }
      else {
         prd = period;
      }
      
      sentence = new TransmittingSentence(frm, tlkID, src, ver, prd, dataFactory);
      try {
         sentence.addSentTransListenerListener(this);
      } 
      catch (TooManyListenersException error) {
         System.out.println ("Serial channel listener already set in SerialChannelIO. " +
            error.toString());
      }
      
      bandwidthDemand = sentence.getBandwidthBPS ();
      
      if (bandwidthDemand < bandwidthBPS) {
         
         bandwidthBPS -= bandwidthDemand;
         
         Integer keyValue = new Integer(frm);
         if (transmittingSentencesMap.containsKey (keyValue)) {
            removedSentence = (TransmittingSentence) transmittingSentencesMap.remove (keyValue);
            bandwidthBPS += removedSentence.getBandwidthBPS ();
            removedSentence.stop();
            addMessage = "New sentence replaced existing sentece.";
         }
         else {
            addMessage = "Ready to add.";
         }
         transmittingSentencesMap.put (keyValue, sentence);
      }
      else {
         sentence.stop ();
         addMessage = "Insufficient bandwidth available to add requested sentence.";
      }
      
      for (int i = 0; i < transmitListListeners.size(); i++) {
        TransmitListListener listener = (TransmitListListener) transmitListListeners.elementAt (i);
        listener.updateList (new Vector<TransmittingSentence>(transmittingSentencesMap.values ()));
        listener.updateBandwidth (bandwidthBPS);
        listener.updateAddMessage (addMessage);
      } 
   }

   /**
    * Remove and stop an individual sentence type from transmitting.
    *
    * @param formatter The sentence to stop, referenced by sentence formatter.
    */   
   public void removeTransmitSentence(int formatter) {
      Integer keyValue = new Integer(formatter);
      if (transmittingSentencesMap.containsKey (keyValue)) {
         transmittingSentencesMap.remove (keyValue);
      }                              
   }
   
   /**
    * Stop all transmitting sentences.
    */
   public void stopAllTransmitSentences () {
      TransmittingSentence removedSentence;
      Vector<TransmittingSentence> transmitList = new Vector<TransmittingSentence>(transmittingSentencesMap.values ());
      for (int i = 0; i < transmitList.size (); i++) {
         removedSentence =  (TransmittingSentence) transmitList.elementAt (i);
         bandwidthBPS += removedSentence.getBandwidthBPS ();
         removedSentence.stop();
      }
      transmittingSentencesMap = new HashMap<Integer, TransmittingSentence>();

      for (int i = 0; i < transmitListListeners.size(); i++) {
        TransmitListListener listener = (TransmitListListener) transmitListListeners.elementAt (i);
        listener.updateList (new Vector<TransmittingSentence>(transmittingSentencesMap.values ()));
        listener.updateBandwidth (bandwidthBPS);
        listener.updateAddMessage (new String ("Transmit list cleared."));
      } 
   }
   
   /**
    * Transmit a sentence.
    *
    * @param sentence The complete sentence to transmit to the serial channel.
    */   
   public synchronized void transmitSentence (String sentence) {
     for (int i = 0; i < sentTransListeners.size(); i++) {
        SentTransListener listener = (SentTransListener) sentTransListeners.elementAt (i);
        listener.transmitSentence (sentence);
     } 
   }
}
/*
 * Revision history:
 *
 * 1.0.0.1  Changed receiveSentence() to receive ReceivedSentence object.
 */
